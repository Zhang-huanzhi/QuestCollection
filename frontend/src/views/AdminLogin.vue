<template>
  <div class="login-container">
    <div class="login-box">
      <h1 class="title">管理员登录</h1>
      
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px" class="login-form">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" placeholder="请输入管理员用户名"></el-input>
        </el-form-item>
        
        <el-form-item label="密码" prop="password">
          <el-input 
            v-model="form.password" 
            type="password" 
            placeholder="请输入密码"
            show-password
          ></el-input>
        </el-form-item>
        
        <el-form-item>
          <el-button type="primary" :loading="loading" @click="login(formRef)">登录</el-button>
          <el-button @click="resetForm(formRef)">重置</el-button>
          <el-button type="text" @click="goToSubmit">返回问题提交</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

const router = useRouter()
const formRef = ref()
const loading = ref(false)

// 表单数据
const form = reactive({
  username: '',
  password: ''
})

// 表单验证规则
const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' }
  ]
}

// 简单的管理员登录验证（实际项目中应该使用更安全的方式）
const login = async (formEl) => {
  if (!formEl) return
  
  await formEl.validate(async (valid, fields) => {
    if (valid) {
      loading.value = true
      
      // 模拟登录延迟
      setTimeout(() => {
        if (form.username === 'admin' && form.password === 'admin123') {
          localStorage.setItem('isAdmin', 'true')
          ElMessage.success('登录成功')
          router.push('/admin/questions')
        } else {
          ElMessage.error('用户名或密码错误')
        }
        loading.value = false
      }, 800)
    }
  })
}

// 重置表单
const resetForm = (formEl) => {
  if (!formEl) return
  formEl.resetFields()
}

// 跳转到问题提交页面
const goToSubmit = () => {
  router.push('/submit')
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  padding: 20px;
}

.login-box {
  width: 100%;
  max-width: 500px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  padding: 30px;
}

.title {
  text-align: center;
  font-size: 24px;
  margin-bottom: 30px;
  color: #409EFF;
}

.login-form {
  margin-top: 20px;
}
</style> 