@echo off
echo Configuring Windows Firewall for Question Collection System...

REM Check for administrator privileges
net session >nul 2>&1
if %errorLevel% neq 0 (
    echo ERROR: This script requires administrator privileges.
    echo Please right-click this file and select "Run as administrator".
    pause
    exit /b 1
)

echo.
echo Adding firewall rules...

REM Add frontend server rule (port 5173)
netsh advfirewall firewall show rule name="QuestionCollection-Frontend" >nul 2>&1
if %errorLevel% equ 0 (
    echo - Frontend rule already exists, skipping...
) else (
    echo - Adding frontend rule (port 5173)...
    netsh advfirewall firewall add rule name="QuestionCollection-Frontend" dir=in action=allow protocol=TCP localport=5173
)

REM Add backend server rule (port 8080)
netsh advfirewall firewall show rule name="QuestionCollection-Backend" >nul 2>&1
if %errorLevel% equ 0 (
    echo - Backend rule already exists, skipping...
) else (
    echo - Adding backend rule (port 8080)...
    netsh advfirewall firewall add rule name="QuestionCollection-Backend" dir=in action=allow protocol=TCP localport=8080
)

echo.
echo Firewall configuration complete!
echo Other devices on your network should now be able to access the application.

pause 