<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8" v-if="optType !='view'">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['mes:md:bom:add']"
        >新增</el-button>
        <ItemSelect ref="itemSelect" @onSelected="onItemSelected" > </ItemSelect>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['mes:md:bom:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="bomList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="物料编码" align="center" prop="bomItemCode" />
      <el-table-column label="物料名称" align="center" prop="bomItemName" :show-overflow-tooltip="true" />
      <el-table-column label="规格" align="center" prop="bomItemSpec" :show-overflow-tooltip="true" />
      <el-table-column label="单位" width="60px" align="center" prop="unitOfMeasure" />
      <el-table-column label="使用比例" width="90px" align="center" prop="quantity" />  
      <el-table-column label="备注" align="center" prop="remark" :show-overflow-tooltip="true" />  
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" v-if="optType !='view'">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['mes:md:bom:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['mes:md:bom:remove']"
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

 <!-- 添加或修改产品BOM关系对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="BOM物料编码" prop="bomItemCode">
          <el-input v-model="form.bomItemCode" readonly placeholder="请输入BOM物料编码" />
        </el-form-item>
        <el-form-item label="BOM物料名称" prop="bomItemName">
          <el-input v-model="form.bomItemName" readonly placeholder="请输入BOM物料名称" />
        </el-form-item>
        <el-form-item label="BOM物料规格" prop="bomItemSpec">
          <el-input v-model="form.bomItemSpec" readonly type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="BOM物料单位" prop="unitOfMeasure">
          <el-input v-model="form.unitOfMeasure" readonly placeholder="请输入BOM物料单位" />
        </el-form-item>
        <el-form-item label="物料使用比例" prop="quantity">
          <el-input-number  :precision="4" :step="0.1" :min="0"  v-model="form.quantity" placeholder="请输入物料使用比例" />
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
import { listBom, getBom, delBom, addBom, updateBom } from "@/api/mes/md/bom";
import ItemSelect  from "@/components/itemSelect/index.vue";

export default {
  name: "Bom",
components: { ItemSelect },
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
      // 产品BOM关系表格数据
      bomList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        itemId: this.itemId,
        bomItemId: null,
        bomItemCode: null,
        bomItemName: null,
        bomItemSpec: null,
        unitOfMeasure: null,
        quantity: null,
        enableFlag: null,
        attr1: null,
        attr2: null,
        attr3: null,
        attr4: null,
      },
      // 表单参数
      form: {},
    // 表单校验
      rules: {
        quantity: [
          { required: true, message: "物料使用比例不能为空", trigger: "blur" }
        ]
      }
    };
  },
  props: {
      optType: undefined,
      itemId: undefined
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询产品BOM关系列表 */
    getList() {
      this.loading = true;
      listBom(this.queryParams).then(response => {
        this.bomList = response.rows;
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
        bomId: null,
        itemId: null,
        bomItemId: null,
        bomItemCode: null,
        bomItemName: null,
        bomItemSpec: null,
        unitOfMeasure: null,
        itemOrProduct: null,
        quantity: null,
        enableFlag: 'Y',
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
      this.ids = selection.map(item => item.bomId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
       this.$refs.itemSelect.showFlag = true;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const bomId = row.bomId || this.ids
      getBom(bomId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改产品BOM关系";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
            updateBom(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });  
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const bomIds = row.bomId || this.ids;
      this.$modal.confirm('是否确认删除产品BOM关系编号为"' + bomIds + '"的数据项？').then(function() {
        return delBom(bomIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    //物料选择弹出框
    onItemSelected(obj){
        debugger;
        this.form.itemId = this.itemId;
        if(obj != undefined && obj != null){
            obj.forEach(element => {
                this.form.bomItemId = element.itemId;
                this.form.bomItemCode = element.itemCode;
                this.form.bomItemName = element.itemName;
                this.form.bomItemSpec = element.specification;
                this.form.unitOfMeasure = element.unitOfMeasure;
                this.form.itemOrProduct = element.itemOrProduct;
                this.form.quantity = 1;
                this.form.enableFlag = 'Y';
                var retFlag = false;
                addBom(this.form).then(response => {
                    this.$modal.msgSuccess("新增成功");
                    this.getList();
                });
            });
        }
    }
  }
};
</script>
