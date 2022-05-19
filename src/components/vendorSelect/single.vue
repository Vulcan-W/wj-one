<template>
  <el-dialog title="供应商选择"
    v-if="showFlag"
    :visible.sync="showFlag"
    :modal= false
    width="80%"
    center
    >
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-row>
        <el-col :span="8">
          <el-form-item label="供应商编码" prop="vendorCode">
            <el-input
              v-model="queryParams.vendorCode"
              placeholder="请输入供应商编码"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="供应商名称" prop="vendorName">
            <el-input
              v-model="queryParams.vendorName"
              placeholder="请输入供应商名称"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="供应商简称" prop="vendorNick">
            <el-input
              v-model="queryParams.vendorNick"
              placeholder="请输入供应商简称"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="英文名称" prop="vendorEn">
            <el-input
              v-model="queryParams.vendorEn"
              placeholder="请输入供应商英文名称"
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

    <el-table v-loading="loading" :data="vendorList" @current-change="handleCurrent" @row-dblclick="handleRowDbClick">
      <el-table-column  width="55" align="center" >
        <template v-slot="scope">
          <el-radio v-model="selectedVendorId" :label="scope.row.vendorId" @change="handleRowChange(scope.row)">{{""}}</el-radio>
        </template>
      </el-table-column>
      <el-table-column label="供应商编码" align="center" prop="vendorCode" >
      </el-table-column>
      <el-table-column label="供应商名称" align="center" prop="vendorName" width="150px" :show-overflow-tooltip="true"/>
      <el-table-column label="供应商简称" align="center" prop="vendorNick" />
      <el-table-column label="供应商等级" align="center" prop="vendorLevel" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.mes_vendor_level" :value="scope.row.vendorLevel"/>
        </template>
      </el-table-column>
      <el-table-column label="供应商评分" align="center" prop="vendorScore" />
      <el-table-column label="供应商电话" align="center" prop="tel" />
      <el-table-column label="是否启用" align="center" prop="enableFlag" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.enableFlag"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" :show-overflow-tooltip="true"/>
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
import { listVendor, getVendor, delVendor, addVendor, updateVendor } from "@/api/mes/md/vendor";
export default {
  name: "VendorSelect",
  dicts: ['sys_yes_no','mes_vendor_level'],
  data() {
    return {
      showFlag:false,
      selectedVendorId: undefined,
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
      // 供应商表格数据
      vendorList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        vendorCode: null,
        vendorName: null,
        vendorNick: null,
        vendorEn: null,
        vendorDes: null,
        vendorLogo: null,
        vendorLevel: null,
        vendorScore: null,
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
      },
      // 表单参数
      form: {},
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询供应商列表 */
    getList() {
      this.loading = true;
      listVendor(this.queryParams).then(response => {
        this.vendorList = response.rows;
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
        if(this.selectedVendorId ==null || this.selectedVendorId==0){
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
