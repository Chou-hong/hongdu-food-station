## 快速开始

### 环境要求
- JDK 17 及以上
- Maven 3.8+
- MySQL 8.0+
- Redis 7.0+
- Node.js 16+（管理端前端运行）
- 微信开发者工具（小程序端运行）

### 1. 数据库初始化
1. 新建数据库 `hongdu_food_station`
2. 执行根目录下 `hongdu_food_station.sql` 脚本，完成表结构与基础数据初始化

### 2. 后端服务启动
1. 进入目录：`hongdu-server/src/main/resources/`
2. 复制 `application-dev.yml.example`，重命名为 `application-dev.yml`
3. 修改配置内的数据库连接、Redis 地址、微信支付 / 登录密钥等信息
4. 运行 `hongdu-server` 模块的主启动类即可启动后端

### 3. 管理端前端启动
1. 进入目录：`project-hongdu-admin-vue-ts`
2. 安装依赖：
   ```bash
   npm install
3. 启动开发服务：
   ```bash
   npm run serve
4. 小程序端启动
微信开发者工具导入 canteen-miniprogram 目录
修改小程序内的后端接口请求地址，编译后即可预览运行
