package com.example.starchart.LocalDB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey
    int id = 1; //Should only hold 1 user at any time.

    @ColumnInfo(name = "username")
    String username;

    @ColumnInfo(name = "password")
    String password;


}
