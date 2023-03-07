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
          v-hasPermi="['mes:qc:templateindex:add']"
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
          v-hasPermi="['mes:qc:templateindex:edit']"
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
          v-hasPermi="['mes:qc:templateindex:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="templateindexList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="检测项名称" align="center" prop="indexName" />
      <el-table-column label="检测项类型" align="center" prop="indexType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.mes_index_type" :value="scope.row.indexType"/>
        </template>
      </el-table-column>
      <el-table-column label="检测工具" align="center" prop="qcTool" />
      <el-table-column label="检测方法" align="center" prop="checkMethod" :show-overflow-tooltip="true"/>
      <el-table-column label="标准值" align="center" prop="standerVal" />
      <el-table-column label="单位" align="center" prop="unitOfMeasure" />
      <el-table-column label="误差上限" align="center" prop="thresholdMax" />
      <el-table-column label="误差下限" align="center" prop="thresholdMin" />
      <el-table-column label="操作" v-if="optType != 'view'" align="center" width="100px" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['mes:qc:templateindex:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['mes:qc:templateindex:remove']"
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

    <!-- 添加或修改检测模板-检测项对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="960px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
            <el-col :span="12">
                <el-form-item label="检测项" prop="indexId">
                    <el-input v-model="form.indexName" placeholder="请选择检测项" >
                        <el-button slot="append" @click="handleSelectIndex" icon="el-icon-search"></el-button>
                    </el-input>
                    <IndexSelect ref="indexSelect" @onSelected="onIndexSelected" ></IndexSelect>
                </el-form-item>
            </el-col>
            <el-col :span="12">
                <el-form-item label="检测工具" prop="qcTool">
                    <el-input v-model="form.qcTool" placeholder="请输入检测工具" />
                </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <el-form-item label="检测要求" prop="checkMethod">
                    <el-input v-model="form.checkMethod" type="textarea" placeholder="请输入内容" />
                </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="12">
                <el-form-item label="标准值" prop="standerVal">
                    <el-input v-model="form.standerVal" placeholder="请输入标准值" />
                </el-form-item>
            </el-col>
            <el-col :span="12">
                <el-form-item label="单位" prop="unitOfMeasure">
                    <el-select v-model="form.unitOfMeasure" placeholder="请选择单位">
                        <el-option
                            v-for="item in measureOptions"
                            :key="item.measureCode"
                            :label="item.measureName"
                            :value="item.measureCode"
                            :disabled="item.enableFlag == 'N'"
                        ></el-option>
                    </el-select>
                </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="12">
                <el-form-item label="误差上限" prop="thresholdMax">
                    <el-input-number :min="0" v-model="form.thresholdMax" placeholder="请输入误差上限" />
                </el-form-item>
            </el-col>
            <el-col :span="12">
                <el-form-item label="误差下限" prop="thresholdMin">
                    <el-input-number :max="0" v-model="form.thresholdMin" placeholder="请输入误差下限" />
                </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <el-form-item label="说明图" prop="docUrl">
                    <el-input v-model="form.docUrl" placeholder="请输入说明图" />
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
import { listTemplateindex, getTemplateindex, delTemplateindex, addTemplateindex, updateTemplateindex } from "@/api/mes/qc/templateindex";
import IndexSelect from "@/components/qcindexSelect/single.vue";
import { listAllUnitmeasure} from "@/api/mes/md/unitmeasure";
export default {
  name: "Templateindex",
  dicts: ['mes_index_type'],
  props:{
      optType: null,
      templateId: null
  },
  components:{IndexSelect},
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
      // 检测模板-检测项表格数据
      templateindexList: [],
      //单位选择
      measureOptions:[],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        templateId: this.templateId,
        indexId: null,
        indexCode: null,
        indexName: null,
        indexType: null,
        qcTool: null,
        checkMethod: null,
        standerVal: null,
        unitOfMeasure: null,
        thresholdMax: null,
        thresholdMin: null,
        docUrl: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        templateId: [
          { required: true, message: "检测模板ID不能为空", trigger: "blur" }
        ],
        indexId: [
          { required: true, message: "检测项不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getUnits();
  },
  methods: {
    /** 查询检测模板-检测项列表 */
    getList() {
      this.loading = true;
      listTemplateindex(this.queryParams).then(response => {
        this.templateindexList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getUnits(){
        listAllUnitmeasure().then(response =>{
            this.measureOptions = response.data;
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
        indexId: null,
        indexCode: null,
        indexName: null,
        indexType: null,
        qcTool: null,
        checkMethod: null,
        standerVal: null,
        unitOfMeasure: null,
        thresholdMax: null,
        thresholdMin: null,
        docUrl: null,
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
      this.title = "添加检测模板-检测项";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const recordId = row.recordId || this.ids
      getTemplateindex(recordId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改检测模板-检测项";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.recordId != null) {
            updateTemplateindex(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTemplateindex(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除检测模板-检测项编号为"' + recordIds + '"的数据项？').then(function() {
        return delTemplateindex(recordIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    //选择检测项弹出框
    handleSelectIndex(){
        this.$refs.indexSelect.showFlag = true;
    },
    //弹框返回值
    onIndexSelected(obj){
        if(obj != undefined && obj != null){
          this.form.indexId = obj.indexId;
          this.form.indexCode = obj.indexCode;
          this.form.indexName = obj.indexName;
          this.form.indexType = obj.indexType;
          this.form.qcTool = obj.qcTool;  
        }
    }
  }
};
</script>
