<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Batch" %>
<%@ page import="database.BatchDatabase" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Create Batch</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>

<h2 align="center">Create Batch</h2>
<div class="container">
    <form action="add-batch" method="post">
        <div class="row">
            <div class="col-lg-6 col-lg-offset-3">
                <div class="form-group">
                    <label for="trainer">Trainer:</label>
                    <input type="text" class="form-control" id="trainer" placeholder="Enter Trainer Name" name="trainer">
                </div>
                
                <div class="form-group">
                    <label for="startTime">Start Time:</label>
                    <input type="datetime-local" class="form-control" id="startTime" name="startTime">
                </div>
                
                <div class="form-group">
                    <label for="endTime">End Time:</label>
                    <input type="datetime-local" class="form-control" id="endTime" name="endTime">
                </div>  
                
                <div class="form-group">
                    <label for="studentNumber">Number of Students:</label>
                    <input type="number" class="form-control" id="studentNumber" placeholder="Enter number of students" name="studentNumber">
                </div>

                <div align="center">
                    <input type="submit" class="btn btn-primary" value="Create Batch"/>
                </div>
            </div>
        </div>
    </form>
    <a href="lists.jsp">Back to Menu</a>
</div>

</body>
</html>
