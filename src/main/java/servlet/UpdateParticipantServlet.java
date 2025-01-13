package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Participant;
import database.ParticipantDatabase;

import java.io.IOException;

/**
 * Servlet implementation class UpdateParticipantServlet
 */
@WebServlet("/update-participant")
public class UpdateParticipantServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UpdateParticipantServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String phone = request.getParameter("phone");
        System.out.println("Received phone parameter: " + phone);

        ParticipantDatabase participantDatabase = new ParticipantDatabase();
        Participant participant = participantDatabase.getParticipant(phone);

        if (participant == null) {
            System.out.println("Participant not found for phone: " + phone);
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Participant not found.");
            return;
        }

        System.out.println("Participant retrieved: " + participant.getName());
        request.setAttribute("participant", participant);
        request.getRequestDispatcher("update-participant.jsp").forward(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.print("updating");
    	
    	String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String batchIdStr = request.getParameter("batchId");

        if (name == null || phone == null || email == null || batchIdStr == null ||
            name.isEmpty() || phone.isEmpty() || email.isEmpty() || batchIdStr.isEmpty()) {

            request.setAttribute("errorMessage", "All fields are required.");
            request.getRequestDispatcher("update-participant.jsp").forward(request, response);
            return;
        }

        int batchId;
        try {
            batchId = Integer.parseInt(batchIdStr);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid batch ID.");
            request.getRequestDispatcher("update-participant.jsp").forward(request, response);
            return;
        }

        Participant participant = new Participant();
        participant.setName(name);
        participant.setPhone(phone);
        participant.setEmail(email);
        participant.setBid(batchId);

        ParticipantDatabase participantDatabase = new ParticipantDatabase();
        boolean success = participantDatabase.updateParticipant(participant);

        if (success) {
            response.sendRedirect("lists.jsp");
        } else {
            request.setAttribute("errorMessage", "Failed to update participant. Please try again.");
            request.getRequestDispatcher("update-participant.jsp").forward(request, response);
        }
    }
}
