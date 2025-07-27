package com.medi.doctor;

import java.io.*;
import java.sql.*;
import java.nio.file.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@WebServlet("/uploadReport")
@MultipartConfig
public class UploadReportServlet extends HttpServlet {

    // ✅ MySQL connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/hospitaldb";
    private static final String DB_USER = "root";
    private static final String DB_PASS = ""; // XAMPP default password

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Encoding & content type
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // 🔽 1. Receive form fields
        String patientName = request.getParameter("patientName");
        String age = request.getParameter("age");
        String gender = request.getParameter("gender");
        String diagnosis = request.getParameter("diagnosis");
        String prescription = request.getParameter("prescription");
        String reportDate = request.getParameter("reportDate");

        // 🔽 2. Handle file upload
        Part filePart = request.getPart("reportFile");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();

        filePart.write(uploadPath + File.separator + fileName);

        // 🔽 3. Insert into database
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String sql = "INSERT INTO medical_reports (patient_name, age, gender, diagnosis, prescription, report_file, report_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, patientName);
            stmt.setInt(2, Integer.parseInt(age));
            stmt.setString(3, gender);
            stmt.setString(4, diagnosis);
            stmt.setString(5, prescription);
            stmt.setString(6, fileName);
            stmt.setDate(7, Date.valueOf(reportDate));
            stmt.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hospitaldb", "root", "");
            System.out.println("✅ DB connected successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        
        
        // 🔽 4. Confirmation output
        PrintWriter out = response.getWriter();
        out.println("<h2>✅ Report Uploaded & Saved to Database!</h2>");
        out.println("<p>Patient Name: " + patientName + "</p>");
        out.println("<p>Age: " + age + "</p>");
        out.println("<p>Gender: " + gender + "</p>");
        out.println("<p>Diagnosis: " + diagnosis + "</p>");
        out.println("<p>Prescription: " + prescription + "</p>");
        out.println("<p>Report File: " + fileName + "</p>");
        out.println("<p>Report Date: " + reportDate + "</p>");
    }
}
