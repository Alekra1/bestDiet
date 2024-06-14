package com.example.bestdiet;

import com.example.bestdiet.database.dish;
import com.example.bestdiet.database.product;

public class body_plan_menu {
    product products;

    dish dishs;
    float count;

    body_plan_menu(product products,dish dishs,float count)
    {
        this.products = products;
        this.dishs = dishs;
        this.count = count;
    }

    product getproduct()
    {
        return products;
    }
    dish getdish()
    {
        return dishs;
    }

    float getcount()
    {
        return count;
    }
}
