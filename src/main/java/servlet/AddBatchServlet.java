package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Batch;
import database.BatchDatabase;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/add-batch")
public class AddBatchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AddBatchServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String trainer = request.getParameter("trainer");
        String startTimeStr = request.getParameter("startTime");
        String endTimeStr = request.getParameter("endTime");
        String studentNumberStr = request.getParameter("studentNumber");


        Timestamp startTime = null;
        Timestamp endTime = null;

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime startDateTime = LocalDateTime.parse(startTimeStr, formatter);
            LocalDateTime endDateTime = LocalDateTime.parse(endTimeStr, formatter);

            startTime = Timestamp.valueOf(startDateTime);
            endTime = Timestamp.valueOf(endDateTime);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid datetime format. Please use yyyy-MM-ddTHH:mm.");
            return;
        }
        
        int studentNumber = Integer.parseInt(studentNumberStr);
   
        Batch batch = new Batch();
        batch.setTrainer(trainer);
        batch.setStartTime(startTime);
        batch.setEndTime(endTime);
        batch.setStudentNumber(studentNumber);


        BatchDatabase batchDatabase = new BatchDatabase();
        boolean success = batchDatabase.insert(batch);
        
        if (success) {

            response.sendRedirect("lists.jsp");
        } else {

            request.setAttribute("errorMessage", "Failed to add participant. Please try again.");
            request.getRequestDispatcher("add-participant.jsp").forward(request, response);
        }
    }
}
