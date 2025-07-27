<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hospital Management System - Login</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        
        body {
            background-color: #f5f5f5;
            display: flex;
            flex-direction: column;
            min-height: 100vh;
        }
        
        header {
            background-color: #2c3e50;
            color: white;
            padding: 1rem 2rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }
        
        .logo {
            display: flex;
            align-items: center;
            gap: 1rem;
        }
        
        .logo h1 {
            font-size: 1.5rem;
        }
        
        .logo-icon {
            color: #3498db;
            font-size: 2rem;
            font-weight: bold;
        }
        
        .container {
            flex: 1;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 2rem;
        }
        
        .login-container {
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            padding: 2rem;
            width: 100%;
            max-width: 400px;
            text-align: center;
        }
        
        .login-container h2 {
            color: #2c3e50;
            margin-bottom: 1.5rem;
        }
        
        .form-group {
            margin-bottom: 1.5rem;
            text-align: left;
        }
        
        .form-group label {
            display: block;
            margin-bottom: 0.5rem;
            color: #555;
            font-weight: 500;
        }
        
        .form-group input, .form-group select {
            width: 100%;
            padding: 0.75rem;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 1rem;
        }
        
        .btn {
            background-color: #3498db;
            color: white;
            border: none;
            padding: 0.75rem 1.5rem;
            font-size: 1rem;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s;
            width: 100%;
        }
        
        .btn:hover {
            background-color: #2980b9;
        }
        
        .forgot-password {
            display: block;
            margin-top: 1rem;
            color: #3498db;
            text-decoration: none;
            font-size: 0.9rem;
        }
        
        footer {
            background-color: #2c3e50;
            color: white;
            text-align: center;
            padding: 1rem;
            margin-top: auto;
        }
        
        @media (max-width: 768px) {
            .container {
                padding: 1rem;
            }
        }
    </style>
</head>
<body>
    <header>
        <div class="logo">
            <div class="logo-icon">H+</div>
            <h1>MedCare Hospital Management System</h1>
        </div>
    </header>
    
    <div class="container">
        <div class="login-container">
            <h2>Login</h2>
            <form id="loginForm" action="login" method="post">
                <div class="form-group">
                    <label for="userType">User Type</label>
                    <select id="userType" name="userType" required>
                        <option value="">Select User Type</option>
                        <option value="admin">Administrator</option>
                        <option value="doctor">Doctor</option>
                        <option value="receptionist">Receptionist</option>
                        <option value="patient">Patient</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" id="username" name="username" required>
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <button type="submit" class="btn">Login</button>
                <a href="#" class="forgot-password">Forgot password?</a>
            </form>
        </div>
    </div>
    
    <footer>
        <p>&copy; 2025 MedCare Hospital Management System. All rights reserved.</p>
    </footer>

    <script>
        document.getElementById('loginForm').addEventListener('submit', function(e) {
    e.preventDefault();
    const userType = document.getElementById('userType').value;
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    
    // In a real application, you would validate credentials with a server
    // For demo purposes, we'll just redirect to different dashboards
    if (username && password) {
        // Redirect based on user type
        if (userType === 'admin') {
            window.location.href = 'admin.html';
        } else if (userType === 'doctor') {
            window.location.href = 'doctor.html';
        } else if (userType === 'receptionist') {
            window.location.href = 'receptionalist.html';
        } else if (userType === 'patient') {
            window.location.href = 'Patient.jsp';
        } else {
            alert('Please select a user type');
        }
    }

        });
    </script>
</body>
</html>