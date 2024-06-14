package com.example.bestdiet;

public class header_plan_menu {
    String name_meal;
    int time;

    header_plan_menu(String name_meal, int time)
    {
        this.name_meal = name_meal;
        this.time = time;
    }

    String getName_meal()
    {
        return name_meal;
    }
    int gettime()
    {
        return time;
    }
}
