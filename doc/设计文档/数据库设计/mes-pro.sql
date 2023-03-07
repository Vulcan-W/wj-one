-- ----------------------------
-- 1、生产工单表
-- ----------------------------
drop table if exists pro_workorder;
create table pro_workorder (
  workorder_id                bigint(20)      not null auto_increment    comment '工单ID',
  workorder_code              varchar(64)     not null                   comment '工单编码',
  workorder_name              varchar(255)    not null                   comment '工单名称',
  order_source                varchar(64)     not null                   comment '来源类型',
  source_code                 varchar(64)                                comment '来源单据',
  product_id                  bigint(20)      not null                   comment '产品ID',
  product_code                varchar(64)     not null                   comment '产品编号',
  product_name                varchar(255)    not null                   comment '产品名称',
  product_spc                 varchar(255)                               comment '规格型号',
  unit_of_measure             varchar(64)     not null                   comment '单位',
  quantity                    double(14,2)    default 0 not null         comment '生产数量',
  quantity_produced           double(14,2)    default 0                  comment '已生产数量',
  quantity_changed            double(14,2)    default 0                  comment '调整数量',
  quantity_scheduled          double(14,2)    default 0                  comment '已排产数量', 
  client_id                   bigint(20)                                 comment '客户ID',
  client_code                 varchar(64)                                comment '客户编码',
  client_name                 varchar(255)                               comment '客户名称',
  request_date                datetime        not null                   comment '需求日期',
  parent_id                   bigint(20)      default 0 not null         comment '父工单',
  ancestors                   varchar(500)    not null                   comment '所有父节点ID',
  status                      varchar(64)     default 'PREPARE'          comment '单据状态',
  remark                      varchar(500)    default ''                 comment '备注',
  attr1                       varchar(64)     default null               comment '预留字段1',
  attr2                       varchar(255)    default null               comment '预留字段2',
  attr3                       int(11)         default 0                  comment '预留字段3',
  attr4                       int(11)         default 0                  comment '预留字段4',
  create_by                   varchar(64)     default ''                 comment '创建者',
  create_time 	              datetime                                   comment '创建时间',
  update_by                   varchar(64)     default ''                 comment '更新者',
  update_time                 datetime                                   comment '更新时间',
  primary key (workorder_id)
) engine=innodb auto_increment=200 comment = '生产工单表';


-- ----------------------------
-- 2、生产工单BOM组成表
-- ----------------------------
drop table if exists pro_workorder_bom;
create table pro_workorder_bom (
  line_id                     bigint(20)      not null auto_increment    comment '行ID',
  workorder_id                bigint(20)      not null                   comment '生产工单ID',
  item_id                     bigint(20)      not null                   comment 'BOM物料ID',
  item_code                   varchar(64)     not null                   comment 'BOM物料编号',
  item_name                   varchar(255)    not null                   comment 'BOM物料名称',
  item_spc                    varchar(255)                               comment '规格型号',
  unit_of_measure             varchar(64)     not null                   comment '单位',
  item_or_product             varchar(20)     not null                   comment '物料产品标识',
  quantity                    double(14,2)    default 0 not null         comment '预计使用量',
  remark                      varchar(500)    default ''                 comment '备注',
  attr1                       varchar(64)     default null               comment '预留字段1',
  attr2                       varchar(255)    default null               comment '预留字段2',
  attr3                       int(11)         default 0                  comment '预留字段3',
  attr4                       int(11)         default 0                  comment '预留字段4',
  create_by                   varchar(64)     default ''                 comment '创建者',
  create_time 	              datetime                                   comment '创建时间',
  update_by                   varchar(64)     default ''                 comment '更新者',
  update_time                 datetime                                   comment '更新时间',
  primary key (line_id)
) engine=innodb auto_increment=200 comment = '生产工单BOM组成表';


-- ----------------------------
-- 3、生产工序表
-- ----------------------------
drop table if exists pro_process;
create table pro_process (
  process_id                     bigint(20)      not null auto_increment    comment '工序ID',
  process_code                   varchar(64)     not null                   comment '工序编码',
  process_name                   varchar(255)    not null                   comment '工序名称',
  attention                      varchar(1000)                              comment '工艺要求',
  enable_flag                    char(1)         default 'Y' not null       comment '是否启用',
  remark                         varchar(500)    default ''                 comment '备注',
  attr1                          varchar(64)     default null               comment '预留字段1',
  attr2                          varchar(255)    default null               comment '预留字段2',
  attr3                          int(11)         default 0                  comment '预留字段3',
  attr4                          int(11)         default 0                  comment '预留字段4',
  create_by                      varchar(64)     default ''                 comment '创建者',
  create_time 	                 datetime                                   comment '创建时间',
  update_by                      varchar(64)     default ''                 comment '更新者',
  update_time                    datetime                                   comment '更新时间',
  primary key (process_id)
) engine=innodb auto_increment=200 comment = '生产工序表';


