<%@ page import="java.sql.*" %>
<%
    String id = request.getParameter("id");
    String patientName = "";
    String doctorId = "";
    String date = "";
    String time = "";

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital_db", "root", "affan3080");
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM appointments WHERE id=?");
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            patientName = rs.getString("patient_name");
            doctorId = rs.getString("doctor_id");
            date = rs.getString("appointment_date");
            time = rs.getString("appointment_time");
        }

        rs.close();
        ps.close();
        conn.close();
    } catch (Exception e) {
        out.println("Error: " + e.getMessage());
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Appointment</title>
</head>
<body>
    <h2>Edit Appointment</h2>
    <form action="UpdateAppointmentServlet" method="post">
        <input type="hidden" name="id" value="<%= id %>">
        <label>Patient Name:</label><br>
        <input type="text" name="patient_name" value="<%= patientName %>" required><br><br>
        <label>Doctor ID:</label><br>
        <input type="text" name="doctor_id" value="<%= doctorId %>" required><br><br>
        <label>Appointment Date:</label><br>
        <input type="date" name="appointment_date" value="<%= date %>" required><br><br>
        <label>Appointment Time:</label><br>
        <input type="time" name="appointment_time" value="<%= time %>" required><br><br>
        <input type="submit" value="Update">
    </form>
</body>
</html>
