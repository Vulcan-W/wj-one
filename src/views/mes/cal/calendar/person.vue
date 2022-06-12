<template>
      <div class="app-container">
          <el-container>
                  <el-header height="20px">
                        <el-form :model="form" size="small" :inline="true" label-width="100px">
                              <el-form-item label="人员" prop="nickName">
                                    <el-input
                                    v-model="form.nickName"
                                    placeholder="请选择查询的人员"
                                    clearable
                                    readonly="readonly"
                                    >
                                          <el-button slot="append" @click="handleQuery" icon="el-icon-search"></el-button>
                                    </el-input>
                                    <UserSingleSelect ref="userSelect" @onSelected="onUserSelected"></UserSingleSelect>
                              </el-form-item>
                        </el-form>
                  </el-header>
                  <el-main>
                        <el-calendar v-loading="loading" v-model="date">
                              <template slot="dateCell" slot-scope="{date, data }">
                              <div>
                                    <el-row>
                                          <el-col :span="6">
                                                <div class="solar">                    
                                                      {{ data.day.split('-')[2] }}
                                                </div>
                                          </el-col>
                                          <el-col :span="12">
                                                <div class="lunar" :class="{ festival : isFestival(date, data) }">{{ solarDate2lunar(data.day) }}</div>
                                          </el-col>
                                          <el-col :span="6">
                                                <el-tag v-if="holidayList.indexOf(data.day) ==-1" effect="dark">班</el-tag>
                                                <el-tag v-else effect="dark" type="success">休</el-tag>
                                          </el-col>
                                    </el-row>
                                    <el-row v-for="calendarDay in calendarDayList " :key="calendarDay.theDay">
                                          <el-col :span="24" v-if="calendarDay.theDay == data.day && holidayList.indexOf(data.day) ==-1">
                                                <div v-for="teamShift in calendarDay.teamShifts" :key="teamShift.orderNum" class="grid-content">
                                                      <el-button v-if="teamShift.orderNum == 1" type="success" icon="el-icon-sunrise">{{ teamShift.teamName }}</el-button>
                                                      <el-button v-if="teamShift.orderNum ==2 && calendarDay.shiftType=='SHIFT_THREE'" type="warning" icon="el-icon-sunny">{{ teamShift.teamName }}</el-button>
                                                      <el-button v-if="teamShift.orderNum ==2 && calendarDay.shiftType=='SHIFT_TWO'"   type="info"    icon="el-icon-moon">{{ teamShift.teamName }}</el-button>
                                                      <el-button v-if="teamShift.orderNum ==3 && calendarDay.shiftType=='SHIFT_THREE'" type="info"    icon="el-icon-moon">{{ teamShift.teamName }}</el-button>
                                                </div>                                               
                                          </el-col>
                                    </el-row>                                  
                              </div>                
                              </template>
                        </el-calendar>
                  </el-main>
          </el-container>
      </div>
</template>

<script>
import { listCalholiday } from "@/api/mes/cal/calholiday";
import { listCalendars } from "@/api/mes/cal/calendar";
import UserSingleSelect from "@/components/userSelect/single.vue"
import calendar from '@/utils/calendar';
export default {
      name: 'UserView',     
      dicts:['mes_calendar_type'], 
      components :{UserSingleSelect},
      data(){
            return {
                  // 遮罩层
                  loading: true,
                  date: new Date(),
                  holidayList:[],//假日
                  workdayList:[],//工作日
                  selectedType:null,
                  form:{},
                  calendarDayList:[

                  ],
                  teamShiftQueryParams:{
                        queryType:'USER',
                  },
                  queryParams: {
                        theDay: null,
                        holidayType: null,
                        startTime: null,
                        endTime: null,
                  },                        
            }
      },
      watch:{
            date:{
                  handler(newVal,oldVal){
                        console.log(newVal.getFullYear()+'-'+(newVal.getMonth()+1)+'-'+newVal.getDate());
                        this.teamShiftQueryParams.date = newVal.getFullYear()+'-'+(newVal.getMonth()+1)+'-'+newVal.getDate();         
                        this.loading = true;         
                        listCalendars(this.teamShiftQueryParams).then(response =>{
                              this.calendarDayList = response.data;
                              this.loading = false;
                        });
                  }
            }
      },
      created() {
            this.getList();
      },
      methods:{
            /** 查询节假日设置列表 */
            getList() {
                  this.loading = true;   
                  this.holidayList =[];
                  this.workdayList =[];
                  let that = this;         
                  listCalholiday(this.queryParams).then(response => {
                  if(response.data !=null){
                        response.data.forEach(theDay => {
                              if(theDay.holidayType =='HOLIDAY'){
                              that.holidayList.push(theDay.theDay);
                              }else{
                              that.workdayList.push(theDay.theDay);
                              }
                        });                    
                        this.loading = false;
                  }
                  });
            },
            //点击人员选择按钮
            handleQuery(){
                  this.$refs.userSelect.showFlag = true;
            },
            //人员选择返回
            onUserSelected(row){                  
                  this.form.nickName = row.nickName;
                  this.loading = true;   
                  this.teamShiftQueryParams.userId = row.userId;
                  this.teamShiftQueryParams.date = this.date;
                  listCalendars(this.teamShiftQueryParams).then(response =>{
                        this.calendarDayList = response.data;
                        this.loading = false;
                  });
            },
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
      .grid-content{
            padding: 5px 0;
      }

      .el-group-list.el-radio-group{
            display: flex;
            flex-direction: column;
            align-items:stretch;
      }

      .el-group-list.el-radio-group .el-radio-button:first-child .el-radio-button__inner,
      .el-group-list.el-radio-group .el-radio-button:last-child .el-radio-button__inner,
      .el-group-list.el-radio-group  .el-radio-button:first-child .el-radio-button__inner,
      .el-group-list.el-radio-group .el-radio-button__inner
      {
            border-radius: 0px !important;
            border: none !important;
      }

      .el-group-list.el-radio-group .el-radio-button{
            border-bottom: 1px solid #F7F7F7 !important;
      }

      .el-group-list.el-radio-group{
            border: 1px solid #dcdfe6;
      }

      .el-group-list.el-radio-group > label > span{
            width: 100%;
            text-align: left;
            padding-left: 20px;
      }
      /**本月周末设置为红色*/
      .el-calendar-table .current:nth-last-child(-n+2) .solar {
            color: red;
            font-size: small;
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
      /**节假日背景设置为绿色 */
      .el-calendar-table .holiday {
            background-color: #88E325;
      }
</style>