-- ----------------------------
-- 4、生产工序内容表
-- ----------------------------
drop table if exists pro_process_content;
create table pro_process_content (
  content_id                     bigint(20)      not null auto_increment    comment '内容ID',
  process_id                     bigint(20)      not null                   comment '工序ID',
  order_num                      int(4)          default 0                  comment '顺序编号',
  content_text                   varchar(500)                               comment '内容说明',
  device                         varchar(255)                               comment '辅助设备',
  material                       varchar(255)                               comment '辅助材料',
  doc_url                        varchar(255)                               comment '材料URL',  
  remark                         varchar(500)    default ''                 comment '备注',
  attr1                          varchar(64)     default null               comment '预留字段1',
  attr2                          varchar(255)    default null               comment '预留字段2',
  attr3                          int(11)         default 0                  comment '预留字段3',
  attr4                          int(11)         default 0                  comment '预留字段4',
  create_by                      varchar(64)     default ''                 comment '创建者',
  create_time 	                 datetime                                   comment '创建时间',
  update_by                      varchar(64)     default ''                 comment '更新者',
  update_time                    datetime                                   comment '更新时间',
  primary key (content_id)
) engine=innodb auto_increment=200 comment = '生产工序内容表';


-- ----------------------------
-- 3、工艺路线表
-- ----------------------------
drop table if exists pro_route;
create table pro_route (
  route_id                       bigint(20)      not null auto_increment    comment '工艺路线ID',
  route_code                     varchar(64)     not null                   comment '工艺路线编号',
  route_name                     varchar(255)    not null                   comment '工艺路线名称',
  route_desc                     varchar(500)                               comment '工艺路线说明',
  enable_flag                    char(1)         default 'Y' not null       comment '是否启用',
  remark                         varchar(500)    default ''                 comment '备注',
  attr1                          varchar(64)     default null               comment '预留字段1',
  attr2                          varchar(255)    default null               comment '预留字段2',
  attr3                          int(11)         default 0                  comment '预留字段3',
  attr4                          int(11)         default 0                  comment '预留字段4',
  create_by                      varchar(64)     default ''                 comment '创建者',
  create_time 	                 datetime                                   comment '创建时间',
  update_by                      varchar(64)     default ''                 comment '更新者',
  update_time                    datetime                                   comment '更新时间',
  primary key (route_id)
) engine=innodb auto_increment=200 comment = '工艺路线表';


-- ----------------------------
-- 4、工艺组成表
-- ----------------------------
drop table if exists pro_route_process;
create table pro_route_process (
  record_id                      bigint(20)      not null auto_increment    comment '记录ID',
  route_id                       bigint(20)      not null                   comment '工艺路线ID',
  process_id                     bigint(20)      not null                   comment '工序ID',
  process_code                   varchar(64)                                comment '工序编码',
  process_name                   varchar(255)                               comment '工序名称',
  order_num                      int(4)          default 1                  comment '序号',
  next_process_id                bigint(20)      not null                   comment '工序ID',
  next_process_code              varchar(64)                                comment '工序编码',
  next_process_name              varchar(255)                               comment '工序名称',
  link_type                      varchar(64)     default 'SS'               comment '与下一道工序关系',
  default_pre_time               int(11)         default 0                  comment '准备时间',
  default_suf_time               int(11)         default 0                  comment '等待时间',
  color_code                     char(7)         default '#00AEF3'          comment '甘特图显示颜色',
  remark                         varchar(500)    default ''                 comment '备注',
  attr1                          varchar(64)     default null               comment '预留字段1',
  attr2                          varchar(255)    default null               comment '预留字段2',
  attr3                          int(11)         default 0                  comment '预留字段3',
  attr4                          int(11)         default 0                  comment '预留字段4',
  create_by                      varchar(64)     default ''                 comment '创建者',
  create_time 	                 datetime                                   comment '创建时间',
  update_by                      varchar(64)     default ''                 comment '更新者',
  update_time                    datetime                                   comment '更新时间',
  primary key (record_id)
) engine=innodb auto_increment=200 comment = '工艺组成表';


