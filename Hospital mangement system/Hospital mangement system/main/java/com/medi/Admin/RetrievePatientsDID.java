import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RetrievePatientsDID")
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
            out.println("<h3 style='color:red'>Doctor ID is missing!</h3>");
            return;
        }

        try (Connection c = GetConnection.getConnection()) {
            // First get doctor's patient list and name
            String doctorSql = "SELECT patients, name FROM doctor WHERE did = ?";
            PreparedStatement psDoctor = c.prepareStatement(doctorSql);
            psDoctor.setString(1, did);
            ResultSet rsDoctor = psDoctor.executeQuery();

            if (!rsDoctor.next()) {
                out.println("<h3 style='color:red'>Doctor not found!</h3>");
                return;
            }

            String patientsStr = rsDoctor.getString("patients");
            String doctorName = rsDoctor.getString("name");

            if (patientsStr == null || patientsStr.trim().isEmpty()) {
                out.println("<h3>No patients found under Dr. " + doctorName + "</h3>");
                return;
            }

            // Parse patient IDs
            String[] patientIds = patientsStr.split(",");

            // Build query to get all patients in one go using IN clause
            StringBuilder queryBuilder = new StringBuilder("SELECT * FROM patient WHERE pid IN (");
            List<String> validPatientIds = new ArrayList<>();
            for (String p : patientIds) {
                p = p.trim();
                if (!p.isEmpty() && p.matches("\\d+")) { // basic validation for digits only
                    validPatientIds.add(p);
                }
            }

            if (validPatientIds.isEmpty()) {
                out.println("<h3>No valid patients found under Dr. " + doctorName + "</h3>");
                return;
            }

            for (int i = 0; i < validPatientIds.size(); i++) {
                queryBuilder.append("?");
                if (i < validPatientIds.size() - 1) {
                    queryBuilder.append(",");
                }
            }
            queryBuilder.append(")");

            PreparedStatement psPatients = c.prepareStatement(queryBuilder.toString());
            for (int i = 0; i < validPatientIds.size(); i++) {
                psPatients.setString(i + 1, validPatientIds.get(i));
            }
            ResultSet rsPatients = psPatients.executeQuery();
            ResultSetMetaData rms = rsPatients.getMetaData();

            // Output HTML
            out.println("<html><head><title>Patients under Dr. " + doctorName + "</title>");
            out.println("<style>");
            out.println("table { border-collapse: collapse; width: 100%; }");
            out.println("th, td { border: 1px solid #ddd; padding: 8px; }");
            out.println("th { background-color: #f2f2f2; }");
            out.println("tr:nth-child(even) { background-color: #f9f9f9; }");
            out.println("</style></head><body>");
            out.println("<h2>Patients under Dr. " + doctorName + "</h2>");
            out.println("<table>");
            out.println("<tr>");
            for (int i = 1; i <= rms.getColumnCount(); i++) {
                out.println("<th>" + rms.getColumnName(i) + "</th>");
            }
            out.println("</tr>");

            while (rsPatients.next()) {
                out.println("<tr>");
                for (int i = 1; i <= rms.getColumnCount(); i++) {
                    out.println("<td>" + rsPatients.getString(i) + "</td>");
                }
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("</body></html>");

        } catch (SQLException e) {
            out.println("<h3 style='color:red'>Error retrieving patients data.</h3>");
            e.printStackTrace(out);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
