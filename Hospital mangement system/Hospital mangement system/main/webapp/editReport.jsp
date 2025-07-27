<%@ page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Map<String, String> r = (Map<String, String>) request.getAttribute("report");
%>
<html>
<head>
    <title>Edit Medical Report</title>
    <style>
        body { font-family: Arial; padding: 20px; }
        form { width: 500px; margin: auto; }
        label { display: block; margin-top: 10px; }
        input, textarea, select { width: 100%; padding: 8px; margin-top: 5px; }
        input[type=submit] { margin-top: 20px; background-color: #3498db; color: white; border: none; cursor: pointer; }
    </style>
</head>
<body>

<h2 style="text-align:center;">Edit Medical Report</h2>

<form action="updateReport" method="post">
    <input type="hidden" name="file" value="<%= r.get("file") %>" />

    <label>Patient Name:</label>
    <input type="text" name="patientName" value="<%= r.get("patientName") %>" required>

    <label>Age:</label>
    <input type="number" name="age" value="<%= r.get("age") %>" required>

    <label>Gender:</label>
    <select name="gender" required>
        <option value="Male" <%= "Male".equals(r.get("gender")) ? "selected" : "" %>>Male</option>
        <option value="Female" <%= "Female".equals(r.get("gender")) ? "selected" : "" %>>Female</option>
        <option value="Other" <%= "Other".equals(r.get("gender")) ? "selected" : "" %>>Other</option>
    </select>

    <label>Diagnosis:</label>
    <textarea name="diagnosis" rows="3"><%= r.get("diagnosis") %></textarea>

    <label>Prescription:</label>
    <textarea name="prescription" rows="3"><%= r.get("prescription") %></textarea>

    <label>Report Date:</label>
    <input type="date" name="reportDate" value="<%= r.get("reportDate") %>" required>

    <input type="submit" value="Update Report">
</form>

</body>
</html>
