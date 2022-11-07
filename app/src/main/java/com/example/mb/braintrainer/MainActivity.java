package com.example.mb.braintrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mb.braintrainer.db.AppDatabase;
import com.example.mb.braintrainer.db.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button button0, button1, button2, button3, buttonplayAgain;
    GridLayout gridLayout;
    int a, b;
    int score = 0;
    int level;
    int noOfQuestions;
    int locationCorrectAnswer;
    ArrayList<Integer> answersList = new ArrayList<>();
    TextView textviewTimer, textviewScore, textviewEquation, textviewResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        buttonplayAgain = findViewById(R.id.playagainbutton);
        gridLayout = findViewById(R.id.gridLayout);
        textviewTimer = findViewById(R.id.textViewTimer);
        textviewScore = findViewById(R.id.textViewScore);
        textviewEquation = findViewById(R.id.textViewEquation);
        textviewResult = findViewById(R.id.textViewResult);
        getHighScore();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            level = extras.getInt("key");
            //The key argument here must match that used in the other activity
        }
        playAgain(button0);

    }

    private void newQuestion() {
        Random random = new Random();
        if (level == 1) {

            //create two random variables..

            a = random.nextInt(20);
            b = random.nextInt(20);
            textviewEquation.setText(Integer.toString(a) + " + " + Integer.toString(b));
            locationCorrectAnswer = random.nextInt(4);

            answersList.clear();

            for (int i = 0; i < 4; i++) {
                if (i == locationCorrectAnswer) {
                    answersList.add(a + b);

                } else {
                    int wrongAnswer = random.nextInt(41);
                    while (wrongAnswer == a + b) {
                        wrongAnswer = random.nextInt(41);
                    }
                    answersList.add(wrongAnswer);
                }
            }

        } else if (level == 2) {

            b = random.nextInt(20 - 5) + 5;
            a = random.nextInt(b - 0);//a is larger tha b ..so we can do a-b
            textviewEquation.setText(Integer.toString(b) + " - " + Integer.toString(a));
            locationCorrectAnswer = random.nextInt(4);
            answersList.clear();
            for (int i = 0; i < 4; i++) {
                if (i == locationCorrectAnswer) {
                    answersList.add(b - a);

                } else {
                    int wrongAnswer = random.nextInt(20);
                    while (wrongAnswer == b - a) {
                        wrongAnswer = random.nextInt(20);
                    }
                    answersList.add(wrongAnswer);
                }
            }


        }

        button0.setText(Integer.toString(answersList.get(0)));
        button1.setText(Integer.toString(answersList.get(1)));
        button2.setText(Integer.toString(answersList.get(2)));
        button3.setText(Integer.toString(answersList.get(3)));
    }

    private int getHighScore() {
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

 /*   private void saveHighScore(int highScore) {
        AppDatabase db = AppDatabase.getDBInstance(this.getApplicationContext());
        User user = new User();
        user.highScore = highScore;
        db.userDAO().InsertUser(user);
        finish();
    }*/

    private void updateHighScore(int newScore) {
        AppDatabase db = AppDatabase.getDBInstance(this.getApplicationContext());
        User user = new User();
        user.highScore = newScore;
        db.userDAO().InsertUser(user);
       //  finish();
    }

    public void checkAnswer(View view) {

        String tag = view.getTag().toString();
textviewResult.setTextColor(getResources().getColor(R.color.black));
textviewResult.setTextSize(45f);
        if (Integer.toString(locationCorrectAnswer).equals(tag)) {
            textviewResult.setText("Correct!");
            score++;
            noOfQuestions++;
        } else {
            textviewResult.setText("Wrong!!!");
            noOfQuestions++;
        }

        textviewScore.setText(score + "/" + noOfQuestions);
        newQuestion();

    }

    public void playAgain(View view) {


        button0.setClickable(true);
        button1.setClickable(true);
        button2.setClickable(true);
        button3.setClickable(true);
        textviewResult.setText("");


        buttonplayAgain.setVisibility(View.INVISIBLE);
        score = 0;
        noOfQuestions = 0;
        textviewTimer.setText("30s");
        textviewScore.setText(score + "/" + noOfQuestions);


        newQuestion();
        new CountDownTimer(30100, 1000) {

            @Override
            public void onTick(long l) {
                textviewTimer.setText(String.valueOf(l / 1000) + "s");

            }

            @Override
            public void onFinish() {
                buttonplayAgain.setVisibility(View.VISIBLE);
                  //textviewResult.setText("Done!!!");

                button0.setClickable(false);
                button1.setClickable(false);
                button2.setClickable(false);
                button3.setClickable(false);
                int highScore= getHighScore();
                if(score>highScore){
                    updateHighScore(score);
                    textviewResult.setTextColor(getResources().getColor(R.color.white));
                    textviewResult.setTextSize(35f);

                    textviewResult.setText("Congratulations...\n new highScore achieved...");

                //    Toast.makeText(getApplicationContext(), "Congratulations... new highScore achieved...", Toast.LENGTH_SHORT).show();

                }
                else{
                    textviewResult.setText("Done!!!");

                }
         

            }
        }.start();
    }
}