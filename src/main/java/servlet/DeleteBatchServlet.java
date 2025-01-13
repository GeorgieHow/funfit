package servlet;

import database.BatchDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delete-batch")
public class DeleteBatchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DeleteBatchServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String batchIdStr = request.getParameter("batchId");

        if (batchIdStr != null) {
            try {
                int batchId = Integer.parseInt(batchIdStr);
                BatchDatabase batchDatabase = new BatchDatabase();
                boolean deleted = batchDatabase.delete(batchId);

                if (deleted) {
                    response.sendRedirect("lists.jsp");  
                } else {
                    response.getWriter().append("Failed to delete batch.");
                }
            } catch (NumberFormatException e) {
                response.getWriter().append("Invalid batch ID.");
            }
        } else {
            response.getWriter().append("Batch ID parameter is missing.");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
