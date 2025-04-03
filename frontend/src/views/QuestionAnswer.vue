<template>
  <div class="answer-container">
    <div class="answer-header">
      <el-page-header @back="goBack" title="返回问题列表" content="回答问题" />
    </div>
    
    <div class="answer-content" v-loading="loading">
      <el-card v-if="question" class="question-card">
        <template #header>
          <div class="question-header">
            <h3>问题详情</h3>
            <el-tag :type="getStatusType(question.status)">{{ getStatusText(question.status) }}</el-tag>
          </div>
        </template>
        
        <div class="question-info">
          <p><strong>提问者：</strong>{{ question.studentName }}</p>
          <p><strong>提问时间：</strong>{{ formatDate(question.createdAt) }}</p>
          <div class="question-content">
            <p><strong>问题内容：</strong></p>
            <div class="content-box">{{ question.content }}</div>
          </div>
          
          <div v-if="question.imagePath" class="question-image">
            <p><strong>附件图片：</strong></p>
            <div class="image-container">
              <el-image 
                :src="getImageUrl(question.imagePath)" 
                :preview-src-list="[getImageUrl(question.imagePath)]"
                fit="contain"
                class="attached-image"
              />
              <p class="image-name">{{ question.imageOriginalName }}</p>
            </div>
          </div>
          
          <div v-if="question.answer" class="question-answer">
            <p><strong>已有回答：</strong></p>
            <div class="content-box">{{ question.answer }}</div>
          </div>
        </div>
      </el-card>
      
      <el-card v-if="question && question.status !== 'ANSWERED'" class="answer-form-card">
        <template #header>
          <h3>回答问题</h3>
        </template>
        
        <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
          <el-form-item label="回答" prop="answer">
            <el-input 
              v-model="form.answer" 
              type="textarea" 
              rows="6" 
              placeholder="请输入回答内容"
            ></el-input>
          </el-form-item>
          
          <el-form-item label="处理方式" prop="status">
            <el-radio-group v-model="form.status">
              <el-radio :label="'ANSWERED'">回答并发布</el-radio>
              <el-radio :label="'REJECTED'">拒绝回答</el-radio>
            </el-radio-group>
          </el-form-item>
          
          <el-form-item>
            <el-button type="primary" :loading="submitting" @click="submitAnswer">提交回答</el-button>
            <el-button @click="resetForm">重置</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getQuestion, answerQuestion } from '../api/question'

const router = useRouter()
const route = useRoute()
const formRef = ref()
const question = ref(null)
const loading = ref(false)
const submitting = ref(false)

// 表单数据
const form = reactive({
  answer: '',
  status: 'ANSWERED'
})

// 表单验证规则
const rules = {
  answer: [
    { required: true, message: '请输入回答内容', trigger: 'blur' },
    { min: 5, max: 2000, message: '回答内容应在 5 到 2000 个字符之间', trigger: 'blur' }
  ],
  status: [
    { required: true, message: '请选择处理方式', trigger: 'change' }
  ]
}

// 获取问题详情
const fetchQuestion = async () => {
  const id = route.params.id
  if (!id) {
    ElMessage.error('问题ID不存在')
    goBack()
    return
  }
  
  loading.value = true
  try {
    const response = await getQuestion(id)
    question.value = response.data
    
    // 如果问题已回答，预填表单
    if (question.value.answer) {
      form.answer = question.value.answer
      form.status = question.value.status
    }
  } catch (error) {
    ElMessage.error('获取问题详情失败')
    goBack()
  } finally {
    loading.value = false
  }
}

// 提交回答
const submitAnswer = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid) => {
    if (valid) {
      submitting.value = true
      try {
        await answerQuestion(question.value.id, form)
        ElMessage.success('回答提交成功')
        goBack()
      } catch (error) {
        ElMessage.error('回答提交失败')
      } finally {
        submitting.value = false
      }
    }
  })
}

// 重置表单
const resetForm = () => {
  if (formRef.value) {
    formRef.value.resetFields()
  }
}

// 返回问题列表
const goBack = () => {
  router.push('/admin/questions')
}

// 状态标签类型
const getStatusType = (status) => {
  const map = {
    'PENDING': 'warning',
    'ANSWERED': 'success',
    'REJECTED': 'danger'
  }
  return map[status] || 'info'
}

// 状态文字
const getStatusText = (status) => {
  const map = {
    'PENDING': '待回答',
    'ANSWERED': '已回答',
    'REJECTED': '已拒绝'
  }
  return map[status] || '未知'
}

// 格式化日期
const formatDate = (dateString) => {
  if (!dateString) return ''
  const date = new Date(dateString)
  return date.toLocaleString()
}

// 获取图片URL
const getImageUrl = (imagePath) => {
  if (!imagePath) return '';
  return `${import.meta.env.VITE_API_URL || '/api'}/files/images/${imagePath}`;
}

// 页面加载时获取问题详情
onMounted(() => {
  fetchQuestion()
})
</script>

<style scoped>
.answer-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.answer-header {
  margin-bottom: 20px;
}

.question-card {
  margin-bottom: 20px;
}

.question-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.question-header h3 {
  margin: 0;
}

.question-info p {
  margin: 10px 0;
}

.content-box {
  background-color: #f8f9fa;
  border-radius: 4px;
  padding: 15px;
  margin: 10px 0;
  white-space: pre-wrap;
}

.question-content, .question-answer {
  margin-top: 20px;
}

.answer-form-card {
  margin-top: 20px;
}

.question-image {
  margin-top: 20px;
}

.image-container {
  margin: 10px 0;
}

.attached-image {
  max-width: 100%;
  max-height: 300px;
  border-radius: 4px;
}

.image-name {
  margin-top: 5px;
  color: #909399;
  font-size: 12px;
}
</style> 