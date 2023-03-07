<template>
  <el-dialog title="设备选择"
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
            v-model="machineryTypeName"
            placeholder="请输入分类名称"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px"
          />
        </div>
        <div class="head-container">
          <el-tree
            :data="machineryTypeOptions"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            ref="tree"
            default-expand-all
            @node-click="handleNodeClick"
          />
        </div>
      </el-col>
      <!--设备数据-->
      <el-col :span="20" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="设备编码" prop="machineryCode">
            <el-input
              v-model="queryParams.machineryCode"
              placeholder="请输入设备编码"
              clearable
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="设备名称" prop="machineryName">
            <el-input
              v-model="queryParams.machineryName"
              placeholder="请输入设备名称"
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
        <el-table v-loading="loading" :data="machineryList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="设备编码" width = "120" align="center" key="machineryCode" prop="machineryCode">
          </el-table-column>
          <el-table-column label="设备名称" min-width="120" align="left" key="machineryName" prop="machineryName" :show-overflow-tooltip="true" />
          <el-table-column label="品牌" align="left" key="machineryBrand" prop="machineryBrand" :show-overflow-tooltip="true" />
          <el-table-column label="规格型号" align="left" key="machinerySpec" prop="machinerySpec" :show-overflow-tooltip="true" />
          <el-table-column label="所属车间" align="center" key="machineryTypeName" prop="machineryTypeName"  :show-overflow-tooltip="true" />
          <el-table-column label="设备状态" align="center" key="status" prop="status" >
            <template slot-scope="scope">
              <dict-tag :options="dict.type.mes_machinery_status" :value="scope.row.status"/>
            </template>
          </el-table-column>
          <el-table-column label="创建时间" align="center" prop="createTime" width="160">
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
import { listMachinery, getMachinery, delMachinery, addMachinery, updateMachinery } from "@/api/mes/dv/machinery";
import { listMachinerytype } from "@/api/mes/dv/machinerytype";
import { listAllWorkshop } from "@/api/mes/md/workshop";

import { getToken } from "@/utils/auth";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "MachinerySelect",
  dicts: ['sys_yes_no','mes_machinery_status'],
  components: { Treeselect },
  data() {
    return {
      showFlag: false,
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
      // 物料产品表格数据
      machineryList: [],
      // 弹出层标题
      title: "",
      // 设备类型树选项
      machineryTypeOptions: [],
      //车间选项
      workshopOptions:[],
      // 是否显示弹出层
      open: false,
      // 设备类型名称
      machineryTypeName: undefined,
      //自动生成物料编码标识
      autoGenFlag: false,
      // 表单参数
      form: {},
      defaultProps: {
        children: "children",
        label: "machineryTypeName"
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        machineryCode: null,
        machineryName: null,
        machineryBrand: null,
        machinerySpec: null,
        machineryTypeId: null,
        machineryTypeCode: null,
        machineryTypeName: null,
        workshopId: null,
        workshopCode: null,
        workshopName: null,
        status: null
      }
    };
  },
  watch: {
    // 根据设备分类名称筛选分类树
    machineryTypeName(val) {
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
      listMachinery(this.queryParams).then(response => {
          this.machineryList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    getWorkshops(){
      listAllWorkshop().then( response => {
        debugger;
        this.workshopOptions =response.data;
      });
    },
    /** 转换设备类型数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.machineryTypeId,
        label: node.machineryTypeName,
        children: node.children
      };
    },
	/** 查询设备类型下拉树结构 */
    getTreeselect() {
      listMachinerytype().then(response => {
        debugger;
        this.machineryTypeOptions = [];
        const data = this.handleTree(response.data, "machineryTypeId", "parentTypeId")[0];
        this.machineryTypeOptions.push(data);
      });
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.machineryTypeName.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.machineryTypeId = data.machineryTypeId;
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
      this.ids = selection.map(item => item.machineryId);
      this.selectedRows = selection;
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    //确定选中
    confirmSelect(){
        if(this.ids ==[] || this.ids.length==0){
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