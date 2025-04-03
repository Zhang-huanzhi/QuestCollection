# Simple Ngrok Usage Guide

## 1. Install Ngrok

```
npm install -g ngrok
```

Or download from [Ngrok website](https://ngrok.com/download).

## 2. Step-by-Step Usage

### Step 1: Start the Application

Run `start.bat` to start both frontend and backend services.

### Step 2: Start Ngrok

Run `ngrok-start.bat` to start Ngrok tunnel service.

### Step 3: Share the Link

After Ngrok starts, it will display a public URL, like:
```
https://xxxx-xxxx-xxxx.ngrok-free.app
```

Share this link with people who need to access your application.

## Common Issues

1. **"Blocked request" error**: Automatically handled in our scripts

2. **Link changes each time**: Free version of Ngrok generates a new URL each time

3. **Using your own domain**: Requires Ngrok paid version 