# 问题收集系统

一个简洁高效的问题收集和回答管理系统，适用于教育场景，让学生能够提交问题并附带图片，教师/管理员可以进行回复管理。

## 功能特点

- 📝 **问题提交**: 学生可以提交问题并上传PNG或JPG格式的图片
- 👨‍🏫 **问题管理**: 管理员可以查看、回答、拒绝或删除问题
- 🖼️ **图片支持**: 支持图片上传和预览功能
- 🔍 **状态筛选**: 可按状态(待回答/已回答/已拒绝)筛选问题
- 🌐 **网络支持**: 支持本地、局域网和公网访问

## 技术栈

### 前端
- Vue 3 - 现代化的响应式框架
- Element Plus - 美观的UI组件库
- Axios - HTTP请求客户端
- Vue Router - 页面路由管理
- Pinia - 状态管理

### 后端
- Spring Boot 2.7 - Java后端框架
- Spring Data JPA - 数据访问层
- MySQL - 关系型数据库
- Lombok - 减少样板代码
- Spring Validation - 数据验证

## 快速开始

### 前置条件

- JDK 8或更高版本
- Node.js 16或更高版本
- MySQL 5.7或更高版本

### 设置数据库

1. 创建MySQL数据库:
```sql
CREATE DATABASE question_collection;
```

2. 配置数据库连接:
编辑 `backend/src/main/resources/application.properties` 修改以下配置:
```properties
spring.datasource.username=你的MySQL用户名
spring.datasource.password=你的MySQL密码
```

### 启动应用

1. 运行 `start.bat` 批处理文件（Windows系统）
   
   或手动启动:

   ```bash
   # 启动后端
   cd backend
   mvn spring-boot:run
   
   # 启动前端
   cd frontend
   npm install
   npm run dev
   ```

2. 访问应用:
   - 前端: http://localhost:5173
   - 后端API: http://localhost:8080/api

### 局域网访问

1. 运行 `setup-firewall.bat` 以管理员身份配置防火墙（仅首次需要）
   
2. 使用脚本显示的IP地址从其他设备访问:
   - 前端: http://您的IP地址:5173
   - 后端API: http://您的IP地址:8080/api

## 项目结构

```
question-collection/
├── backend/                   # 后端 Spring Boot 项目
│   ├── src/main/java/
│   │   └── com/example/questioncollection/
│   │       ├── config/        # 配置类
│   │       ├── controller/    # 控制器
│   │       ├── dto/           # 数据传输对象
│   │       ├── entity/        # 实体类
│   │       ├── exception/     # 异常处理
│   │       ├── repository/    # 数据访问层
│   │       └── service/       # 业务逻辑层
│   └── src/main/resources/    # 配置文件
│
├── frontend/                  # 前端 Vue 3 项目
│   ├── src/
│   │   ├── api/               # API请求
│   │   ├── assets/            # 静态资源
│   │   ├── components/        # Vue组件
│   │   ├── router/            # 路由配置
│   │   ├── store/             # 状态管理
│   │   ├── views/             # 页面视图
│   │   ├── App.vue            # 主组件
│   │   └── main.js            # 入口文件
│   ├── .env                   # 环境变量
│   └── vite.config.js         # Vite配置
│
├── start.bat                  # 启动脚本
├── setup-firewall.bat         # 防火墙配置脚本
└── README.md                  # 项目文档
```

## 用户指南

### 学生使用流程

1. 访问前端页面
2. 填写姓名和问题内容
3. 可选择上传图片（支持JPG/PNG格式）
4. 提交问题
5. 等待管理员回复

### 管理员使用流程

1. 点击"管理员入口"
2. 使用默认账户登录:
   - 用户名: admin
   - 密码: admin123
3. 查看待回答问题列表
4. 可以对问题进行以下操作:
   - 回答问题
   - 拒绝问题
   - 删除问题

## 公网部署

如需部署到公网，有以下几种方案:

### 方案1: 端口转发
配置路由器将外部端口映射到本地服务

### 方案2: 内网穿透
使用花生壳、Ngrok等工具进行内网穿透

### 方案3: 云服务器部署
将应用部署到云服务器上，配置域名访问

## 注意事项

- 默认管理员账号在生产环境中应当修改
- 图片上传默认存储在项目根目录的`upload-dir`文件夹中
- 本应用默认配置适用于开发和测试环境，生产环境请加强安全配置

## 授权许可

本项目采用MIT许可证 