-- ----------------------------
-- 1、检测项表
-- ----------------------------
drop table if exists qc_index;
create table qc_index (
  index_id                    bigint(20)      not null auto_increment    comment '检测项ID',
  index_code                  varchar(64)     not null                   comment '检测项编码',
  index_name                  varchar(255)    not null                   comment '检测项名称',
  index_type                  varchar(64)     not null                   comment '检测项类型',
  qc_tool                     varchar(255)                               comment '检测工具',
  remark                      varchar(500)    default ''                 comment '备注',
  attr1                       varchar(64)     default null               comment '预留字段1',
  attr2                       varchar(255)    default null               comment '预留字段2',
  attr3                       int(11)         default 0                  comment '预留字段3',
  attr4                       int(11)         default 0                  comment '预留字段4',
  create_by                   varchar(64)     default ''                 comment '创建者',
  create_time 	              datetime                                   comment '创建时间',
  update_by                   varchar(64)     default ''                 comment '更新者',
  update_time                 datetime                                   comment '更新时间',
  primary key (index_id)
) engine=innodb auto_increment=200 comment = '检测项表';


-- ----------------------------
-- 2、检测模板表
-- ----------------------------
drop table if exists qc_template;
create table qc_template (
  template_id                 bigint(20)      not null auto_increment    comment '检测模板ID',
  template_code               varchar(64)     not null                   comment '检测模板编号',
  template_name               varchar(255)    not null                   comment '检测模板名称',
  qc_types                    varchar(255)    not null                   comment '检测种类',
  enable_flag                 char(1)         default 'Y'                comment '是否启用',
  remark                      varchar(500)    default ''                 comment '备注',
  attr1                       varchar(64)     default null               comment '预留字段1',
  attr2                       varchar(255)    default null               comment '预留字段2',
  attr3                       int(11)         default 0                  comment '预留字段3',
  attr4                       int(11)         default 0                  comment '预留字段4',
  create_by                   varchar(64)     default ''                 comment '创建者',
  create_time 	              datetime                                   comment '创建时间',
  update_by                   varchar(64)     default ''                 comment '更新者',
  update_time                 datetime                                   comment '更新时间',
  primary key (template_id)
) engine=innodb auto_increment=200 comment = '检测模板表';


-- ----------------------------
-- 3、检测模板-检测项表
-- ----------------------------
drop table if exists qc_template_index;
create table qc_template_index (
  record_id                   bigint(20)      not null auto_increment    comment '记录ID',
  template_id                 bigint(20)      not null                   comment '检测模板ID',
  index_id                    bigint(20)      not null                   comment '检测项ID',
  index_code                  varchar(64)     not null                   comment '检测项编码',
  index_name                  varchar(255)    not null                   comment '检测项名称',
  index_type                  varchar(64)     not null                   comment '检测项类型',
  qc_tool                     varchar(255)                               comment '检测工具',
  check_method                varchar(500)                               comment '检测要求',
  stander_val                 double(12,4)                               comment '标准值',
  unit_of_measure             varchar(64)                                comment '单位',
  threshold_max               double(12,4)                               comment '误差上限',
  threshold_min               double(12,4)                               comment '误差下限',
  doc_url                     varchar(255)                               comment '说明图',
  remark                      varchar(500)    default ''                 comment '备注',
  attr1                       varchar(64)     default null               comment '预留字段1',
  attr2                       varchar(255)    default null               comment '预留字段2',
  attr3                       int(11)         default 0                  comment '预留字段3',
  attr4                       int(11)         default 0                  comment '预留字段4',
  create_by                   varchar(64)     default ''                 comment '创建者',
  create_time 	              datetime                                   comment '创建时间',
  update_by                   varchar(64)     default ''                 comment '更新者',
  update_time                 datetime                                   comment '更新时间',
  primary key (record_id)
) engine=innodb auto_increment=200 comment = '检测模板-检测项表';



