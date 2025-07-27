package com.hospital.servlets;

import java.io.*;

import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/CreateAppointmentServlet")
public class CreateAppointmentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String patientName = request.getParameter("patient_name");
        String doctorName = request.getParameter("doctor_name");
        String appointmentType = request.getParameter("appointment_type");
        String appointmentDate = request.getParameter("appointment_date");
        String appointmentTime = request.getParameter("appointment_time");
        String reason = request.getParameter("reason");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital_db", "root", "your_password");

            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO appointments (patient_name, doctor_name, appointment_type, appointment_date, appointment_time, reason) VALUES (?, ?, ?, ?, ?, ?)"
            );
            ps.setString(1, patientName);
            ps.setString(2, doctorName);
            ps.setString(3, appointmentType);
            ps.setString(4, appointmentDate);
            ps.setString(5, appointmentTime);
            ps.setString(6, reason);
            ps.executeUpdate();

            // Redirect to view page
            response.sendRedirect("view_appointments.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
