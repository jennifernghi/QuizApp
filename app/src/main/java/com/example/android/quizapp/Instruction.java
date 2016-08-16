package com.example.android.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

/**
 * Created by jennifernghinguyen on 8/3/16.
 */
public class Instruction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intruction);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        getWindow().setLayout((int)(width*0.9), (int)(height*0.8));
    }

    public void close(View view){
        sendIntent(HomeActivity.class);
    }
    protected void sendIntent(Class<?> className) {
        Intent intent = new Intent(this, className);
        startActivity(intent);
    }
}
