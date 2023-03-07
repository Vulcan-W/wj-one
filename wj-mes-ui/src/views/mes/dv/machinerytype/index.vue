<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="类型名称" prop="machineryTypeName">
        <el-input
          v-model="queryParams.machineryTypeName"
          placeholder="请输入设备类型名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否启用" prop="enableFlag">
        <el-select v-model="queryParams.enableFlag" placeholder="选择是或否" clearable>
          <el-option
            v-for="dict in dict.type.sys_yes_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
          v-hasPermi="['mes:dv:machinerytype:add']"
        >新增</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="machinerytypeList"
      row-key="machineryTypeId"
      default-expand-all
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column label="设备类型编码" prop="machineryTypeCode" />
      <el-table-column label="设备类型名称" align="center" prop="machineryTypeName" />
      <el-table-column label="是否启用" align="center" prop="enableFlag" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.enableFlag"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['dv:machinerytype:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAdd(scope.row)"
            v-hasPermi="['dv:machinerytype:add']"
          >新增</el-button>
          <el-button
            v-if="scope.row.parentTypeId != 0"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['dv:machinerytype:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改设备类型对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="24" v-if="form.parentTypeId !== 0">
            <el-form-item label="父类型" prop="parentTypeId">
              <treeselect v-model="form.parentTypeId" :options="machinerytypeOptions" :normalizer="normalizer" placeholder="请选择父类型" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="设备类型名称" prop="machineryTypeName">
              <el-input v-model="form.machineryTypeName" placeholder="请输入设备类型名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
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
import { listMachinerytype, getMachinerytype, delMachinerytype, addMachinerytype, updateMachinerytype } from "@/api/mes/dv/machinerytype";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Machinerytype",
  dicts: ['sys_yes_no'],
  components: {
    Treeselect
  },
  data() {
    return {
      optType:undefined,
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 设备类型表格数据
      machinerytypeList: [],
      // 设备类型树选项
      machinerytypeOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        machineryTypeCode: null,
        machineryTypeName: null,
        parentTypeId: null,
        ancestors: null,
        enableFlag: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        parentTypeId: [
          { required: true, message: "父类型不能为空", trigger: "blur" }
        ],
        machineryTypeName: [
          { required: true, message: "设备类型名称不能为空", trigger: "blur" }
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
    /** 查询设备类型列表 */
    getList() {
      this.loading = true;
      listMachinerytype(this.queryParams).then(response => {
        this.machinerytypeList = this.handleTree(response.data, "machineryTypeId", "parentTypeId");
        this.loading = false;
      });
    },
    /** 转换设备类型数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.machineryTypeId,
        label: node.machineryTypeName,
        children: node.children
      };
    },
	/** 查询设备类型下拉树结构 */
    getTreeselect() {
      listMachinerytype().then(response => {
        debugger;
        this.machinerytypeOptions = [];
        const data = this.handleTree(response.data, "machineryTypeId", "parentTypeId")[0];
        this.machinerytypeOptions.push(data);
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
        machineryTypeId: null,
        machineryTypeName: null,
        parentTypeId: 1,
        ancestors: null,
        enableFlag: 'Y',
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
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      this.reset();
      this.getTreeselect();
      if (row != null && row.machineryTypeId) {
        this.form.parentTypeId = row.machineryTypeId;
      } else {
        this.form.parentTypeId = 0;
      }
      this.open = true;
      this.title = "添加设备类型";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getTreeselect();
      if (row != null) {
        this.form.parentTypeId = row.machineryTypeId;
      }
      getMachinerytype(row.machineryTypeId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改设备类型";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.machineryTypeId != null) {
            updateMachinerytype(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMachinerytype(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除设备类型编号为"' + row.machineryTypeId + '"的数据项？').then(function() {
        return delMachinerytype(row.machineryTypeId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    }
  }
};
</script>
