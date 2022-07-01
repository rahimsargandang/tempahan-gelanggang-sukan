package com.example.aplikasitempahangelanggangsukan;

import java.util.Date;

public class TimeSlots {

    private Date bookingDate;
    private String timeSlot;
    private String available;
    private String userEmail;
    private String courtname;
    private String courtaddress;


    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }


    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }


    public String getCourtname() {
        return courtname;
    }

    public void setCourtname(String courtname) {
        this.courtname = courtname;
    }

    public String getCourtaddress() {
        return courtaddress;
    }

    public void setCourtaddress(String courtaddress) {
        this.courtaddress = courtaddress;
    }
}
