import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RetrievePatients")
public class RetrievePatientsDID extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public RetrievePatientsDID() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String did = request.getParameter("did");
        if (did == null || did.trim().isEmpty()) {
            out.println("<h3 style='color:red;text-align:center;'>Doctor ID is missing!</h3>");
            return;
        }

        try (Connection c = GetConnection.getConnection()) {
            // 1. Get doctor info and patient IDs safely using PreparedStatement
            String docSql = "SELECT patients, name FROM doctor WHERE did = ?";
            PreparedStatement docStmt = c.prepareStatement(docSql);
            docStmt.setInt(1, Integer.parseInt(did));
            ResultSet docRs = docStmt.executeQuery();

            if (!docRs.next()) {
                out.println("<h3 style='color:red;text-align:center;'>No doctor found with ID: " + did + "</h3>");
                return;
            }

            String patientsStr = docRs.getString("patients");
            String doctorName = docRs.getString("name");

            if (patientsStr == null || patientsStr.trim().isEmpty()) {
                out.println("<h3 style='text-align:center;'>No patients assigned to Dr. " + doctorName + ".</h3>");
                return;
            }

            String[] patientIds = patientsStr.split(",");

            // 2. Query patient details for these IDs using one SQL IN clause for better performance
            // Build placeholders (?, ?, ...) for prepared statement
            StringBuilder placeholders = new StringBuilder();
            for (int i = 0; i < patientIds.length; i++) {
                placeholders.append("?");
                if (i < patientIds.length - 1) placeholders.append(",");
            }

            String patientSql = "SELECT * FROM patient WHERE pid IN (" + placeholders.toString() + ")";
            PreparedStatement patientStmt = c.prepareStatement(patientSql);

            for (int i = 0; i < patientIds.length; i++) {
                patientStmt.setInt(i + 1, Integer.parseInt(patientIds[i].trim()));
            }

            ResultSet patientRs = patientStmt.executeQuery();
            ResultSetMetaData rms = patientRs.getMetaData();

            // 3. Output HTML with table
            out.println("<html><head><title>Patients of Dr. " + doctorName + "</title>");
            out.println("<style>");
            out.println("table {border-collapse: collapse; width: 90%; margin: 20px auto; font-family: Arial, sans-serif;}");
            out.println("th, td {border: 1px solid #ddd; padding: 8px; text-align: left;}");
            out.println("th {background-color: #4CAF50; color: white;}");
            out.println("tr:nth-child(even) {background-color: #f2f2f2;}");
            out.println("</style></head><body>");
            out.println("<h2 style='text-align:center;'>Patients under Dr. " + doctorName + "</h2>");
            out.println("<table><tr>");
            for (int i = 1; i <= rms.getColumnCount(); i++) {
                out.println("<th>" + rms.getColumnName(i) + "</th>");
            }
            out.println("</tr>");

            while (patientRs.next()) {
                out.println("<tr>");
                for (int i = 1; i <= rms.getColumnCount(); i++) {
                    out.println("<td>" + patientRs.getString(i) + "</td>");
                }
                out.println("</tr>");
            }

            out.println("</table></body></html>");

        } catch (SQLException e) {
            out.println("<h3 style='color:red;text-align:center;'>Database error. Please try again later.</h3>");
            e.printStackTrace(out);
        } catch (NumberFormatException e) {
            out.println("<h3 style='color:red;text-align:center;'>Invalid Doctor ID format.</h3>");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
