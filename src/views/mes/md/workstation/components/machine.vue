<template>
  <div class="app-container">
    <el-table v-loading="loading" :data="workstationmachineList">
      <el-table-column label="设备编号" align="center" prop="machineryCode" />
      <el-table-column label="设备名称" align="center" width="150px" prop="machineryName" />
      <el-table-column label="数量" width="50px" align="center" prop="quantity" />
      <el-table-column label="操作" v-if="optType !='view'" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['md:workstationmachine:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { listWorkstationmachine, getWorkstationmachine, delWorkstationmachine, addWorkstationmachine, updateWorkstationmachine } from "@/api/mes/md/workstationmachine";

export default {
  name: "Workstationmachine",
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
      // 设备资源表格数据
      workstationmachineList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        workstationId: null,
        machineId: null,
        machineCode: null,
        machineName: null,
        quantity: null,
      },
      // 表单参数
      form: {}
    };
  },
  props: {
      workstationId: undefined,
      optType: undefined,
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询设备资源列表 */
    getList() {
      this.loading = true;
      this.queryParams.workstationId = this.workstationId;
      listWorkstationmachine(this.queryParams).then(response => {
        this.workstationmachineList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },

    // 表单重置
    reset() {
      this.form = {
        recordId: null,
        workstationId: null,
        machineId: null,
        machineCode: null,
        machineName: null,
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
      this.title = "添加设备资源";
    },

    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.recordId != null) {
            updateWorkstationmachine(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addWorkstationmachine(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除设备资源？').then(function() {
        return delWorkstationmachine(recordIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    }
  }
};
</script>
