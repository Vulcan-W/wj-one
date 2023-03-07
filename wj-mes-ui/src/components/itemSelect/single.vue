<template>
  <el-dialog title="物料产品选择"
    v-if="showFlag"
    :visible.sync="showFlag"
    :modal= false
    width="80%"
    center
  >
    <el-row :gutter="20">
      <!--分类数据-->
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
      <!--物料数据-->
      <el-col :span="20" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="物料编码" prop="itemCode">
            <el-input
              v-model="queryParams.itemCode"
              placeholder="请输入物料编码"
              clearable
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="物料名称" prop="itemName">
            <el-input
              v-model="queryParams.itemName"
              placeholder="请输入物料名称"
              clearable
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
        <el-table v-loading="loading" :data="itemList" @current-change="handleCurrent" @row-dblclick="handleRowDbClick">
          <el-table-column width="50" align="center" >
              <template v-slot="scope">
                  <el-radio v-model="selectedItemId" :label="scope.row.itemId" @change="handleRowChange(scope.row)">{{""}}</el-radio>
              </template>
          </el-table-column>
          <el-table-column label="物料编码" width = "120" align="center" key="itemCode" prop="itemCode" v-if="columns[0].visible" >
          </el-table-column>
          <el-table-column label="物料名称" min-width="120" align="left" key="itemName" prop="itemName" v-if="columns[1].visible" :show-overflow-tooltip="true" />
          <el-table-column label="规格型号" align="left" key="specification" prop="specification" v-if="columns[2].visible" :show-overflow-tooltip="true" />
          <el-table-column label="单位" align="center" key="unitOfMeasure" prop="unitOfMeasure" v-if="columns[3].visible" :show-overflow-tooltip="true" />
          <el-table-column label="物料/产品" align="center" key="itemOrProduct" prop="itemOrProduct" v-if="columns[4].visible" :show-overflow-tooltip="true" >
            <template slot-scope="scope">
              <dict-tag :options="dict.type.mes_item_product" :value="scope.row.itemOrProduct"/>
            </template>
          </el-table-column>
          <el-table-column label="所属分类" align="center" key="itemTypeName" prop="itemTypeName" v-if="columns[5].visible" width="120" />
          <el-table-column label="创建时间" align="center" prop="createTime" v-if="columns[6].visible" width="160">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createTime) }}</span>
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
import { listMdItem, getMdItem, delMdItem, addMdItem, updateMdItem} from "@/api/mes/md/mdItem";
import { listAllUnitmeasure} from "@/api/mes/md/unitmeasure";
import {genCode} from "@/api/system/autocode/rule"
import { getToken } from "@/utils/auth";
import { treeselect } from "@/api/mes/md/itemtype";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "MdItemSingle",
  dicts: ['sys_yes_no','mes_item_product'],
  components: { Treeselect },
  data() {
    return {
      showFlag:false,
      // 选中数组
      selectedItemId: undefined,
      selectedRows: undefined,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 物料产品表格数据
      itemList: null,
      // 部门树选项
      itemTypeOptions: undefined,
      // 部门名称
      itemTypeName: undefined,
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        itemCode: undefined,
        itemName: undefined,
        itemTypeId: 0
      },
      // 列信息
      columns: [
        { key: 0, label: `物料/产品编码`, visible: true },
        { key: 1, label: `物料/产品名称`, visible: true },
        { key: 2, label: `规格型号`, visible: true },
        { key: 3, label: `单位`, visible: true },
        { key: 4, label: `物料/产品`, visible: true },
        { key: 5, label: `物料分类`, visible: true },
        { key: 6, label: `创建时间`, visible: true }
      ]
    };
  },
  watch: {
    // 根据名称筛选分类树
    itemTypeName(val) {
      this.$refs.tree.filter(val);
    }
  },
  created() {
    this.getList();
    this.getTreeselect();
  },
  methods: {
    /** 查询物料编码列表 */
    getList() {
      this.loading = true;
      listMdItem(this.queryParams).then(response => {
          this.itemList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
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
    handleCurrent(row){
        if(row){
            this.selectedRows = row;
        }
    },
    handleRowDbClick(row){
      if(row){
        this.selectedRows = row;
        this.$emit('onSelected',this.selectedRows);
        this.showFlag = false;
      }
    },
    // 单选选中数据
    handleRowChange(row) {
      debugger;
      if(row){
        this.selectedRows = row;
      }
    },
    //确定选中
    confirmSelect(){
        if(this.selectedItemId ==null || this.selectedItemId==0){
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