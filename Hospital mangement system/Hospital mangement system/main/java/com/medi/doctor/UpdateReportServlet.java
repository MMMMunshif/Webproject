package com.medi.doctor;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/updateReport")
public class UpdateReportServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/hospitaldb";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String patientName = request.getParameter("patientName");
        String age = request.getParameter("age");
        String gender = request.getParameter("gender");
        String diagnosis = request.getParameter("diagnosis");
        String prescription = request.getParameter("prescription");
        String reportDate = request.getParameter("reportDate");
        String fileName = request.getParameter("file");

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String sql = "UPDATE medical_reports SET patient_name=?, age=?, gender=?, diagnosis=?, prescription=?, report_date=? WHERE report_file=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, patientName);
            stmt.setInt(2, Integer.parseInt(age));
            stmt.setString(3, gender);
            stmt.setString(4, diagnosis);
            stmt.setString(5, prescription);
            stmt.setDate(6, Date.valueOf(reportDate));
            stmt.setString(7, fileName);
            stmt.executeUpdate();

            response.sendRedirect("viewReports");

        } catch (Exception e) {
            e.printStackTrace();
            PrintWriter out = response.getWriter();
            out.println("<h2>❌ Update Failed!</h2>");
        }
    }
}
