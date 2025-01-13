package servlet;

import database.ParticipantDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delete-participant")
public class DeleteParticipantServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DeleteParticipantServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String phone = request.getParameter("phone");

        if (phone != null) {
            ParticipantDatabase participantDatabase = new ParticipantDatabase();
            boolean deleted = participantDatabase.delete(phone);

            if (deleted) {
                response.sendRedirect("lists.jsp");  
            } else {
                response.getWriter().append("Failed to delete participant.");
            }
        } else {
            response.getWriter().append("Phone parameter is missing.");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
