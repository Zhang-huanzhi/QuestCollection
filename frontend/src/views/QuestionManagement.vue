<template>
  <div class="management-container">
    <div class="management-header">
      <h1 class="title">问题管理</h1>
      <div class="actions">
        <el-radio-group v-model="currentStatus" @change="handleStatusChange">
          <el-radio-button label="ALL">全部问题</el-radio-button>
          <el-radio-button label="PENDING">待回答</el-radio-button>
          <el-radio-button label="ANSWERED">已回答</el-radio-button>
          <el-radio-button label="REJECTED">已拒绝</el-radio-button>
        </el-radio-group>
        <el-button type="danger" @click="handleLogout">退出登录</el-button>
      </div>
    </div>
    
    <!-- 问题列表 -->
    <el-card class="question-list" v-loading="loading">
      <el-table :data="questions" style="width: 100%" empty-text="暂无问题">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="studentName" label="学生姓名" width="120" />
        <el-table-column prop="content" label="问题内容" show-overflow-tooltip />
        <el-table-column label="图片" width="80">
          <template #default="scope">
            <el-tag v-if="scope.row.imagePath" type="info" size="small">
              有图片
            </el-tag>
            <span v-else>-</span>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="scope">
            <el-tag
              :type="getStatusType(scope.row.status)"
              disable-transitions
            >
              {{ getStatusText(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createdAt" label="提交时间" width="180">
          <template #default="scope">
            {{ formatDate(scope.row.createdAt) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-button 
              type="primary" 
              size="small"
              :disabled="scope.row.status === 'ANSWERED'"
              @click="handleAnswer(scope.row)"
            >
              回答
            </el-button>
            <el-button
              type="danger"
              size="small"
              @click="handleDelete(scope.row)"
            >
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      
      <!-- 分页 -->
      <div class="pagination-container">
        <el-pagination
          :current-page="currentPage"
          :page-size="pageSize"
          :page-sizes="[5, 10, 20, 30]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          @update:current-page="val => currentPage = val"
          @update:page-size="val => pageSize = val"
        />
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getAllQuestions, getQuestionsByStatus, deleteQuestion } from '../api/question'

const router = useRouter()
const questions = ref([])
const loading = ref(false)
const currentStatus = ref('ALL')
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

// 获取问题列表
const fetchQuestions = async () => {
  loading.value = true
  try {
    let response
    
    if (currentStatus.value === 'ALL') {
      response = await getAllQuestions(currentPage.value - 1, pageSize.value)
    } else {
      response = await getQuestionsByStatus(currentStatus.value, currentPage.value - 1, pageSize.value)
    }
    
    questions.value = response.data.content
    total.value = response.data.totalElements
  } catch (error) {
    ElMessage.error('获取问题列表失败')
  } finally {
    loading.value = false
  }
}

// 处理状态变更
const handleStatusChange = () => {
  currentPage.value = 1
  fetchQuestions()
}

// 处理页码变更
const handleCurrentChange = (page) => {
  currentPage.value = page
  fetchQuestions()
}

// 处理每页数量变更
const handleSizeChange = (size) => {
  pageSize.value = size
  fetchQuestions()
}

// 回答问题
const handleAnswer = (row) => {
  router.push(`/admin/answer/${row.id}`)
}

// 删除问题
const handleDelete = (row) => {
  ElMessageBox.confirm(
    '确定要删除这个问题吗？删除后无法恢复。',
    '提示',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }
  ).then(async () => {
    try {
      await deleteQuestion(row.id)
      ElMessage.success('删除成功')
      fetchQuestions()
    } catch (error) {
      ElMessage.error('删除失败')
    }
  }).catch(() => {})
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

// 退出登录
const handleLogout = () => {
  localStorage.removeItem('isAdmin')
  router.push('/admin')
}

// 页面加载时获取问题列表
onMounted(() => {
  fetchQuestions()
})
</script>

<style scoped>
.management-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.management-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.title {
  font-size: 24px;
  color: #409EFF;
  margin: 0;
}

.actions {
  display: flex;
  gap: 15px;
}

.question-list {
  margin-bottom: 20px;
}

.pagination-container {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}
</style> 