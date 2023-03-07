<template>
  <el-dialog title="客户选择"
    v-if="showFlag"
    :visible.sync="showFlag"
    :modal= false
    width="80%"
    center
    >
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-row>
        <el-col :span="8">
          <el-form-item label="客户编码" prop="clientCode">
            <el-input
              v-model="queryParams.clientCode"
              placeholder="请输入客户编码"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="客户名称" prop="clientName">
            <el-input
              v-model="queryParams.clientName"
              placeholder="请输入客户名称"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="客户简称" prop="clientNick">
            <el-input
              v-model="queryParams.clientNick"
              placeholder="请输入客户简称"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
            <el-form-item label="客户英文名称" prop="clientEn">
            <el-input
              v-model="queryParams.clientEn"
              placeholder="请输入客户英文名称"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="是否启用" prop="enableFlag">
            <el-select
              v-model="queryParams.enableFlag"
              placeholder="是否启用"
              clearable
              style="width: 215px"
            >
              <el-option
                v-for="dict in dict.type.sys_yes_no"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <el-table v-loading="loading" :data="clientList" @current-change="handleCurrent" @row-dblclick="handleRowDbClick">
        <el-table-column width="50" align="center" >
            <template v-slot="scope">
                <el-radio v-model="selectedClientId" :label="scope.row.clientId" @change="handleRowChange(scope.row)">{{""}}</el-radio>
            </template>
        </el-table-column>
      <el-table-column label="客户编码" align="center" prop="clientCode" />
      <el-table-column label="客户名称" width="150px" align="center" prop="clientName" :show-overflow-tooltip="true"/>
      <el-table-column label="客户简称" align="center" prop="clientNick" />
      <el-table-column label="客户类型" align="center" prop="clientType" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.mes_client_type" :value="scope.row.clientType"/>
        </template>
      </el-table-column>
      <el-table-column label="客户电话" align="center" prop="tel" />
      <el-table-column label="联系人" align="center" prop="contact1" />
      <el-table-column label="联系人-电话" align="center" prop="contact1Tel" />
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
import { listClient, getClient, delClient, addClient, updateClient } from "@/api/mes/md/client";
export default {
  name: "ClientSelectSingle",
  dicts: ['mes_client_type','sys_yes_no'],
  data() {
    return {
      showFlag:false,
      selectedClientId: undefined,
      selectedRow: undefined,
      // 遮罩层
      loading: true,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 客户表格数据
      clientList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        clientCode: null,
        clientName: null,
        clientNick: null,
        clientEn: null,
        clientDes: null,
        clientLogo: null,
        clientType: null,
        address: null,
        website: null,
        email: null,
        tel: null,
        contact1: null,
        contact1Tel: null,
        contact1Email: null,
        contact2: null,
        contact2Tel: null,
        contact2Email: null,
        creditCode: null,
        enableFlag: null,
      }      
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询客户列表 */
    getList() {
      this.loading = true;
      listClient(this.queryParams).then(response => {
        this.clientList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
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
            this.selectedRow = row;
        }
    },
    //行双击选中
    handleRowDbClick(row){
      if(row){
        this.selectedRow = row;
        this.$emit('onSelected',this.selectedRow);
        this.showFlag = false;
      }
    },
    // 单选选中数据
    handleRowChange(row) {
      debugger;
      if(row){
        this.selectedRow = row;
      }
    },
    //确定选中
    confirmSelect(){
        if(this.selectedClientId ==null || this.selectedClientId==0){
            this.$notify({
                title:'提示',
                type:'warning',
                message: '请至少选择一条数据!'
            });
            return;
        }
        this.$emit('onSelected',this.selectedRow);
        this.showFlag = false;
    }
  }
};
</script>
