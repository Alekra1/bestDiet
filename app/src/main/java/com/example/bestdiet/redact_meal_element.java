package com.example.bestdiet;

public class redact_meal_element {
        String name;

        String type;
        float count;

        String photo;

        redact_meal_element(String name,String type,float count,String photo)
        {
            this.name = name;
            this.type = type;
            this.count = count;
            this.photo = photo;
        }

        String getname()
        {
            return name;
        }

        String getType()
    {
        return type;
    }

        float getcount()
        {
            return count;
        }

        String getphoto(){return this.photo;}

}
