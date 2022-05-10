<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['mes:pro:workorderbom:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="workorderbomList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="BOM物料编号" width="120" align="center" prop="itemCode" />
      <el-table-column label="BOM物料名称" width="200" align="center" prop="itemName" :show-overflow-tooltip="true"/>
      <el-table-column label="规格型号" align="center" prop="itemSpc" :show-overflow-tooltip="true"/>
      <el-table-column label="单位" align="center" prop="unitOfMeasure" />
      <el-table-column label="物料/产品" align="center" prop="itemOrProduct" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.mes_item_product" :value="scope.row.itemOrProduct"/>
        </template>
      </el-table-column>
      <el-table-column label="预计使用量" align="center" prop="quantity" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            v-if="scope.row.status == 'PREPARE'"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['mes:pro:workorderbom:edit']"
          >修改</el-button>

          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleAddSubWorkorder(scope.row)"
            v-if="workorder.status == 'CONFIRMED' && scope.row.itemOrProduct=='PRODUCT'"
            v-hasPermi="['mes:pro:workorder:edit']"
          >生成工单</el-button>
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

    <!-- 添加或修改生产工单BOM组成对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="预计使用量" prop="quantity">
          <el-input v-model="form.quantity" placeholder="请输入预计使用量" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listWorkorderbom, getWorkorderbom, delWorkorderbom, addWorkorderbom, updateWorkorderbom } from "@/api/mes/pro/workorderbom";

export default {
  name: "Workorderbom",
  dicts: ['mes_item_product'],
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
      // 生产工单BOM组成表格数据
      workorderbomList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        workorderId: null,
        itemId: null,
        itemCode: null,
        itemName: null,
        itemSpc: null,
        unitOfMeasure: null,
        itemOrProduct: null,
        quantity: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        workorderId: [
          { required: true, message: "生产工单ID不能为空", trigger: "blur" }
        ],
        itemId: [
          { required: true, message: "BOM物料ID不能为空", trigger: "blur" }
        ],
        itemCode: [
          { required: true, message: "BOM物料编号不能为空", trigger: "blur" }
        ],
        itemName: [
          { required: true, message: "BOM物料名称不能为空", trigger: "blur" }
        ],
        unitOfMeasure: [
          { required: true, message: "单位不能为空", trigger: "blur" }
        ],
        itemOrProduct: [
          { required: true, message: "物料产品标识不能为空", trigger: "blur" }
        ],
        quantity: [
          { required: true, message: "预计使用量不能为空", trigger: "blur" }
        ],
      }
    };
  },
  props: {
      optType: undefined,
      workorder: undefined
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询生产工单BOM组成列表 */
    getList() {
      this.loading = true;
      this.queryParams.workorderId = this.workorder.workorderId;
      listWorkorderbom(this.queryParams).then(response => {
        this.workorderbomList = response.rows;
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
        lineId: null,
        workorderId: null,
        itemId: null,
        itemCode: null,
        itemName: null,
        itemSpc: null,
        unitOfMeasure: null,
        itemOrProduct: null,
        quantity: null,
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
      this.ids = selection.map(item => item.lineId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const lineId = row.lineId || this.ids
      getWorkorderbom(lineId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改生产工单BOM组成";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.lineId != null) {
            updateWorkorderbom(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addWorkorderbom(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    handleAddSubWorkorder(row){
      debugger;
      var temp = JSON.parse(JSON.stringify(this.workorder));
      temp.workorderCode = null;
      temp.workorderName = row.itemName + "【"+row.quantity+"】"+row.unitOfMeasure;
      temp.productId = row.itemId;
      temp.productCode = row.itemCode;
      temp.productName = row.itemName;
      temp.unitOfMeasure = row.unitOfMeasure;
      temp.quantity = row.quantity;
      temp.status = 'PREPARE';
      this.$emit('handleAddSub',temp);
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('mes/pro/workorderbom/export', {
        ...this.queryParams
      }, `workorderbom_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
