-- ----------------------------
-- 1、仓库表
-- ----------------------------
drop table if exists wm_warehouse;
create table wm_warehouse (
  warehouse_id           bigint(20)      not null auto_increment    comment '仓库ID',
  warehouse_code         varchar(64)     not null                   comment '仓库编码',
  warehouse_name         varchar(255)    not null                   comment '仓库名称',
  location               varchar(500)                               comment '位置',
  area                   double(12,2)                               comment '面积',
  charge                 varchar(64)                                comment '负责人',
  remark                 varchar(500)    default ''                 comment '备注',
  attr1                  varchar(64)     default null               comment '预留字段1',
  attr2                  varchar(255)     default null              comment '预留字段2',
  attr3                  int(11)         default 0                  comment '预留字段3',
  attr4                  int(11)         default 0                  comment '预留字段4',
  create_by              varchar(64)     default ''                 comment '创建者',
  create_time 	         datetime                                   comment '创建时间',
  update_by              varchar(64)     default ''                 comment '更新者',
  update_time            datetime                                   comment '更新时间',
  primary key (warehouse_id)
) engine=innodb auto_increment=200 comment = '仓库表';


-- ----------------------------
-- 2、库区表
-- ----------------------------
drop table if exists wm_storage_location;
create table wm_storage_location (
  location_id           bigint(20)      not null auto_increment     comment '库区ID',
  location_code         varchar(64)     not null                    comment '库区编码',
  location_name         varchar(255)    not null                    comment '库区名称',
  warehouse_id          bigint(20)      not null                    comment '仓库ID',
  area                  double(12,2)                                comment '面积',
  area_flag             char(1)         default 'N'                 comment '是否开启库位管理',
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time 	        datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (location_id)
) engine=innodb auto_increment=200 comment = '库区表';


-- ----------------------------
-- 3、库位表
-- ----------------------------
drop table if exists wm_storage_area;
create table wm_storage_area (
  area_id               bigint(20)      not null auto_increment     comment '库位ID',
  area_code             varchar(64)     not null                    comment '库位编码',
  area_name             varchar(255)    not null                    comment '库位名称',
  location_id           bigint(20)      not null                    comment '库区ID',
  area                  double(8,2)                                 comment '面积',
  max_loa               double(8,2)                                 comment '最大载重量',
  position_x            int(11)                                     comment '库位位置X',
  position_y            int(11)                                     comment '库位位置y',
  position_z            int(11)                                     comment '库位位置z',
  enable_flag           char(1)                                     comment '是否启用',
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time 	        datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (area_id)
) engine=innodb auto_increment=200 comment = '库位表';


-- ----------------------------
-- 4、物料入库单表
-- ----------------------------
drop table if exists wm_item_recpt;
create table wm_item_recpt (
  recpt_id              bigint(20)      not null auto_increment     comment '入库单ID',
  recpt_code            varchar(64)     not null                    comment '入库单编号',
  recpt_name            varchar(255)    not null                    comment '入库单名称',
  iqc_id                bigint(20)                                  comment '来料检验单ID',
  iqc_code              varchar(64)                                 comment '来料检验单编号',  
  po_code               varchar(64)                                 comment '采购订单编号',  
  vendor_id             bigint(20)                                  comment '供应商ID',
  vendor_code           varchar(64)                                 comment '供应商编码',
  vendor_name           varchar(255)                                comment '供应商名称',
  vendor_nick           varchar(255)                                comment '供应商简称',
  warehouse_id          bigint(20)                                  comment '仓库ID',
  warehouse_code        varchar(64)                                 comment '仓库编码',
  warehouse_name        varchar(255)                                comment '仓库名称',
  location_id           bigint(20)                                  comment '库区ID',
  location_code         varchar(64)                                 comment '库区编码',
  location_name         varchar(255)                                comment '库区名称',
  area_id               bigint(20)                                  comment '库位ID',
  area_code             varchar(64)                                 comment '库位编码',
  area_name             varchar(255)                                comment '库位名称', 
  recpt_date            datetime                                    comment '入库日期',
  status                varchar(64)     default 'PREPARE'           comment '单据状态',  
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time 	        datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (recpt_id)
) engine=innodb auto_increment=200 comment = '物料入库单表';


