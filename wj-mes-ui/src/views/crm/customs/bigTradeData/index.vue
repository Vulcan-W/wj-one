<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="月份" prop="dataMonth">
        <el-input
          v-model="queryParams.dataMonth"
          placeholder="请输入月份"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="数量" prop="quantity">
        <el-input
          v-model="queryParams.quantity"
          placeholder="请输入数量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="出口商" prop="exporter">
        <el-input
          v-model="queryParams.exporter"
          placeholder="请输入出口商"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="出口商所在国家" prop="exporterCountry">
        <el-input
          v-model="queryParams.exporterCountry"
          placeholder="请输入出口商所在国家"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="毛重" prop="weightGross">
        <el-input
          v-model="queryParams.weightGross"
          placeholder="请输入毛重"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="净重" prop="weight">
        <el-input
          v-model="queryParams.weight"
          placeholder="请输入净重"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="公吨" prop="tonne">
        <el-input
          v-model="queryParams.tonne"
          placeholder="请输入公吨"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="金额美元" prop="amount">
        <el-input
          v-model="queryParams.amount"
          placeholder="请输入金额美元"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="美元重量计单价" prop="priceUnitWeight">
        <el-input
          v-model="queryParams.priceUnitWeight"
          placeholder="请输入美元重量计单价"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="美元数量计单价" prop="priceUnitQuantity">
        <el-input
          v-model="queryParams.priceUnitQuantity"
          placeholder="请输入美元数量计单价"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="本国币种金额" prop="amountLocal">
        <el-input
          v-model="queryParams.amountLocal"
          placeholder="请输入本国币种金额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="合同金额" prop="amountContract">
        <el-input
          v-model="queryParams.amountContract"
          placeholder="请输入合同金额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="申报数量" prop="quantityDeclared">
        <el-input
          v-model="queryParams.quantityDeclared"
          placeholder="请输入申报数量"
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
          v-hasPermi="['customs:bigTradeData:add']"
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
          v-hasPermi="['customs:bigTradeData:edit']"
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
          v-hasPermi="['customs:bigTradeData:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['customs:bigTradeData:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="bigTradeDataList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="海关编码" align="center" prop="customsCode" />
      <el-table-column label="编码产品描述" align="center" prop="customsCodeDes" />
      <el-table-column label="交易日期" align="center" prop="tradeDate" />
      <el-table-column label="月份" align="center" prop="dataMonth" />
      <el-table-column label="进口商" align="center" prop="importer" />
      <el-table-column label="进口商所在国家" align="center" prop="importerCountry" />
      <el-table-column label="出口商" align="center" prop="exporter" />
      <el-table-column label="出口商所在国家" align="center" prop="exporterCountry" />
      <el-table-column label="重量单位" align="center" prop="weightUnit" />
      <el-table-column label="数量单位" align="center" prop="quantityUnit" />
      <el-table-column label="数量" align="center" prop="quantity" />
      <el-table-column label="毛重" align="center" prop="weightGross" />
      <el-table-column label="净重" align="center" prop="weight" />
      <el-table-column label="公吨" align="center" prop="tonne" />
      <el-table-column label="金额美元" align="center" prop="amount" />
      <el-table-column label="美元重量计单价" align="center" prop="priceUnitWeight" />
      <el-table-column label="美元数量计单价" align="center" prop="priceUnitQuantity" />
      <el-table-column label="本国币种金额" align="center" prop="amountLocal" />
      <el-table-column label="合同金额" align="center" prop="amountContract" />
      <el-table-column label="币种" align="center" prop="currency" />
      <el-table-column label="成交方式" align="center" prop="dealMethod" />
      <el-table-column label="详细产品名称" align="center" prop="productNameDetail" />
      <el-table-column label="产品规格型号品牌" align="center" prop="productModel" />
      <el-table-column label="当地港口" align="center" prop="portLocal" />
      <el-table-column label="国外港口" align="center" prop="portAbroad" />
      <el-table-column label="运输方式" align="center" prop="transportMethod" />
      <el-table-column label="贸易方式" align="center" prop="tradeMethod" />
      <el-table-column label="中转国" align="center" prop="countryTransit" />
      <el-table-column label="提单号" align="center" prop="billLadingNo" />
      <el-table-column label="编码产品描述本国语言" align="center" prop="productDesLocal" />
      <el-table-column label="详细产品名称本国语言" align="center" prop="productNameDetailLocal" />
      <el-table-column label="产品规格型号品牌本国语言" align="center" prop="productModelLocal" />
      <el-table-column label="进口商本地语言" align="center" prop="importerLocalLang" />
      <el-table-column label="数据库" align="center" prop="database" />
      <el-table-column label="出口商本地语言" align="center" prop="exporterLocalLang" />
      <el-table-column label="关单号" align="center" prop="customsClearanceNo" />
      <el-table-column label="申报数量" align="center" prop="quantityDeclared" />
      <el-table-column label="进出口" align="center" prop="importExport" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['customs:bigTradeData:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['customs:bigTradeData:remove']"
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

    <!-- 添加或修改某通数据对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="海关编码" prop="customsCode">
          <el-input v-model="form.customsCode" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="编码产品描述" prop="customsCodeDes">
          <el-input v-model="form.customsCodeDes" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="交易日期" prop="tradeDate">
          <el-input v-model="form.tradeDate" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="月份" prop="dataMonth">
          <el-input v-model="form.dataMonth" placeholder="请输入月份" />
        </el-form-item>
        <el-form-item label="进口商" prop="importer">
          <el-input v-model="form.importer" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="进口商所在国家" prop="importerCountry">
          <el-input v-model="form.importerCountry" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="出口商所在国家" prop="exporter">
          <el-input v-model="form.exporter" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="出口商" prop="exporterCountry">
          <el-input v-model="form.exporterCountry" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="重量单位" prop="weightUnit">
          <el-input v-model="form.weightUnit" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="数量单位" prop="quantityUnit">
          <el-input v-model="form.quantityUnit" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="数量" prop="quantity">
          <el-input v-model="form.quantity" placeholder="请输入数量" />
        </el-form-item>
        <el-form-item label="毛重" prop="weightGross">
          <el-input v-model="form.weightGross" placeholder="请输入毛重" />
        </el-form-item>
        <el-form-item label="净重" prop="weight">
          <el-input v-model="form.weight" placeholder="请输入净重" />
        </el-form-item>
        <el-form-item label="公吨" prop="tonne">
          <el-input v-model="form.tonne" placeholder="请输入公吨" />
        </el-form-item>
        <el-form-item label="金额美元" prop="amount">
          <el-input v-model="form.amount" placeholder="请输入金额美元" />
        </el-form-item>
        <el-form-item label="美元重量计单价" prop="priceUnitWeight">
          <el-input v-model="form.priceUnitWeight" placeholder="请输入美元重量计单价" />
        </el-form-item>
        <el-form-item label="美元数量计单价" prop="priceUnitQuantity">
          <el-input v-model="form.priceUnitQuantity" placeholder="请输入美元数量计单价" />
        </el-form-item>
        <el-form-item label="本国币种金额" prop="amountLocal">
          <el-input v-model="form.amountLocal" placeholder="请输入本国币种金额" />
        </el-form-item>
        <el-form-item label="合同金额" prop="amountContract">
          <el-input v-model="form.amountContract" placeholder="请输入合同金额" />
        </el-form-item>
        <el-form-item label="币种" prop="currency">
          <el-input v-model="form.currency" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="成交方式" prop="dealMethod">
          <el-input v-model="form.dealMethod" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="详细产品名称" prop="productNameDetail">
          <el-input v-model="form.productNameDetail" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="产品规格型号品牌" prop="productModel">
          <el-input v-model="form.productModel" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="当地港口" prop="portLocal">
          <el-input v-model="form.portLocal" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="国外港口" prop="portAbroad">
          <el-input v-model="form.portAbroad" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="运输方式" prop="transportMethod">
          <el-input v-model="form.transportMethod" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="贸易方式" prop="tradeMethod">
          <el-input v-model="form.tradeMethod" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="中转国" prop="countryTransit">
          <el-input v-model="form.countryTransit" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="提单号" prop="billLadingNo">
          <el-input v-model="form.billLadingNo" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="编码产品描述本国语言" prop="productDesLocal">
          <el-input v-model="form.productDesLocal" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="详细产品名称本国语言" prop="productNameDetailLocal">
          <el-input v-model="form.productNameDetailLocal" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="产品规格型号品牌本国语言" prop="productModelLocal">
          <el-input v-model="form.productModelLocal" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="进口商本地语言" prop="importerLocalLang">
          <el-input v-model="form.importerLocalLang" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="数据库" prop="database">
          <el-input v-model="form.database" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="出口商本地语言" prop="exporterLocalLang">
          <el-input v-model="form.exporterLocalLang" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="关单号" prop="customsClearanceNo">
          <el-input v-model="form.customsClearanceNo" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="申报数量" prop="quantityDeclared">
          <el-input v-model="form.quantityDeclared" placeholder="请输入申报数量" />
        </el-form-item>
        <el-form-item label="进出口" prop="importExport">
          <el-input v-model="form.importExport" type="textarea" placeholder="请输入内容" />
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
import { listBigTradeData, getBigTradeData, delBigTradeData, addBigTradeData, updateBigTradeData } from "@/api/crm/bigTradeData";

export default {
  name: "BigTradeData",
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
      // 某通数据表格数据
      bigTradeDataList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        customsCode: null,
        customsCodeDes: null,
        tradeDate: null,
        dataMonth: null,
        importer: null,
        importerCountry: null,
        exporter: null,
        exporterCountry: null,
        weightUnit: null,
        quantityUnit: null,
        quantity: null,
        weightGross: null,
        weight: null,
        tonne: null,
        amount: null,
        priceUnitWeight: null,
        priceUnitQuantity: null,
        amountLocal: null,
        amountContract: null,
        currency: null,
        dealMethod: null,
        productNameDetail: null,
        productModel: null,
        portLocal: null,
        portAbroad: null,
        transportMethod: null,
        tradeMethod: null,
        countryTransit: null,
        billLadingNo: null,
        productDesLocal: null,
        productNameDetailLocal: null,
        productModelLocal: null,
        importerLocalLang: null,
        database: null,
        exporterLocalLang: null,
        customsClearanceNo: null,
        quantityDeclared: null,
        importExport: null
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
    /** 查询某通数据列表 */
    getList() {
      this.loading = true;
      listBigTradeData(this.queryParams).then(response => {
        this.bigTradeDataList = response.rows;
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
        customsCode: null,
        customsCodeDes: null,
        tradeDate: null,
        dataMonth: null,
        importer: null,
        importerCountry: null,
        exporter: null,
        exporterCountry: null,
        weightUnit: null,
        quantityUnit: null,
        quantity: null,
        weightGross: null,
        weight: null,
        tonne: null,
        amount: null,
        priceUnitWeight: null,
        priceUnitQuantity: null,
        amountLocal: null,
        amountContract: null,
        currency: null,
        dealMethod: null,
        productNameDetail: null,
        productModel: null,
        portLocal: null,
        portAbroad: null,
        transportMethod: null,
        tradeMethod: null,
        countryTransit: null,
        billLadingNo: null,
        productDesLocal: null,
        productNameDetailLocal: null,
        productModelLocal: null,
        importerLocalLang: null,
        database: null,
        exporterLocalLang: null,
        customsClearanceNo: null,
        quantityDeclared: null,
        importExport: null
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
      this.title = "添加某通数据";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getBigTradeData(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改某通数据";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateBigTradeData(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBigTradeData(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除某通数据编号为"' + ids + '"的数据项？').then(function() {
        return delBigTradeData(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('customs/bigTradeData/export', {
        ...this.queryParams
      }, `bigTradeData_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
