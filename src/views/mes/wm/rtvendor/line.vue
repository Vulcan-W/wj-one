<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['mes:wm:rtvendor:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['mes:wm:rtvendor:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="rtvendorlineList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="物料编码" width="120px" align="center" prop="itemCode" />
      <el-table-column label="物料名称" width="150px" align="center" prop="itemName" :show-overflow-tooltip="true"/>
      <el-table-column label="规格型号" align="center" prop="specification" :show-overflow-tooltip="true"/>
      <el-table-column label="单位" align="center" prop="unitOfMeasure" />
      <el-table-column label="退货数量" align="center" prop="quantityRted" />
      <el-table-column label="批次号" align="center" prop="batchCode" />
      <el-table-column label="仓库" align="center" prop="warehouseName" />
      <el-table-column label="库区" align="center" prop="locationName" />
      <el-table-column label="库位" align="center" prop="areaName" />
      <el-table-column label="备注" align="center" prop="remark" :show-overflow-tooltip="true"/>
      <el-table-column label="操作" width="100px"  v-if="optType != 'view'" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['mes:wm:rtvendor:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['mes:wm:rtvendor:remove']"
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

    <!-- 添加或修改供应商退货行对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="960px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
            <el-col :span="8">
                <el-form-item label="产品物料编码" prop="itemCode">
                    <el-input v-model="form.itemCode" readonly="readonly" placeholder="请选择产品物料编码" >
                      <el-button slot="append" @click="handleSelectStock" icon="el-icon-search"></el-button>
                    </el-input>
                </el-form-item>
                <StockSelect ref="stockSelect" :batchCode="batchCdoe" :vendorId="vendorId" @onSelected="onStockSelected"></StockSelect>
            </el-col>
            <el-col :span="8">
                <el-form-item label="产品物料名称" prop="itemName">
                    <el-input v-model="form.itemName" readonly="readonly" />
                </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="退货数量" prop="quantityRted">
                <el-input-number :max="form.quantityMax" v-model="form.quantityRted" placeholder="请输入退货数量" />
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
              <el-form-item label="单位" prop="unitOfMeasure">
                <el-input v-model="form.unitOfMeasure" readonly="readonly" />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="批次号" prop="batchCode">
                <el-input v-model="form.batchCode" readonly="readonly" />
              </el-form-item>
            </el-col>
            <el-col :span="8">

            </el-col>
        </el-row>
        <el-row>
            <el-col :span="8">
              <el-form-item label="仓库" prop="warehouseName">
                <el-input v-model="form.warehouseName" readonly="readonly" />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="库区" prop="locationName">
                <el-input v-model="form.locationName" readonly="readonly" />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="库位" prop="areaName">
                <el-input v-model="form.areaName" readonly="readonly" />
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
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listRtvendorline, getRtvendorline, delRtvendorline, addRtvendorline, updateRtvendorline } from "@/api/mes/wm/rtvendorline";
import StockSelect from "@/components/stockSelect/single.vue"
export default {
  name: "Rtvendorline",
  components:{StockSelect},
  props:{
    optType: null,
    rtId: null,
    batchCode: null,
    vendorId: null
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
      // 供应商退货行表格数据
      rtvendorlineList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        rtId: this.rtId,
        itemId: null,
        itemCode: null,
        itemName: null,
        specification: null,
        unitOfMeasure: null,
        quantityRted: null,
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
      },
      // 表单参数
      form: {
        rtId: this.rtId
      },
      // 表单校验
      rules: {
        itemId: [
          { required: true, message: "产品物料ID不能为空", trigger: "blur" }
        ],
        quantityRted: [
          { required: true, message: "退货数量不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询供应商退货行列表 */
    getList() {
      this.loading = true;
      listRtvendorline(this.queryParams).then(response => {
        this.rtvendorlineList = response.rows;
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
        rtId: this.rtId,
        itemId: null,
        itemCode: null,
        itemName: null,
        specification: null,
        unitOfMeasure: null,
        quantityRted: null,
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
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加供应商退货行";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const lineId = row.lineId || this.ids
      getRtvendorline(lineId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改供应商退货行";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.lineId != null) {
            updateRtvendorline(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRtvendorline(this.form).then(response => {
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
      const lineIds = row.lineId || this.ids;
      this.$modal.confirm('是否确认删除供应商退货行编号为"' + lineIds + '"的数据项？').then(function() {
        return delRtvendorline(lineIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    handleSelectStock(){
      this.$refs.stockSelect.showFlag = true;
      this.$refs.stockSelect.getList();
    },
    //物料选择弹出框
    onStockSelected(obj){
        if(obj != undefined && obj != null){
          this.form.materialStockId = obj.materialStockId;
          this.form.itemId = obj.itemId;
          this.form.itemCode = obj.itemCode;
          this.form.itemName = obj.itemName;
          this.form.specification = obj.specification;
          this.form.unitOfMeasure = obj.unitOfMeasure;  
          this.form.warehouseId = obj.warehouseId;
          this.form.warehouseCode = obj.warehouseCode;
          this.form.warehouseName = obj.warehouseName;
          this.form.locationId = obj.locationId;
          this.form.locationCode = obj.locationCode;
          this.form.locationName = obj.locationName;
          this.form.areaId = obj.areaId;
          this.form.areaCode = obj.areaCode;
          this.form.areaName = obj.areaName;
          this.form.quantityRted = obj.quantityOnhand;
          this.form.quantityMax = obj.quantityOnhand;
        }
    },
  }
};
</script>
