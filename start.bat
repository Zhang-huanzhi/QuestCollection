@echo off
echo Starting Question Collection System...

REM Get local IP address for LAN access
for /f "tokens=2 delims=:" %%a in ('ipconfig ^| findstr /r /c:"IPv4 Address"') do (
    set IP_ADDRESS=%%a
)
set IP_ADDRESS=%IP_ADDRESS:~1%
echo Local IP address: %IP_ADDRESS%

REM Start the backend server
echo Starting backend server...
start cmd /k "cd backend && mvn spring-boot:run"

echo Waiting for backend to initialize...
timeout /t 10 /nobreak

REM Start the frontend server
echo Starting frontend server...
start cmd /k "cd frontend && npm install && npm run dev"

echo.
echo =====================================================
echo System is now running!
echo.
echo Local access:
echo - Frontend: http://localhost:5173
echo - Backend API: http://localhost:8080/api
echo.
echo LAN access:
echo - Frontend: http://%IP_ADDRESS%:5173
echo - Backend API: http://%IP_ADDRESS%:8080/api
echo =====================================================
echo.
echo Note: If this is your first time, run setup-firewall.bat as administrator
echo to configure Windows Firewall for network access.

pause 