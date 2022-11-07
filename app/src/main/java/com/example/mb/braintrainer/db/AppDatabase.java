package com.example.mb.braintrainer.db;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.net.ContentHandler;

@Database(entities = {User.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
   public abstract UserDAO userDAO();
   public static AppDatabase INSTANCE;

   public static AppDatabase getDBInstance(Context context){
      if(INSTANCE==null){
         INSTANCE= Room.databaseBuilder(
                 context.getApplicationContext()
                 ,AppDatabase.class,"DB_NAME")
                 .allowMainThreadQueries()
                 .build();

      }
      return INSTANCE;
   }
}
