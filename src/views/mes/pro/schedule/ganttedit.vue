<template>
    <div class="wrapper">
      <div class="container">
        <GanttChar class="left-container" ref="ganttChar" optType="edit" :tasks="tasks"></GanttChar>
      </div>      
    </div> 
</template>

<script>
import GanttChar from "./ganttx.vue";
import {listGanttTaskList} from "@/api/mes/pro/protask";
export default {
    name:'',
    components:{GanttChar},
    data(){
        return {
            tasks:{
                data: [],
                links: []
            },
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
        }
    },
    created(){
        this.getGanttTasks();
    },
    methods: {
        getGanttTasks(){
            listGanttTaskList(this.queryParams).then(response =>{
                debugger;
                this.tasks.data = response.data.data;
                this.tasks.links = response.data.links;
                this.$refs.ganttChar.reload();
            });
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