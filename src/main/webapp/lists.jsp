<%@ page import="model.Participant" %>
<%@ page import="model.Batch" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="database.ParticipantDatabase" %>
<%@ page import="database.BatchDatabase" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Lists</title>
</head>
<body>
<h1>Participant's List :</h1>
<table class="table">
    <tr>
    	<th>Pid</th>
        <th>Name</th>
        <th>Phone</th>
        <th>Email</th>
        <th>Bid</th>
        <th> </th>
        <th> </th>
    </tr>
    <%
        ArrayList<Participant> participants = new ParticipantDatabase().getParticipants();
        for (Participant p : participants) {
    %>
    <tr>
  		<td><%= p.getPid() %></td>
        <td><%= p.getName() %></td>
        <td><%= p.getPhone() %></td>
        <td><%= p.getEmail() %></td>
        <td><%= p.getBid() %></td>   
  		<td><a href="update-participant?phone=<%= p.getPhone() %>">Edit</a></td>
        <td><a href="delete-participant?phone=<%= p.getPhone() %>">Delete</a></td>
    </tr>
    <%
        }
    %>
</table>

<a href="add-participant.jsp"> Add Participant</a><br>

<h1>Batch List :</h1>
<table class="table">
    <tr>
    	<th>Bid</th>
        <th>Start Time</th>
        <th>End Time</th>
        <th>Trainer</th>
        <th>No. of Students</th>
        <th> </th>
        <th> </th>
    </tr>
    <%
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
        ArrayList<Batch> batches = new BatchDatabase().getBatches();
        for (Batch b : batches) {
    %>
    <tr>
  		<td><%= b.getBid() %></td>
        <td><%= b.getStartTime() != null ? dateFormat.format(b.getStartTime()) : "N/A" %></td>
    	<td><%= b.getEndTime() != null ? dateFormat.format(b.getEndTime()) : "N/A" %></td>
        <td><%= b.getTrainer() %></td>
        <td><%= b.getStudentNumber() %></td>
        <td><a href="update-batch?batchId=<%= b.getBid() %>">Edit</a></td>
        <td><a href="delete-batch?batchId=<%= b.getBid() %>">Delete</a></td>
    </tr>
    <%
        }
    %>
</table>

<a href="add-batch.jsp"> Add Batch</a><br>

<h1>Today's Classes:</h1>
<table class="table">
    <tr>
        <th>Bid</th>
        <th>Start Time</th>
        <th>End Time</th>
        <th>Trainer</th>
        <th>No. of Students</th>
    </tr>
    <%  
        ArrayList<Batch> todayBatches = new BatchDatabase().getBatchesForToday();
        
        for (Batch b : todayBatches) {
    %>
    <tr>
        <td><%= b.getBid() %></td>
        <td><%= b.getStartTime() != null ? dateFormat.format(b.getStartTime()) : "N/A" %></td>
        <td><%= b.getEndTime() != null ? dateFormat.format(b.getEndTime()) : "N/A" %></td>
        <td><%= b.getTrainer() %></td>
        <td><%= b.getStudentNumber() %></td>
    </tr>
    <%
        }
    %>
</table>
</body>
</html>