<template>
  <section>
    <el-col :span="24" class="toolbar">
      <el-button type="primary" @click="openAddDialog" size="small" class="fa fa-plus"> 新增</el-button>
    </el-col>
    <el-col :span="24">
      <el-table
        :data="resources"
        border
        highlight-current-row
        style="width: 100%">
        <el-table-column
          prop="name"
          width="180"
          label="名称">
          <template scope="scope">
            <el-button @click="operateNode(scope.row);" type="text"
                       :class="scope.row.url === null ? 'resource-one' : ( scope.row.type === 0 ? 'resource-two' : 'resource-three')"
                       :icon="scope.row.type === 0 ? (hasElement(scope.row.id) ? 'caret-bottom' : 'caret-right') : 'time'">
              {{scope.row.name}}
            </el-button>
          </template>
        </el-table-column>
        <el-table-column
          prop="url"
          width="200"
          label="URL">
        </el-table-column>
        <el-table-column
          width="80"
          prop="type"
          label="类型">
          <template scope="scope">
            <div slot="reference" class="name-wrapper">
              <el-tag v-if="scope.row.type == 0" color="#58B7FF">菜单</el-tag>
              <el-tag v-if="scope.row.type == 1">按钮</el-tag>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          prop="hidden"
          width="65"
          label="隐藏">
          <template scope="scope">
            <span v-if="scope.row.hidden" class="fa fa-ok" aria-hidden="true"></span>
            <label v-if="!scope.row.hidden"> —</label>
          </template>
        </el-table-column>
        <el-table-column
          prop="introduction"
          label="说明">
        </el-table-column>
        <el-table-column width="200" label="操作">
          <template scope="scope">
            <el-button v-if="scope.row.type == 0"
                       class="fa fa-plus"
                       size="mini"
                       @click="openAddDialog(scope.row)">
            </el-button>
            <el-button v-if="scope.row.type == 0"
                       size="mini"
                       class="fa fa-arrow-up"
                       @click="moveUp(scope.row)">
            </el-button>
            <el-button
              size="mini"
              icon="edit"
              @click="openEditDialog(scope.row)">
            </el-button>
            <el-button
              size="mini"
              icon="delete"
              type="danger"
              @click="handleDelete(scope.row)">
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-col>

    <el-dialog :title="dialogFormTitle" v-model="dialogFormVisible">
      <el-form :model="resource" label-width="80px">
        <el-form-item label="名称">
          <el-input v-model="resource.name"></el-input>
        </el-form-item>
        <el-form-item label="URL">
          <el-input v-model="resource.url"></el-input>
        </el-form-item>
        <el-form-item label="父资源">
          <el-input
            v-model="resource.parent.name"
            :disabled="true">
          </el-input>
        </el-form-item>
        <el-form-item label="样式">
          <el-input v-model="resource.icon"></el-input>
        </el-form-item>
        <el-form-item label="类型">
          <el-radio-group v-model="resource.type">
            <el-radio :label="0">菜单</el-radio>
            <el-radio :label="1">按钮</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="隐藏">
          <el-radio-group v-model="resource.hidden">
            <el-radio :label="false">显示</el-radio>
            <el-radio :label="true">影藏</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="说明">
          <el-input v-model="resource.introduction"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click.native="editSubmit">确 定</el-button>
      </div>
    </el-dialog>

  </section>
</template>
<style lang="scss" type="text/scss" scope>
  .resource-one {
    margin-left: 0px;
  }

  .resource-two {
    margin-left: 20px;
  }

  .resource-three {
    margin-left: 40px;
  }

  .resource-four {
    margin-left: 60px;
  }
</style>
<script>
  //自定义工具
  import util from '../../commons/util';
  import tableTree from '../../commons/table-tree';
  export default {
    data() {
      return {
        resource: {
          id: '',
          name: '',
          icon: '',
          url: '',
          sequence: null,
          type: 0,
          parent: {
            id: null,
            name: '',
            type: 0,
          },
          hidden: false,
        },
        dialogFormVisible: false,
        dialogFormTitle: '',
        props: {
          label: 'name',
          children: 'children'
        },
        resources: [],
        //原始数据
        rawResources: [],
        //展开的ID
        openIds: [],
      };
    },
    created: function () {
      this.loadTopResource();
    },

    methods: {
      loadTopResource(){
        this.$http.get('system/resource/tree')
          .then((response) => {
            this.rawResources = response.data.data;
            this.operateNode();
          });
      },
      //展开或者关闭节点
      operateNode(row){
        if (row == null) {
          this.resources = [];
          tableTree.operateNode(this.rawResources, this.resources, this.openIds);
        } else {
          if (row.type == 0) {
            this.resources = [];
            tableTree.operateNode(this.rawResources, this.resources, this.openIds, row.id);
          }
        }
      },
      //判断该行是否展开
      hasElement(element){
        return util.array.contains(this.openIds, element);
      },

      //打开添加对话框
      openAddDialog(row){
        this.dialogFormTitle = '新增';
        this.dialogFormVisible = true;
        this.resource.id = null;
        this.resource.name = null;
        this.resource.url = null;
        this.resource.icon = null;
        if (row != null) {
          this.resource.parent.id = row.id;
          this.resource.parent.name = row.name;
        } else {
          this.resource.parent.id = null;
          this.resource.parent.name = null;
        }
        this.resource.type = 0;
        this.resource.hidden = false;
        this.resource.introduction = null;
      },
      //打开编辑对话框
      openEditDialog(row){
        this.dialogFormTitle = '编辑';
        this.dialogFormVisible = true;
        this.resource.id = row.id;
        this.resource.icon = row.icon;
        if (row.parent != null) {
          this.resource.parent.id = row.parent.id;
          this.resource.parent.name = row.parent.name;
        } else {
          this.resource.parent.id = null;
          this.resource.parent.name = null;
        }
        this.resource.name = row.name;
        this.resource.url = row.url;
        this.resource.type = row.type;
        this.resource.hidden = row.hidden;
        this.resource.introduction = row.introduction;
      },
      //删除资源
      handleDelete(row){
        this.$confirm('确认删除？', '警告', {type: 'warning'}).then(() => {
          this.$http.get('resource/delete?id=' + row.id)
            .then((response) => {
              this.loadTopResource();
            });
        })
      },
      editSubmit(){
        //如果ID 为空 为添加
        if (this.resource.id == null) {
          this.$http.post('system/resource/add', this.resource)
            .then((response) => {
              this.loadTopResource();
            });
        } else {
          this.$http.post('system/resource/update', this.resource)
            .then((response) => {
              this.loadTopResource();
            });
        }
        this.dialogFormVisible = false;
      },

      //资源向上移动
      moveUp(row){
        //判断当前的排序是否为1
        if (row.sequence === 1) {
          this.$message('该菜单已经是最顶端了');
          return;
        }
        this.$http.get('system/resource/up?id=' + row.id)
          .then((response) => {
            this.loadTopResource();
          });
      },
    }
  };
</script>