-- ----------------------------
-- 5、物料入库单行表
-- ----------------------------
drop table if exists wm_item_recpt_line;
create table wm_item_recpt_line (
  line_id               bigint(20)      not null auto_increment     comment '行ID',
  recpt_id              bigint(20)                                  comment '入库单ID',
  item_id               bigint(20)      not null                    comment '产品物料ID',
  item_code             varchar(64)                                 comment '产品物料编码',
  item_name             varchar(255)                                comment '产品物料名称',
  specification         varchar(500)                                comment '规格型号',
  unit_of_measure       varchar(64)                                 comment '单位',
  quantity_recived      double(12,2)    not null                    comment '入库数量',
  batch_code            varchar(255)                                comment '入库批次号',
  warehouse_id          bigint(20)                                  comment '仓库ID',
  warehouse_code        varchar(64)                                 comment '仓库编码',
  warehouse_name        varchar(255)                                comment '仓库名称',
  location_id           bigint(20)                                  comment '库区ID',
  location_code         varchar(64)                                 comment '库区编码',
  location_name         varchar(255)                                comment '库区名称',
  area_id               bigint(20)                                  comment '库位ID',
  area_code             varchar(64)                                 comment '库位编码',
  area_name             varchar(255)                                comment '库位名称', 
  expire_date           datetime                                    comment '有效期', 
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time 	        datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (line_id)
) engine=innodb auto_increment=200 comment = '物料入库单行表';




-- ----------------------------
-- 6、库存事务表
-- ----------------------------
drop table if exists wm_transaction;
create table wm_transaction (
  transaction_id        bigint(20)      not null auto_increment     comment '事务ID',
  transaction_type      varchar(64)     not null                    comment '事务类型',
  item_id               bigint(20)      not null                    comment '产品物料ID',
  item_code             varchar(64)                                 comment '产品物料编码',
  item_name             varchar(255)                                comment '产品物料名称',
  specification         varchar(500)                                comment '规格型号',
  unit_of_measure       varchar(64)                                 comment '单位',   
  batch_code            varchar(255)                                comment '入库批次号',
  warehouse_id          bigint(20)      not null                    comment '仓库ID',
  warehouse_code        varchar(64)                                 comment '仓库编码',
  warehouse_name        varchar(255)                                comment '仓库名称',
  location_id           bigint(20)                                  comment '库区ID',
  location_code         varchar(64)                                 comment '库区编码',
  location_name         varchar(255)                                comment '库区名称',
  area_id               bigint(20)                                  comment '库位ID',
  area_code             varchar(64)                                 comment '库位编码',
  area_name             varchar(255)                                comment '库位名称', 
  vendor_id             bigint(20)                                  comment '供应商ID',
  vendor_code           varchar(64)                                 comment '供应商编号',
  vendor_name           varchar(255)                                comment '供应商名称',
  vendor_nick           varchar(64)                                 comment '供应商简称',
  source_doc_type       varchar(64)                                 comment '单据类型',
  source_doc_id         bigint(20)                                  comment '单据ID',
  source_doc_code       varchar(64)                                 comment '单据编号',
  source_doc_line_id    bigint(20)                                  comment '单据行ID',
  material_stock_id     bigint(20)      not null                    comment '库存记录ID',
  transaction_flag      int(1)          default 1                   comment '库存方向',
  transaction_quantity  double(12,2)                                comment '事务数量',
  transaction_date      datetime                                    comment '事务日期',
  related_transaction_id bigint(20)                                 comment '关联的事务ID', 
  erp_date              datetime                                    comment 'ERP账期',
  expire_date           datetime                                    comment '库存有效期',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time 	        datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (transaction_id)
) engine=innodb auto_increment=200 comment = '库存事务表';



