<%@ page import="java.sql.*" %>
<%
    int id = Integer.parseInt(request.getParameter("id"));
    String jdbcURL = "jdbc:mysql://localhost:4306/receptionist";
    String dbUser = "root";
    String dbPassword = "";
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
        String sql = "SELECT * FROM patients WHERE id = ?";
        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        rs = stmt.executeQuery();
        if (rs.next()) {
%>
<html>
<head><title>Edit Patient</title>

 <style>
        body {
            font-family: Arial, sans-serif;
            background-color:gray;
            padding: 40px;
        }
        .form-container {
            background-color:lightblue;
            padding: 30px;
            border-radius: 10px;
            width: 400px;
            margin: 0 auto;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .form-container h2 {
            text-align: center;
            margin-bottom: 25px;
        }
        .form-container label {
            font-weight: bold;
        }
        .form-container input[type="text"],
        .form-container input[type="date"],
        .form-container input[type="email"] {
            width: 100%;
            padding: 10px;
            margin-top: 6px;
            margin-bottom: 16px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        .form-container input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 12px;
            width: 100%;
            font-size: 16px;
            border-radius: 5px;
            cursor: pointer;
        }
        .form-container input[type="submit"]:hover {
            background-color: #45a049;
        }
    </style>

</head>
<body>
<div class="form-container">
<h2>Edit Patient</h2>
<form action="UpdatePatientServlet" method="post">
    <input type="hidden" name="id" value="<%= rs.getInt("id") %>" />
    Full Name: <input type="text" name="fullName" value="<%= rs.getString("full_name") %>" /><br/><br/>
    DOB: <input type="date" name="dob" value="<%= rs.getString("dob") %>" /><br/><br/>
    Gender: <input type="text" name="gender" value="<%= rs.getString("gender") %>" /><br/><br/>
    Phone: <input type="text" name="phone" value="<%= rs.getString("phone") %>" /><br/><br/>
    Address: <input type="text" name="address" value="<%= rs.getString("address") %>" /><br/><br/>
    Email: <input type="text" name="email" value="<%= rs.getString("email") %>" /><br/><br/>
    <input type="submit" value="Update Patient" />
</form>
</div>
</body>
</html>
<%
        }
    } catch (Exception e) {
        out.println("Error: " + e.getMessage());
    } finally {
        if (rs != null) rs.close();
        if (stmt != null) stmt.close();
        if (conn != null) conn.close();
    }
%>
