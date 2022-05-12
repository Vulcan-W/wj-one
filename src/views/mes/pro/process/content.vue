<template>
  <div class="app-container">
    <el-row v-if="optType !='view'" :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['mes:pro:processcontent:add']"
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
          v-hasPermi="['mes:pro:processcontent:edit']"
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
          v-hasPermi="['mes:pro:processcontent:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['mes:pro:processcontent:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="processcontentList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="顺序编号" align="center" prop="orderNum" />
      <el-table-column label="步骤说明" width="400px" align="center" prop="contentText" :show-overflow-tooltip="true"/>
      <el-table-column label="辅助设备" align="center" prop="device" :show-overflow-tooltip="true"/>
      <el-table-column label="辅助材料" align="center" prop="material" :show-overflow-tooltip="true"/>
      <el-table-column label="备注" align="center" prop="remark" :show-overflow-tooltip="true"/>
      <el-table-column label="操作" width="100px" v-if="optType !='view'" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['mes:pro:processcontent:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['mes:pro:processcontent:remove']"
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

    <!-- 添加或修改生产工序内容对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="960px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row >
            <el-col :span="12">
                <el-form-item label="顺序编号" prop="orderNum">
                    <el-input-number :min="1" v-model="form.orderNum" placeholder="请输入顺序编号" />
                </el-form-item>
            </el-col>
            <el-col :span="12">
                <el-form-item label="材料URL" prop="docUrl">
                    <el-input v-model="form.docUrl" placeholder="请输入材料URL" />
                </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="12">
                <el-form-item label="辅助设备" prop="device">
                    <el-input v-model="form.device" placeholder="请输入辅助设备" />
                </el-form-item>
            </el-col>
            <el-col :span="12">
                <el-form-item label="辅助材料" prop="material">
                    <el-input v-model="form.material" placeholder="请输入辅助材料" />
                </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <el-form-item label="步骤说明" prop="contentText">
                    <el-input v-model="form.contentText" type="textarea" placeholder="请输入内容" />
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
import { listProcesscontent, getProcesscontent, delProcesscontent, addProcesscontent, updateProcesscontent } from "@/api/mes/pro/processcontent";

export default {
  name: "Processcontent",
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
      // 生产工序内容表格数据
      processcontentList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        processId: this.processId,
        orderNum: null,
        contentText: null,
        device: null,
        material: null,
        docUrl: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        processId: [
          { required: true, message: "工序不能为空", trigger: "blur" }
        ],
      }
    };
  },
  props :{
      processId: undefined,
      optType: undefined
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询生产工序内容列表 */
    getList() {
      this.loading = true;
      listProcesscontent(this.queryParams).then(response => {
        this.processcontentList = response.rows;
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
        contentId: null,
        processId: this.processId,
        orderNum: null,
        contentText: null,
        device: null,
        material: null,
        docUrl: null,
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
      this.ids = selection.map(item => item.contentId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加操作步骤";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const contentId = row.contentId || this.ids
      getProcesscontent(contentId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改操作步骤";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.contentId != null) {
            updateProcesscontent(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProcesscontent(this.form).then(response => {
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
      const contentIds = row.contentId || this.ids;
      this.$modal.confirm('是否确认删除操作步骤？').then(function() {
        return delProcesscontent(contentIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('mes/pro/processcontent/export', {
        ...this.queryParams
      }, `processcontent_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
