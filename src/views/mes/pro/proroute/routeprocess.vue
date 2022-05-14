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
          v-hasPermi="['pro:routeprocess:add']"
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
          v-hasPermi="['pro:routeprocess:edit']"
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
          v-hasPermi="['pro:routeprocess:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="routeprocessList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" fixed prop="orderNum" />
      <el-table-column label="工序编码" width="120px" fixed align="center" prop="processCode" />
      <el-table-column label="工序名称" width="150px" fixed align="center" prop="processName" />

      <el-table-column label="下一道工序" width="150px"  align="center" prop="nextProcessName" />
      <el-table-column label="与下一道工序关系" width="150px" align="center" prop="linkType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.mes_link_type" :value="scope.row.linkType"/>
        </template>
      </el-table-column>
      <el-table-column label="准备时间" align="center" prop="defaultPreTime" />
      <el-table-column label="等待时间" align="center" prop="defaultSufTime" />
      <el-table-column label="甘特图显示颜色" align="center" prop="colorCode" >
        <template slot-scope="scope">
            <el-color-picker v-model="scope.row.colorCode" disabled></el-color-picker>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" fixed="right" width="150px" v-if="optType !='view'" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['mes:pro:routeprocess:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['mes:pro:routeprocess:remove']"
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

    <!-- 添加或修改工艺组成对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="960px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="150px">
        <el-row>
            <el-col :span="12">
                <el-form-item label="序号" prop="orderNum">
                    <el-input-number :min="1" v-model="form.orderNum" placeholder="请输入序号" />
                </el-form-item>
            </el-col>
            <el-col :span="12">
                <el-form-item label="工序" prop="processId">
                    <el-select v-model="form.processId" placeholder="请选择工序">
                        <el-option
                        v-for="item in processOptions"
                        :key="item.processId"
                        :label="item.processName"
                        :value="item.processId"
                        ></el-option>
                    </el-select>
                </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="12">
                <el-form-item label="与下一道工序关系" prop="linkType">
                    <el-tooltip effect="dark" placement="right">
                    <div slot="content">
                        S-to-S：当前工序开始生产，下一道工序才可开始生产 </br>
                        F-to-F：当前工序结束生产，下一道工序才可结束生产 </br>
                        S-to-F：当前工序开始生产，下一道工序才可结束生产 </br>
                        F-to-S：当前工序结束生产，下一道工序才可开始生产
                    </div>
                    <el-select v-model="form.linkType" placeholder="请选择与下一道工序关系">
                        <el-option
                        v-for="dict in dict.type.mes_link_type"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                        ></el-option>
                    </el-select>
                    </el-tooltip>
                </el-form-item>
            </el-col>
            <el-col :span="5">
                <el-form-item label="甘特图显示颜色" prop="colorCode">
                    <el-color-picker v-model="form.colorCode"></el-color-picker>                        
                </el-form-item>
            </el-col>
            <el-col :span="7">
                    <el-input v-model="form.colorCode" maxlength="7" placeholder="请输入颜色编码在左侧选择颜色" />                
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="12">
                <el-form-item label="准备时间" prop="defaultPreTime">
                    <el-tooltip effect="dark" content="当前系统支持的最小时间粒度为1小时" placement="right">
                        <el-input-number :min="0" :step="1" v-model="form.defaultPreTime" placeholder="请输入准备时间" />
                    </el-tooltip>
                </el-form-item>
            </el-col>
            <el-col :span="12">
                <el-form-item label="等待时间" prop="defaultSufTime">
                    <el-input-number :min="0" :step="1" v-model="form.defaultSufTime" placeholder="请输入等待时间" />
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
import { listRouteprocess, getRouteprocess, delRouteprocess, addRouteprocess, updateRouteprocess } from "@/api/mes/pro/routeprocess";
import {listAllProcess} from "@/api/mes/pro/process";
export default {
  name: "Routeprocess",
  dicts: ['mes_link_type'],
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
      // 工艺组成表格数据
      routeprocessList: [],
      //工序选项
      processOptions:[],    
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        routeId: this.routeId,
        processId: null,
        processCode: null,
        processName: null,
        orderNum: null,
        nextProcessId: null,
        nextProcessCode: null,
        nextProcessName: null,
        linkType: null,
        defaultPreTime: null,
        defaultSufTime: null,
        colorCode: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        routeId: [
          { required: true, message: "工艺路线ID不能为空", trigger: "blur" }
        ],
        processId: [
          { required: true, message: "工序ID不能为空", trigger: "blur" }
        ],
        nextProcessId: [
          { required: true, message: "工序ID不能为空", trigger: "blur" }
        ],
      }
    };
  },
  props :{
      routeId: undefined,
      optType: undefined
  },
  created() {
    this.getList();
    this.getProcess();
  },
  methods: {
    /** 查询工艺组成列表 */
    getList() {
      this.loading = true;
      listRouteprocess(this.queryParams).then(response => {
        this.routeprocessList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    //查询工序信息
    getProcess(){
      listAllProcess().then( response =>{
        this.processOptions = response.data;
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
        routeId: this.routeId,
        processId: null,
        processCode: null,
        processName: null,
        orderNum: 1,
        nextProcessId: null,
        nextProcessCode: null,
        nextProcessName: null,
        linkType: null,
        defaultPreTime: 0,
        defaultSufTime: 0,
        colorCode: '#00AEF3',
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
      this.ids = selection.map(item => item.recordId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加工艺组成";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const recordId = row.recordId || this.ids
      getRouteprocess(recordId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改工艺组成";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.recordId != null) {
            updateRouteprocess(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRouteprocess(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除工艺组成编号为"' + recordIds + '"的数据项？').then(function() {
        return delRouteprocess(recordIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('mes/pro/routeprocess/export', {
        ...this.queryParams
      }, `routeprocess_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
