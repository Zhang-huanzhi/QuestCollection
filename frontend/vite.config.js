import { defineConfig } from 'vite' 
import vue from '@vitejs/plugin-vue' 
 
export default defineConfig({ 
  plugins: [vue()], 
  server: { 
    host: '0.0.0.0', 
    port: 5174, 
    proxy: { 
      '/api': { 
        target: 'http://localhost:8080', 
        changeOrigin: true 
      } 
    }, 
    strictPort: true, 
    cors: true, 
    hmr: { 
      clientPort: 'auto' 
    }, 
    allowedHosts: [ 
      'localhost', 
      '127.0.0.1', 
      '.ngrok-free.app' 
    ] 
  } 
}) 
