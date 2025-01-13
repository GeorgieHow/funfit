package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Participant;

public class ParticipantDatabase {

    public boolean insert(Participant p) {
        System.out.println("Inserting " + p);
        Connection con = MyConnection.getConnection();
        String sql = "INSERT INTO participants (name, phone, email, bid) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, p.getName());
            preparedStatement.setString(2, p.getPhone());
            preparedStatement.setString(3, p.getEmail());
            preparedStatement.setInt(4, p.getBid()); 
            preparedStatement.executeUpdate();
            System.out.println("Insert executed");

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                p.setPid(generatedKeys.getInt(1));
                System.out.println("Generated pid: " + p.getPid());
            }

            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Insert exception");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public ArrayList<Participant> getParticipants() {
        Connection con = MyConnection.getConnection();
        ArrayList<Participant> participants = new ArrayList<>();
        String sql = "SELECT * FROM participants";
        try {
            ResultSet rs = MyConnection.runSql(sql);
            while (rs.next()) {
                Participant participant = new Participant();
                participant.setPid(rs.getInt("pid"));
                participant.setName(rs.getString("name"));
                participant.setPhone(rs.getString("phone"));
                participant.setEmail(rs.getString("email"));
                participant.setBid(rs.getInt("bid")); 
                participants.add(participant);
            }
        } catch (SQLException e) {
            System.out.println("Fetch exception");
            e.printStackTrace();
        }
        return participants;
    }

    public boolean delete(String phone) {
        Connection con = MyConnection.getConnection();
        String sql = "DELETE FROM participants WHERE phone = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, phone);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Delete exception");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Participant getParticipant(String phone) {
        Participant participant = null;
        String sql = "SELECT * FROM participants WHERE phone = ?";
        try {
            PreparedStatement preparedStatement = MyConnection.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, phone);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                participant = new Participant();
                participant.setPid(rs.getInt("pid"));
                participant.setName(rs.getString("name"));
                participant.setPhone(rs.getString("phone"));
                participant.setEmail(rs.getString("email"));
                participant.setBid(rs.getInt("bid")); 
            }
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Get participant exception");
            e.printStackTrace();
        }
        return participant;
    }

    public boolean updateParticipant(Participant participant) {
        Connection con = MyConnection.getConnection();
        String sql = "UPDATE participants SET name = ?, email = ?, bid = ? WHERE phone = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, participant.getName());
            preparedStatement.setString(2, participant.getEmail());
            preparedStatement.setInt(3, participant.getBid()); 
            preparedStatement.setString(4, participant.getPhone());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Update exception");
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public Participant getParticipantById(int pid) {
        Participant participant = null;
        String sql = "SELECT * FROM participants WHERE pid = ?";
        try {
            Connection con = MyConnection.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, pid);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                participant = new Participant();
                participant.setPid(rs.getInt("pid"));
                participant.setName(rs.getString("name"));
                participant.setPhone(rs.getString("phone"));
                participant.setEmail(rs.getString("email"));
                participant.setBid(rs.getInt("bid")); 
            }
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Get participant by ID exception");
            e.printStackTrace();
        }
        return participant;
    }
}
