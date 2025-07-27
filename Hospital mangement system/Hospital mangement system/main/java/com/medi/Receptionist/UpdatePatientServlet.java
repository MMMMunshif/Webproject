package com.medi.doctor;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/UpdatePatientServlet")
public class UpdatePatientServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String fullName = request.getParameter("fullName");
        String dob = request.getParameter("dob");
        String gender = request.getParameter("gender");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String email = request.getParameter("email");

        String jdbcURL = "jdbc:mysql://localhost:4306/receptionist";
        String dbUser = "root";
        String dbPassword = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
            String sql = "UPDATE patients SET full_name=?, dob=?, gender=?, phone=?, address=?, email=? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, fullName);
            stmt.setDate(2, Date.valueOf(dob));
            stmt.setString(3, gender);
            stmt.setString(4, phone);
            stmt.setString(5, address);
            stmt.setString(6, email);
            stmt.setInt(7, id);

            int rows = stmt.executeUpdate();
            response.getWriter().println(rows > 0 ? "Patient updated successfully." : "Update failed.");
            conn.close();
        } catch (Exception e) {
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
