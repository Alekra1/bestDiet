package com.example.bestdiet;

import com.example.bestdiet.database.clients;

public class body_client_card {
        clients client;

        body_client_card(clients client)
        {
            this.client = client;
        }

        clients getclient()
        {
            return this.client;
        }
}
