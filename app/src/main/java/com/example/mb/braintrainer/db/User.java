package com.example.mb.braintrainer.db;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

   @PrimaryKey(autoGenerate = true)
   public int uid;

   @ColumnInfo(name ="high_score" )
   public int highScore;

}
