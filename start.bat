@echo off
setlocal enabledelayedexpansion

echo ==============================================
echo Question Collection System - Startup Script
echo ==============================================

REM Start the frontend
cd /d "%~dp0frontend"
echo Starting frontend service...
start cmd /c "npm run dev -- --host 0.0.0.0 --port 5174"

REM Start the backend
cd /d "%~dp0backend"
echo Starting backend service...
start cmd /c "mvn spring-boot:run"

echo.
echo ==============================================
echo System is now running!
echo.
echo Frontend URL: http://localhost:5174
echo Backend API: http://localhost:8080/api
echo.
echo Please keep command windows open to keep services running
echo ==============================================

echo Press any key to exit this window (services will continue in background)...
pause > nul 