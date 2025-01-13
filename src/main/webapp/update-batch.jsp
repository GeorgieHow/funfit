<%@ page import="model.Batch"%>
<!DOCTYPE html>
<html>
<head>
    <title>Update Batch</title>
</head>
<body>
    <%
        Batch batch = (Batch) request.getAttribute("batch");
        if (batch == null) {
            out.println("<p>Batch not found.</p>");
            return;
        }
    %>

    <h2>Update Batch</h2>
    <form action="update-batch" method="post">
        <input type="hidden" name="batchId" value="<%= batch.getBid() %>">

        <label for="trainer">Trainer:</label>
        <input type="text" id="trainer" name="trainer" value="<%= batch.getTrainer() %>" required><br>

        <label for="startTime">Start Time:</label>
        <input type="datetime-local" id="startTime" name="startTime" 
               value="<%= batch.getStartTime().toLocalDateTime().toString().replace(' ', 'T') %>" required><br>

        <label for="endTime">End Time:</label>
        <input type="datetime-local" id="endTime" name="endTime" 
               value="<%= batch.getEndTime().toLocalDateTime().toString().replace(' ', 'T') %>" required><br>

        <label for="studentNumber">Student Number:</label>
        <input type="number" id="studentNumber" name="studentNumber" value="<%= batch.getStudentNumber() %>" required><br>

        <button type="submit">Update</button>
    </form>

    <a href="lists.jsp">Back to List</a>
</body>
</html>
