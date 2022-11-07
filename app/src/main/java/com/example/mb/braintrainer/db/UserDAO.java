package com.example.mb.braintrainer.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDAO {

 @Query("SELECT * FROM user")
 List<User> getAllUsers();

 @Insert
 void InsertUser(User user);

 @Delete
 void DeleteUser(User user);
}
