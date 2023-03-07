<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="工单编码" prop="workorderCode">
        <el-input
          v-model="queryParams.workorderCode"
          placeholder="请输入工单编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工单名称" prop="workorderName">
        <el-input
          v-model="queryParams.workorderName"
          placeholder="请输入工单名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="来源单据" prop="sourceCode">
        <el-input
          v-model="queryParams.sourceCode"
          placeholder="请输入来源单据"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="产品编号" prop="productCode">
        <el-input
          v-model="queryParams.productCode"
          placeholder="请输入产品编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="产品名称" prop="productName">
        <el-input
          v-model="queryParams.productName"
          placeholder="请输入产品名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="客户编码" prop="clientCode">
        <el-input
          v-model="queryParams.clientCode"
          placeholder="请输入客户编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="客户名称" prop="clientName">
        <el-input
          v-model="queryParams.clientName"
          placeholder="请输入客户名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="需求日期" prop="requestDate">
        <el-date-picker clearable
          v-model="queryParams.requestDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择需求日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>        
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-refresh" circle="" @click="getList"></el-button>
        <el-button type="primary" icon="el-icon-edit" circle="" @click="handleOpenGantt"></el-button>
      </el-form-item>
    </el-form>
    <div class="wrapper">
      <div class="container">
        <GanttChar class="left-container" ref="ganttChar" :tasks="tasks"></GanttChar>
      </div>      
    </div>    
    <el-table
      v-loading="loading"
      :data="workorderList"
      row-key="workorderId"
      default-expand-all
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column label="工单编码" width="180" prop="workorderCode" >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            @click="handleView(scope.row)"
            v-hasPermi="['mes:pro:workorder:query']"
          >{{scope.row.workorderCode}}</el-button>
        </template>
      </el-table-column>
      <el-table-column label="工单名称" width="200" align="center" prop="workorderName" :show-overflow-tooltip="true"/>
      <el-table-column label="工单来源" align="center" prop="orderSource" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.mes_workorder_sourcetype" :value="scope.row.orderSource"/>
        </template>
      </el-table-column>
      <el-table-column label="订单编号" width="140" align="center" prop="sourceCode" />
      <el-table-column label="产品编号" width="120" align="center" prop="productCode" />
      <el-table-column label="产品名称" width="200" align="center" prop="productName" :show-overflow-tooltip="true"/>
      <el-table-column label="规格型号" align="center" prop="productSpc" :show-overflow-tooltip="true"/>
      <el-table-column label="单位" align="center" prop="unitOfMeasure" />
      <el-table-column label="工单数量" align="center" prop="quantity" />                 
      <el-table-column label="调整数量" align="center" prop="quantityChanged" />
      <el-table-column label="已排产数量" align="center" width="100px" prop="quantityScheduled" />
      <el-table-column label="已生产数量" align="center" width="100px" prop="quantityProduced" /> 
      <el-table-column label="客户编码" align="center" prop="clientCode" />
      <el-table-column label="客户名称" align="center" prop="clientName" :show-overflow-tooltip="true"/>
      <el-table-column label="需求日期" align="center" prop="requestDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.requestDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="排产状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.mes_order_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="150px" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            v-if="scope.row.status =='CONFIRMED'"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['mes:pro:workorder:edit']"
          >排产</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改生产工单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="960px" append-to-body>
      <el-form ref="form" :model="form" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="工单编号" prop="workorderCode">
              <el-input v-model="form.workorderCode" readonly="readonly" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="工单名称" prop="workorderName">
              <el-input v-model="form.workorderName" readonly="readonly" />
            </el-form-item>
          </el-col>          
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="来源类型" prop="orderSource">              
              <el-radio-group v-model="form.orderSource" disabled>
                <el-radio
                  v-for="dict in dict.type.mes_workorder_sourcetype"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>            
            </el-form-item>
          </el-col>                    
          <el-col :span="8" v-if="form.orderSource == 'ORDER'">
            <el-form-item label="订单编号" prop="sourceCode">
              <el-input v-model="form.sourceCode" readonly="readonly" />
            </el-form-item>
          </el-col>         
          <el-col :span="8">
            <el-form-item label="排产状态" prop="status">
              <el-select v-model="form.status" disabled>
                <el-option
                  v-for="dict in dict.type.mes_order_status"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col> 
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="产品编号" prop="productCode">
              <el-input v-model="form.productCode" readonly="readonly" >
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="产品名称" prop="productName">
              <el-input v-model="form.productName" readonly="readonly" />
            </el-form-item>
          </el-col>          
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="规格型号" prop="productSpc">
              <el-input v-model="form.productSpc" readonly="readonly" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="单位" prop="unitOfMeasure">
              <el-input v-model="form.unitOfMeasure" readonly="readonly" />
            </el-form-item>
          </el-col>          
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="工单数量" prop="quantity">
              <el-input v-model="form.quantity" readonly="readonly" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="需求日期" prop="requestDate">
              <el-date-picker disabled
                v-model="form.requestDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择需求日期">
              </el-date-picker>
            </el-form-item>
          </el-col>          
        </el-row>
        <el-row v-if="form.orderSource == 'ORDER'">
          <el-col :span="12">
            <el-form-item label="客户编号" prop="clientCode">
              <el-input v-model="form.clientCode" readonly="readonly" >
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="客户名称" prop="clientName">
              <el-input v-model="form.clientName" readonly="readonly" />
            </el-form-item>
          </el-col>
          <el-col></el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" readonly="readonly" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <el-steps :active="activeProcess" v-if="form.workorderId !=null" align-center simple>
        <el-step  v-for="(item,index) in processOptions"
                  :title="item.processName" @click.native="handleStepClick(index)">           
        </el-step>
      </el-steps>
      <el-card v-for=" (item,index) in processOptions " :key="index" v-if="activeProcess == index && form.workorderId !=null">
        <ProTask :workorderId="form.workorderId" :processId="item.processId" :colorCode="item.colorCode" :optType="optType"></ProTask>
      </el-card>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="cancel" v-if="optType =='view' || form.status !='PREPARE' ">返回</el-button>
        <el-button type="primary" @click="submitForm" v-if="form.status =='PREPARE' && optType !='view' ">确 定</el-button>
        <el-button type="success" @click="handleFinish" v-if="form.status =='PREPARE' && optType !='view'  && form.workorderId !=null">完成</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listWorkorder, getWorkorder, delWorkorder, addWorkorder, updateWorkorder } from "@/api/mes/pro/workorder";
