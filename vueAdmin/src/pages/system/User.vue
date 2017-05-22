<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form label-width="80px" :inline="true" :model="queryParameter">
        <el-form-item label="账号">
          <el-input style="width: 140px;" size="small" v-model="queryParameter.userName" placeholder="账号"></el-input>
        </el-form-item>
        <el-form-item label="账号状态">
          <el-checkbox-group size="small" style="width: 140px;" v-model="queryParameter.locked">
            <el-checkbox :label="0">锁定</el-checkbox>
            <el-checkbox :label="1">活动</el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item>
          <el-button size="small" type="primary" v-on:click="getUserPageList">查询</el-button>
          <el-button size="small" type="primary" @click="openAddDialog">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>
    <!--列表-->
    <el-table :data="userPage.rows"
              highlight-current-row
              v-loading="listLoading"
              @selection-change="selsChange"
              style="width: 100%;">
      <el-table-column fixed type="selection" width="50">
      </el-table-column>
      <el-table-column type="index" width="40">
      </el-table-column>
      <el-table-column prop="userName" label="账号" width="120">
      </el-table-column>
      <el-table-column prop="userName" label="角色" width="120">
        <template scope="scope">
          <label v-for="role in scope.row.roles">
            [{{role.name}}]
          </label>
        </template>
      </el-table-column>
      <el-table-column prop="local" label="状态" min-width="85">
        <template scope="scope">
          <span v-if="scope.row.locked" class="glyphicon glyphicon-ok" aria-hidden="true"></span>
          <label v-if="!scope.row.locked"> —</label>
        </template>
      </el-table-column>
      <el-table-column inline-template :context="_self" label="操作" width="200" fixed="right">
	      <span>
					<el-button size="mini" @click="openEditDialog(row)" icon="edit"></el-button>
					<el-button size="mini" @click="restorePass(row)" class="fa fa-key"></el-button>
					<el-button type="danger" size="mini" @click="deleteUer(row)" icon="delete"></el-button>
				</span>
      </el-table-column>
    </el-table>
    <!--分页-->
    <el-col :span="24" class="toolbar" style="padding-bottom:10px;">
      <el-button size="small" type="danger" @click="batchRemove" :disabled="this.sels.length===0">批量删除</el-button>
      <el-pagination small layout="prev, pager, next" @current-change="handleCurrentChange"
                     :page-rows="queryParameter.rows" :total="userPage.total"
                     style="float:right;">
      </el-pagination>
    </el-col>

    <!--编辑界面-->
    <el-dialog :title="dialogFormTitle" v-model="dialogFormVisible" :close-on-click-modal="false">
      <el-form :model="user" label-width="80px" ref="user">
        <el-form-item label="账号" prop="userName">
          <el-input v-model="user.userName" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="角色" prop="rolesId">
          <el-select v-model="user.rolesId" multiple placeholder="请选择">
            <el-option
              v-for="item in roleList"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="用户类型" prop="type">
          <el-select v-model="user.type" placeholder="请选择">
            <el-option label="普通管理员" value="1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="user.locked">
            <el-radio :label="false">活动</el-radio>
            <el-radio :label="true">锁定</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
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
        listLoading: false,
        editLoading: false,
        dialogFormVisible: false,
        dialogFormTitle: '',
        //选中的
        sels: [],
        user: {
          id: null,
          userName: null,
          locked: false,
          type: null,
          rolesId: [],
        },
        roleList: {
          id: null,
          name: null
        },
        userPage: {
          rows: [],
          total: 0,
        },
        queryParameter: {
          userName: null,
          roleName: null,
          locked: [0, 1],
          page: 1,
          rows: 20,
        }
      }

    },
    mounted(){
      this.getUserPageList();
      this.getRoles();
    },
    methods: {
      formatSex(row, column){
        return row.gender == 0 ? '男' : row.gender == 1 ? '女' : '未知';
      },
      getUserPageList(){
        this.listLoading = true;
        this.$http.post('system/user/page', this.queryParameter)
          .then((socketResponse) => {
            this.userPage = socketResponse.data.data;
            this.listLoading = false;
          });
      },
      handleCurrentChange(val) {
        this.queryParameter.page = val;
        this.getUserPageList();
      },
      openAddDialog(){
        this.dialogFormVisible = true;
        this.dialogFormTitle = '新增';
        this.user.id = null;
        this.user.userName = null;
        this.user.type = null;
        this.user.rolesId = [];
        this.user.locked = false;
      },
      openEditDialog(row){
        this.dialogFormVisible = true;
        this.dialogFormTitle = '编辑';
        this.user.id = row.id;
        this.user.userName = row.userName;
        this.user.type = '' + row.type;
        for (let i = 0; i < row.roles.length; i++){
          this.user.rolesId.push(row.roles[i].id);
        }
        this.user.locked = row.locked;
      },
      getRoles(){
        this.$http.get('system/role/list')
          .then((response) => {
            this.roleList = response.data.data;
          });
      },
      handleCurrentChange(){

      },
      editSubmit(){
        //新增
        if (this.user.id == null) {
          this.$http.post('system/user/add', this.user)
            .then((response) => {
              this.userPage.rows.push(this.user);
              this.dialogFormVisible = false;
            });
        } else {
          this.$http.post('system/user/update', this.user)
            .then((response) => {
              this.dialogFormVisible = false;
              this.getUserPageList();
            });
        }
      },
      //还原默认密码
      restorePass(row){
        this.$confirm('确认还原用户 [ ' + row.userName + ' ] 密码？', '提示', {
          type: 'warning'
        }).then(() => {
          this.$http.get('system/user/restorePass?id=' + row.id)
            .then((response) => {
              this.$message.success('密码还原成功');
            });
        });
      },

      deleteUer(row){
        this.$confirm('确认删除[ ' + row.userName + ' ]用户？', '提示', {
          type: 'warning'
        }).then(() => {
          this.listLoading = true;
          this.$http.get('system/user/delete?id=' + row.id)
            .then((response) => {
              this.listLoading = false;
              this.getUserPageList();
            });
        });
        s
      },
      selsChange(sels){
        this.sels = sels;
      },
      //单个删除
      batchRemove(row){
        this.$confirm('确认删除选中用户吗？', '提示', {
          type: 'warning'
        }).then(() => {
          this.listLoading = true;
          this.$http.post('system/user/delete', this.sels)
            .then((response) => {
              this.listLoading = false;
              this.getUserPageList();
            });
        }).catch(() => {
        });
      }
    },
  }
</script>
