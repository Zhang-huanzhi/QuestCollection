<template>
  <div class="submit-container">
    <div class="submit-box">
      <h1 class="title">问题收集系统</h1>
      <p class="subtitle">请提交您的问题，我们将尽快回复</p>
      
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px" class="question-form">
        <el-form-item label="姓名" prop="studentName">
          <el-input v-model="form.studentName" placeholder="请输入您的姓名"></el-input>
        </el-form-item>
        
        <el-form-item label="问题" prop="content">
          <el-input 
            v-model="form.content" 
            type="textarea" 
            rows="6" 
            placeholder="请详细描述您的问题"
          ></el-input>
        </el-form-item>
        
        <el-form-item label="图片" prop="image">
          <el-upload
            class="question-upload"
            action="#"
            :auto-upload="false"
            :on-change="handleImageChange"
            :on-remove="handleImageRemove"
            :limit="1"
            accept="image/jpeg,image/png"
            :file-list="fileList"
          >
            <el-button type="primary">选择图片</el-button>
            <template #tip>
              <div class="el-upload__tip">
                只能上传jpg/jpeg/png文件，且不超过10MB
              </div>
            </template>
          </el-upload>
        </el-form-item>
        
        <el-form-item>
          <el-button type="primary" :loading="loading" @click="submitForm(formRef)">提交问题</el-button>
          <el-button @click="resetForm(formRef)">重置</el-button>
          <el-button type="text" @click="goToAdmin">管理员入口</el-button>
        </el-form-item>
      </el-form>
      
      <!-- 显示提交成功信息 -->
      <el-dialog v-model="dialogVisible" title="提交成功" width="30%" center>
        <div class="success-message">
          <el-icon class="success-icon"><Check /></el-icon>
          <p>您的问题已提交成功！</p>
          <p>我们会尽快回复您的问题</p>
        </div>
        <template #footer>
          <span class="dialog-footer">
            <el-button type="primary" @click="dialogVisible = false">确定</el-button>
          </span>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { Check } from '@element-plus/icons-vue'
import { submitQuestion } from '../api/question'

const router = useRouter()
const formRef = ref()
const loading = ref(false)
const dialogVisible = ref(false)
const fileList = ref([])

// 表单数据
const form = reactive({
  studentName: '',
  content: '',
  image: null
})

// 表单验证规则
const rules = {
  studentName: [
    { required: true, message: '请输入姓名', trigger: 'blur' },
    { min: 2, max: 20, message: '姓名长度应在 2 到 20 个字符之间', trigger: 'blur' }
  ],
  content: [
    { required: true, message: '请输入问题内容', trigger: 'blur' },
    { min: 5, max: 1000, message: '问题内容应在 5 到 1000 个字符之间', trigger: 'blur' }
  ]
}

// 处理图片变更
const handleImageChange = (file) => {
  // 验证文件类型
  const isJPG = file.raw.type === 'image/jpeg'
  const isPNG = file.raw.type === 'image/png'
  
  if (!isJPG && !isPNG) {
    ElMessage.error('图片只能是JPG或PNG格式!')
    fileList.value = []
    form.image = null
    return false
  }
  
  // 验证文件大小
  const isLt10M = file.size / 1024 / 1024 < 10
  if (!isLt10M) {
    ElMessage.error('图片大小不能超过10MB!')
    fileList.value = []
    form.image = null
    return false
  }
  
  form.image = file.raw
  return true
}

// 移除图片
const handleImageRemove = () => {
  form.image = null
}

// 提交表单
const submitForm = async (formEl) => {
  if (!formEl) return
  
  await formEl.validate(async (valid, fields) => {
    if (valid) {
      loading.value = true
      try {
        await submitQuestion(form)
        loading.value = false
        dialogVisible.value = true
        resetForm(formEl)
      } catch (error) {
        ElMessage.error('提交失败，请稍后重试')
        loading.value = false
      }
    }
  })
}

// 重置表单
const resetForm = (formEl) => {
  if (!formEl) return
  formEl.resetFields()
  fileList.value = []
  form.image = null
}

// 跳转到管理员页面
const goToAdmin = () => {
  router.push('/admin')
}
</script>

<style scoped>
.submit-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  padding: 20px;
}

.submit-box {
  width: 100%;
  max-width: 600px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  padding: 30px;
}

.title {
  text-align: center;
  font-size: 24px;
  margin-bottom: 10px;
  color: #409EFF;
}

.subtitle {
  text-align: center;
  color: #606266;
  margin-bottom: 30px;
}

.question-form {
  margin-top: 20px;
}

.question-upload {
  width: 100%;
}

.success-message {
  text-align: center;
  padding: 20px 0;
}

.success-icon {
  font-size: 48px;
  color: #67C23A;
  margin-bottom: 20px;
}
</style>