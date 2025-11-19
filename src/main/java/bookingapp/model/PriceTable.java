package bookingapp.model;

import java.time.LocalTime;

public class PriceTable {
    private int id;
    private String day_in_week;
    private LocalTime start_time, end_time;
    private float price;

    public PriceTable(int id, String day_in_week,LocalTime start_time, LocalTime end_time, float price) {
        this.id = id;
        this.day_in_week = day_in_week;
        this.start_time = start_time;
        this.price = price;
        this.end_time = end_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay_in_week() {
        return day_in_week;
    }

    public void setDay_in_week(String day_in_week) {
        this.day_in_week = day_in_week;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
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
}
