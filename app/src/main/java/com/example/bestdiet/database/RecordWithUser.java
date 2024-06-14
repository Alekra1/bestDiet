package com.example.bestdiet.database;

import androidx.room.Embedded;
import androidx.room.Relation;

public class RecordWithUser {
    @Embedded
    public records record;

    @Relation(
            parentColumn = "userid",
            entityColumn = "uid"
    )
    public user user;
}
