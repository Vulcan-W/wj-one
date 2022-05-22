<template>
  <el-dialog title="客户选择"
    v-if="showFlag"
    :visible.sync="showFlag"
    :modal= false
    width="80%"
    center
    >
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="检验单编号" prop="iqcCode">
        <el-input
          v-model="queryParams.iqcCode"
          placeholder="请输入来料检验单编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="供应商编码" prop="vendorCode">
        <el-input
          v-model="queryParams.vendorCode"
          placeholder="请输入供应商编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="供应商名称" prop="vendorName">
        <el-input
          v-model="queryParams.vendorName"
          placeholder="请输入供应商名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="供应商批次号" prop="vendorBatch">
        <el-input
          v-model="queryParams.vendorBatch"
          placeholder="请输入供应商批次号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="产品物料编码" prop="itemCode">
        <el-input
          v-model="queryParams.itemCode"
          placeholder="请输入产品物料编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="产品物料名称" prop="itemName">
        <el-input
          v-model="queryParams.itemName"
          placeholder="请输入产品物料名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="检测人员" prop="inspector">
        <el-input
          v-model="queryParams.inspector"
          placeholder="请输入检测人员"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="iqcList" @current-change="handleCurrent" @row-dblclick="handleRowDbClick">
      <el-table-column width="50" align="center" >
        <template v-slot="scope">
            <el-radio v-model="selectedId" :label="scope.row.iqcId" @change="handleRowChange(scope.row)">{{""}}</el-radio>
        </template>
      </el-table-column>
      <el-table-column label="来料检验单编号" width="140px" align="center" prop="iqcCode" >
      </el-table-column>
      <el-table-column label="来料检验单名称" width="120px" align="center" prop="iqcName" :show-overflow-tooltip="true"/>
      <el-table-column label="供应商简称" width="100px" align="center" prop="vendorNick" :show-overflow-tooltip="true"/>
      <el-table-column label="供应商批次号" width="120px" align="center" prop="vendorBatch" />
      <el-table-column label="产品物料编码" width="120px" align="center" prop="itemCode" />
      <el-table-column label="产品物料名称" width="120px" align="center" prop="itemName" :show-overflow-tooltip="true"/>
      <el-table-column label="接收数量" width="90px" align="center" prop="quantityRecived" />
      <el-table-column label="检测数量" width="90px" align="center" prop="quantityCheck" />
      <el-table-column label="不合格数" align="center" prop="quantityUnqualified" />
      <el-table-column label="检测结果" align="center" prop="checkResult" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.mes_qc_result" :value="scope.row.checkResult"/>
        </template>
      </el-table-column>
      <el-table-column label="来料日期" align="center" prop="reciveDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.reciveDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="检测日期" align="center" prop="inspectDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.inspectDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="检测人员" align="center" prop="inspectorName" />
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
import { listIqc, getIqc, delIqc, addIqc, updateIqc } from "@/api/mes/qc/iqc";
export default {
  name: "IqcSelect",
  dicts: ['mes_qc_result','mes_order_status'],
  data() {
    return {
      showFlag:false,
      // 遮罩层
      loading: true,
      // 选中
      selectedId: null,
      selectedRow: undefined,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 来料检验单表格数据
      iqcList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        iqcCode: null,
        iqcName: null,
        templateId: null,
        vendorId: null,
        vendorCode: null,
        vendorName: null,
        vendorNick: null,
        vendorBatch: null,
        itemId: null,
        itemCode: null,
        itemName: null,
        specification: null,     
        checkResult: 'ACCEPT',
        reciveDate: null,
        inspectDate: null,
        inspector: null,
        status: null,
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询来料检验单列表 */
    getList() {
      this.loading = true;
      listIqc(this.queryParams).then(response => {
        this.iqcList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
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
        if(this.selectedId ==null || this.selectedId==0){
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