import {listGanttTaskList} from "@/api/mes/pro/protask";
import { listProductprocess } from "@/api/mes/pro/routeprocess";
import ProTask from "./proTask.vue";
import GanttChar from "./ganttx.vue";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Workorder",
  dicts: ['mes_order_status','mes_workorder_sourcetype'],
  components: {
    Treeselect,
    ProTask,
    GanttChar
  },
  data() {
    return {
      //自动生成编码
      autoGenFlag:false,
      optType: undefined,
      activeProcess: 0,
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 生产工单表格数据
      workorderList: [],
      // 生产工单树选项
      workorderOptions: [],
      //当前生产工单中产品对应的工序列表
      processOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        workorderCode: null,
        workorderName: null,
        orderSource: null,
        sourceCode: null,
        productId: null,
        productCode: null,
        productName: null,
        productSpc: null,
        unitOfMeasure: null,
        quantity: null,
        quantityProduced: null,
        quantityChanged: null,
        quantityScheduled: null,
        clientId: null,
        clientCode: null,
        clientName: null,
        requestDate: null,
        parentId: null,
        ancestors: null,
        status: null,
      },
      tasks:{
        data: [],
        links: []
      },
      // 表单参数
      form: {},
    };
  },
  created() {
    this.getList();
    this.getGanttTasks();
  },
  methods: {
    /** 查询生产工单列表 */
    getList() {
      this.loading = true;
      listWorkorder(this.queryParams).then(response => {
        this.workorderList = this.handleTree(response.data, "workorderId", "parentId");
        this.loading = false;
      });
    },
    handleOpenGantt(){
      this.$router.push({ path: '/mes/pro/schedule/ganttedit'})
    },
    getGanttTasks(){
      listGanttTaskList(this.queryParams).then(response =>{
        debugger;
        this.tasks.data = response.data.data;
        this.tasks.links = response.data.links;
        this.$refs.ganttChar.reload();
      });
    },

    getProcess(){
        listProductprocess(this.form.productId).then(response =>{
            this.processOptions = response.data;
        });
    },

    /** 转换生产工单数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.workorderId,
        label: node.workorderName,
        children: node.children
      };
    },
	/** 查询生产工单下拉树结构 */
    getTreeselect() {
      listWorkorder().then(response => {
        this.workorderOptions = [];
        const data = { workorderId: 0, workorderName: '顶级节点', children: [] };
        data.children = this.handleTree(response.data, "workorderId", "parentId");
        this.workorderOptions.push(data);
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
        workorderId: null,
        workorderCode: null,
        workorderName: null,
        orderSource: null,
        sourceCode: null,
        productId: null,
        productCode: null,
        productName: null,
        productSpc: null,
        unitOfMeasure: null,
        quantity: null,
        quantityProduced: null,
        quantityChanged: null,
        quantityScheduled: null,
        clientId: null,
        clientCode: null,
        clientName: null,
        requestDate: null,
        parentId: null,
        status: "PREPARE",
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null        
      };
      this.activeProcess =0;
      this.autoGenFlag = false;
      this.resetForm("form");
    },
    //甘特图按钮点击
    openGanttChart(){
      this.$refs.ganttChar.showFlag =true;
    },
    //Step点击
    handleStepClick(index){
        this.activeProcess =index;
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    //从BOM行中直接新增
    handleSubAdd(row){
      debugger;
      this.open = false;
      this.reset();
      this.getTreeselect();
      if (row != null && row.workorderId) {
        this.form = row;
        this.form.parentId = row.workorderId;
        this.form.workorderId = null;
        this.form.workorderCode = null;
      } else {
        this.form.parentId = 0;
      }
      this.open = true;
      this.title = "添加生产工单";
      this.optType="add";
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      this.reset();
      this.getTreeselect();
      if (row != null && row.workorderId) {
        this.form.parentId = row.workorderId;
        this.form.orderSource = row.orderSource;
        this.form.sourceCode = row.sourceCode;
        this.form.clientId = row.clientId;
        this.form.clientCode = row.clientCode;
        this.form.clientName = row.clientName;
      } else {
        this.form.parentId = 0;
      }
      this.open = true;
      this.title = "添加生产工单";
      this.optType="add";
    },
    // 查询明细按钮操作
    handleView(row){
      this.reset();      
      this.getTreeselect();
      const workorderId = row.workorderId || this.ids;
      getWorkorder(workorderId).then(response => {
        this.form = response.data;
        this.getProcess();
        this.open = true;
        this.title = "查看工单信息";
        this.optType = "view";
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getTreeselect();
      if (row != null) {
        this.form.parentId = row.workorderId;
      }
      getWorkorder(row.workorderId).then(response => {
        debugger;
        this.form = response.data;
        this.getProcess();
        this.open = true;
        this.title = "修改生产工单";
        this.optType="edit";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.workorderId != null) {
            updateWorkorder(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              //this.open = false;
              this.$refs["bomlist"].getList();
              this.getList();
            });
          } else {
            addWorkorder(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              //this.open = false;
              this.form.workorderId = response.data;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$modal.confirm('是否确认删除生产工单编号为"' + row.workorderId + '"的数据项？').then(function() {
        return delWorkorder(row.workorderId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    handleSelectProduct(){
      this.$refs.itemSelect.showFlag = true;
    },
    handleSelectClient(){
      this.$refs.clientSelect.showFlag = true;
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('mes/pro/workorder/export', {
        ...this.queryParams
      }, `workorder_${new Date().getTime()}.xlsx`)
    },
    handleFinish(){
      let that = this;
      this.$modal.confirm('是否完成工单编制？【完成后将不能更改】').then(function(){
        that.form.status = 'CONFIRMED';
        that.submitForm();
      });
    },
    //物料选择弹出框
    onItemSelected(obj){
        debugger;
        if(obj != undefined && obj != null){
          this.form.productId = obj.itemId;
          this.form.productCode = obj.itemCode;
          this.form.productName = obj.itemName;
          this.form.productSpc = obj.specification;
          this.form.unitOfMeasure = obj.unitOfMeasure;  
        }
    },
    //客户选择弹出框
    onClientSelected(obj){
        if(obj != undefined && obj != null){
          this.form.clientId = obj.clientId;
          this.form.clientCode = obj.clientCode;
          this.form.clientName = obj.clientName;
        }
    },
    //自动生成编码
    handleAutoGenChange(autoGenFlag){
      debugger;
      if(autoGenFlag){
        genCode('WORKORDER_CODE').then(response =>{
          this.form.workorderCode = response;
        });
      }else{
        this.form.workorderCode = null;
      }
    }
  }
};
</script>
<style scoped>
.wrapper{
    height: 400px;
}
.container {
  height: 100%;
  width: 100%;
}
.left-container {
  overflow: hidden;
  position: relative;
  height: 100%;
}
</style>
