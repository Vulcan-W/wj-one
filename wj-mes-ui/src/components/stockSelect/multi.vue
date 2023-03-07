<template>
  <el-dialog title="人员选择"
    v-if="showFlag"
    :visible.sync="showFlag"
    :modal= false
    width="80%"
    center
  >
    <el-row :gutter="20">
      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-input
            v-model="itemTypeName"
            placeholder="请输入分类名称"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px"
          />
        </div>
        <div class="head-container">
          <el-tree
            :data="itemTypeOptions"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            ref="tree"
            default-expand-all
            @node-click="handleNodeClick"
          />
        </div>
      </el-col>
      <el-col :span="20" :xs="24">

      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
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
        <el-form-item label="入库批次号" prop="batchCode">
          <el-input
            v-model="queryParams.batchCode"
            placeholder="请输入入库批次号"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="仓库名称" prop="warehouseName">
          <el-input
            v-model="queryParams.warehouseName"
            placeholder="请输入仓库名称"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="供应商编号" prop="vendorCode">
          <el-input
            v-model="queryParams.vendorCode"
            placeholder="请输入供应商编号"
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
        <el-form-item label="库存有效期" prop="expireDate">
          <el-date-picker clearable
            v-model="queryParams.expireDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择库存有效期">
          </el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <el-row :gutter="10" class="mb8">    
        <el-col :span="1.5">
          <el-button
            type="warning"
            plain
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['wm:wmstock:export']"
          >导出</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>

      <el-table v-loading="loading" :data="wmstockList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="产品物料编码" width="120px" align="center" prop="itemCode" />
        <el-table-column label="产品物料名称" width="150px" align="center" prop="itemName" :show-overflow-tooltip="true"/>
        <el-table-column label="规格型号" align="center" prop="specification" :show-overflow-tooltip="true"/>
        <el-table-column label="单位" align="center" prop="unitOfMeasure" />
        <el-table-column label="入库批次号" width="100px" align="center" prop="batchCode" :show-overflow-tooltip="true"/>
        <el-table-column label="仓库" align="center" prop="warehouseName" />
        <el-table-column label="库区" align="center" prop="locationName" />
        <el-table-column label="库位" align="center" prop="areaName" />
        <el-table-column label="供应商编号" width="100px" align="center" prop="vendorCode" />
        <el-table-column label="供应商名称" width="120px" align="center" prop="vendorName" :show-overflow-tooltip="true"/>
        <el-table-column label="供应商简称" width="100px" align="center" prop="vendorNick" />
        <el-table-column label="在库数量" align="center" prop="quantityOnhand" />
        <el-table-column label="库存有效期" align="center" prop="expireDate" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.expireDate, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
      </el-table>
    
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
      </el-col>
    </el-row>
    <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="confirmSelect">确 定</el-button>
        <el-button @click="showFlag=false">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { listWmstock, getWmstock, delWmstock, addWmstock, updateWmstock } from "@/api/mes/wm/wmstock";
import { treeselect } from "@/api/mes/md/itemtype";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
export default {
  name: "WmstockMultiSelect",
  components: { Treeselect },
  data() {
    return {
      showFlag:false,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      selectedRows: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      //物料产品分类树
      itemTypeOptions: undefined,
      // 库存记录表格数据
      wmstockList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        itemTypeId: null,
        itemId: null,
        itemCode: null,
        itemName: null,
        specification: null,
        unitOfMeasure: null,
        batchCode: null,
        warehouseId: null,
        warehouseCode: null,
        warehouseName: null,
        locationId: null,
        locationCode: null,
        locationName: null,
        areaId: null,
        areaCode: null,
        areaName: null,
        vendorId: null,
        vendorCode: null,
        vendorName: null,
        vendorNick: null,
        quantityOnhand: null,
        expireDate: null,
      },
      // 表单参数
      form: {},
    };
  },
  created() {
    this.getList();
    this.getTreeselect();
  },
  methods: {
    /** 查询库存记录列表 */
    getList() {
      this.loading = true;
      listWmstock(this.queryParams).then(response => {
        this.wmstockList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询分类下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.itemTypeOptions = response.data;
      });
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.itemTypeId = data.id;
      this.handleQuery();
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
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.materialStockId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },

  }
};
</script>
