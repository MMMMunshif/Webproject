package com.hospital.servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class UpdateAppointmentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String patientName = request.getParameter("patient_name");
        int doctorId = Integer.parseInt(request.getParameter("doctor_id"));
        String date = request.getParameter("appointment_date");
        String time = request.getParameter("appointment_time");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital_db", "root", "affan3080");
            PreparedStatement ps = conn.prepareStatement("UPDATE appointments SET patient_name=?, doctor_id=?, appointment_date=?, appointment_time=? WHERE id=?");
            ps.setString(1, patientName);
            ps.setInt(2, doctorId);
            ps.setString(3, date);
            ps.setString(4, time);
            ps.setInt(5, id);

            ps.executeUpdate();
            ps.close();
            conn.close();

            response.sendRedirect("view_appointments.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
