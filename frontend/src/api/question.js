import request from './request'

// 学生提交问题（包含图片上传）
export function submitQuestion(data) {
  // 使用FormData处理文件上传
  const formData = new FormData()
  formData.append('studentName', data.studentName)
  formData.append('content', data.content)
  
  // 如果有图片，添加到formData
  if (data.image) {
    formData.append('image', data.image)
  }
  
  return request({
    url: '/questions',
    method: 'post',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

// 获取单个问题详情
export function getQuestion(id) {
  return request({
    url: `/questions/${id}`,
    method: 'get'
  })
}

// 获取所有问题（分页）
export function getAllQuestions(page = 0, size = 10) {
  return request({
    url: '/questions',
    method: 'get',
    params: { page, size }
  })
}

// 按状态获取问题（分页）
export function getQuestionsByStatus(status, page = 0, size = 10) {
  return request({
    url: `/questions/status/${status}`,
    method: 'get',
    params: { page, size }
  })
}

// 获取学生提交的问题
export function getQuestionsByStudent(studentName) {
  return request({
    url: `/questions/student/${studentName}`,
    method: 'get'
  })
}

// 回答问题
export function answerQuestion(id, data) {
  return request({
    url: `/questions/${id}/answer`,
    method: 'put',
    data
  })
}

// 删除问题
export function deleteQuestion(id) {
  return request({
    url: `/questions/${id}`,
    method: 'delete'
  })
} 