package com.example.bestdiet;

public class Item {

    private int type;
    private Object object;

    public Item(int type,Object object)
    {
        this.type = type;
        this.object = object;
    }

    public int getType(){
        return this.type;
    }

    public Object geObject(){
        return this.object;
    }
}
