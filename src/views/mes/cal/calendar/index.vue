<template>
    <div class="app-container">
        <el-tabs type="border-card">
            <el-tab-pane label="类型">
                <CalendarTypeView></CalendarTypeView>
            </el-tab-pane>
            <el-tab-pane label="班组"></el-tab-pane>
            <el-tab-pane label="个人"></el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
import CalendarTypeView from "./calendarType.vue"

export default {
    components:{CalendarTypeView},
    data(){
        return {

        }
    }
}
</script>

<style>

</style>