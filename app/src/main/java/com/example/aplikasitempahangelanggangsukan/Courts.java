package com.example.aplikasitempahangelanggangsukan;

import java.io.Serializable;

public class Courts implements Serializable {
    private String courtname;
    private String coutaddress;

    public Courts(){}

    public String getCoutaddress() {
        return coutaddress;
    }

    public void setCoutaddress(String coutaddress) {
        this.coutaddress = coutaddress;
    }

    public String getCourtname() {
        return courtname;
    }

    public void setCourtname(String courtname) {
        this.courtname = courtname;
    }
}
