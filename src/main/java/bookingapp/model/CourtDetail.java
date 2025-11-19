package bookingapp.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class CourtDetail {
    private int id, court_id;
    private LocalDate date;
    private LocalTime start_time, end_time;
    private String status;

    public CourtDetail(int id, int court_id, LocalDate date, LocalTime start_time, LocalTime end_time, String status) {
        this.id = id;
        this.court_id = court_id;
        this.date = date;
        this.start_time = start_time;
        this.end_time = end_time;
        this.status = status;
    }

    public int getCourt_id() {
        return court_id;
    }

    public void setCourt_id(int court_id) {
        this.court_id = court_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalTime getEnd_time() {
        return end_time;
    }

    public void setEnd_time(LocalTime end_time) {
        this.end_time = end_time;
    }

    public LocalTime getStart_time() {
        return start_time;
    }

    public void setStart_time(LocalTime start_time) {
        this.start_time = start_time;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
