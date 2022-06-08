<template>
    <div class="app-container">
        <el-calendar v-model="date">
            <template slot="dateCell" slot-scope="{date, data }">
                <div class="solar">{{ data.day.split('-')[2] }}</div>
                <div class="lunar" :class="{ festival : isFestival(date, data) }">{{ solarDate2lunar(data.day) }}</div>
            </template>
        </el-calendar>
    </div>
</template>

<script>
import calendar from '@/utils/calendar';
export default {
    data(){
        return {
            date: new Date(),
        }
    },
    methods:{
        isFestival(slotDate, slotData) {
            let solarDayArr = slotData.day.split('-');
            let lunarDay = calendar.solar2lunar(solarDayArr[0], solarDayArr[1], solarDayArr[2])
    
            // 公历节日\农历节日\农历节气
            let festAndTerm = [];
            festAndTerm.push(lunarDay.festival == null ? '' : ' ' + lunarDay.festival)
            festAndTerm.push(lunarDay.lunarFestival == null ? '' : '' + lunarDay.lunarFestival)
            festAndTerm.push(lunarDay.Term == null ? '' : '' + lunarDay.Term)
            festAndTerm = festAndTerm.join('')
    
            return festAndTerm != ''
        },
        solarDate2lunar(solarDate) {
            var solar = solarDate.split('-')
            var lunar = calendar.solar2lunar(solar[0], solar[1], solar[2])       
            
            let lunarMD = lunar.IMonthCn + lunar.IDayCn;
            // 公历节日\农历节日\农历节气
            let festAndTerm = [];
            festAndTerm.push(lunar.festival == null ? '' : ' ' + lunar.festival)
            festAndTerm.push(lunar.lunarFestival == null ? '' : '' + lunar.lunarFestival)
            festAndTerm.push(lunar.Term == null ? '' : '' + lunar.Term)
            festAndTerm = festAndTerm.join('')
    
            return festAndTerm == '' ? lunarMD : festAndTerm
            
        }
    }
}
</script>

<style>
/**本月周末设置为红色*/
  .el-calendar-table .current:nth-last-child(-n+2) .solar {
    color: red;
  }
  /**本月农历设置为灰色*/
  .el-calendar-table .current .lunar {
    color: #606266;
    font-size: small;
  }
  /**本月农历节日设置为红色*/
  .el-calendar-table .current .lunar.festival {
    color: green;
    font-size: small;
  }
</style>