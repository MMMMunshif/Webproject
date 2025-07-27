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

@WebServlet("/RetrieveDoctor")
public class RetrieveDoctor extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public RetrieveDoctor() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try (Connection c = GetConnection.getConnection()) {
            String sql = "SELECT * FROM doctor";
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rms = rs.getMetaData();

            out.println("<html><head><title>Doctors List</title>");
            out.println("<style>");
            out.println("table { border-collapse: collapse; width: 100%; }");
            out.println("th, td { border: 1px solid #ddd; padding: 8px; }");
            out.println("th { background-color: #f2f2f2; }");
            out.println("</style></head><body>");
            out.println("<h2>List of Doctors</h2>");
            out.println("<table>");
            out.println("<tr>");
            // Print headers dynamically (excluding 10th column if you want, but here print all 9 + extra)
            for (int i = 1; i <= rms.getColumnCount(); i++) {
                out.println("<th>" + rms.getColumnName(i) + "</th>");
            }
            out.println("<th>Patients Under</th>");  // Extra column for button
            out.println("</tr>");

            while (rs.next()) {
                out.println("<tr>");
                for (int i = 1; i <= rms.getColumnCount(); i++) {
                    out.println("<td>" + rs.getString(i) + "</td>");
                }
                // Add button/form to view patients under this doctor
                String did = rs.getString("did");
                out.println("<td>");
                out.println("<form method='GET' action='RetrievePatientsDID'>");
                out.println("<input type='hidden' name='did' value='" + did + "'/>");
                out.println("<input type='submit' value='View Patients'/>");
                out.println("</form>");
                out.println("</td>");

                out.println("</tr>");
            }
            out.println("</table>");
            out.println("</body></html>");

        } catch (SQLException e) {
            response.setContentType("text/html");
            out.println("<h3 style='color:red'>Error fetching doctors data. Please try again.</h3>");
            e.printStackTrace(out);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
