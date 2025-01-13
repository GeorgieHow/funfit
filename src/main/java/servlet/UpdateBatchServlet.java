package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Batch;
import database.BatchDatabase;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/update-batch")
public class UpdateBatchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UpdateBatchServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String batchIdStr = request.getParameter("batchId");

        if (batchIdStr == null || batchIdStr.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Batch ID is required.");
            return;
        }

        int batchId;
        try {
            batchId = Integer.parseInt(batchIdStr);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Batch ID.");
            return;
        }

        BatchDatabase batchDatabase = new BatchDatabase();
        Batch batch = batchDatabase.getBatch(batchId);

        if (batch == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Batch not found.");
            return;
        }

        request.setAttribute("batch", batch);
        request.getRequestDispatcher("update-batch.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String trainer = request.getParameter("trainer");
        String startTimeStr = request.getParameter("startTime");
        String endTimeStr = request.getParameter("endTime");
        String studentNumberStr = request.getParameter("studentNumber");
        String batchIdStr = request.getParameter("batchId");

        if (trainer == null || startTimeStr == null || endTimeStr == null || studentNumberStr == null || batchIdStr == null ||
            trainer.isEmpty() || startTimeStr.isEmpty() || endTimeStr.isEmpty() || studentNumberStr.isEmpty() || batchIdStr.isEmpty()) {

            request.setAttribute("errorMessage", "All fields are required.");
            request.getRequestDispatcher("update-batch.jsp").forward(request, response);
            return;
        }

        int batchId;
        int studentNumber;
        Timestamp startTime;
        Timestamp endTime;

        try {
            batchId = Integer.parseInt(batchIdStr);
            studentNumber = Integer.parseInt(studentNumberStr);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime startDateTime = LocalDateTime.parse(startTimeStr, formatter);
            LocalDateTime endDateTime = LocalDateTime.parse(endTimeStr, formatter);

            startTime = Timestamp.valueOf(startDateTime);
            endTime = Timestamp.valueOf(endDateTime);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Invalid input format.");
            request.getRequestDispatcher("update-batch.jsp").forward(request, response);
            return;
        }

        Batch batch = new Batch();
        batch.setBid(batchId);
        batch.setTrainer(trainer);
        batch.setStartTime(startTime);
        batch.setEndTime(endTime);
        batch.setStudentNumber(studentNumber);

        BatchDatabase batchDatabase = new BatchDatabase();
        boolean success = batchDatabase.updateBatch(batch);

        if (success) {
            response.sendRedirect("lists.jsp");
        } else {
            request.setAttribute("errorMessage", "Failed to update batch. Please try again.");
            request.getRequestDispatcher("update-batch.jsp").forward(request, response);
        }
    }
}