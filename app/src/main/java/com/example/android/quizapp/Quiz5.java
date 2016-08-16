package com.example.android.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class Quiz5 extends AppCompatActivity {
    //solution for this quiz
    public static final int QUIZ5_SOLUTION = 1;
    private RadioButton oneSelection;
    private RadioButton twoSelection;
    private RadioButton threeSelection;
    private TextView textViewName;
    private TextView textViewScore;
    private TextView solution;
    private LinearLayout solutionLayout;
    private HomeActivity homeActivity = new HomeActivity();
    private int playerScore;
    private String playerName;
    private Button doneButton;

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

    //@return RadioButton first radiobutton
    public RadioButton getOneSelection() {
        return this.oneSelection = (RadioButton) findViewById(R.id.one_choice);
    }

    //@return RadioButton second radiobutton
    public RadioButton getTwoSelection() {
        return this.twoSelection = (RadioButton) findViewById(R.id.two_choice);
    }

    //@return RadioButton third radiobutton
    public RadioButton getThreeSelection() {
        return this.threeSelection = (RadioButton) findViewById(R.id.three_choice);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz5);
        receiveIntent();
    }

    /*
    * this method enable doneButton
     */
    public void radioButtonSelected(View view) {
        //activate doneButton when 1 radiobutton is chosen
        getDoneButton().setVisibility(View.VISIBLE);
        getDoneButton().setEnabled(true);
    }

    protected void receiveIntent() {

        Intent intent = getIntent();

        playerName = intent.getStringExtra(getString(R.string.player_name_intent_extra));
        getTextViewName().setText(playerName);


        playerScore = intent.getIntExtra(getString(R.string.player_score_intent_extra), HomeActivity.INITIAL_SCORE);
        getTextViewScore().setText(getString(R.string.empty_string) + playerScore);
    }

    /*
     * this method will be activated when correct answer is chosen
     */
    protected void correctRadioButtonSelected(RadioButton radioButton1, RadioButton radioButton2) {

        playerScore = playerScore + homeActivity.plus3Points();
        homeActivity.setPlayerScore(playerScore);
        getTextViewScore().setText(getString(R.string.empty_string) + playerScore);
        //deactivate other 2 buttons
        radioButton1.setEnabled(false);
        radioButton2.setEnabled(false);
        //show appropriate toast
        resultToastForRadioButton(getString(R.string.correct_message), getString(R.string.plus_3_points), R.drawable.correct);
    }

    /*
     * this method will be activated when incorrect answer is chosen
     */
    protected void inCorrectRadioButtonSelected(RadioButton radioButton1, RadioButton radioButton2) {

        playerScore = playerScore + homeActivity.minus1Point();
        homeActivity.setPlayerScore(playerScore);
        getTextViewScore().setText(getString(R.string.empty_string) + playerScore);
        //deactivate other 2 buttons
        radioButton1.setEnabled(false);
        radioButton2.setEnabled(false);
        //show appropriate toast
        resultToastForRadioButton(getString(R.string.wrong_message), getString(R.string.minus_1_point), R.drawable.wrong);

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

    /*
     * when done button is clicked
     */
    public void done(View view) {
        //if correct button is chosen
        if (getTwoSelection().isChecked()) {
            correctRadioButtonSelected(getOneSelection(), getThreeSelection());

        } else if (getOneSelection().isChecked()) {
            //if incorrect button is chosen
            inCorrectRadioButtonSelected(getTwoSelection(), getThreeSelection());
        } else {
            inCorrectRadioButtonSelected(getOneSelection(), getTwoSelection());
        }
        getDoneButton().setText(getString(R.string.next));//change the doneButton's text to Next
        //Show solutionLayout ans solution
        getSolutionLayout().setVisibility(View.VISIBLE);
        getSolution().setText(getString(R.string.quiz5_solution_part1) + QUIZ5_SOLUTION + getString(R.string.quiz5_solution_part2));
        //assign new onClick for doneButton
        getDoneButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendIntent(QuizFinal.class);
            }
        });


    }

    /*
     * resultToast - show the toast
     */
    protected void resultToastForRadioButton(String message, String pointAdded, int resId) {

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
}
