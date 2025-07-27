<%@ page import="java.sql.*" %>
<html>
<head>
    <title>View Appointments</title>
    <style>
        body {
            font-family: Arial;
            background-color: #f3f4f6;
        }
        .container {
            width: 80%;
            margin: auto;
            background: white;
            padding: 20px;
            margin-top: 40px;
            border-radius: 10px;
            box-shadow: 0 0 10px #ccc;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            padding: 12px;
            border-bottom: 1px solid #ddd;
            text-align: center;
        }
        th {
            background: #007bff;
            color: white;
        }
        .btn {
            padding: 6px 12px;
            font-size: 14px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            margin: 0 3px;
        }
        .edit-btn {
            background-color: #28a745;
            color: white;
        }
        .delete-btn {
            background-color: #dc3545;
            color: white;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Appointment Records</h2>
<%
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/hospital_db", "root", "affan3080");
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM appointments");

        if (!rs.isBeforeFirst()) {
%>
        <p>No appointment records found.</p>
<%
        } else {
%>
    <table>
        <tr>
            <th>ID</th>
            <th>Patient Name</th>
            <th>Doctor Name</th>
            <th>Date</th>
            <th>Time</th>
            <th>Actions</th> <!-- ✅ New column -->
        </tr>
<%
            while (rs.next()) {
                int id = rs.getInt("id");
%>
        <tr>
            <td><%= id %></td>
            <td><%= rs.getString("patient_name") %></td>
            <td><%= rs.getString("doctor_name") %></td>
            <td><%= rs.getString("appointment_date") %></td>
            <td><%= rs.getString("appointment_time") %></td>
            <td>
                <!-- Edit button -->
                <form action="edit_appointment.jsp" method="get" style="display:inline;">
                    <input type="hidden" name="id" value="<%= id %>">
                    <button type="submit" class="btn edit-btn">Edit</button>
                </form>

                <!-- Delete button -->
                <form action="DeleteAppointmentServlet" method="post" style="display:inline;" onsubmit="return confirm('Are you sure you want to delete this appointment?');">
                    <input type="hidden" name="id" value="<%= id %>">
                    <button type="submit" class="btn delete-btn">Delete</button>
                </form>
            </td>
        </tr>
<%
            }
        }
        rs.close(); st.close(); con.close();
    } catch(Exception e) {
        out.println("Error: " + e.getMessage());
    }
%>
    </table>
</div>
</body>
</html>
