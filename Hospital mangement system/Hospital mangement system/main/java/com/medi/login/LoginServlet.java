package com.medi.login;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/hospitaldb";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userType = request.getParameter("userType");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String sql = "SELECT * FROM users WHERE username=? AND password=? AND user_type=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, userType);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                if (userType.equals("admin")) {
                    response.sendRedirect("admin.html");
                } else if (userType.equals("doctor")) {
                    response.sendRedirect("uploadreport.html");
                } else if (userType.equals("receptionist")) {
                    response.sendRedirect("receptionalist.html");
                } else if (userType.equals("patient")) {
                    response.sendRedirect("patient.html");
                }
            } else {
                PrintWriter out = response.getWriter();
                out.println("<h3 style='color:red'>Invalid Username / Password / Role</h3>");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