-- ----------------------------
-- 4、检测模板-产品表
-- ----------------------------
drop table if exists qc_template_product;
create table qc_template_product (
  record_id                   bigint(20)      not null auto_increment    comment '记录ID',
  template_id                 bigint(20)      not null                   comment '检测模板ID',
  item_id                     bigint(20)      not null                   comment '产品物料ID',
  item_code                   varchar(64)                                comment '产品物料编码',
  item_name                   varchar(255)                               comment '产品物料名称',
  specification               varchar(500)                               comment '规格型号',
  unit_of_measure             varchar(64)                                comment '单位',
  quantity_check              int(11)         default 1                  comment '最低检测数',
  quantity_unqualified        int(11)         default 0                  comment '最大不合格数',
  cr_rate                     double(12,2)    default 0                  comment '最大致命缺陷率',
  maj_rate                    double(12,2)    default 0                  comment '最大严重缺陷率',
  min_rate                    double(12,2)    default 100                comment '最大轻微缺陷率',
  remark                      varchar(500)    default ''                 comment '备注',
  attr1                       varchar(64)     default null               comment '预留字段1',
  attr2                       varchar(255)    default null               comment '预留字段2',
  attr3                       int(11)         default 0                  comment '预留字段3',
  attr4                       int(11)         default 0                  comment '预留字段4',
  create_by                   varchar(64)     default ''                 comment '创建者',
  create_time 	              datetime                                   comment '创建时间',
  update_by                   varchar(64)     default ''                 comment '更新者',
  update_time                 datetime                                   comment '更新时间',
  primary key (record_id)
) engine=innodb auto_increment=200 comment = '检测模板-产品表';


-- ----------------------------
-- 5、常用缺陷表
-- ----------------------------
drop table if exists qc_defect;
create table qc_defect (
  defect_id                   bigint(20)      not null auto_increment    comment '缺陷ID',
  defect_code                 varchar(64)     not null                   comment '缺陷编码',
  defect_name                 varchar(500)    not null                   comment '缺陷描述',
  index_type                  varchar(64)     not null                   comment '检测项类型',
  defect_level                varchar(64)     not null                   comment '缺陷等级',
  remark                      varchar(500)    default ''                 comment '备注',
  attr1                       varchar(64)     default null               comment '预留字段1',
  attr2                       varchar(255)    default null               comment '预留字段2',
  attr3                       int(11)         default 0                  comment '预留字段3',
  attr4                       int(11)         default 0                  comment '预留字段4',
  create_by                   varchar(64)     default ''                 comment '创建者',
  create_time 	              datetime                                   comment '创建时间',
  update_by                   varchar(64)     default ''                 comment '更新者',
  update_time                 datetime                                   comment '更新时间',
  primary key (defect_id)
) engine=innodb auto_increment=200 comment = '常见缺陷表';



