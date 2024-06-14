package com.example.bestdiet.database;

public class client_in_client_list {


    private int client_id;

    private String firstname;
    private String name;
    private String lastname;

    private int year;

    private String gender;

    private String photo_url;


    public client_in_client_list(clients client) {
        this.client_id = client.getUid();
        this.firstname = client.getFirstName();
        this.name = client.getMiddleName();
        this.lastname = client.getLastName();
        this.year = client.getYear();
        this.gender = client.getGender();
        this.photo_url = client.getphoto_url();
    }

    public int getClient_id() {
        return client_id;
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
    public int getYear() {
        return year;
    }

    public String getGender() {
        return gender;
    }

    public String getPhoto_url() {
        return photo_url;
    }



}
