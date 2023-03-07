<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="库位编码" prop="areaCode">
            <el-input
              v-model="queryParams.areaCode"
              placeholder="请输入库位编码"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="库位名称" prop="areaName">
            <el-input
              v-model="queryParams.areaName"
              placeholder="请输入库位名称"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="库位位置" prop="positionX">
            <el-row>
              <el-col :span="8">
                <el-input-number
                  :min="0"
                  :step="1"
                  v-model="queryParams.positionX"
                  placeholder="请输入库位位置X"
                  clearable
                  @keyup.enter.native="handleQuery"
                />
              </el-col>
              <el-col :span="8">
                <el-input-number
                  :min="0"
                  :step="1"
                  v-model="queryParams.positionY"
                  placeholder="请输入库位位置y"
                  clearable
                  @keyup.enter.native="handleQuery"
                />
              </el-col>
              <el-col :span="8">
                <el-input-number
                  :min="0"
                  :step="1"
                  v-model="queryParams.positionZ"
                  placeholder="请输入库位位置z"
                  clearable
                  @keyup.enter.native="handleQuery"
                />
              </el-col>
            </el-row>
          </el-form-item>
        </el-col>
        <el-col :span="12">
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
          v-hasPermi="['mes:wm:area:add']"
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
          v-hasPermi="['mes:wm:area:edit']"
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
          v-hasPermi="['mes:wm:area:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="areaList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="库位编码" align="center" prop="areaCode" >
        <template slot-scope="scope">
          <el-button
            type="text"
            @click="handleView(scope.row)"
            v-hasPermi="['mes:wm:area:query']"
          >{{scope.row.areaCode}}</el-button>
        </template>
      </el-table-column>
      <el-table-column label="库位名称" align="center" prop="areaName" />
      <el-table-column label="面积" align="center" prop="area" />
      <el-table-column label="最大载重量" align="center" prop="maxLoa" />
      <el-table-column label="库位位置X" align="center" prop="positionX" />
      <el-table-column label="库位位置y" align="center" prop="positionY" />
      <el-table-column label="库位位置z" align="center" prop="positionZ" />
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
            v-hasPermi="['wm:area:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['wm:area:remove']"
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

    <!-- 添加或修改库位设置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="960px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="库位编码" prop="areaCode">
              <el-input v-model="form.areaCode" placeholder="请输入库位编码" />
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
            <el-form-item label="库位名称" prop="areaName">
              <el-input v-model="form.areaName" placeholder="请输入库位名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="面积" prop="area">
              <el-input-number :min="0" :step="1" :percision="2" v-model="form.area" placeholder="请输入面积" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="最大载重量" prop="maxLoa">
              <el-input-number v-model="form.maxLoa" :step="1" :percision="2" placeholder="请输入最大载重量" />
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
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="库位位置X" prop="positionX">
              <el-input-number :min="0" :step="1" v-model="form.positionX" placeholder="请输入库位位置X" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="库位位置y" prop="positionY">
              <el-input-number :min="0" :step="1" v-model="form.positionY" placeholder="请输入库位位置y" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="库位位置z" prop="positionZ">
              <el-input-number :min="0" :step="1" v-model="form.positionZ" placeholder="请输入库位位置z" />
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
import { listArea, getArea, delArea, addArea, updateArea } from "@/api/mes/wm/area";
import {genCode} from "@/api/system/autocode/rule"
export default {
  name: "Area",
  dicts: ['sys_yes_no'],
  data() {
    return {
      //自动生成编码
      autoGenFlag:false,
      optType: undefined,
      locationId: undefined,
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
      // 库位设置表格数据
      areaList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        areaCode: null,
        areaName: null,
        locationId: null,
        area: null,
        maxLoa: null,
        positionX: null,
        positionY: null,
        positionZ: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        areaCode: [
          { required: true, message: "库位编码不能为空", trigger: "blur" }
        ],
        areaName: [
          { required: true, message: "库位名称不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    //从上级页面获取的规则ID
    const locationId = this.$route.query.locationId;
    this.locationId = locationId;
    this.queryParams.locationId = locationId;
    this.getList();
  },
  methods: {
    /** 查询库位设置列表 */
    getList() {
      this.loading = true;
      listArea(this.queryParams).then(response => {
        this.areaList = response.rows;
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
        areaId: null,
        areaCode: null,
        areaName: null,
        locationId: this.locationId,
        area: null,
        maxLoa: null,
        positionX: null,
        positionY: null,
        positionZ: null,
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
      this.ids = selection.map(item => item.areaId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加库位";
      this.optType = "add";
    },
    // 查询明细按钮操作
    handleView(row){
      this.reset();
      const areaId = row.areaId || this.ids
      getArea(areaId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "查看库位";
        this.optType = "view";
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const areaId = row.areaId || this.ids
      getArea(areaId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改库位";
        this.optType = "edit";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.areaId != null) {
            updateArea(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addArea(this.form).then(response => {
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
      const areaIds = row.areaId || this.ids;
      this.$modal.confirm('是否确认删除库位设置编号为"' + areaIds + '"的数据项？').then(function() {
        return delArea(areaIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('mes/wm/area/export', {
        ...this.queryParams
      }, `area_${new Date().getTime()}.xlsx`)
    },
    //自动生成编码
    handleAutoGenChange(autoGenFlag){
      if(autoGenFlag){
        genCode('AREA_CODE').then(response =>{
          this.form.areaCode = response;
        });
      }else{
        this.form.areaCode = null;
      }      
    }
  }
};
</script>
