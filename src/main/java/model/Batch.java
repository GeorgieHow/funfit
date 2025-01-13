package model;

import java.sql.Timestamp;

public class Batch {

    private int bid;
    private Timestamp startTime;
    private Timestamp endTime;
    private String trainer;
    private int studentNumber;
    
    public Batch() {
        super();
    }

    public Batch(int bid, Timestamp startTime, Timestamp endTime, String trainer, int studentNumber) {
        super();
        this.bid = bid;
        this.startTime = startTime;
        this.endTime = endTime;
        this.trainer = trainer;
        this.studentNumber = studentNumber;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    public int getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(int studentNumber) {
        this.studentNumber = studentNumber;
    }

    @Override
    public String toString() {
        return "Batch [bid=" + bid + ", startTime=" + startTime + ", endTime=" + endTime + ", trainer=" + trainer
                + ", studentNumber=" + studentNumber + "]";
    }
}
