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
          v-hasPermi="['mes:cal:team:add']"
        >新增</el-button>
        <UserMultiSelect ref="userSelect" @onSelected="userSelected"></UserMultiSelect>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['mes:cal:team:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['mes:cal:team:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="teammemberList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="用户名" align="center" prop="userName" />
      <el-table-column label="用户昵称" align="center" prop="nickName" />
      <el-table-column label="电话" align="center" prop="tel" />
      <el-table-column label="操作" align="center" v-if="optType !='view'" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['cal:teammember:remove']"
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
  </div>
</template>

<script>
import { listTeammember, getTeammember, delTeammember, addTeammember } from "@/api/mes/cal/teammember";
import UserMultiSelect from "@/components/userSelect/multi.vue"
export default {
  name: "Teammember",
  components: {UserMultiSelect},
  props:{
    teamId:null,
    optType:null
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
      // 班组成员表格数据
      teammemberList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        teamId: this.teamId,
        userId: null,
        userName: null,
        nickName: null,
        tel: null,
      },
      // 表单参数
      form: {},
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询班组成员列表 */
    getList() {
      this.loading = true;
      listTeammember(this.queryParams).then(response => {
        this.teammemberList = response.rows;
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
        memberId: null,
        teamId: null,
        userId: null,
        userName: null,
        nickName: null,
        tel: null,
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
      this.ids = selection.map(item => item.memberId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.$refs.userSelect.showFlag = true;
      this.open = true;
      this.title = "添加班组成员";
    },
    userSelected(rows){
      if(rows !=null){
        rows.forEach(user => {
          this.form.teamId = this.teamId;
          this.form.userId = user.userId;
          this.form.userName = user.userName;
          this.form.nickName = user.nickName;
          this.form.tel = user.phonenumber;  
          addTeammember(this.form).then(response => {              
            this.getList();  
          });
        });
      }
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const memberIds = row.memberId || this.ids;
      this.$modal.confirm('是否确认删除班组成员？').then(function() {
        return delTeammember(memberIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('mes/cal/teammember/export', {
        ...this.queryParams
      }, `teammember_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
