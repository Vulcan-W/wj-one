<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-row>
        <el-col :span="8">
          <el-form-item label="供应商编码" prop="vendorCode">
            <el-input
              v-model="queryParams.vendorCode"
              placeholder="请输入供应商编码"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="供应商名称" prop="vendorName">
            <el-input
              v-model="queryParams.vendorName"
              placeholder="请输入供应商名称"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="供应商简称" prop="vendorNick">
            <el-input
              v-model="queryParams.vendorNick"
              placeholder="请输入供应商简称"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="英文名称" prop="vendorEn">
            <el-input
              v-model="queryParams.vendorEn"
              placeholder="请输入供应商英文名称"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="是否启用" prop="enableFlag">
            <el-select
              v-model="queryParams.enableFlag"
              placeholder="是否启用"
              clearable
              style="width: 215px"
            >
              <el-option
                v-for="dict in dict.type.sys_yes_no"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['md:vendor:add']"
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
          v-hasPermi="['md:vendor:edit']"
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
          v-hasPermi="['md:vendor:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['md:vendor:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="vendorList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="供应商编码" align="center" prop="vendorCode" >
          <template slot-scope="scope">
                <el-button              
                  type="text"
                  @click="handleView(scope.row)"
                  v-hasPermi="['mes:md:vendor:query']"
                >{{scope.row.vendorCode}}</el-button>
          </template>
      </el-table-column>
      <el-table-column label="供应商名称" align="center" prop="vendorName" width="150px" :show-overflow-tooltip="true"/>
      <el-table-column label="供应商简称" align="center" prop="vendorNick" />
      <el-table-column label="供应商等级" align="center" prop="vendorLevel" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.mes_vendor_level" :value="scope.row.vendorLevel"/>
        </template>
      </el-table-column>
      <el-table-column label="供应商评分" align="center" prop="vendorScore" />
      <el-table-column label="供应商电话" align="center" prop="tel" />
      <el-table-column label="是否启用" align="center" prop="enableFlag" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.enableFlag"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" :show-overflow-tooltip="true"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['md:vendor:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['md:vendor:remove']"
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

    <!-- 添加或修改供应商对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="960px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="供应商编码" prop="vendorCode">
              <el-input v-model="form.vendorCode" placeholder="请输入供应商编码" />
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item  label-width="80">
              <el-switch v-model="autoGenFlag"
                  active-color="#13ce66"
                  active-text="自动生成"
                  @change="handleAutoGenChange(autoGenFlag)" v-if="optType != 'view'">               
              </el-switch>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="供应商名称" prop="vendorName">
              <el-input v-model="form.vendorName" placeholder="请输入供应商名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="供应商简称" prop="vendorNick">
              <el-input v-model="form.vendorNick" placeholder="请输入供应商简称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="供应商英文名称" prop="vendorEn">
              <el-input v-model="form.vendorEn" placeholder="请输入供应商英文名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="供应商简介" prop="vendorDes">
              <el-input v-model="form.vendorDes" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="供应商地址" prop="address">
              <el-input v-model="form.address" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="供应商等级" prop="vendorLevel">
              <el-select v-model="form.vendorLevel" placeholder="请选择供应商等级">
              <el-option
                  v-for="dict in dict.type.mes_vendor_level"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="供应商评分" prop="vendorScore">
              <el-input-number :min="0" :max="100" v-model="form.vendorScore" placeholder="请输入供应商评分" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="供应商官网地址" prop="website">
              <el-input v-model="form.website" placeholder="请输入供应商官网地址" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="供应商邮箱地址" prop="email">
              <el-input v-model="form.email" placeholder="请输入供应商邮箱地址" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="供应商电话" prop="tel">
              <el-input v-model="form.tel" placeholder="请输入供应商电话" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="联系人1" prop="contact1">
              <el-input v-model="form.contact1" placeholder="请输入联系人1" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="联系人1-电话" prop="contact1Tel">
              <el-input v-model="form.contact1Tel" placeholder="请输入联系人1-电话" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="联系人1-邮箱" prop="contact1Email">
              <el-input v-model="form.contact1Email" placeholder="请输入联系人1-邮箱" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="联系人2" prop="contact2">
              <el-input v-model="form.contact2" placeholder="请输入联系人2" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="联系人2-电话" prop="contact2Tel">
              <el-input v-model="form.contact2Tel" placeholder="请输入联系人2-电话" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="联系人2-邮箱" prop="contact2Email">
              <el-input v-model="form.contact2Email" placeholder="请输入联系人2-邮箱" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="统一社会信用代码" prop="creditCode">
              <el-input v-model="form.creditCode" placeholder="请输入统一社会信用代码" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="是否启用" prop="enableFlag">
               <el-radio-group v-model="form.enableFlag" disabled v-if="optType=='view'">
                <el-radio
                  v-for="dict in dict.type.sys_yes_no"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
              <el-radio-group v-model="form.enableFlag" v-else>
                <el-radio
                  v-for="dict in dict.type.sys_yes_no"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="供应商LOGO地址" prop="vendorLogo">
              <el-input v-model="form.vendorLogo" placeholder="请输入供应商LOGO地址" />
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
import { listVendor, getVendor, delVendor, addVendor, updateVendor } from "@/api/mes/md/vendor";
import {genCode} from "@/api/system/autocode/rule"
export default {
  name: "Vendor",
  dicts: ['sys_yes_no','mes_vendor_level'],
  data() {
    return {
      //自动生成编码
      autoGenFlag:false,
      optType: undefined,
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
      // 供应商表格数据
      vendorList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        vendorCode: null,
        vendorName: null,
        vendorNick: null,
        vendorEn: null,
        vendorDes: null,
        vendorLogo: null,
        vendorLevel: null,
        vendorScore: null,
        address: null,
        website: null,
        email: null,
        tel: null,
        contact1: null,
        contact1Tel: null,
        contact1Email: null,
        contact2: null,
        contact2Tel: null,
        contact2Email: null,
        creditCode: null,
        enableFlag: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        vendorCode: [
          { required: true, message: "供应商编码不能为空", trigger: "blur" }
        ],
        vendorName: [
          { required: true, message: "供应商名称不能为空", trigger: "blur" }
        ],
        enableFlag: [
          { required: true, message: "是否启用不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询供应商列表 */
    getList() {
      this.loading = true;
      listVendor(this.queryParams).then(response => {
        this.vendorList = response.rows;
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
        vendorId: null,
        vendorCode: null,
        vendorName: null,
        vendorNick: null,
        vendorEn: null,
        vendorDes: null,
        vendorLogo: null,
        vendorLevel: "B",
        vendorScore: null,
        address: null,
        website: null,
        email: null,
        tel: null,
        contact1: null,
        contact1Tel: null,
        contact1Email: null,
        contact2: null,
        contact2Tel: null,
        contact2Email: null,
        creditCode: null,
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
      this.ids = selection.map(item => item.vendorId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加供应商";
      this.optType = "add";
    },
    // 查询明细按钮操作
    handleView(row){
      this.reset();
      const vendorId = row.vendorId || this.ids
      getVendor(vendorId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "查看供应商";
        this.optType = "view";
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const vendorId = row.vendorId || this.ids
      getVendor(vendorId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改供应商";
        this.optType = "edit";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.vendorId != null) {
            updateVendor(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addVendor(this.form).then(response => {
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
      const vendorIds = row.vendorId || this.ids;
      this.$modal.confirm('是否确认删除供应商编号为"' + vendorIds + '"的数据项？').then(function() {
        return delVendor(vendorIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('md/vendor/export', {
        ...this.queryParams
      }, `vendor_${new Date().getTime()}.xlsx`)
    },
    //自动生成编码
    handleAutoGenChange(autoGenFlag){
      debugger;
      if(autoGenFlag){
        genCode('VENDOR_CODE').then(response =>{
          this.form.vendorCode = response;
        });
      }else{
        this.form.vendorCode = null;
      }      
    }
  }
};
</script>
