<template>
  <section>
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form label-width="80px" :inline="true" :model="queryParam">
        <el-form-item label="访问账号">
          <el-input size="small" v-model="queryParam.userName" placeholder="访问账号"></el-input>
        </el-form-item>
        <el-form-item label="用户昵称">
          <el-input size="small" v-model="queryParam.nickName" placeholder="用户昵称"></el-input>
        </el-form-item>
        <el-form-item label="时间范围">
          <el-date-picker
            size="small"
            v-model="timeRange"
            type="datetimerange"
            @change="dateTimeChange"
            placeholder="选择时间范围">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="访问方法">
          <el-input size="small" v-model="queryParam.method" placeholder="访问方法"></el-input>
        </el-form-item>
        <el-form-item label="提交参数">
          <el-input size="small" v-model="queryParam.param" placeholder="提交参数"></el-input>
        </el-form-item>
        <el-form-item label="访问IP">
          <el-input size="small" v-model="queryParam.ip" placeholder="访问IP"></el-input>
        </el-form-item>
        <el-form-item label=" ">
          <el-button size="small" type="primary" icon="search" v-on:click="getLogList">查询</el-button>
        </el-form-item>
      </el-form>
    </el-col>
    <el-col :span="24">
      <el-table
        border
        highlight-current-row
        :data="logPage.rows"
        :loading="listLoading"
        style="width: 100%">
        <el-table-column
          fixed
          prop="userName"
          width="100"
          label="操作用户">
        </el-table-column>
        <el-table-column
          prop="method"
          width="600"
          label="操作方法">
        </el-table-column>
        <el-table-column
          prop="ip"
          width="100"
          label="访问IP">
        </el-table-column>
        <el-table-column
          prop="time"
          width="200"
          :formatter="formatDateTime"
          sortable
          label="时间">
        </el-table-column>
        <el-table-column
          prop="param"
          width="500"
          :formatter="formatParamLength"
          label="请求参数">
        </el-table-column>
      </el-table>
      <!--分页-->
      <el-col :span="24" class="toolbar" style="padding-bottom:10px;">
        <el-pagination small layout="prev, pager, next, jumper" @current-change="handleCurrentChange"
                       :page-rows="queryParam.rows" :total="logPage.total" style="float:right;">
        </el-pagination>
      </el-col>
    </el-col>
  </section>
</template>
<style>
</style>
<script>
  export default{
    data(){
      return {
        logPage: {
          rows: [],
          total: 1,
        },
        timeRange:null,
        listLoading: false,
        queryParam: {
          nickName: null,
          userName: null,
          ip: null,
          method: null,
          param: null,
          startTime: null,
          endTime: null,
          page: 1,
          row: 20,
        }
      }
    },
    mounted(){
      this.getLogList();
    },
    methods: {
      getLogList(){
        this.listLoading = true;
        this.$http.post('system/log/page', this.queryParam)
          .then((socketRequest) => {
            this.logPage = socketRequest.data.data;
            this.listLoading = false;
          });
      },
      formatDateTime(row, column){
        return new Date(parseInt(("/Date("+row.time+")/").substr(6, 13))).toLocaleString();
      },
      formatParamLength(row, column){
        return row.param.substr(0,60) + (row.param.length > 60 ? '...' : '');
      },
      handleCurrentChange(val){
        this.queryParam.page = val;
        this.getLogList();
      },
      dateTimeChange(val){
          var t = val.split(' - ');
          this.queryParam.startTime = t[0];
          this.queryParam.endTime = t[1];
      }
    },
  }
</script>
