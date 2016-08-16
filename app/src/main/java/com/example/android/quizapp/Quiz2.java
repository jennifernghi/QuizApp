package com.example.android.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Quiz2 extends AppCompatActivity {
    // solution for current quiz
    public static final String QUIZ2_ANSWER1 = "emu love volume";
    public static final String QUIZ2_ANSWER2 = "racecar";
    public static final String QUIZ2_ANSWER3 = "go deliver a dare, vile dog";
    //global variables
    private TextView textViewName;
    private TextView textViewScore;
    private HomeActivity homeActivity = new HomeActivity();
    private TextView solution;
    private LinearLayout solutionLayout;
    private int playerScore;
    private String playerName;
    private CheckBox aSelection;
    private CheckBox bSelection;
    private CheckBox cSelection;
    private CheckBox dSelection;
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

    //@return CheckBox first Checkbox
    public CheckBox getaSelection() {
        return this.aSelection = (CheckBox) findViewById(R.id.a_choice);
    }

    //@return CheckBox second Checkbox
    public CheckBox getbSelection() {
        return this.bSelection = (CheckBox) findViewById(R.id.b_choice);
    }

    //@return CheckBox third Checkbox
    public CheckBox getcSelection() {
        return this.cSelection = (CheckBox) findViewById(R.id.c_choice);
    }

    //@return CheckBox fourth Checkbox
    public CheckBox getdSelection() {
        return this.dSelection = (CheckBox) findViewById(R.id.d_choice);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz2);
        recieveIntent();

    }

    public void sendIntent() {
        sendIntent(Quiz3.class);
    }

    protected void sendIntent(Class<?> className) {
        Intent intent = new Intent(this, className);

        playerName = getTextViewName().getText().toString();
        playerScore = Integer.parseInt(getTextViewScore().getText().toString());
        intent.putExtra(getString(R.string.player_name_intent_extra), playerName);
        intent.putExtra(getString(R.string.player_score_intent_extra), playerScore);
        startActivity(intent);
    }


    public void recieveIntent() {
        Intent intent = getIntent();//new intent

        playerName = intent.getStringExtra(getString(R.string.player_name_intent_extra));//get playerName value from Quiz1
        homeActivity.setPlayerName(playerName);//update the name
        getTextViewName().setText(playerName);//show the playername on the this activity


        playerScore = intent.getIntExtra(getString(R.string.player_score_intent_extra), HomeActivity.INITIAL_SCORE);
        homeActivity.setPlayerScore(playerScore);//update the score
        getTextViewScore().setText(getString(R.string.empty_string) + playerScore);//show the playerScore on the this activity
    }


    /*
    * this method enable doneButton
     */
    protected void activate(View view) {
        getDoneButton().setVisibility(View.VISIBLE);
        getDoneButton().setEnabled(true);
    }

    /*
    * this method deactivate checkboxes
     */
    protected void deactivate(View view) {
        view.setEnabled(false);
    }

    public void done(View view) {
        // if only incorrect checkbox chosen
        if (countCheckBoxCorrect() - countCheckBoxInCorrect() < 0) {
            //load toast for incorrect answer
            resultToast(countCheckBoxCorrect(), countCheckBoxInCorrect(), R.drawable.wrong);
        } else {
            //otherwise, load toast for correct answer
            resultToast(countCheckBoxCorrect(), countCheckBoxInCorrect(), R.drawable.correct);
        }
        // deactivate all checkbox when done button is clicked
        deactivate(getaSelection());
        deactivate(getbSelection());
        deactivate(getcSelection());
        deactivate(getdSelection());
        //get the score
        playerScore = playerScore + getTotalPointForCheckBox(countCheckBoxCorrect(), countCheckBoxInCorrect());
        getTextViewScore().setText(getString(R.string.empty_string) + playerScore);
        //change the donButton's text to Next
        getDoneButton().setText(getString(R.string.next));
        //show solution layout and solution
        getSolution().setText(QUIZ2_ANSWER1 + "\n" + QUIZ2_ANSWER2 + "\n" + QUIZ2_ANSWER3);
        getSolutionLayout().setVisibility(View.VISIBLE);
        //assign new method for donebutton
        getDoneButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendIntent();
            }
        });

    }

    //Toast show result and points
    protected void resultToast(int correct, int incorrect, int resId) {

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout_for_checkbox, (ViewGroup) findViewById(R.id.toast_layout_root));

        TextView correctNumber = (TextView) layout.findViewById(R.id.toast_correct_number);
        correctNumber.setText(correct + getString(R.string.slash) + getCorrectAnswers().length);//show number of correct choices

        TextView pointadded = (TextView) layout.findViewById(R.id.toast_correct_point);
        pointadded.setText(getString(R.string.plus_sign) + correct + getString(R.string.point_text_plural)); // +1 point earned for each correct answer
        TextView incorrectNumber = (TextView) layout.findViewById(R.id.toast_incorrect_number);
        incorrectNumber.setText(incorrect + getString(R.string.slash) + getInCorrectAnswers().length);//show number of incorrect choices

        TextView pointDeducted = (TextView) layout.findViewById(R.id.toast_incorrect_point);
        pointDeducted.setText(getString(R.string.minus_sign) + incorrect + getString(R.string.point_text_plural));// -1 point deductuted for each incorrect answer

        TextView pointText = (TextView) layout.findViewById(R.id.toast_total_point);
        int totalPointAdded = correct - incorrect;
        pointText.setText(getString(R.string.total_point_text) + totalPointAdded);// show total points earned

        ImageView symbol = (ImageView) layout.findViewById(R.id.toast_img);
        symbol.setImageResource(resId);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.FILL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    //@return count number of correct answers
    protected int countCheckBoxCorrect() {
        int count = 0;
        CheckBox[] correctAnswer = getCorrectAnswers();
        for (int i = 0; i < correctAnswer.length; i++) {
            if (correctAnswer[i].isChecked()) {
                count++;
            }

        }
        return count;
    }

    //@return count number of incorrect answers
    protected int countCheckBoxInCorrect() {
        int count = 0;
        CheckBox[] inCorrectAnswer = getInCorrectAnswers();
        for (int i = 0; i < inCorrectAnswer.length; i++) {
            if (inCorrectAnswer[i].isChecked()) {
                count++;
            }

        }
        return count;
    }

    //@return correctAnswer - array that holds all correct answers
    protected CheckBox[] getCorrectAnswers() {
        CheckBox[] correctAnswer = {getaSelection(), getbSelection(), getdSelection()};
        return correctAnswer;

    }

    //@return correctAnswer - array that holds all incorrect answers
    protected CheckBox[] getInCorrectAnswers() {

        CheckBox[] correctAnswer = {getcSelection()};
        return correctAnswer;

    }

    //@return total points earned for checkbox quiz
    protected int getTotalPointForCheckBox(int numberOfCorrect, int numberOfIncorrect) {
        return numberOfCorrect - numberOfIncorrect;
    }
}
