package com.example.chuck.quizproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Charles on 4/25/2017.
 * Screen to take QR code picture
 */

public class QRScreen extends AppCompatActivity implements View.OnClickListener {
    Button take;
    int type;
    String ans;
    String[] answers;
    String[] questions;
    protected static int branch = 0;
    public static final String ANSWERS = "answers";
    public static final String QUESTIONS = "questions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr);
        take = (Button)findViewById(R.id.start);
        take.setOnClickListener(this);
        Intent intent = this.getIntent();
        ans = intent.getStringExtra(MainActivity.ANSWERS);
        answers = ans.split("|");
        questions = intent.getStringExtra(MainActivity.QUESTIONS).split("|");
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == take.getId())//Take QR code picture
        {
            startQuiz();
        }
    }

    private void startQuiz()
    {
        if(type == 1)//Question type is multiple choice
        {
            branch = 1;
            Intent intent = new Intent(this, MultipleChoice.class);
            intent.putExtra(ANSWERS, answers);
            intent.putExtra(QUESTIONS, questions);
            startActivity(intent);
        }
        if(type == 2)//Question type is short answer
        {
            branch = 2;
            Intent intent = new Intent(this, Shortans.class);
            intent.putExtra(QUESTIONS, questions);
            startActivity(intent);
        }
    }

}