-- ----------------------------
-- 6、来料检验单表
-- ----------------------------
drop table if exists qc_iqc;
create table qc_iqc (
  iqc_id                      bigint(20)      not null auto_increment    comment '来料检验单ID',
  iqc_code                    varchar(64)     not null                   comment '来料检验单编号',
  iqc_name                    varchar(500)    not null                   comment '来料检验单名称',
  template_id                 bigint(20)      not null                   comment '检验模板ID',
  vendor_id                   bigint(20)      not null                   comment '供应商ID',
  vendor_code                 varchar(64)     not null                   comment '供应商编码',
  vendor_name                 varchar(255)    not null                   comment '供应商名称',
  vendor_nick                 varchar(255)                               comment '供应商简称',
  vendor_batch                varchar(64)                                comment '供应商批次号',
  item_id                     bigint(20)      not null                   comment '产品物料ID',
  item_code                   varchar(64)                                comment '产品物料编码',
  item_name                   varchar(255)                               comment '产品物料名称',
  specification               varchar(500)                               comment '规格型号',
  unit_of_measure             varchar(64)                                comment '单位',
  quantity_min_check          int(11)         default 1                  comment '最低检测数',
  quantity_max_unqualified    int(11)         default 0                  comment '最大不合格数',
  quantity_recived            double(12,2)    not null                   comment '本次接收数量',
  quantity_check              int(11)         not null                   comment '本次检测数量',
  quantity_unqualified        int(11)         default 0                  comment '不合格数',
  cr_rate                     double(12,2)    default 0                  comment '致命缺陷率',
  maj_rate                    double(12,2)    default 0                  comment '严重缺陷率',
  min_rate                    double(12,2)    default 0                  comment '轻微缺陷率',
  cr_quantity                 int(11)         default 0                  comment '致命缺陷数量',
  maj_quantity                int(11)         default 0                  comment '严重缺陷数量',
  min_quantity                int(11)         default 0                  comment '轻微缺陷数量',
  check_result                varchar(64)                                comment '检测结果',
  recive_date                 datetime                                   comment '来料日期',
  inspect_date                datetime                                   comment '检测日期',
  inspector                   varchar(64)                                comment '检测人员',
  status                      varchar(64)                                comment '单据状态',
  remark                      varchar(500)    default ''                 comment '备注',
  attr1                       varchar(64)     default null               comment '预留字段1',
  attr2                       varchar(255)    default null               comment '预留字段2',
  attr3                       int(11)         default 0                  comment '预留字段3',
  attr4                       int(11)         default 0                  comment '预留字段4',
  create_by                   varchar(64)     default ''                 comment '创建者',
  create_time 	              datetime                                   comment '创建时间',
  update_by                   varchar(64)     default ''                 comment '更新者',
  update_time                 datetime                                   comment '更新时间',
  primary key (iqc_id)
) engine=innodb auto_increment=200 comment = '来料检验单表';




-- ----------------------------
-- 7、来料检验单行表
-- ----------------------------
drop table if exists qc_iqc_line;
create table qc_iqc_line (
  line_id                     bigint(20)      not null auto_increment    comment '记录ID',
  iqc_id                      bigint(20)      not null                   comment '检验单ID',
  index_id                    bigint(20)      not null                   comment '检测项ID',
  index_code                  varchar(64)                                comment '检测项编码',
  index_name                  varchar(255)                               comment '检测项名称',
  index_type                  varchar(64)                                comment '检测项类型',
  qc_tool                     varchar(255)                               comment '检测工具',
  check_method                varchar(500)                               comment '检测要求',
  stander_val                 double(12,4)                               comment '标准值',
  unit_of_measure             varchar(64)                                comment '单位',
  threshold_max               double(12,4)                               comment '误差上限',
  threshold_min               double(12,4)                               comment '误差下限',
  cr_quantity                 int(11)         default 0                  comment '致命缺陷数量',
  maj_quantity                int(11)         default 0                  comment '严重缺陷数量',
  min_quantity                int(11)         default 0                  comment '轻微缺陷数量',
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
) engine=innodb auto_increment=200 comment = '来料检验单行表';




-- ----------------------------
-- 8、来料检验单缺陷记录表
-- ----------------------------
drop table if exists qc_iqc_defect;
create table qc_iqc_defect (
  record_id                   bigint(20)      not null auto_increment    comment '缺陷ID',
  iqc_id                      bigint(20)      not null                   comment '来料检验单ID',
  line_id                     bigint(20)      not null                   comment '来料检验单行ID',
  defect_name                 varchar(500)    not null                   comment '缺陷描述',  
  defect_level                varchar(64)     not null                   comment '缺陷等级',
  defect_quantity             int(11)         default 1                  comment '缺陷数量',
  remark                      varchar(500)    default ''                 comment '备注',
  attr1                       varchar(64)     default null               comment '预留字段1',
  attr2                       varchar(255)    default null               comment '预留字段2',
  attr3                       int(11)         default 0                  comment '预留字段3',
  attr4                       int(11)         default 0                  comment '预留字段4',
  create_by                   varchar(64)     default ''                 comment '创建者',
  create_time 	              datetime                                   comment '创建时间',
  update_by                   varchar(64)     default ''                 comment '更新者',
  update_time                 datetime                                   comment '更新时间',
  primary key (record_id)
) engine=innodb auto_increment=200 comment = '来料检验单缺陷记录表';






