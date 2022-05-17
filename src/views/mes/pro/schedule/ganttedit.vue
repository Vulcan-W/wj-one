<template>
    <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1"></el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          @click="handleUpdate"
          v-hasPermi="['mes:pro:schedule:edit']"
        >保存</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          @click="handleClose"
        >关闭</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-refresh"
          size="mini"
          @click="handleRefresh"
        >重新加载</el-button>
      </el-col>
    </el-row>
    <div class="wrapper">
      <div class="container">
        <GanttChar class="left-container" ref="ganttChar" optType="edit" :ids="changedTaskIds" :tasks="tasks"></GanttChar>
      </div>      
    </div> 
    </div>
</template>

<script>
import GanttChar from "./ganttx.vue";
import {listGanttTaskList,updateProtask} from "@/api/mes/pro/protask";
export default {
    name:'',
    components:{GanttChar},
    data(){
        return {
            tasks:{
                data: [],
                links: []
            },
            changedTaskIds:[],
            optType:'edit',
            // 查询参数
            queryParams: {
                workorderCode: null,
                workorderName: null,
                orderSource: null,
                sourceCode: null,
                productId: null,
                productCode: null,
                productName: null,
                productSpc: null,
                unitOfMeasure: null,
                quantity: null,
                quantityProduced: null,
                quantityChanged: null,
                quantityScheduled: null,
                clientId: null,
                clientCode: null,
                clientName: null,
                requestDate: null,
                parentId: null,
                ancestors: null,
                status: null,
            },
            form:{
                taskId: null,                              
                startTime: null,
                duration: null,
                endTime: null            
            }
        }
    },
    watch: {
        tasks(){
            console.log("TaskChanged");
        }
    },
    created(){
        this.getGanttTasks();
    },
    methods: {
        getGanttTasks(){
            listGanttTaskList(this.queryParams).then(response =>{
                this.tasks.data = response.data.data;
                this.tasks.links = response.data.links;
                this.$refs.ganttChar.reload();
            });
        },
        handleUpdate(){
            debugger;
            if(this.changedTaskIds.length>0){
                this.reset();
                this.changedTaskIds.forEach(id => {
                    debugger;
                    let nTask = this.tasks.data.filter(task => task.id == id)[0];
                    let startDate = new Date(nTask.start_date);
                    let endDate = new Date(nTask.end_date);
                    this.form.taskId = nTask.id;
                    this.form.startTime = startDate.getFullYear()+'-'+(startDate.getMonth()+1)+'-'+startDate.getDate()+' '+startDate.getHours()+':'+startDate.getMinutes()+':'+startDate.getSeconds();
                    this.form.duration = nTask.duration;
                    this.form.endTime = endDate.getFullYear()+'-'+(endDate.getMonth()+1)+'-'+endDate.getDate()+' '+endDate.getHours()+':'+endDate.getMinutes()+':'+endDate.getSeconds();
                    updateProtask(this.form).then(response =>{
                        console.log("update success:"+id);
                    },err =>{
                        debugger;
                        console.log("update failed:"+err.msg);
                    });
                });
                this.changedTaskIds =[];                
                this.$modal.msgSuccess("保存成功");
            }            
        },
        handleRefresh(){
            this.$refs.ganttChar.reload();
        },
        handleClose(){
            const obj = { path: "/mes/pro/proschedule" };
            this.$tab.closeOpenPage(obj);
        },
        reset() {
            this.form = {
                taskId: null,
                startTime: null,
                duration: null,
                endTime: null,
            };
        },

    }
}
</script>

<style scoped>
.wrapper{
    height: 700px;
}
.container {
  height: 100%;
  width: 100%;
}
.left-container {
  overflow: hidden;
  position: relative;
  height: 100%;
}
</style>