package com.example.android.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Quiz4 extends AppCompatActivity {

    //solution for this quiz
    public static final int QUIZ4_CORRECT_ANSWER = 24;
    //global variables
    private TextView textViewName;
    private TextView textViewScore;
    private HomeActivity homeActivity = new HomeActivity();
    private int playerScore;
    private String playerName;
    private Button doneButton;
    private EditText answer;
    private TextView solution;
    private LinearLayout solutionLayout;

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

    //@return button doneButton
    public Button getDoneButton() {

        return this.doneButton = (Button) findViewById(R.id.done_button);
    }

    //@return LinearLayout solutionLayout
    public LinearLayout getSolutionLayout() {
        return this.solutionLayout = (LinearLayout) findViewById(R.id.solution_layout);
    }

    //@return TextView solution
    public TextView getSolution() {
        return this.solution = (TextView) findViewById(R.id.solution);
    }
    public EditText getAnswer(){
        return this.answer = (EditText) findViewById(R.id.answer_field);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz4);
        receiveIntent();
    }


    public void receiveIntent() {
        Intent intent = getIntent();//new intent

        playerName = intent.getStringExtra(getString(R.string.player_name_intent_extra));//get playerName value from Quiz3
        homeActivity.setPlayerName(playerName);//update the name
        getTextViewName().setText(playerName);//show the playername on the this activity


        playerScore = intent.getIntExtra(getString(R.string.player_score_intent_extra), HomeActivity.INITIAL_SCORE);
        homeActivity.setPlayerScore(playerScore);//update the score
        getTextViewScore().setText(getString(R.string.empty_string) + playerScore);//show the playerScore on the this activity
    }


    public void sendIntent() {
        sendIntent(Quiz5.class);
    }

    protected void sendIntent(Class<?> className) {
        //send this activity's info to next activity
        Intent intent = new Intent(this, className);
        playerName = getTextViewName().getText().toString();
        playerScore = Integer.parseInt(getTextViewScore().getText().toString());
        intent.putExtra(getString(R.string.player_name_intent_extra), playerName);
        intent.putExtra(getString(R.string.player_score_intent_extra), playerScore);
        startActivity(intent);
    }

    /* compare method: compare the result input with solution
     *  @return boolean value - tue
     */
    protected boolean stringCompare() {
        //false if user input empty string, show errorToast
        if (getAnswer().getText().toString().equals(getString(R.string.empty_string))) {
            errorToast(getString(R.string.number_error));
            return false;
        } else {
            //true id user input a number, sho appropriate resultToast
            if (Integer.parseInt(getAnswer().getText().toString()) == QUIZ4_CORRECT_ANSWER) {
                //if correct, plus 3 point
                resultToast(getString(R.string.correct_message), getString(R.string.plus_3_points), R.drawable.correct);
                playerScore = homeActivity.plus3Points();
            } else {
                //otherwise, deduct 1 point
                resultToast(getString(R.string.wrong_message), getString(R.string.minus_1_point), R.drawable.wrong);
                playerScore = homeActivity.minus1Point();
            }
            getAnswer().setEnabled(false);//deactivate edittext
            return true;
        }
    }

    public void done(View view) {

        if (stringCompare()) {
            getTextViewScore().setText(getString(R.string.empty_string) + playerScore);//update the score
            //show solutionLayout and Solution
            getSolutionLayout().setVisibility(View.VISIBLE);
            getSolution().setText(getString(R.string.quiz4_solution) + QUIZ4_CORRECT_ANSWER + getString(R.string.period));
            getDoneButton().setText(getString(R.string.next));//change the text of doneButton to Next
            //assign new onClick for doneButton
            getDoneButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendIntent();
                }
            });
        }
    }

    protected void resultToast(String message, String pointAdded, int resId) {

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_layout_root));

        TextView messageText = (TextView) layout.findViewById(R.id.toast_message);
        messageText.setText(message);

        TextView pointText = (TextView) layout.findViewById(R.id.toast_point);
        pointText.setText(getString(R.string.point_message) + pointAdded);

        ImageView symbol = (ImageView) layout.findViewById(R.id.toast_img);
        symbol.setImageResource(resId);
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.FILL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    protected void errorToast(String message) {

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_layout_root));

        TextView messageText = (TextView) layout.findViewById(R.id.toast_message);
        messageText.setText(message);


        ImageView symbol = (ImageView) layout.findViewById(R.id.toast_img);
        symbol.setImageResource(R.drawable.wrong);
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.FILL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}
