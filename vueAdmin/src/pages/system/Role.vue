<template>

  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar">
      <el-button type="primary" size="small" class="fa fa-plus" @click="handleAdd"> 新增</el-button>
    </el-col>
    <el-col :span="24">
      <el-table :data="roles" v-loading="roleLoading" style="width: 100%" border>
        <el-table-column prop="name" label="角色名"></el-table-column>
        <el-table-column prop="introduction" label="说明"></el-table-column>
        <el-table-column width="180px;" inline-template label="操作" :context="_self">
          <template>
            <el-button size="mini" icon="edit" @click="handleEdit(row)"></el-button>
            <el-button type="danger" icon="delete" size="mini" @click="handleDel(row)"></el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-col>
    </el-row>

    <!-- 编辑框 -->
    <el-dialog :title="dialogFormTitle" v-model="dialogFormVisible">
      <el-form v-show="dialogFormTitle == '编辑' || dialogFormTitle == '新增'" :model="role" label-width="80px">
        <el-form-item label="名称">
          <el-input v-model="role.name"></el-input>
        </el-form-item>
        <el-form-item label="说明">
          <el-input type="textarea" :rows="2" v-model="role.introduction">
          </el-input>
        </el-form-item>
        <el-form-item label="角色资源">
          <el-tree
            ref="resourceTree"
            :data="resources"
            :props="props"
            node-key="id"
            show-checkbox>
          </el-tree>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click.native="editSubmit" :loading="btnLoading">确 定</el-button>
      </div>
    </el-dialog>
  </section>
</template>
<style>

</style>
<script>
  export default{
    data(){
      return {
        roles: [],
        role: {
          id: '',
          name: '',
          introduction: '',
          resourceLeafIds: [],
        },
        dialogFormVisible: false,
        dialogFormTitle: '',
        btnLoading: false,
        resources: [],
        props: {
          label: 'name',
          children: 'children'
        },
        roleLoading: false,
      }
    },
    created: function () {
      //获取所有角色
      this.loadAll();
      //获取所有资源
      this.loadAllResources();
    },
    methods: {
      handleAdd(){
        this.dialogFormVisible = true;
        this.dialogFormTitle = '新增';
        this.role.id = null;
        this.role.name = '';
        this.role.introduction = '';
        this.$refs.resourceTree.setCheckedKeys([]);
      },

      handleEdit(row) {
        this.dialogFormVisible = true;
        this.dialogFormTitle = '编辑';
        this.role.id = row.id;
        this.role.name = row.name;
        this.role.introduction = row.introduction;
        this.$refs.resourceTree.setCheckedKeys(row.resourceLeafIds);
      },

      handleDel(row) {
        this.$confirm('确认删除吗?', '提示', {
          type: 'warning'
        }).then(() => {
          this.$http.get('system/role/delete?id=' + row.id)
            .then((response) => {
              this.loadAll();
            });
        });
      },

      editSubmit(){
        this.$confirm('确认提交吗？', '提示', {}).then(() => {
          this.btnLoading = true;
          this.role.resourceLeafIds = this.$refs.resourceTree.getCheckedKeys();
          if (this.role.id == null) {
            this.$http.post('system/role/add', this.role)
              .then((response) => {
                this.dialogFormVisible = false;
                this.loadAll();
                this.$message.success('提交成功');
              });
          } else {
            this.$http.post('system/role/update', this.role)
              .then((response) => {
                this.dialogFormVisible = false;
                this.loadAll();
                this.$message.success('更新成功');
              });
          }
          this.btnLoading = false;
        })
      },

      loadAll() {
        this.roleLoading = true;
        this.$http.get('system/role/list')
          .then((response) => {
            this.roles = response.data.data;
            this.roleLoading = false;
          });
      },
      loadAllResources(){
        this.$http.get('system/resource/tree')
          .then((response) => {
            this.resources = response.data.data;
          });
      }

    },
  }
</script>
