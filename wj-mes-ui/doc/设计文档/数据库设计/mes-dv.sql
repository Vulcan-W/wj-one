-- ----------------------------
-- 1、设备类型表
-- ----------------------------
drop table if exists dv_machinery_type;
create table dv_machinery_type (
  machinery_type_id           bigint(20)      not null auto_increment    comment '设备类型ID',
  machinery_type_code         varchar(64)     not null                   comment '设备类型编码',
  machinery_type_name         varchar(255)    not null                   comment '设备类型名称',
  parent_type_id              bigint(20)      default 0                  comment '父类型ID',
  ancestors                   varchar(255)    not null                   comment '所有父节点ID',
  enable_flag                 char(1)         default 'Y' not null       comment '是否启用',
  remark                      varchar(500)    default ''                 comment '备注',
  attr1                       varchar(64)     default null               comment '预留字段1',
  attr2                       varchar(255)    default null               comment '预留字段2',
  attr3                       int(11)         default 0                  comment '预留字段3',
  attr4                       int(11)         default 0                  comment '预留字段4',
  create_by                   varchar(64)     default ''                 comment '创建者',
  create_time 	              datetime                                   comment '创建时间',
  update_by                   varchar(64)     default ''                 comment '更新者',
  update_time                 datetime                                   comment '更新时间',
  primary key (machinery_type_id)
) engine=innodb auto_increment=200 comment = '设备类型表';


-- ----------------------------
-- 2、设备表
-- ----------------------------
drop table if exists dv_machinery;
create table dv_machinery (
  machinery_id                bigint(20)      not null auto_increment    comment '设备类型ID',
  machinery_code              varchar(64)     not null                   comment '设备类型编码',
  machinery_name              varchar(255)    not null                   comment '设备类型名称',
  machinery_brand             varchar(255)                               comment '品牌',
  machinery_spec              varchar(255)                               comment '规格型号',
  machinery_type_id           bigint(20)      not null                   comment '设备类型ID',
  machinery_type_code         varchar(64)                                comment '设备类型编码',
  machinery_type_name         varchar(255)                               comment '设备类型名称',
  workshop_id                 bigint(20)      not null                   comment '所属车间ID',
  workshop_code               varchar(64)                                comment '所属车间编码',
  workshop_name               varchar(255)                               comment '所属车间名称',
  status                      varchar(64)     default 'STOP' not null    comment '设备状态',
  remark                      varchar(500)    default ''                 comment '备注',
  attr1                       varchar(64)     default null               comment '预留字段1',
  attr2                       varchar(255)    default null               comment '预留字段2',
  attr3                       int(11)         default 0                  comment '预留字段3',
  attr4                       int(11)         default 0                  comment '预留字段4',
  create_by                   varchar(64)     default ''                 comment '创建者',
  create_time 	              datetime                                   comment '创建时间',
  update_by                   varchar(64)     default ''                 comment '更新者',
  update_time                 datetime                                   comment '更新时间',
  primary key (machinery_id)
) engine=innodb auto_increment=200 comment = '设备表';


-- ----------------------------
-- 3、设备点检保养项目表
-- ----------------------------
drop table if exists dv_subject;
create table dv_subject (
  subject_id                  bigint(20)      not null auto_increment    comment '项目ID',
  subject_code                varchar(64)     not null                   comment '项目编码',
  subject_name                varchar(255)                               comment '项目名称',
  subject_type                varchar(64)     default 0                  comment '项目类型',
  subject_content             varchar(500)    not null                   comment '项目内容',
  subject_standard            varchar(255)                               comment '标准',
  enable_flag                 char(1)         default 'Y' not null       comment '是否启用',
  remark                      varchar(500)    default ''                 comment '备注',
  attr1                       varchar(64)     default null               comment '预留字段1',
  attr2                       varchar(255)    default null               comment '预留字段2',
  attr3                       int(11)         default 0                  comment '预留字段3',
  attr4                       int(11)         default 0                  comment '预留字段4',
  create_by                   varchar(64)     default ''                 comment '创建者',
  create_time 	              datetime                                   comment '创建时间',
  update_by                   varchar(64)     default ''                 comment '更新者',
  update_time                 datetime                                   comment '更新时间',
  primary key (subject_id)
) engine=innodb auto_increment=200 comment = '设备点检保养项目表';



-- ----------------------------
-- 4、设备点检保养计划头表
-- ----------------------------
drop table if exists dv_check_plan;
create table dv_check_plan (
  plan_id                     bigint(20)      not null auto_increment    comment '计划ID',
  plan_code                   varchar(64)     not null                   comment '计划编码',
  plan_name                   varchar(255)                               comment '计划名称',
  plan_type                   varchar(64)     not null                   comment '计划类型',
  start_date                  datetime                                   comment '开始日期',
  end_date                    datetime                                   comment '结束日期',
  cycle_type                  varchar(64)                                comment '频率',
  cycle_count                 int(11)                                    comment '次数',
  status                      varchar(64)                                comment '状态',
  remark                      varchar(500)    default ''                 comment '备注',
  attr1                       varchar(64)     default null               comment '预留字段1',
  attr2                       varchar(255)    default null               comment '预留字段2',
  attr3                       int(11)         default 0                  comment '预留字段3',
  attr4                       int(11)         default 0                  comment '预留字段4',
  create_by                   varchar(64)     default ''                 comment '创建者',
  create_time 	              datetime                                   comment '创建时间',
  update_by                   varchar(64)     default ''                 comment '更新者',
  update_time                 datetime                                   comment '更新时间',
  primary key (plan_id)
) engine=innodb auto_increment=200 comment = '设备点检保养计划头表';




-- ----------------------------
-- 5、点检保养设备表
-- ----------------------------
drop table if exists dv_check_machinery;
create table dv_check_machinery (
  record_id                   bigint(20)      not null auto_increment    comment '流水号',
  plan_id                     bigint(20)      not null                   comment '计划ID',
  machinery_id                bigint(20)      not null                   comment '设备ID',
  machinery_code              varchar(64)     not null                   comment '设备编码',
  machinery_name              varchar(255)    not null                   comment '设备名称',
  machinery_brand             varchar(255)                               comment '品牌',
  machinery_spec              varchar(255)                               comment '规格型号',
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
) engine=innodb auto_increment=200 comment = '点检设备表';


-- ----------------------------
-- 6、点检保养项目表
-- ----------------------------
drop table if exists dv_check_subject;
create table dv_check_subject (
  record_id                   bigint(20)      not null auto_increment    comment '流水号',
  plan_id                     bigint(20)      not null                   comment '计划ID',
  subject_id                  bigint(20)      not null                   comment '项目ID',
  subject_code                varchar(64)     not null                   comment '项目编码',
  subject_name                varchar(255)                               comment '项目名称',
  subject_type                varchar(64)                                comment '项目类型',
  subject_content             varchar(500)    not null                   comment '项目内容',
  subject_standard            varchar(255)                               comment '标准',
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
) engine=innodb auto_increment=200 comment = '点检项目表';
