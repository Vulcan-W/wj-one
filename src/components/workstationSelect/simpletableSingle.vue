<template>
  <el-dialog title="设备选择"
    v-if="showFlag"
    :visible.sync="showFlag"
    :modal= false
    width="80%"
    center
  >
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-row>
        <el-col :span="8">
          <el-form-item label="工作站编码" prop="workstationCode">
            <el-input
              v-model="queryParams.workstationCode"
              placeholder="请输入工作站编码"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="工作站名称" prop="workstationName">
            <el-input
              v-model="queryParams.workstationName"
              placeholder="请输入工作站名称"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="所在车间名称" prop="workshopName">
            <el-select v-model="queryParams.workshopId" placeholder="请选择车间">
              <el-option
                v-for="item in workshopOptions"
                :key="item.workshopId"
                :label="item.workshopName"
                :value="item.workshopId"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <el-table v-loading="loading" :data="workstationList" @current-change="handleCurrent" @row-dblclick="handleRowDbClick">
      <el-table-column width="55" align="center" >
        <template v-slot="scope">
            <el-radio v-model="selectedWorkstationId" :label="scope.row.workstationId" @change="handleRowChange(scope.row)">{{""}}</el-radio>
        </template>
      </el-table-column>
      <el-table-column label="工作站编号" align="center" prop="workstationCode" >
      </el-table-column>
      <el-table-column label="工作站名称" align="center" prop="workstationName" />
      <el-table-column label="工作站地点" align="center" prop="workstationAddress" />
      <el-table-column label="所在车间名称" align="center" prop="workshopName" />
      <el-table-column label="所属工序" align="center" prop="processName" />
      <el-table-column label="备注" align="center" prop="remark" />      
    </el-table>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="confirmSelect">确 定</el-button>
        <el-button @click="showFlag=false">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { listWorkstation, getWorkstation, delWorkstation, addWorkstation, updateWorkstation } from "@/api/mes/md/workstation";
import {listAllProcess} from "@/api/mes/pro/process";
import { listAllWorkshop } from "@/api/mes/md/workshop";
export default {
  name: "WorkstationSelect",
  dicts: ['sys_yes_no'],
  data() {
    return {
      showFlag:false,
      // 遮罩层
      loading: true,
      // 选中数组
      selectedWorkstationId: undefined,
      selectedRows: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 工作站表格数据
      workstationList: [],
      //车间选项
      workshopOptions:[],
      //工序选项
      processOptions:[],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        workstationCode: null,
        workstationName: null,
        workstationAddress: null,
        workshopId: null,
        workshopCode: null,
        workshopName: null,
        processId: this.processId,
        processCode: null,
        processName: null,
        enableFlag: null,
      },
      // 表单参数
      form: {},
    };
  },
  props:{
      processId: undefined //外部传入的工序过滤条件
  },
  created() {
    this.getList();
    this.getWorkshops();
    this.getProcess();
  },
  methods: {
    /** 查询工作站列表 */
    getList() {
      this.loading = true;
      listWorkstation(this.queryParams).then(response => {
        this.workstationList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    //查询车间信息
    getWorkshops(){
      listAllWorkshop().then( response => {        
        this.workshopOptions = response.data;
      });
    },
    //查询工序信息
    getProcess(){
      listAllProcess().then( response =>{
        this.processOptions = response.data;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        workstationId: null,
        workstationCode: null,
        workstationName: null,
        workstationAddress: null,
        workshopId: null,
        workshopCode: null,
        workshopName: null,
        processId: null,
        processCode: null,
        processName: null,
        enableFlag: 'Y',
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    handleCurrent(row){
        if(row){
            this.selectedRows = row;
        }
    },
    // 单选选中数据
    handleRowChange(row) {
      if(row){
        this.selectedRows = row;
      }
    },
    //双击选中
    handleRowDbClick(row){
      if(row){
        this.selectedRows = row;
        this.$emit('onSelected',this.selectedRows);
        this.showFlag = false;
      }
    },
    //确定选中
    confirmSelect(){
        if(this.selectedWorkstationId == null || this.selectedWorkstationId == 0){
            this.$notify({
                title:'提示',
                type:'warning',
                message: '请至少选择一条数据!'
            });
            return;
        }
        this.$emit('onSelected',this.selectedRows);
        this.showFlag = false;
    }
  }
};
</script>
