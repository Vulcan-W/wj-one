<template>
  <div class="app-container">
    <el-table v-loading="loading" :data="workstationworkerList" @selection-change="handleSelectionChange">
      <el-table-column label="岗位名称" align="center" prop="postName" />
      <el-table-column label="数量" align="center" prop="quantity" />
      <el-table-column label="操作" align="center" v-if="optType !='view'" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['mes:md:workstationworker:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['mes:md:workstationworker:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <!-- 添加或修改人力资源对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="岗位" prop="postId">
            <el-select v-model="form.postId"  placeholder="请选择岗位">
                <el-option
                  v-for="dict in postOptions"
                  :key="dict.postId"
                  :label="dict.postName"
                  :value="dict.postId"
                ></el-option>
            </el-select>
        </el-form-item>

        <el-form-item label="数量" prop="quantity">
          <el-input-number :min="1" v-model="form.quantity" placeholder="请输入所需人员数量" />
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
import { listWorkstationworker, getWorkstationworker, delWorkstationworker, addWorkstationworker, updateWorkstationworker } from "@/api/mes/md/workstationworker";
import {listAllPost} from "@/api/system/post";
export default {
  name: "Workstationworker",
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
      // 人力资源表格数据
      workstationworkerList: [],
      postOptions:[],      
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        workstationId: this.workstationId,
        postId: null,
        postCode: null,
        postName: null,
        quantity: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        workstationId: [
          { required: true, message: "工作站ID不能为空", trigger: "blur" }
        ],
        postId: [
          { required: true, message: "岗位ID不能为空", trigger: "blur" }
        ],
        quantity: [
          { required: true, message: "数量不能为空", trigger: "blur" }
        ],
      }
    };
  },
  props: {
    workstationId: undefined,
    optType: undefined,
  },
  created() {
    this.getList();
    this.getPostList();
  },
  methods: {
    /** 查询人力资源列表 */
    getList() {
      this.loading = true;
      listWorkstationworker(this.queryParams).then(response => {
        this.workstationworkerList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getPostList(){
      listAllPost().then(response =>{
        this.postOptions = response.data;
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
        workstationId: this.workstationId,
        postId: null,
        postCode: null,
        postName: null,
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
      this.title = "添加人力资源";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const recordId = row.recordId || this.ids
      getWorkstationworker(recordId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改人力资源";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.recordId != null) {
            updateWorkstationworker(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addWorkstationworker(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除此岗位资源').then(function() {
        return delWorkstationworker(recordIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    }
  }
};
</script>
