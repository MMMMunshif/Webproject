package com.medi.doctor;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/editReport")
public class EditReportServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/hospitaldb";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fileName = request.getParameter("file");

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String sql = "SELECT * FROM medical_reports WHERE report_file = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, fileName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Map<String, String> report = new HashMap<>();
                report.put("patientName", rs.getString("patient_name"));
                report.put("age", String.valueOf(rs.getInt("age")));
                report.put("gender", rs.getString("gender"));
                report.put("diagnosis", rs.getString("diagnosis"));
                report.put("prescription", rs.getString("prescription"));
                report.put("reportDate", rs.getDate("report_date").toString());
                report.put("file", rs.getString("report_file"));

                request.setAttribute("report", report);
                RequestDispatcher dispatcher = request.getRequestDispatcher("editReport.jsp");
                dispatcher.forward(request, response);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("viewReports");
    }
}
