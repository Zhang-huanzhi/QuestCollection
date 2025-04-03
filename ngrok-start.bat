@echo off
echo ==============================================
echo Ngrok Startup Script - Public Access
echo ==============================================

echo.
echo This script will start Ngrok and expose your app to the internet
echo Please ensure:
echo 1. You have installed Ngrok (via npm install -g ngrok or from the website)
echo 2. You have started the frontend service (via start.bat)
echo.

REM Update vite config with Ngrok domain support
cd /d "%~dp0frontend"
echo import { defineConfig } from 'vite' > vite.config.js.new
echo import vue from '@vitejs/plugin-vue' >> vite.config.js.new
echo. >> vite.config.js.new
echo export default defineConfig({ >> vite.config.js.new
echo   plugins: [vue()], >> vite.config.js.new
echo   server: { >> vite.config.js.new
echo     host: '0.0.0.0', >> vite.config.js.new
echo     port: 5174, >> vite.config.js.new
echo     proxy: { >> vite.config.js.new
echo       '/api': { >> vite.config.js.new
echo         target: 'http://localhost:8080', >> vite.config.js.new
echo         changeOrigin: true >> vite.config.js.new
echo       } >> vite.config.js.new
echo     }, >> vite.config.js.new
echo     strictPort: true, >> vite.config.js.new
echo     cors: true, >> vite.config.js.new
echo     hmr: { >> vite.config.js.new
echo       clientPort: 'auto' >> vite.config.js.new
echo     }, >> vite.config.js.new
echo     allowedHosts: [ >> vite.config.js.new
echo       'localhost', >> vite.config.js.new
echo       '127.0.0.1', >> vite.config.js.new
echo       '.ngrok-free.app' >> vite.config.js.new
echo     ] >> vite.config.js.new
echo   } >> vite.config.js.new
echo }) >> vite.config.js.new

move /y vite.config.js.new vite.config.js

echo.
echo vite.config.js has been updated with Ngrok support
echo.
echo Starting Ngrok (port 5174)...
echo.

REM Use user's known Ngrok path
set NGROK_PATH="D:\Environment\Ngrok_ac\ngrok-v3-stable-windows-amd64\ngrok.exe"

echo Using Ngrok from: %NGROK_PATH%
echo.
echo After Ngrok starts, copy the HTTPS URL to share with others
echo.

%NGROK_PATH% http 5174

pause 