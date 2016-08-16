package com.example.android.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    public static final int INITIAL_SCORE = 0;
    private String playerName;
    private int playerScore = 0;
    private EditText name;
    public EditText getName(){
        return this.name = (EditText) findViewById(R.id.field_name);
    }
    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void start(View view) {
        if(getName().getText().toString().trim().equals(getString(R.string.empty_string))||getName().getText().toString().trim().isEmpty()){
            errorToast(getString(R.string.name_error));
        }else {
            sendIntent(Quiz1.class);
        }
    }

    protected int plus3Points() {
        return playerScore = playerScore + 3;
    }

    protected int minus1Point() {
        return playerScore = playerScore - 1;
    }

    protected void sendIntent(Class<?> className) {
        Intent intent = new Intent(this, className);
        playerName = getName().getText().toString();
        setPlayerName(playerName);
        intent.putExtra(getString(R.string.player_name_intent_extra), playerName);
        intent.putExtra(getString(R.string.player_score_intent_extra), playerScore);
        startActivity(intent);
    }

    public void openInstruction(View view){
        sendIntent(Instruction.class);
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
