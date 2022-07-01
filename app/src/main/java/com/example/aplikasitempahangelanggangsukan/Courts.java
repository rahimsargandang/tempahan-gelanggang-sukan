package com.example.aplikasitempahangelanggangsukan;

import java.io.Serializable;

public class Courts implements Serializable {
    private String courtname;
    private String courtaddress;

    public Courts(){}

    public String getCourtaddress() {
        return courtaddress;
    }

    public void setCourtaddress(String courtaddress) {
        this.courtaddress = courtaddress;
    }

    public String getCourtname() {
        return courtname;
    }

    public void setCourtname(String courtname) {
        this.courtname = courtname;
    }
}
