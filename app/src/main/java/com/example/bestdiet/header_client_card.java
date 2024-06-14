package com.example.bestdiet;

public class header_client_card {
    String Image_path;
    String firstname;
    String name;
    String lastname;

    header_client_card(String Image_path, String firstname,String name,String lastname)
    {
        this.Image_path = Image_path;
        this.firstname = firstname;
        this.name = name;
        this.lastname = lastname;

    }

    String getImagepath()
    {
        return Image_path;
    }
    String getfirstname()
    {
        return firstname;
    }

    String getname()
    {
        return name;
    }

    String getlastname()
    {
        return lastname;
    }

}
