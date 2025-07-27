<%@ page import="java.util.*" %>
<html>
<head>
    <title>Medical Reports</title>
    <style>
        table {
            width: 90%;
            margin: auto;
            border-collapse: collapse;
            margin-top: 40px;
        }
        th, td {
            padding: 10px;
            border: 1px solid #aaa;
            text-align: center;
        }
        th {
            background-color: #3498db;
            color: white;
        }
    </style>
</head>
<body>
    <h2 style="text-align:center;">All Medical Reports</h2>
    <table>
        <tr>
            <th>Patient Name</th>
            <th>Age</th>
            <th>Gender</th>
            <th>Diagnosis</th>
            <th>Prescription</th>
            <th>Report Date</th>
            <th>File</th>
        </tr>
        <%
            List<Map<String, String>> reports = (List<Map<String, String>>) request.getAttribute("reports");
            for (Map<String, String> r : reports) {
        %>
        <tr>
            <td><%= r.get("patientName") %></td>
            <td><%= r.get("age") %></td>
            <td><%= r.get("gender") %></td>
            <td><%= r.get("diagnosis") %></td>
            <td><%= r.get("prescription") %></td>
            <td><%= r.get("reportDate") %></td>
            <td>
    <a href="uploads/<%= r.get("file") %>" target="_blank">View</a> |
    <a href="editReport?file=<%= r.get("file") %>"> Edit</a>
    <a href="deleteReport?file=<%= r.get("file") %>">| Delete</a>

    
</td>

        </tr>
        <% } %>
    </table>
</body>
</html>
