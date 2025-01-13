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
 * Servlet implementation class AddParticipantServlet
 */
@WebServlet("/add-participant")
public class AddParticipantServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddParticipantServlet() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String batchIdStr = request.getParameter("batchId");


        if (name == null || phone == null || email == null || batchIdStr == null || name.isEmpty() || phone.isEmpty() || email.isEmpty() || batchIdStr.isEmpty()) {

            request.setAttribute("errorMessage", "All fields are required.");
            request.getRequestDispatcher("add-participant.jsp").forward(request, response);
            return;
        }


        int batchId = Integer.parseInt(batchIdStr);

        System.out.println("Adding participant with batchId: " + batchId);

 
        Participant participant = new Participant();
        participant.setName(name);
        participant.setPhone(phone);
        participant.setEmail(email);
        participant.setBid(batchId);


        ParticipantDatabase participantDatabase = new ParticipantDatabase();
        boolean success = participantDatabase.insert(participant);


        if (success) {

            response.sendRedirect("lists.jsp");
        } else {

            request.setAttribute("errorMessage", "Failed to add participant. Please try again.");
            request.getRequestDispatcher("add-participant.jsp").forward(request, response);
        }
    }
}