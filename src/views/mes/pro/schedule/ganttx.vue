<template>
  <div ref="gantt"></div>
</template>

<script>
import { gantt } from 'dhtmlx-gantt';
export default {
  name: 'GanttChar',
  data() {
    return {
      newObj: {}
    }
  },
  props: {
    tasks: {
      type: Object,
      default () {
        return {data: [], links: []}
      }
    }
  },

  created() {
  },

  mounted: function () {
    // 初始化甘特图配置
    this.initConfig();

    // 甘特图初始化和导入数据
    gantt.init(this.$refs.gantt);
    gantt.parse(this.tasks);
  },

  methods: {
    // 初始化甘特图数据
    initData() {

    },
    // 初始化甘特图配置
    initConfig() {
      // 1 基础配置
      // 1.1 甘特图是否只读
      gantt.config.readonly = true;
      // 1.2 表格列设置
      gantt.config.columns = [
        {name:"text", label:"任务名", tree:true, width:'100' },
        {name:"workstation",label:"工作站",align:"center",width:'*'},
        {name:"start_date", label:"开始时间", align:"center", width:'*' },
        {name:"end_date", label:"结束时间", align:"center" , width:'*'},
        // {name:"duration",   label:"Duration",   align: "center" },
      ];
      // 1.3 设置中文
      gantt.i18n.setLocale("cn");
      // 1.4 鼠标悬浮框
      gantt.plugins({tooltip: true});// 启用tooltip悬浮框
      gantt.templates.tooltip_text = function(start, end, task) {
        return "<b>任务名称:</b> "+task.text+"<br/><b>时长:</b> " + task.duration+"天<br/><b>说明:</b>" +task.toolTipsTxt;
      }
      // 1.5 初始化的时候就展开树结构
      gantt.config.open_tree_initially = true;
      // 1.6 显示到任务上的文本
      gantt.templates.task_text = function (start, end, task) {
        return "<b style='text-align:left;'>计划名称:</b> " + task.text +"    <span style='text-align:left;'>" +" 完成计划："+ Math.round(task.progress * 100) + "% </span>";
      };
      // 1.7 显示单元格
      gantt.config.show_task_cells = true;

      // 2 时间配置
      // 2.1 时间坐标轴单位 minute/hour/day/week/quarter/month/year
      gantt.config.scale_unit = "day";
      // 2.2 日期格式
      gantt.config.date_scale = "%d";
      // 2.3 时间坐标为月份的时候  先显示年份再月份
      gantt.config.subscales = [{unit: "month", step: 1, date: "%Y,%F"}];
      // 2.4 定义从后端获取或发送到后端的日期数据解析格式
      gantt.config.xml_date = "%Y-%m-%d";

      // 3 拖动配置
      // 3.1 自动调整类型,当存在子节点时自动升级为project
      gantt.config.auto_types = false;
      // 3.2 设置不可以拖动进度
      gantt.config.drag_progress = true;
      // 3.3 设置Task不可以拖动
      gantt.config.drag_move = false;
      // 3.4 设置不可以拖动关系
      gantt.config.drag_links = true;
      // 3.5 设置不可拖动Task 大小
      gantt.config.drag_resize = true;
      // 3.6 单击显示添加详情
      gantt.config.details_on_create = true;
      // 3.7 双击显示明细
      gantt.config.details_on_dblclick = true;

    }
  }
}
</script>

<style>
  @import "~dhtmlx-gantt/codebase/dhtmlxgantt.css";
  /* 任务名前的logo */
  .gantt_tree_icon.gantt_file, .gantt_tree_icon.gantt_folder_open, .gantt_tree_icon.gantt_folder_closed{width: 10px; background-image: none;}
  /* 任务线 */
  .gantt_task_line{
    height: 16px !important;
    line-height: 16px !important;
    margin-top: 8px;
    border: none !important;
    border-radius: 8px;
  }
  .gantt_task_line.gantt_milestone{border-radius: 0;}
  /* 里程碑 */
  .gantt_task_line.gantt_milestone{
    width: 12px !important;
    height: 16px !important;
    border: none !important;
    background-color: inherit !important;
    background-size: cover;
  }
  /* 里程碑icon */
  .gantt_task_line.gantt_milestone .gantt_task_content{
    width: 12px;
    height: 16px;
    margin-left: 40px;
    transform: rotate(0deg) !important;
    background-image: url('../../../../assets/logo/logo.png') !important;
  }
  /* 左侧悬浮单元格背景色 */
  .gantt_grid_data .gantt_row.odd:hover, .gantt_grid_data .gantt_row:hover{background-color: #f3f1fe !important;}
  /* 选中一行背景色 */
  .gantt_grid_data .gantt_row.gantt_selected, .gantt_grid_data .gantt_row.odd.gantt_selected, .gantt_task_row.gantt_selected{background-color: #f3f1fe !important;}
  /* 选中一行每个单元格右border颜色 */
  .gantt_task_row.gantt_selected .gantt_task_cell{border-right-color: #f3f1fe !important;}

  /* 标记线 */
  .gantt_marker{background-color: #f4ae05;opacity: .8;}


  /*滚动条整体样式*/
  .gantt-wrapper ::-webkit-scrollbar {
    width: 14px;  /*宽分别对应竖滚动条的尺寸*/
    height: 6px;  /*高分别对应横滚动条的尺寸*/
  }

  /*滚动条里面轨道*/
  .gantt-wrapper ::-webkit-scrollbar-track {
    background-color: rgba(0,0,0,.2);
    border-radius: 5px;
  }
  /*滚动条里面拖动条*/
  .gantt-wrapper ::-webkit-scrollbar-thumb {
    background-color: rgba(0,0,0,.3);
    border-radius: 5px;
  }
  .gantt-wrapper ::-webkit-scrollbar-thumb:hover {
    background-color: rgba(0,0,0,.5);
    border-radius: 5px;
  }

  /* 展开收起背景图片 */
  .gantt_tree_icon.gantt_close, .gantt_tree_icon.gantt_open{
    background-image: none !important;
    position: relative;
  }
  /* 收起小三角 */
  .gantt_tree_icon.gantt_close::after{
    position: absolute;
    content: '';
    top: 14px;
    left: 10px;
    border-right: 6px solid transparent;
    border-left: 6px solid transparent;
    border-top: 6px solid #999999;

  }
  /* 展开小三角 */
  .gantt_tree_icon.gantt_open::after{
    position: absolute;
    content: '';
    top: 12px;
    left: 12px;
    border-bottom: 6px solid transparent;
    border-top: 6px solid transparent;
    border-left: 6px solid #999999;
  }
  /* 下方滚动条宽度 */
  .gantt_layout_cell .gantt_hor_scroll{
    width: calc(100% - 199px) !important;
    left: 199px;
  }
</style>