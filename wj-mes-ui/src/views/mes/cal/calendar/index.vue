<template>
    <div class="app-container">
        <el-tabs type="border-card">
            <el-tab-pane label="分类">
                <CalendarTypeView></CalendarTypeView>
            </el-tab-pane>
            <el-tab-pane label="班组">
                <TeamView></TeamView>
            </el-tab-pane>
            <el-tab-pane label="个人">
                <UserView></UserView>
            </el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
import CalendarTypeView from "./calendarType.vue"
import TeamView from "./team.vue"
import UserView from "./person.vue"
export default {
    components:{CalendarTypeView,TeamView,UserView},
    data(){
        return {

        }
    }
}
</script>

<style>

</style>