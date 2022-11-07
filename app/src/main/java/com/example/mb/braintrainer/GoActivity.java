package com.example.mb.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mb.braintrainer.db.AppDatabase;
import com.example.mb.braintrainer.db.User;

import java.util.List;

public class GoActivity extends AppCompatActivity {
    Button easyButton, hardButton, goButton;
    TextView tvHighscore;
    int highScoreValue;
    int leveltype = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go);
        easyButton = findViewById(R.id.buttonLevelEasy);
        hardButton = findViewById(R.id.buttonLevelHard);
        hardButton = findViewById(R.id.buttonLevelHard);
        tvHighscore = findViewById(R.id.tvHighScoreValue);
        goButton = findViewById(R.id.button);
        highScoreValue = viewHighScore();
        String str_score=(String.valueOf(highScoreValue));
        tvHighscore.setText("High Score :  "+str_score);


    }

    @Override
    protected void onStart() {
        super.onStart();
        highScoreValue = viewHighScore();
        String str_score=(String.valueOf(highScoreValue));
        tvHighscore.setText("High Score :  "+str_score);
    }

    private int viewHighScore() {
        AppDatabase db = AppDatabase.getDBInstance(this.getApplicationContext());
        List<User> userList = db.userDAO().getAllUsers();
        if (userList.size() == 0) {
            return 0;
        } else {
            int highVal = userList.size() - 1;
            //   Toast.makeText(this, String.valueOf(userList.get(highVal).highScore), Toast.LENGTH_SHORT).show();
            //   Log.e("TAG", "GetHighScore: " + String.valueOf(userList.get(highVal).highScore));
            return userList.get(highVal).highScore;

        }
    }

    public void startGameGoNext(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("key", leveltype);
        startActivity(intent);
    }

    public void easyClicked(View view) {
        view.setBackgroundColor(getResources().getColor(R.color.button_dark));
        hardButton.setBackgroundColor(getResources().getColor(R.color.button_light));
        easyButton.setTextColor(getResources().getColor(R.color.white));
        easyButton.setTextSize(32f);
        hardButton.setTextSize(25f);

        hardButton.setTextColor(getResources().getColor(R.color.black));
        goButton.setVisibility(View.VISIBLE);
        leveltype = 1;


    }

    public void hardClicked(View view) {
        easyButton.setBackgroundColor(getResources().getColor(R.color.button_light));
        view.setBackgroundColor(getResources().getColor(R.color.button_dark));
        hardButton.setTextColor(getResources().getColor(R.color.white));
        easyButton.setTextColor(getResources().getColor(R.color.black));
        hardButton.setTextSize(32f);
        easyButton.setTextSize(25f);
        goButton.setVisibility(View.VISIBLE);
        leveltype = 2;

    }
}