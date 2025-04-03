# Network Setup Guide

Follow these steps to make your application accessible over your local network:

## 1. Start the application

Run the `start.bat` script to start both the backend and frontend servers.

## 2. Find your IP address

1. Open Command Prompt
2. Run the command: `ipconfig`
3. Look for "IPv4 Address" under your active network connection
4. Note down this IP address (e.g., 192.168.1.100)

## 3. Access the application

- On your own computer, you can use:
  - Frontend: http://localhost:5173
  - Backend API: http://localhost:8080/api

- From other devices on the same network, use:
  - Frontend: http://YOUR_IP:5173 (replace YOUR_IP with the IP address you found)
  - Backend API: http://YOUR_IP:8080/api

## Troubleshooting

### Firewall Settings

If other devices cannot access your application, you might need to allow traffic through your firewall:

1. Open Command Prompt as administrator
2. Run these commands:

```
netsh advfirewall firewall add rule name="QuestionCollection-Frontend" dir=in action=allow protocol=TCP localport=5173
netsh advfirewall firewall add rule name="QuestionCollection-Backend" dir=in action=allow protocol=TCP localport=8080
```

### CORS Issues

If you see CORS errors in the browser console, you may need to manually add your IP to the allowed origins:

1. Open the file: `backend/src/main/resources/application.properties`
2. Find the line: `spring.mvc.cors.allowed-origins=...`
3. Add your IP: `spring.mvc.cors.allowed-origins=http://localhost:5173,http://127.0.0.1:5173,http://YOUR_IP:5173`
4. Save and restart the application

### Network Connection Issues

If you see "Network Error" messages:
1. Ensure both frontend and backend are running
2. Check that the correct URLs are being used
3. Verify there are no firewalls or antivirus software blocking the connection 