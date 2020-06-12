package com.example.starchart.LocalDB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
        @Query("SELECT * FROM user")
        List<User> getAll();

        @Insert
        void insertUser(User user);

        @Update
        void update(User user);

        @Delete
        void delete(User user);
}
