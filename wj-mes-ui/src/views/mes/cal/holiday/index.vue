<template>
    <div class="app-container">
        <el-calendar v-model="date">
            <template slot="dateCell" slot-scope="{date, data }">
                <div @contextmenu.prevent="onRightClick(data)">
                    <el-row>
                        <el-col :span="16">
                            <div class="solar">                    
                                {{ data.day.split('-')[2] }}
                            </div>
                        </el-col>
                        <el-col :span="8">
                            <el-tag v-if="holidayList.indexOf(data.day) ==-1" effect="dark">班</el-tag>
                            <el-tag v-else effect="dark" type="success">休</el-tag>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="24">
                            <div class="lunar" :class="{ festival : isFestival(date, data) }">{{ solarDate2lunar(data.day) }}</div>
                        </el-col>                        
                    </el-row>                                        
                </div>                
            </template>
        </el-calendar>
        <!-- 添加或修改节假日设置对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
            <el-form ref="form" :model="form" :rules="rules" label-width="80px">
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="日期" prop="theDay">
                            <el-input v-model="form.theDay" readonly="readonly"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="类型" prop="holidayType">
                            <el-radio-group v-model="form.holidayType">
                                <el-radio label="HOLIDAY">假</el-radio>
                                <el-radio label="WORKDAY">班</el-radio>
                            </el-radio-group>
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
import { listCalholiday, getCalholiday, delCalholiday, addCalholiday, updateCalholiday } from "@/api/mes/cal/calholiday";
import calendar from '@/utils/calendar';
export default {
    data(){
        return {
            // 遮罩层
            loading: true,
            date: new Date(),
            holidayList:[],//假日
            workdayList:[],//工作日
            // 弹出层标题
            title: "节假日设置",
            // 是否显示弹出层
            open: false,
            form:{},
            queryParams: {
                theDay: null,
                holidayType: null,
                startTime: null,
                endTime: null,
            },
            // 表单校验
            rules: {
                holidayType: [
                { required: true, message: "请选择类型", trigger: "blur" }
                ],
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
                debugger;
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
        onRightClick(date){
            this.open = true;
            this.reset();
            this.form.theDay=date.day;
        },
        submitForm(){
            this.$refs["form"].validate(valid => {
                if (valid) {
                    addCalholiday(this.form).then(response => {
                    this.$modal.msgSuccess("设置成功");
                    this.open = false;
                    this.getList();
                    });
                }
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
                holidayId: null,
                theDay: null,
                holidayType: null,
                startTime: new Date().setHours(0),
                endTime: new Date().setHours(23)
            };
            this.resetForm("form");
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
  /**节假日背景设置为绿色 */
  .el-calendar-table .holiday {
      background-color: #88E325;
  }
</style>