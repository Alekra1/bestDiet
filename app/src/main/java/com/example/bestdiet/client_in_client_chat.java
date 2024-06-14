package com.example.bestdiet;

import com.example.bestdiet.database.clients;

public class client_in_client_chat {

    private String firstname;
    private String name;
    private String lastname;



    public client_in_client_chat(String firstname, String name, String lastname) {
        this.firstname = firstname;
        this.name = name;
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getname() {
        return name;
    }

    public String getlastname() {
        return lastname;
    }


}
