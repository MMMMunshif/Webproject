package com.medi.doctor;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/deleteReport")

public class DeleteReportServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/hospitaldb";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fileName = request.getParameter("file");

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            // 1. Delete from database
            String sql = "DELETE FROM medical_reports WHERE report_file=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, fileName);
            stmt.executeUpdate();

            // 2. Delete file from uploads folder
            String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
            File file = new File(uploadPath, fileName);
            if (file.exists()) file.delete();

            // 3. Redirect
            response.sendRedirect("viewReports");

        } catch (Exception e) {
            e.printStackTrace();
            PrintWriter out = response.getWriter();
            out.println("<h2>❌ Delete Failed!</h2>");
        }
    }
}
