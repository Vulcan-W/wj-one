<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['pro:protask:add']"
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
          v-hasPermi="['pro:protask:edit']"
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
          v-hasPermi="['pro:protask:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['pro:protask:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="protaskList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />      
      <el-table-column label="任务编号" align="center" width="100px" prop="taskCode" :show-overflow-tooltip="true"/>
      <el-table-column label="任务名称" align="center" width="120px" prop="taskName" :show-overflow-tooltip="true"/>
      <el-table-column label="工作站编号" align="center" width="150px" prop="workstationCode" :show-overflow-tooltip="true"/>
      <el-table-column label="工作站名称" align="center" width="150px" prop="workstationName" :show-overflow-tooltip="true"/>      
      <el-table-column label="排产数量" align="center" prop="quantity" />
      <el-table-column label="已生产数量" align="center" width="100px" prop="quantityProduced" />
      <el-table-column label="开始生产时间" align="center" prop="startTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime,'{y}-{m}-{d} {h}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="生产时长" align="center" prop="duration" />
      <el-table-column label="预计完成时间" align="center" prop="endTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime,'{y}-{m}-{d} {h}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="显示颜色" align="center" prop="colorCode" >
        <template slot-scope="scope">
          <el-color-picker v-model="scope.row.colorCode" disabled></el-color-picker>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="100px" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['pro:protask:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['pro:protask:remove']"
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

    <!-- 添加或修改生产任务对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1080px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="工作站" prop="workstationName">
              <el-input v-model="form.workstationName" placeholder="请选择工作站" >
                <el-button slot="append" icon="el-icon-search" @click="handleWorkstationSelect"></el-button>
              </el-input>
            </el-form-item>
            <WorkstationSelect ref="wsSelect" :processId="processId"  @onSelected="onWorkstationSelected"> </WorkstationSelect>
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
            <el-form-item label="排产数量" prop="quantity">
              <el-input-number :min="1" v-model="form.quantity" placeholder="请输入排产数量" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="开始时间" prop="startTime">
              <el-date-picker clearable
                v-model="form.startTime"
                @change="calculateEndTime"
                type="datetime"
                value-format="yyyy-MM-dd HH"
                placeholder="请选择开始生产时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="生产时长" prop="duration">
              <el-input v-model="form.duration" @change="calculateEndTime" placeholder="请输入生产时长" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="完成时间" prop="endTime">
              <el-date-picker clearable disabled
                v-model="form.endTime"
                type="datetime"
                value-format="yyyy-MM-dd HH">
              </el-date-picker>
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
import { listProtask, getProtask, delProtask, addProtask, updateProtask } from "@/api/mes/pro/protask";
import WorkstationSelect from "@/components/workstationSelect/simpletableSingle.vue"
export default {
  name: "Protask",
  components: {WorkstationSelect},
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
      // 生产任务表格数据
      protaskList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        taskCode: null,
        taskName: null,
        workorderId: this.workorderId,
        workorderCode: null,
        workorderName: null,
        workstationId: null,
        workstationCode: null,
        workstationName: null,
        processId: this.processId,
        processCode: null,
        processName: null,
        itemId: null,
        itemCode: null,
        itemName: null,
        specification: null,
        unitOfMeasure: null,
        quantity: null,
        quantityProduced: null,
        quantityChanged: null,
        clientId: null,
        clientCode: null,
        clientName: null,
        clientNick: null,
        startTime: null,
        duration: null,
        endTime: null,
        colorCode: null,
        requestDate: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        workstationId: [
          { required: true, message: "工作站不能为空", trigger: "blur" }
        ],
        quantity: [
          { required: true, message: "排产数量不能为空", trigger: "blur" }
        ],
        startTime: [
          { required: true, message: "请选择开始生产日期",trigger: "blur"}
        ],
        duration: [
          { required: true, message: "清输入估算的生产用时",trigger: "blur"}
        ]
      }
    };
  },
  props:{
      workorderId: null,
      colorCode: null,
      processId: null,
      optType: null
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询生产任务列表 */
    getList() {
      this.loading = true;
      listProtask(this.queryParams).then(response => {
        this.protaskList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    //计算结束时间
    calculateEndTime(){
      debugger;
      if(this.form.startTime !=null && this.form.duration !=null){
          let parts = this.form.startTime.split(' ');
          let startDate = new Date(parts[0]);
          startDate = startDate.getFullYear() + '-' 
              + (startDate.getMonth()+1) + '-'   
              + startDate.getDate() + ' '
                  + parts[1].split(':')[0] + ':' 
                  + '00' + ':' 
                  + '00';
          this.form.startTime = startDate;
          startDate = Date.parse(new Date(startDate))/1000; 
          startDate += (3600)* 8 * this.form.duration; //这里以8小时为一个单位
          let endDate = new Date(parseInt(startDate) * 1000); 
          this.form.endTime =endDate.getFullYear() + '-' 
                        + (endDate.getMonth()+1) + '-' 
                        + endDate.getDate() + ' '
                          + endDate.getHours() + ':' 
                          + endDate.getMinutes() + ':' 
                          + endDate.getSeconds();
          

      }
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        taskId: null,
        taskCode: null,
        taskName: null,
        workorderId: this.workorderId,
        workorderCode: null,
        workorderName: null,
        workstationId: null,
        workstationCode: null,
        workstationName: null,
        processId: this.processId,
        processCode: null,
        processName: null,
        itemId: null,
        itemCode: null,
        itemName: null,
        specification: null,
        unitOfMeasure: null,
        quantity: null,
        quantityProduced: null,
        quantityChanged: null,
        clientId: null,
        clientCode: null,
        clientName: null,
        clientNick: null,
        startTime: null,
        duration: null,
        endTime: null,
        colorCode: this.colorCode,
        requestDate: null,
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
      this.ids = selection.map(item => item.taskId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加生产任务";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const taskId = row.taskId || this.ids
      getProtask(taskId).then(response => {
        this.form = response.data;
        // this.form.startTime = new Date(response.data.startTime);
        // this.form.endTime = new Date(response.data.endTime);
        this.open = true;
        this.title = "修改生产任务";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          debugger;
          if (this.form.taskId != null) {
            updateProtask(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProtask(this.form).then(response => {
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
      const taskIds = row.taskId || this.ids;
      this.$modal.confirm('是否确认删除生产任务编号为"' + taskIds + '"的数据项？').then(function() {
        return delProtask(taskIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('mes/pro/protask/export', {
        ...this.queryParams
      }, `protask_${new Date().getTime()}.xlsx`)
    },
    handleWorkstationSelect(){
      this.$refs.wsSelect.showFlag = true;
    },
    onWorkstationSelected(row){
      debugger;
      if(row != undefined && row != null){
        this.form.workstationId = row.workstationId;
        this.form.workstationCode = row.workstationCode;
        this.form.workstationName = row.workstationName;
      }
    }
  }
};
</script>
