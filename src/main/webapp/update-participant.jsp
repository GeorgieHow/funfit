<%@ page import="java.util.ArrayList"%>
<%@ page import="model.Batch"%>
<%@ page import="database.BatchDatabase"%>
<%@ page import="model.Participant"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Update Participant</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>

<%
Participant participant = (Participant) request.getAttribute("participant");
%>

	<h2 align="center">Update Participant</h2>
	<div class="container">
		<form action="update-participant" method="post">
			<div class="row">
				<div class="col-lg-6 col-lg-offset-3">
					<div class="form-group">
						<label for="name">Name:</label> <input type="text"
							class="form-control" id="name" placeholder="Enter Name"
							name="name"
							value="<%=(participant != null) ? participant.getName() : ""%>">
					</div>

					<div class="form-group">
						<label for="phone">Phone:</label> <input type="number"
							class="form-control" id="phone" placeholder="Enter phone no."
							name="phone"
							value="<%=(participant != null) ? participant.getPhone() : ""%>"
							readonly>
					</div>
					<div class="form-group">
						<label for="email">Email:</label> <input type="email"
							class="form-control" id="email" placeholder="Enter email"
							name="email"
							value="<%=(participant != null) ? participant.getEmail() : ""%>">
					</div>

					<div class="form-group">
						<label for="batch">Batch:</label> <select class="form-control"
							id="batch" name="batchId">
							<option value="">Select Batch</option>
							<%
							ArrayList<Batch> batches = new BatchDatabase().getBatches();
							if (batches != null) {
								for (Batch batch : batches) {
									boolean isSelected = (participant != null && participant.getBid() == batch.getBid());
							%>
							<option value="<%=batch.getBid()%>"
								<%=isSelected ? "selected" : ""%>>
								<%=batch.getTrainer() + " - " + batch.getStartTime() + " to " + batch.getEndTime()%>
							</option>
							<%
							}
							} else {
							%>
							<option value="0">No Batch</option>
							<%
							}
							%>
						</select>
					</div>

					<div align="center">
						<input type="submit" class="btn btn-primary" value="Update" />
					</div>
				</div>
			</div>
		</form>
		<a href="lists.jsp">Menu</a>
	</div>

</body>
</html>
