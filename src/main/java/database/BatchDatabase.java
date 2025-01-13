package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import model.Batch;

public class BatchDatabase {

    public boolean insert(Batch batch) {
        System.out.println("Inserting " + batch);
        Connection con = MyConnection.getConnection();
        String sql = "INSERT INTO batch (start_time, end_time, trainer, student_number) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setTimestamp(1, batch.getStartTime()); 
            preparedStatement.setTimestamp(2, batch.getEndTime());   
            preparedStatement.setString(3, batch.getTrainer());
            preparedStatement.setInt(4, batch.getStudentNumber());
            preparedStatement.executeUpdate();
            System.out.println("Insert executed");

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                batch.setBid(generatedKeys.getInt(1));
                System.out.println("Generated bid: " + batch.getBid());
            }

            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Insert exception");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public ArrayList<Batch> getBatches() {
        Connection con = MyConnection.getConnection();
        ArrayList<Batch> batches = new ArrayList<>();
        String sql = "SELECT * FROM batch";
        try {
            ResultSet rs = MyConnection.runSql(sql);
            while (rs.next()) {
                Batch batch = new Batch();
                batch.setBid(rs.getInt("bid"));
                batch.setStartTime(rs.getTimestamp("start_time")); 
                batch.setEndTime(rs.getTimestamp("end_time"));     
                batch.setTrainer(rs.getString("trainer"));
                batch.setStudentNumber(rs.getInt("student_number"));
                batches.add(batch);
            }
        } catch (SQLException e) {
            System.out.println("Fetch exception");
            e.printStackTrace();
        }
        return batches;
    }

    public boolean delete(int bid) {
        Connection con = MyConnection.getConnection();
        String sql = "DELETE FROM batch WHERE bid = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, bid);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Delete exception");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Batch getBatch(int bid) {
        Batch batch = null;
        String sql = "SELECT * FROM batch WHERE bid = ?";
        try {
            PreparedStatement preparedStatement = MyConnection.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, bid);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                batch = new Batch();
                batch.setBid(rs.getInt("bid"));
                batch.setStartTime(rs.getTimestamp("start_time")); 
                batch.setEndTime(rs.getTimestamp("end_time"));     
                batch.setTrainer(rs.getString("trainer"));
                batch.setStudentNumber(rs.getInt("student_number"));
            }
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Get batch exception");
            e.printStackTrace();
        }
        return batch;
    }

    public boolean updateBatch(Batch batch) {
        Connection con = MyConnection.getConnection();
        String sql = "UPDATE batch SET start_time = ?, end_time = ?, trainer = ?, student_number = ? WHERE bid = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setTimestamp(1, batch.getStartTime()); 
            preparedStatement.setTimestamp(2, batch.getEndTime());   
            preparedStatement.setString(3, batch.getTrainer());
            preparedStatement.setInt(4, batch.getStudentNumber());
            preparedStatement.setInt(5, batch.getBid());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Update exception");
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public ArrayList<Batch> getBatchesForToday() {
        ArrayList<Batch> todayBatches = new ArrayList<>();
        try {
            Connection con = MyConnection.getConnection();
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String todayStart = dateFormat.format(currentTime) + " 00:00:00";
            String todayEnd = dateFormat.format(currentTime) + " 23:59:59";
            
            String query = "SELECT * FROM batch WHERE start_time >= ? AND start_time <= ?";
            
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, todayStart);
            stmt.setString(2, todayEnd);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Batch batch = new Batch();
                batch.setBid(rs.getInt("bid"));
                batch.setStartTime(rs.getTimestamp("start_time"));
                batch.setEndTime(rs.getTimestamp("end_time"));
                batch.setTrainer(rs.getString("trainer"));
                batch.setStudentNumber(rs.getInt("student_number"));
                todayBatches.add(batch);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todayBatches;
    }

}
