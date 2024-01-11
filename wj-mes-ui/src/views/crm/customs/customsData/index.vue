<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="商品编码" prop="productCode">
        <el-input
          v-model="queryParams.productCode"
          placeholder="请输入商品编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="商品名称" prop="productName">
        <el-input
          v-model="queryParams.productName"
          placeholder="请输入商品名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="贸易伙伴编码" prop="tradePartnerCode">
        <el-input
          v-model="queryParams.tradePartnerCode"
          placeholder="请输入贸易伙伴编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="贸易伙伴名称" prop="tradePartnerName">
        <el-input
          v-model="queryParams.tradePartnerName"
          placeholder="请输入贸易伙伴名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="贸易方式编码
" prop="tradeMethodCode">
        <el-input
          v-model="queryParams.tradeMethodCode"
          placeholder="请输入贸易方式编码
"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="贸易方式名称" prop="tradeMethodName">
        <el-input
          v-model="queryParams.tradeMethodName"
          placeholder="请输入贸易方式名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="注册地编码" prop="locationCode">
        <el-input
          v-model="queryParams.locationCode"
          placeholder="请输入注册地编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="注册地名称" prop="locationName">
        <el-input
          v-model="queryParams.locationName"
          placeholder="请输入注册地名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="第一数量" prop="firstQuantity">
        <el-input
          v-model="queryParams.firstQuantity"
          placeholder="请输入第一数量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="第一计量单位
" prop="firstMeasurementUnit">
        <el-input
          v-model="queryParams.firstMeasurementUnit"
          placeholder="请输入第一计量单位
"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="第二数量" prop="secondQuantity">
        <el-input
          v-model="queryParams.secondQuantity"
          placeholder="请输入第二数量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="第二计量单位" prop="secondMeasurementUnit">
        <el-input
          v-model="queryParams.secondMeasurementUnit"
          placeholder="请输入第二计量单位"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="金额/人民币" prop="tradeAmount">
        <el-input
          v-model="queryParams.tradeAmount"
          placeholder="请输入金额/人民币"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['crm:customs:customsData:add']"
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
          v-hasPermi="['crm:customs:customsData:edit']"
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
          v-hasPermi="['crm:customs:customsData:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['crm:customs:customsData:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="customsDataList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" />
      <el-table-column label="商品编码" align="center" prop="productCode" />
      <el-table-column label="商品名称" align="center" prop="productName" />
      <el-table-column label="贸易伙伴编码" align="center" prop="tradePartnerCode" />
      <el-table-column label="贸易伙伴名称" align="center" prop="tradePartnerName" />
      <el-table-column label="贸易方式编码
" align="center" prop="tradeMethodCode" />
      <el-table-column label="贸易方式名称" align="center" prop="tradeMethodName" />
      <el-table-column label="注册地编码" align="center" prop="locationCode" />
      <el-table-column label="注册地名称" align="center" prop="locationName" />
      <el-table-column label="第一数量" align="center" prop="firstQuantity" />
      <el-table-column label="第一计量单位
" align="center" prop="firstMeasurementUnit" />
      <el-table-column label="第二数量" align="center" prop="secondQuantity" />
      <el-table-column label="第二计量单位" align="center" prop="secondMeasurementUnit" />
      <el-table-column label="金额/人民币" align="center" prop="tradeAmount" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['crm:customs:customsData:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['crm:customs:customsData:remove']"
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

    <!-- 添加或修改海关数据对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="商品编码" prop="productCode">
          <el-input v-model="form.productCode" placeholder="请输入商品编码" />
        </el-form-item>
        <el-form-item label="商品名称" prop="productName">
          <el-input v-model="form.productName" placeholder="请输入商品名称" />
        </el-form-item>
        <el-form-item label="贸易伙伴编码" prop="tradePartnerCode">
          <el-input v-model="form.tradePartnerCode" placeholder="请输入贸易伙伴编码" />
        </el-form-item>
        <el-form-item label="贸易伙伴名称" prop="tradePartnerName">
          <el-input v-model="form.tradePartnerName" placeholder="请输入贸易伙伴名称" />
        </el-form-item>
        <el-form-item label="贸易方式编码
" prop="tradeMethodCode">
          <el-input v-model="form.tradeMethodCode" placeholder="请输入贸易方式编码
" />
        </el-form-item>
        <el-form-item label="贸易方式名称" prop="tradeMethodName">
          <el-input v-model="form.tradeMethodName" placeholder="请输入贸易方式名称" />
        </el-form-item>
        <el-form-item label="注册地编码" prop="locationCode">
          <el-input v-model="form.locationCode" placeholder="请输入注册地编码" />
        </el-form-item>
        <el-form-item label="注册地名称" prop="locationName">
          <el-input v-model="form.locationName" placeholder="请输入注册地名称" />
        </el-form-item>
        <el-form-item label="第一数量" prop="firstQuantity">
          <el-input v-model="form.firstQuantity" placeholder="请输入第一数量" />
        </el-form-item>
        <el-form-item label="第一计量单位
" prop="firstMeasurementUnit">
          <el-input v-model="form.firstMeasurementUnit" placeholder="请输入第一计量单位
" />
        </el-form-item>
        <el-form-item label="第二数量" prop="secondQuantity">
          <el-input v-model="form.secondQuantity" placeholder="请输入第二数量" />
        </el-form-item>
        <el-form-item label="第二计量单位" prop="secondMeasurementUnit">
          <el-input v-model="form.secondMeasurementUnit" placeholder="请输入第二计量单位" />
        </el-form-item>
        <el-form-item label="金额/人民币" prop="tradeAmount">
          <el-input v-model="form.tradeAmount" placeholder="请输入金额/人民币" />
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
import { listCustomsData, getCustomsData, delCustomsData, addCustomsData, updateCustomsData } from "@/api/crm/customsData";

export default {
  name: "CustomsData",
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
      // 海关数据表格数据
      customsDataList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        productCode: null,
        productName: null,
        tradePartnerCode: null,
        tradePartnerName: null,
        tradeMethodCode: null,
        tradeMethodName: null,
        locationCode: null,
        locationName: null,
        firstQuantity: null,
        firstMeasurementUnit: null,
        secondQuantity: null,
        secondMeasurementUnit: null,
        tradeAmount: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询海关数据列表 */
    getList() {
      this.loading = true;
      listCustomsData(this.queryParams).then(response => {
        this.customsDataList = response.rows;
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
        id: null,
        productCode: null,
        productName: null,
        tradePartnerCode: null,
        tradePartnerName: null,
        tradeMethodCode: null,
        tradeMethodName: null,
        locationCode: null,
        locationName: null,
        firstQuantity: null,
        firstMeasurementUnit: null,
        secondQuantity: null,
        secondMeasurementUnit: null,
        tradeAmount: null
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加海关数据";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCustomsData(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改海关数据";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCustomsData(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCustomsData(this.form).then(response => {
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
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除海关数据编号为"' + ids + '"的数据项？').then(function() {
        return delCustomsData(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('crm/customs/customsData/export', {
        ...this.queryParams
      }, `customsData_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
