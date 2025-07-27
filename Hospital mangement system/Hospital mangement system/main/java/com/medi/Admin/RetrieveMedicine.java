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

@WebServlet("/RetrieveMedicine")
public class RetrieveMedicine extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public RetrieveMedicine() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try (Connection c = GetConnection.getConnection()) {
            String sql = "SELECT * FROM medicine";
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            ResultSetMetaData rms = rs.getMetaData();

            // HTML header + CSS for table styling
            out.println("<html><head><title>Medicine List</title>");
            out.println("<style>");
            out.println("table { border-collapse: collapse; width: 80%; margin: 20px auto; font-family: Arial, sans-serif; }");
            out.println("th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }");
            out.println("th { background-color: #4CAF50; color: white; }");
            out.println("tr:nth-child(even) { background-color: #f2f2f2; }");
            out.println("</style>");
            out.println("</head><body>");
            out.println("<h2 style='text-align:center;'>Medicine List</h2>");
            out.println("<table>");

            // Table headers
            out.println("<tr>");
            for (int i = 1; i <= rms.getColumnCount(); i++) {
                out.println("<th>" + rms.getColumnName(i) + "</th>");
            }
            out.println("</tr>");

            // Table rows
            while (rs.next()) {
                out.println("<tr>");
                for (int i = 1; i <= rms.getColumnCount(); i++) {
                    out.println("<td>" + rs.getString(i) + "</td>");
                }
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("</body></html>");

        } catch (SQLException e) {
            out.println("<h3 style='color:red; text-align:center;'>Error retrieving medicine data. Please try again later.</h3>");
            e.printStackTrace(out);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
