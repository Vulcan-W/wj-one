<template>
  <div class="app-container">
    <el-row :gutter="10" v-if="optType !='view'" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['mes:qc:templateproduct:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['mes:qc:templateproduct:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['mes:qc:templateproduct:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="templateproductList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="产品物料编码" align="center" width="100px" prop="itemCode" :show-overflow-tooltip="true"/>
      <el-table-column label="产品物料名称" align="center" width="120px" prop="itemName" :show-overflow-tooltip="true"/>
      <el-table-column label="规格型号" align="center" prop="specification" :show-overflow-tooltip="true"/>
      <el-table-column label="最低检测数" align="center" width="100px" prop="quantityCheck" />
      <el-table-column label="单位" align="center" width="100px" prop="unitOfMeasure" />
      <el-table-column label="最大不合格数" align="center" prop="quantityUnqualified" />
      <el-table-column label="最大致命缺陷率" align="center" prop="crRate" >
          <template slot-scope="scope">
              <span>{{scope.row.crRate+"%"}}</span>
          </template>
      </el-table-column>
      <el-table-column label="最大严重缺陷率" align="center" prop="majRate" >
            <template slot-scope="scope">
              <span>{{scope.row.majRate+"%"}}</span>
          </template>
      </el-table-column>
      <el-table-column label="最大轻微缺陷率" align="center" prop="minRate" >
            <template slot-scope="scope">
                <span>{{scope.row.minRate+"%"}}</span>
            </template>
      </el-table-column>
      <el-table-column label="操作" align="center" v-if="optType != 'view'" width="100px" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['mes:qc:templateproduct:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['mes:qc:templateproduct:remove']"
          >删除</el-button>
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

    <!-- 添加或修改检测模板-产品对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="960px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-row>
            <el-col :span="12">
                <el-form-item label="产品物料编码" prop="itemCode">
                    <el-input v-model="form.itemCode" placeholder="请输入产品物料编码" >
                        <el-button slot="append" @click="handleSelectItem" icon="el-icon-search"></el-button>
                    </el-input>
                    <ItemSelect ref="itemSelect" @onSelected="onItemSelected" ></ItemSelect>
                </el-form-item>
            </el-col>
            <el-col :span="12">
                <el-form-item label="产品物料名称" prop="itemName">
                    <el-input v-model="form.itemName" readonly="readonly" />
                </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <el-form-item label="规格型号" prop="specification">
                    <el-input v-model="form.specification" readonly="readonly" type="textarea" />
                </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="8">
                <el-form-item label="最低检测数" prop="quantityCheck">
                    <el-input-number :min="1" size="small" v-model="form.quantityCheck" placeholder="请输入最低检测数" />
                </el-form-item>
            </el-col>
            <el-col :span="8">
                <el-form-item label="最大不合格数" prop="quantityUnqualified">
                    <el-tooltip content="检测过程中缺陷数量超出最大不合格数后，整批将判定为不合格。为0时，代表不启用此限制" placement="right">
                        <el-input-number :min="0" size="small" v-model="form.quantityUnqualified" placeholder="请输入最大不合格数" />
                    </el-tooltip>
                </el-form-item>
            </el-col>
            <el-col :span="8">
                <el-form-item label="单位" prop="unitOfMeasure">
                    <el-input v-model="form.unitOfMeasure" readonly="readonly" />
                </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="8">
                <el-form-item label="最大致命缺陷率" prop="crRate">
                    <el-tooltip content="缺陷比例超出指定百分比后，整批将判断为不合格。为0时，代表不允许出现致命缺陷。">
                        <el-input-number :min="0" :max="100" :pricision="2" size="small" v-model="form.crRate" placeholder="请输入最大致命缺陷率" />
                    </el-tooltip>
                </el-form-item>
            </el-col>
            <el-col :span="8">
                <el-form-item label="最大严重缺陷率" prop="majRate">
                    <el-tooltip content="缺陷比例超出指定百分比后，整批将判断为不合格。为0时，代表不允许出现严重缺陷。">
                        <el-input-number :min="0" :max="100" :pricision="2" size="small" v-model="form.majRate" placeholder="请输入最大严重缺陷率" />
                    </el-tooltip>
                </el-form-item>
            </el-col>
            <el-col :span="8">
                <el-form-item label="最大轻微缺陷率" prop="minRate">
                    <el-tooltip content="缺陷比例超出指定百分比后，整批将判断为不合格。为0时，代表不允许出现轻微缺陷。">
                        <el-input-number :min="0" :max="100" :pricision="2" size="small" v-model="form.minRate" placeholder="请输入最大轻微缺陷率" />
                    </el-tooltip>
                </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <el-form-item label="备注" prop="remark">
                    <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
                </el-form-item>
            </el-col>          
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="cancel" v-if="optType =='view'">返回</el-button>
        <el-button type="primary" @click="submitForm" v-else>确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listTemplateproduct, getTemplateproduct, delTemplateproduct, addTemplateproduct, updateTemplateproduct } from "@/api/mes/qc/templateproduct";
import ItemSelect  from "@/components/itemSelect/single.vue";
export default {
  name: "Templateproduct", 
  components: {ItemSelect},
  props:{
      optType: null,
      templateId: null
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 检测模板-产品表格数据
      templateproductList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        templateId: this.templateId,
        itemId: null,
        itemCode: null,
        itemName: null,
        specification: null,
        unitOfMeasure: null,
        quantityCheck: null,
        quantityUnqualified: null,
        crRate: null,
        majRate: null,
        minRate: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        templateId: [
          { required: true, message: "检测模板ID不能为空", trigger: "blur" }
        ],
        itemId: [
          { required: true, message: "产品物料不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询检测模板-产品列表 */
    getList() {
      this.loading = true;
      listTemplateproduct(this.queryParams).then(response => {
        this.templateproductList = response.rows;
        this.total = response.total;
        this.loading = false;
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
        recordId: null,
        templateId: this.templateId,
        itemId: null,
        itemCode: null,
        itemName: null,
        specification: null,
        unitOfMeasure: null,
        quantityCheck: null,
        quantityUnqualified: null,
        crRate: 0,
        majRate: 0,
        minRate: 100,
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
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.recordId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加检测模板-产品";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const recordId = row.recordId || this.ids
      getTemplateproduct(recordId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改检测模板-产品";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.recordId != null) {
            updateTemplateproduct(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTemplateproduct(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const recordIds = row.recordId || this.ids;
      this.$modal.confirm('是否确认删除检测目中的物料产品？').then(function() {
        return delTemplateproduct(recordIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    //物料选择弹出框
    handleSelectItem(){
      this.$refs.itemSelect.showFlag = true;
    },
    //物料选择弹出框
    onItemSelected(obj){
        if(obj != undefined && obj != null){
          this.form.itemId = obj.itemId;
          this.form.itemCode = obj.itemCode;
          this.form.itemName = obj.itemName;
          this.form.specification = obj.specification;
          this.form.unitOfMeasure = obj.unitOfMeasure;  
        }
    },
  }
};
</script>
