package com.example.android.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;

public class QuizFinal extends AppCompatActivity {

    //Max score can be earned of the app
    public static final int TOTAL_SCORE = 15;
    private TextView textViewName;
    private TextView textViewScore;
    private int playerScore;
    private String playerName;
    private TextView percent;
    private TextView textViewMaxScore;


    /*
     * getters
     */
    //@return textview textViewName
    public TextView getTextViewName() {
        return this.textViewName = (TextView) findViewById(R.id.text_view_name);
    }

    //@return textview textViewScore
    public TextView getTextViewScore() {
        return this.textViewScore = (TextView) findViewById(R.id.text_view_score);
    }

    //@return textview textViewMaxScore
    public TextView getTextViewMaxScore() {
        return this.textViewMaxScore = (TextView) findViewById(R.id.text_view_max_score);
    }

    public TextView getPercent() {
        return this.percent = (TextView) findViewById(R.id.text_view_score_percent);
    }

    //set textViewMaxScore = TOTAL_SCORE
    public void setTextViewMaxScore() {

        getTextViewMaxScore().setText(String.valueOf(TOTAL_SCORE));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        receiveIntent();
        getTotalPercent();
        setTextViewMaxScore();
    }


    protected void receiveIntent() {
        Intent intent = getIntent();//new intent

        playerName = intent.getStringExtra(getString(R.string.player_name_intent_extra));//get playerName value from Quiz5
        getTextViewName().setText(playerName);//show the playername on the this activity


        playerScore = intent.getIntExtra(getString(R.string.player_score_intent_extra), HomeActivity.INITIAL_SCORE);
        getTextViewScore().setText(getString(R.string.empty_string) + playerScore);//show the playerScore on the this activity
    }

    //go back to HomeActivity
    public void sendIntent(View view) {
        sendIntent(HomeActivity.class);
    }

    protected void sendIntent(Class<?> className) {
        Intent intent = new Intent(this, className);
        startActivity(intent);
    }

    public void getTotalPercent() {
        DecimalFormat format = new DecimalFormat("0.0");//format double value
        getPercent().setText(format.format(calculatePercentage(playerScore)));
    }

    protected double calculatePercentage(int playerScore) {
        if (playerScore < 0) {
            return 0.0;
        } else {
            //calculate overall percentage
            return ((double) playerScore / (double) TOTAL_SCORE) * 100;
        }
    }


}