-- ----------------------------
-- 4、产品制程
-- ----------------------------
drop table if exists pro_route_product;
create table pro_route_product (
  record_id                      bigint(20)      not null auto_increment    comment '记录ID',
  route_id                       bigint(20)      not null                   comment '工艺路线ID',
  item_id                        bigint(20)      not null                   comment '产品物料ID',
  item_code                      varchar(64)     not null                   comment '产品物料编码',
  item_name                      varchar(255)    not null                   comment '产品物料名称',
  specification                  varchar(500)    default null               comment '规格型号',
  unit_of_measure                varchar(64)     not null                   comment '单位',
  quantity                       int(11)         default 1                  comment '生产数量',
  production_time                double(12,2)    default 1                  comment '生产用时',
  time_unit_type                 varchar(64)     default 'MINUTE'           comment '时间单位',
  remark                         varchar(500)    default ''                 comment '备注',
  attr1                          varchar(64)     default null               comment '预留字段1',
  attr2                          varchar(255)    default null               comment '预留字段2',
  attr3                          int(11)         default 0                  comment '预留字段3',
  attr4                          int(11)         default 0                  comment '预留字段4',
  create_by                      varchar(64)     default ''                 comment '创建者',
  create_time 	                 datetime                                   comment '创建时间',
  update_by                      varchar(64)     default ''                 comment '更新者',
  update_time                    datetime                                   comment '更新时间',
  primary key (record_id)
) engine=innodb auto_increment=200 comment = '产品制程';


-- ----------------------------
-- 4、生产任务表
-- ----------------------------
drop table if exists pro_task;
create table pro_task (
  task_id                        bigint(20)      not null auto_increment    comment '任务ID',
  task_code                      varchar(64)     not null                   comment '任务编号',
  task_name                      varchar(255)    not null                   comment '任务名称',
  workorder_id                   bigint(20)      not null                   comment '生产工单ID',
  workorder_code                 varchar(64)     not null                   comment '生产工单编号',
  workorder_name                 varchar(255)    not null                   comment '工单名称',
  workstation_id                 bigint(20)      not null                   comment '工作站ID',
  workstation_code               varchar(64)     not null                   comment '工作站编号',
  workstation_name               varchar(255)    not null                   comment '工作站名称',
  process_id                     bigint(20)      not null                   comment '工序ID',
  process_code                   varchar(64)                                comment '工序编码',
  process_name                   varchar(255)                               comment '工序名称',
  item_id                        bigint(20)      not null                   comment '产品物料ID',
  item_code                      varchar(64)     not null                   comment '产品物料编码',
  item_name                      varchar(255)    not null                   comment '产品物料名称',
  specification                  varchar(500)                               comment '规格型号',
  unit_of_measure                varchar(64)     not null                   comment '单位',
  quantity                       double(14,2)    default 1 not null         comment '排产数量',
  quantity_produced              double(14,2)    default 0                  comment '已生产数量',
  quantity_changed               double(14,2)    default 0                  comment '调整数量',
  client_id                      bigint(20)      not null                   comment '客户ID',
  client_code                    varchar(64)     not null                   comment '客户编码',
  client_name                    varchar(255)    not null                   comment '客户名称',
  client_nick                    varchar(255)                               comment '客户简称',
  start_time                     datetime        default CURRENT_TIMESTAMP  comment '开始生产时间',
  duration                       int(11)         default 1                  comment '生产时长',
  end_time                       datetime                                   comment '完成生产时间',
  color_code                     char(7)         default '#00AEF3'          comment '甘特图显示颜色',
  request_date                   datetime                                   comment '需求日期',
  remark                         varchar(500)    default ''                 comment '备注',
  attr1                          varchar(64)     default null               comment '预留字段1',
  attr2                          varchar(255)    default null               comment '预留字段2',
  attr3                          int(11)         default 0                  comment '预留字段3',
  attr4                          int(11)         default 0                  comment '预留字段4',
  create_by                      varchar(64)     default ''                 comment '创建者',
  create_time 	                 datetime                                   comment '创建时间',
  update_by                      varchar(64)     default ''                 comment '更新者',
  update_time                    datetime                                   comment '更新时间',
  primary key (task_id)
) engine=innodb auto_increment=200 comment = '生产任务表';


