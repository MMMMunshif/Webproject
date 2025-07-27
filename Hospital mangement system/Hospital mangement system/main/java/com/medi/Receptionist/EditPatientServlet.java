package com.medi.doctor;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/EditPatientServlet")
public class EditPatientServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id")); //get the id in the editpatient.jsp
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:4307/receptionist", "root", "");

            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM patients WHERE id=?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            if (rs.next()) { //if the patient is there show the form
                out.println("<h2>Edit Patient</h2>");
                out.println("<form method='post' action='UpdatePatientServlet'>");
                out.println("<input type='hidden' name='id' value='" + rs.getInt("id") + "' />");
                out.println("Name: <input type='text' name='fullName' value='" + rs.getString("full_name") + "' /><br>");
                out.println("Phone: <input type='text' name='phone' value='" + rs.getString("phone") + "' /><br>");
                out.println("Email: <input type='email' name='email' value='" + rs.getString("email") + "' /><br>");
                out.println("Address: <textarea name='address'>" + rs.getString("address") + "</textarea><br>");
                out.println("<input type='submit' value='Update' />");
                out.println("</form>");
            }

            conn.close();
        } catch (Exception e) {
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}


