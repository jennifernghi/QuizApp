package com.example.android.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
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

public class Quiz1 extends AppCompatActivity {

    public static final String QUIZ1_SOLUTION =  "Two";//solution of current quiz
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
        return solution = (TextView) findViewById(R.id.solution);
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

    public void setDoneButton(Button doneButton){
        this.doneButton = doneButton;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz1);
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

        Intent intent = getIntent();//new intent

        playerName = intent.getStringExtra(getString(R.string.player_name_intent_extra));//get playerName value from HomeActivity
        homeActivity.setPlayerName(playerName);//update the name
        getTextViewName().setText(playerName);//show the playername on the this activity


        playerScore = intent.getIntExtra(getString(R.string.player_score_intent_extra), HomeActivity.INITIAL_SCORE);
        homeActivity.setPlayerScore(playerScore);//update the score
        getTextViewScore().setText(getString(R.string.empty_string) + playerScore);//show the playerScore on the this activity
    }
    /*
     * this method will be activated when correct answer is chosen
     */
    protected void correctRadioButtonSelected(RadioButton radioButton1, RadioButton radioButton2) {

        playerScore = homeActivity.plus3Points();//add 3 points if correct
        homeActivity.setPlayerScore(playerScore);
        getTextViewScore().setText(getString(R.string.empty_string) + playerScore);
        //deactivate other 2 buttons
        radioButton1.setEnabled(false);
        radioButton2.setEnabled(false);
        //show the toast
        resultToastForRadioButton(getString(R.string.correct_message), getString(R.string.plus_3_points), R.drawable.correct);
    }
    /*
    * this method will be activated when incorrect answer is chosen
     */
    protected void inCorrectRadioButtonSelected(RadioButton radioButton1, RadioButton radioButton2) {
        playerScore = homeActivity.minus1Point();// minus 1 point if wrong
        homeActivity.setPlayerScore(playerScore);
        getTextViewScore().setText("" + playerScore);
        radioButton1.setEnabled(false);
        radioButton2.setEnabled(false);
        resultToastForRadioButton(getString(R.string.wrong_message), getString(R.string.minus_1_point), R.drawable.wrong);

    }

    protected void sendIntent(Class<?> className) {
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
            correctRadioButtonSelected(getOneSelection(), getThreeSelection());//call correctRadioButtonSelected(RadioButton radioButton1, RadioButton radioButton2) method
        } else if (getOneSelection().isChecked()) {
            //if incorrect button is chosen
            inCorrectRadioButtonSelected(getTwoSelection(), getThreeSelection());//call inCorrectRadioButtonSelected(RadioButton radioButton1, RadioButton radioButton2)
        } else {
            inCorrectRadioButtonSelected(getOneSelection(), getTwoSelection());//inCorrectRadioButtonSelected(RadioButton radioButton1, RadioButton radioButton2)
        }
        getDoneButton().setText(getString(R.string.next));//change the doneButton's text to Next
        getSolutionLayout().setVisibility(View.VISIBLE);//Show solutionLayout
        getSolution().setText(getString(R.string.quiz1_solution_part1) + QUIZ1_SOLUTION + getString(R.string.quiz1_solution_part2));//show solution for current quiz
        //assign new onClick for doneButton
        getDoneButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendIntent(Quiz2.class);// load quiz2 activity
            }
        });


    }

    /*
     * toast display result and points of the quiz
     */
    protected void resultToastForRadioButton(String message, String pointAdded, int resId) {

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_layout_root));

        TextView messageText = (TextView) layout.findViewById(R.id.toast_message);
        messageText.setText(message);

        TextView pointText = (TextView) layout.findViewById(R.id.toast_point);
        pointText.setText(getString(R.string.point_message) + pointAdded);

        ImageView symbol = (ImageView) layout.findViewById(R.id.toast_img);
        symbol.setImageResource(resId);// image displayed in the toast
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.FILL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}
