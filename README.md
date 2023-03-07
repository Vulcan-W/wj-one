<p align="center">
	<img alt="logo" width="96px" src="https://gitee.com/kutangguo/wj-mes-ui/raw/master/src/assets/logo/logo.png">
</p>
<h1 align="center" style="margin: 30px 0 30px; font-weight: bold;">WJ-MES</h1>
<h4 align="center">专业、通用、开源、免费的MES系统</h4>
<p align="center">
	<a href="https://gitee.com/kutangguo/wj-mes/stargazers"><img src="https://gitee.com/y_project/WJ-Vue/badge/star.svg?theme=dark"></a>
	<a href="https://gitee.com/kutangguo/wj-mes/blob/master/LICENSE"><img src="https://img.shields.io/github/license/mashape/apistatus.svg"></a>
</p>

# wj-mes

### 系统简介
WJ-MES系统是一款B/S结构、开源、免费的生产执行管理系统。

此系统基于ktg-mes和ruoyi-flowable整合而成。

因为需要做MES，又不想自己从头开始。虽然ktg-mes是gitee中当前最强的MES之一了，但缺少工作流模块。
就想整合一个基于ruoyi-vue框架的工作流框架。试了很多，最终与ruoyi-flowable整合成功。
感谢 心笑峰、tony 大神！

QQ：357793237

### 系统架构

当前版本基于WJ前后端分离版本开发。详情请参照：https://gitee.com/y_project/WJ-Vue

### 功能简介

PC端：

- 系统管理

- 主数据

  - 物料产品管理

    ![](https://gitee.com/kutangguo/wj-mes/raw/master/doc/%E8%AE%BE%E8%AE%A1%E6%96%87%E6%A1%A3/%E5%8E%9F%E5%9E%8B%E8%AE%BE%E8%AE%A1/%E6%88%AA%E5%9B%BE/%E7%89%A9%E6%96%99%E4%BA%A7%E5%93%81%E8%AE%BE%E7%BD%AE.png)

  - 工作站设置

    ![](https://gitee.com/kutangguo/wj-mes/raw/master/doc/%E8%AE%BE%E8%AE%A1%E6%96%87%E6%A1%A3/%E5%8E%9F%E5%9E%8B%E8%AE%BE%E8%AE%A1/%E6%88%AA%E5%9B%BE/%E5%B7%A5%E4%BD%9C%E7%AB%99%E8%AE%BE%E7%BD%AE.png)

- 生产管理

  - 生产排产

    ![](https://gitee.com/kutangguo/wj-mes/raw/master/doc/%E8%AE%BE%E8%AE%A1%E6%96%87%E6%A1%A3/%E5%8E%9F%E5%9E%8B%E8%AE%BE%E8%AE%A1/%E6%88%AA%E5%9B%BE/%E7%94%9F%E4%BA%A7%E6%8E%92%E4%BA%A7.png)

- 质量管理

- 生产排班

  - 节假日/工作日设置

    ![](https://gitee.com/kutangguo/wj-mes/raw/master/doc/%E8%AE%BE%E8%AE%A1%E6%96%87%E6%A1%A3/%E5%8E%9F%E5%9E%8B%E8%AE%BE%E8%AE%A1/%E6%88%AA%E5%9B%BE/%E8%8A%82%E5%81%87%E6%97%A5%E8%AE%BE%E7%BD%AE.png)
    
  - 排班日历

    ![](https://gitee.com/kutangguo/wj-mes/raw/master/doc/%E8%AE%BE%E8%AE%A1%E6%96%87%E6%A1%A3/%E5%8E%9F%E5%9E%8B%E8%AE%BE%E8%AE%A1/%E6%88%AA%E5%9B%BE/%E6%8E%92%E7%8F%AD%E6%97%A5%E5%8E%86.png)
    
    

- 仓储管理
  
  - 库存现有量
  
    ![](https://gitee.com/kutangguo/wj-mes/raw/master/doc/%E8%AE%BE%E8%AE%A1%E6%96%87%E6%A1%A3/%E5%8E%9F%E5%9E%8B%E8%AE%BE%E8%AE%A1/%E6%88%AA%E5%9B%BE/%E5%BA%93%E5%AD%98%E7%8E%B0%E6%9C%89%E9%87%8F.png)
  
- 设备管理

- 统计报表

- 大屏展示

Android端：

- 生产管理
- 质量管理
- 仓储管理
- 设备管理

触控屏端：

- 登录

  ![](https://gitee.com/kutangguo/wj-mes/raw/master/doc/%E8%AE%BE%E8%AE%A1%E6%96%87%E6%A1%A3/%E5%8E%9F%E5%9E%8B%E8%AE%BE%E8%AE%A1/%E6%88%AA%E5%9B%BE/%E8%A7%A6%E6%8E%A7%E5%B1%8F-%E7%99%BB%E5%BD%95%E9%A1%B5%E9%9D%A2.png)

- 生产

- 质量

- 工序

- 设备

PDA：

- 仓储
- 生产

完整功能清单及简介请参照doc->设计文档->功能设计->《功能清单.xml》

### 安装部署

请参照doc->实施文档