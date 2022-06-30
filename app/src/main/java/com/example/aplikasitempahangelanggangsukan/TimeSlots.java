package com.example.aplikasitempahangelanggangsukan;

import java.util.Date;

public class TimeSlots {

    private Date sportDate;
    private String timeSpan;
    private String available;
    private String userEmail;
    private String courtname;
    private String coutaddress;

    public Date getSportDate() {
        return sportDate;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setSportDate(Date sportDate) {
        this.sportDate = sportDate;
    }

    public String getTimeSpan() {
        return timeSpan;
    }

    public void setTimeSpan(String timeSpan) {
        this.timeSpan = timeSpan;
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

    public String getCoutaddress() {
        return coutaddress;
    }

    public void setCoutaddress(String coutaddress) {
        this.coutaddress = coutaddress;
    }
}
