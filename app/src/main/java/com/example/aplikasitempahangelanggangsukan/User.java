package com.example.aplikasitempahangelanggangsukan;

public class User {
    public String fullName, email, occupation, usertype;

    public User(){

    }

    public User(String fullName, String email, String occupation, String usertype){
        this.fullName = fullName;
        this.email = email;
        this.occupation = occupation;
        this.usertype = usertype;
    }
}