-- ----------------------------
-- 7、库存记录表
-- ----------------------------
drop table if exists wm_material_stock;
create table wm_material_stock (
  material_stock_id     bigint(20)      not null auto_increment     comment '事务ID',
  item_type_id          bigint(20)                                  comment '物料类型ID',
  item_id               bigint(20)      not null                    comment '产品物料ID',
  item_code             varchar(64)                                 comment '产品物料编码',
  item_name             varchar(255)                                comment '产品物料名称',
  specification         varchar(500)                                comment '规格型号',
  unit_of_measure       varchar(64)                                 comment '单位',   
  batch_code            varchar(255)                                comment '入库批次号',
  warehouse_id          bigint(20)      not null                    comment '仓库ID',
  warehouse_code        varchar(64)                                 comment '仓库编码',
  warehouse_name        varchar(255)                                comment '仓库名称',
  location_id           bigint(20)                                  comment '库区ID',
  location_code         varchar(64)                                 comment '库区编码',
  location_name         varchar(255)                                comment '库区名称',
  area_id               bigint(20)                                  comment '库位ID',
  area_code             varchar(64)                                 comment '库位编码',
  area_name             varchar(255)                                comment '库位名称', 
  vendor_id             bigint(20)                                  comment '供应商ID',
  vendor_code           varchar(64)                                 comment '供应商编号',
  vendor_name           varchar(255)                                comment '供应商名称',
  vendor_nick           varchar(64)                                 comment '供应商简称',
  quantity_onhand       double(12,2)                                comment '在库数量',
  expire_date           datetime                                    comment '库存有效期',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time 	        datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (material_stock_id)
) engine=innodb auto_increment=200 comment = '库存记录表';





-- ----------------------------
-- 8、供应商退货表
-- ----------------------------
drop table if exists wm_rt_vendor;
create table wm_rt_vendor (
  rt_id              bigint(20)      not null auto_increment        comment '退货单ID',
  rt_code            varchar(64)     not null                       comment '退货单编号',
  rt_name            varchar(255)    not null                       comment '退货单名称',  
  po_code               varchar(64)                                 comment '采购订单编号',  
  vendor_id             bigint(20)                                  comment '供应商ID',
  vendor_code           varchar(64)                                 comment '供应商编码',
  vendor_name           varchar(255)                                comment '供应商名称',
  vendor_nick           varchar(255)                                comment '供应商简称',
  batch_code            varchar(255)                                comment '批次号',
  rt_date               datetime                                    comment '退货日期',
  status                varchar(64)     default 'PREPARE'           comment '单据状态',  
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time 	        datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (rt_id)
) engine=innodb auto_increment=200 comment = '供应商退货表';


-- ----------------------------
-- 9、供应商退货行表
-- ----------------------------
drop table if exists wm_rt_vendor_line;
create table wm_rt_vendor_line (
  line_id               bigint(20)      not null auto_increment     comment '行ID',
  rt_id                 bigint(20)                                  comment '退货单ID',
  material_stock_id     bigint(20)                                  comment '库存记录ID',
  item_id               bigint(20)      not null                    comment '产品物料ID',
  item_code             varchar(64)                                 comment '产品物料编码',
  item_name             varchar(255)                                comment '产品物料名称',
  specification         varchar(500)                                comment '规格型号',
  unit_of_measure       varchar(64)                                 comment '单位',
  quantity_rted         double(12,2)    not null                    comment '退货数量',
  batch_code            varchar(255)                                comment '批次号',
  warehouse_id          bigint(20)                                  comment '仓库ID',
  warehouse_code        varchar(64)                                 comment '仓库编码',
  warehouse_name        varchar(255)                                comment '仓库名称',
  location_id           bigint(20)                                  comment '库区ID',
  location_code         varchar(64)                                 comment '库区编码',
  location_name         varchar(255)                                comment '库区名称',
  area_id               bigint(20)                                  comment '库位ID',
  area_code             varchar(64)                                 comment '库位编码',
  area_name             varchar(255)                                comment '库位名称', 
  remark                varchar(500)    default ''                  comment '备注',
  attr1                 varchar(64)     default null                comment '预留字段1',
  attr2                 varchar(255)    default null                comment '预留字段2',
  attr3                 int(11)         default 0                   comment '预留字段3',
  attr4                 int(11)         default 0                   comment '预留字段4',
  create_by             varchar(64)     default ''                  comment '创建者',
  create_time 	        datetime                                    comment '创建时间',
  update_by             varchar(64)     default ''                  comment '更新者',
  update_time           datetime                                    comment '更新时间',
  primary key (line_id)
) engine=innodb auto_increment=200 comment = '供应商退货行表';








