<template>
    <el-dialog title="物料产品选择"
    v-if="showFlag"
    :visible.sync="showFlag"
    :modal= false
    width="60%"
    center
    > 
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="检测项类型" prop="indexType">
            <el-radio-group v-model="queryParams.indexType" @change="handleQuery">
                <el-radio-button  v-for="dict in dict.type.mes_index_type"
                        :key="dict.value"
                        :label="dict.value"
                        :value="dict.value"                        
                        >{{dict.label}}</el-radio-button>   
            </el-radio-group>    
        </el-form-item>    
        <el-form-item label="检测项名称" prop="indexName">
        <el-input
          v-model="queryParams.indexName"
          placeholder="请输入检测项名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
        </el-form-item> 
        <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="qcindexList" @current-change="handleCurrent" @row-dblclick="handleRowDbClick">
        <el-table-column width="50" align="center" >
            <template v-slot="scope">
                <el-radio v-model="selectedIndexId" :label="scope.row.indexId" @change="handleRowChange(scope.row)">{{""}}</el-radio>
            </template>
        </el-table-column>
      <el-table-column label="检测项编码" align="center" prop="indexCode" />
      <el-table-column label="检测项名称" align="center" prop="indexName" />
      <el-table-column label="检测项类型" align="center" prop="indexType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.mes_index_type" :value="scope.row.indexType"/>
        </template>
      </el-table-column>
      <el-table-column label="检测工具" align="center" prop="qcTool" />
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
import { listQcindex, getQcindex, delQcindex, addQcindex, updateQcindex } from "@/api/mes/qc/qcindex";
export default {
  name: "QcindexSelect",
  dicts: ['mes_index_type'],
  data() {
    return {
      showFlag:false,
      // 遮罩层
      loading: true,
      // 选中数组
      selectedIndexId: null,
      selectedRows: undefined,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 检测项表格数据
      qcindexList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        indexCode: null,
        indexName: null,
        indexType: null,
        qcTool: null,
      },
      // 表单参数
      form: {}
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询检测项列表 */
    getList() {
      this.loading = true;
      listQcindex(this.queryParams).then(response => {
        this.qcindexList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 表单重置
    reset() {
      this.form = {
        indexId: null,
        indexCode: null,
        indexName: null,
        indexType: null,
        qcTool: null,
        remark: null,
        attr1: null,
        attr2: null,
        attr3: null,
        attr4: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
      this.autoGenFlag = false;
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
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.indexId)
      this.single = selection.length!==1
      this.multiple = !selection.length
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
    //选中确认
    confirmSelect(){
        if(this.selectedIndexId ==null || this.selectedIndexId==0){
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
