<template>
  <el-dialog title="缺陷记录"
    v-if="showFlag"
    :visible.sync="showFlag"
    :modal= false
    width="80%"
    center
  >
    
    <el-row :gutter="10" v-if="optType !='view'" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['mes:qc:iqcdefect:add']"
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
          v-hasPermi="['mes:qc:iqcdefect:edit']"
        >常见缺陷</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="iqcdefectList" @selection-change="handleSelectionChange">
      <el-table-column label="缺陷描述" width="400px" align="center" prop="defectName" >
            <template slot-scope="scope">
              <el-input type="textarea" v-model="scope.row.defectName"></el-input>
            </template>
      </el-table-column>
      <el-table-column label="缺陷等级" align="center" prop="defectLevel">
        <template slot-scope="scope">
            <el-select v-model="scope.row.defectLevel">
                <el-option
                    v-for="dict in dict.type.mes_defect_level"
                    :key="dict.value"
                    :label="dict.label"
                    :value="dict.value"
                />                
            </el-select>          
        </template>
      </el-table-column>
      <el-table-column label="缺陷数量" align="center" prop="defectQuantity" >
        <template slot-scope="scope">
            <el-input-number :min="1" v-model="scope.row.defectQuantity" />
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            v-if="optType !='view'"
            @click="handleDelete(scope.$index, scope.row)"
            v-hasPermi="['mes:qc:iqcdefect:remove']"
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
    <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="cancel" v-if="optType =='view'">返回</el-button>
        <el-button type="primary" v-hasPermi="['mes:qc:iqcdefect:update']" v-else @click="confirm">确 定</el-button>
        <el-button @click="showFlag=false">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { listIqcdefect, getIqcdefect, delIqcdefect, addIqcdefect, updateIqcdefect } from "@/api/mes/qc/iqcdefect";

export default {
  name: "Iqcdefect",
  dicts: ['mes_defect_level'],
  props:{
      iqcId:null,
      lineId:null,
      optType:null
  },
  watch :{
      iqcId(){
        this.queryParams.iqcId = this.iqcId;        
      },
      lineId(){
        this.queryParams.lineId = this.lineId;
      }      
  },
  data() {
    return {
      showFlag: false,
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
      // 来料检验单缺陷记录表格数据
      iqcdefectList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        iqcId: this.iqcId,
        lineId: this.lineId,
        defectName: null,
        defectLevel: null,
        defectQuantity: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        iqcId: [
          { required: true, message: "来料检验单ID不能为空", trigger: "blur" }
        ],
        lineId: [
          { required: true, message: "来料检验单行ID不能为空", trigger: "blur" }
        ],
        defectName: [
          { required: true, message: "缺陷描述不能为空", trigger: "blur" }
        ],
        defectLevel: [
          { required: true, message: "缺陷等级不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询来料检验单缺陷记录列表 */
    getList() {
      debugger;
      this.loading = true;
      listIqcdefect(this.queryParams).then(response => {
        this.iqcdefectList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.showFlag = false;
    },
    // 表单重置
    reset() {
      this.form = {
        recordId: null,
        iqcId: null,
        lineId: null,
        defectName: null,
        defectLevel: null,
        defectQuantity: null,
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
        var row = {
            recordId: null,
            iqcId: this.iqcId,
            lineId: this.lineId,
            defectName: null,
            defectLevel: null,
            defectQuantity: null,
            flag:'add',
            remark: null
        }
        this.iqcdefectList.unshift(row);
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const recordId = row.recordId || this.ids
      getIqcdefect(recordId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改来料检验单缺陷记录";
      });
    },
    /** 提交按钮 */
    confirm() {
        if(this.iqcdefectList.length !=0){
            updateIqcdefect(this.iqcdefectList).then(res => {
              debugger;
            this.$modal.msgSuccess(res.msg);
            if (res.code === 200) {
              this.open = false;
              this.getList();
              this.$parent.getList();
            }
          });
        }
    },
    /** 删除按钮操作 */
    handleDelete(index,row) {
        debugger;
        const recordIds = row.recordId || this.ids;
        let that = this;
        this.$modal.confirm('是否确认删除缺陷记录？').then(function() {
            that.iqcdefectList.forEach(function(item,i,arr){
                debugger;
                if(i == index){
                  if(that.iqcdefectList[index].flag=='add'){
                    //如果是刚新增的则直接删除
                    that.iqcdefectList.splice(index,1);
                  }else{
                    //如果是从数据库查询的则从数据库删除
                    delIqcdefect(recordIds).then(res=>{
                      that.getList();
                    });
                  }                                      
                }
            })
        });
        this.$parent.getList();
    }
  }
};
</script>
