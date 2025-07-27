<%@ page import="java.sql.*" %>
<%
    String id = request.getParameter("id");

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital_db", "root", "affan3080");
        PreparedStatement ps = conn.prepareStatement("DELETE FROM appointments WHERE id=?");
        ps.setString(1, id);
        ps.executeUpdate();

        ps.close();
        conn.close();

        response.sendRedirect("view_appointments.jsp");
    } catch (Exception e) {
        out.println("Error: " + e.getMessage());
    }
%>
