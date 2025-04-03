import axios from 'axios'
import { ElMessage } from 'element-plus'

// 创建axios实例
const request = axios.create({
  baseURL: import.meta.env.VITE_API_URL || '/api',
  timeout: 15000,
  withCredentials: false  // 修改为false，避免跨域问题
})

// 请求拦截器
request.interceptors.request.use(
  config => {
    // 此处可以添加认证信息等
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// 响应拦截器
request.interceptors.response.use(
  response => {
    const res = response.data
    
    // 判断请求是否成功 (后端可能不一定会返回status字段，或者使用不同名称)
    if (res.status && res.status !== 200) {
      ElMessage.error(res.message || '请求失败')
      return Promise.reject(new Error(res.message || '请求失败'))
    }
    
    return res
  },
  error => {
    // 处理错误响应
    const { response } = error
    let message = '请求失败'
    
    if (response && response.data) {
      message = response.data.message || `错误码: ${response.status}`
    } else if (error.message && error.message.includes('timeout')) {
      message = '请求超时，请检查网络连接'
    } else if (error.message && error.message.includes('Network Error')) {
      message = '网络错误，请检查服务是否启动'
    }
    
    ElMessage.error(message)
    return Promise.reject(error)
  }
)

export default request 