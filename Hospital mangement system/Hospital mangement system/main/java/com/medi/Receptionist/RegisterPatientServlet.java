package com.medi.doctor;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/RegisterPatientServlet")
public class RegisterPatientServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) //do post is used to handle post request
            throws ServletException, IOException {

        // reading data entered by the user
        String fullName = request.getParameter("fullName");
        String dob = request.getParameter("dob");
        String gender = request.getParameter("gender");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String email = request.getParameter("email");

        // DB connection parameters
        String jdbcURL = "jdbc:mysql://localhost:4306/receptionist";
        String dbUser = "root";
        String dbPassword = "";

        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connection to the database
            Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

            // Insert query
            String sql = "INSERT INTO patients (full_name, dob, gender, phone, address, email) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);//encapsulstion
            stmt.setString(1, fullName);
            stmt.setDate(2, Date.valueOf(dob)); // Convert to java.sql.Date
            stmt.setString(3, gender);
            stmt.setString(4, phone);
            stmt.setString(5, address);
            stmt.setString(6, email);

            int result = stmt.executeUpdate(); //execute the insert query

            if (result > 0) {
                // Show the registered patient details
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();

                out.println("<html><head><style>"); //starts HTML structure
                out.println("table { border-collapse: collapse; width: 60%; margin-top: 20px; }");
                out.println("th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }");
                out.println("th { background-color: #f2f2f2; }");
                out.println(".button { padding: 6px 12px; border: none; color: white; border-radius: 4px; text-decoration: none; }");
                out.println(".edit-btn { background-color: green; }");
                out.println(".delete-btn { background-color: red; }");
                out.println("</style></head><body style='font-family: Arial, sans-serif;'>");

                out.println("<h3 style='color: green;'>Patient registered successfully.</h3>");

                // Fetch the newly inserted patient or lastly inserted data
                String fetchSql = "SELECT * FROM patients ORDER BY id DESC LIMIT 1";
                Statement fetchStmt = conn.createStatement();
                ResultSet rs = fetchStmt.executeQuery(fetchSql); //Activating the query above

                if (rs.next()) {
                    int patientId = rs.getInt("id");

                    out.println("<h4>Registered Patient Details:</h4>");
                    out.println("<table>");
                    out.println("<tr><th>Full Name</th><td>" + rs.getString("full_name") + "</td></tr>");
                    out.println("<tr><th>DOB</th><td>" + rs.getDate("dob") + "</td></tr>");
                    out.println("<tr><th>Gender</th><td>" + rs.getString("gender") + "</td></tr>");
                    out.println("<tr><th>Phone</th><td>" + rs.getString("phone") + "</td></tr>");
                    out.println("<tr><th>Address</th><td>" + rs.getString("address") + "</td></tr>");
                    out.println("<tr><th>Email</th><td>" + rs.getString("email") + "</td></tr>");
                    out.println("<tr><th>Actions</th><td>");

                    // Edit and Delete buttons newly inserted patients
                    out.println("<a href='editPatient.jsp?id=" + patientId + "' class='button edit-btn'>Edit</a>&nbsp;");
                    out.println("<a href='DeletePatientServlet?id=" + patientId + "' class='button delete-btn' onclick=\"return confirm('Are you sure you want to delete this patient?');\">Delete</a>");

                    out.println("</td></tr>");
                    out.println("</table>");
                }

                rs.close();
                fetchStmt.close();
                out.println("</body></html>");
            } else {
                response.getWriter().println("Failed to register patient."); //if insertion fails,print error message
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
