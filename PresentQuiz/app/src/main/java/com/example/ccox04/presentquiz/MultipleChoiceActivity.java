package com.example.ccox04.presentquiz;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

/*
* Author:      Chris Cox
* Contributor: Charles Ritchie
* Class:       CS 3714
* Due Date:    5/2/17
* Assignment:  Final Group Project
* Description: Create/ Design/ Build your own application from ground up that will better the blacksburg community.
*              We designed an application to replace an iClicker. This applciation connect to a QT run server
*              that would be run on the professors machine.  This server communicates with the application using
*              TCP protocol.  The application handles the connection using AsyncTasks.  One AsyncTask receives the quiz
*              and one AsyncTask sends the quiz back out to the server.  Additionally this project allows connection to the
*              server by decrypting a image to retrieve the server's IP Address and Port.  Then the quiz is administered
*              and the student can answer multiple choice or short answer questions.  Once the student is finished with
*              the quiz then it is sent back to the server where it is stored in a local file on the professors machine
*              with student identifier(PID), questions, answers given, student answers, and the correct answer to each question.
* File: MultipleChoiceActivity.java
*
*/

public class MultipleChoiceActivity extends AppCompatActivity {
    Button answerABtn, answerBBtn, answerCBtn, answerDBtn, answerEBtn;
    TextView questionTextView;
    String answerA, answerB, answerC, answerD, answerE, currentQuestion;
    int correctAnswer;
    private int chosen;
    String accent = "#616BCF";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_choice);
        answerABtn = (Button) findViewById(R.id.answerAButtonMC);
        answerBBtn = (Button) findViewById(R.id.answerBButtonMC);
        answerCBtn = (Button) findViewById(R.id.answerCButtonMC);
        answerDBtn = (Button) findViewById(R.id.answerDButtonMC);
        answerEBtn = (Button) findViewById(R.id.answerEButtonMC);
        correctAnswer = 0;
        Integer.decode(accent);
        questionTextView = (TextView) findViewById(R.id.question_mcTextView);
        Intent getMainIntent = getIntent();
        Bundle getMainBundle = getMainIntent.getExtras();
        if (getMainIntent.hasExtra(MainActivity.QUESTION_MC)) {
            questionTextView.setText(getMainBundle.getString(MainActivity.QUESTION_MC));
            currentQuestion = getMainBundle.getString(MainActivity.QUESTION_MC);
            answerA = getMainBundle.getString(MainActivity.ANSWERA);
            answerB = getMainBundle.getString(MainActivity.ANSWERB);
            answerC = getMainBundle.getString(MainActivity.ANSWERC);
            answerD = getMainBundle.getString(MainActivity.ANSWERD);
            answerE = getMainBundle.getString(MainActivity.ANSWERE);
            answerABtn.setText(answerA);
            answerBBtn.setText(answerB);
            answerCBtn.setText(answerC);
            answerDBtn.setText(answerD);
            answerEBtn.setText(answerE);
            chosen = 0;
            correctAnswer = getMainBundle.getInt(MainActivity.CORRECTANSWER_MC);
        }
    }


    public void onClickAnswerA(View view){
        if(chosen != 1)
        {
            resetColor(chosen);
            chosen = 1;
            answerABtn.setBackgroundColor(Color.rgb(221,207,77));
            Toast.makeText(this, "Click A again to confirm answer",Toast.LENGTH_SHORT).show();
        }
        else {

            Intent goToMainActivity = new Intent();
            goToMainActivity.putExtra("MCQuestionCompleted", answerA);
            setResult(RESULT_OK, goToMainActivity);
            finish();
        }
    }

    private void resetColor(int chosen) {
        if(chosen == 1)
        {
            answerABtn.setBackgroundColor(Color.parseColor(accent));
        }
        else if(chosen == 2)
        {
            answerBBtn.setBackgroundColor(Color.parseColor(accent));
        }
        else if(chosen == 3)
        {
            answerCBtn.setBackgroundColor(Color.parseColor(accent));
        }
        else if(chosen == 4)
        {
            answerDBtn.setBackgroundColor(Color.parseColor(accent));
        }
        else if(chosen == 5)
        {
            answerEBtn.setBackgroundColor(Color.parseColor(accent));
        }

    }

    public void onClickAnswerB(View view){
        if(chosen != 2)
        {
            resetColor(chosen);
            chosen = 2;
            answerBBtn.setBackgroundColor(Color.rgb(221,207,77));
            Toast.makeText(this, "Click B again to confirm answer",Toast.LENGTH_SHORT).show();
        }
        else {
            Intent goToMainActivity = new Intent();
            goToMainActivity.putExtra("MCQuestionCompleted", answerB);
            setResult(RESULT_OK, goToMainActivity);
            finish();
        }
    }
    public void onClickAnswerC(View view){
        if(chosen != 3)
        {
            resetColor(chosen);
            chosen = 3;
            answerCBtn.setBackgroundColor(Color.rgb(221,207,77));
            Toast.makeText(this, "Click C again to confirm answer",Toast.LENGTH_SHORT).show();
        }
        else {
            Intent goToMainActivity = new Intent();
            goToMainActivity.putExtra("MCQuestionCompleted", answerC);
            setResult(RESULT_OK, goToMainActivity);
            finish();
        }
    }
    public void onClickAnswerD(View view){
        if(chosen != 4)
        {
            resetColor(chosen);
            chosen = 4;
            answerDBtn.setBackgroundColor(Color.rgb(221,207,77));
            Toast.makeText(this, "Click D again to confirm answer",Toast.LENGTH_SHORT).show();
        }
        else {
            Intent goToMainActivity = new Intent();
            goToMainActivity.putExtra("MCQuestionCompleted", answerD);
            setResult(RESULT_OK, goToMainActivity);
            finish();
        }
    }
    public void onClickAnswerE(View view){
        if(chosen != 5) {
            resetColor(chosen);
            chosen = 5;
            answerEBtn.setBackgroundColor(Color.rgb(221,207,77));
            Toast.makeText(this, "Click E again to confirm answer", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent goToMainActivity = new Intent();
            goToMainActivity.putExtra("MCQuestionCompleted", answerE);
            setResult(RESULT_OK, goToMainActivity);
            finish();
        }

    }

    public void showShrtToast(String toastString){
        Toast.makeText(this, toastString, Toast.LENGTH_SHORT).show();
    }

    public void showLongToast(String toastString){
        Toast.makeText(this, toastString, Toast.LENGTH_LONG).show();
    }

    // This handles if the user presses the back button;  Without this when pressed app will crash
    @Override
    public void onBackPressed() {
        Intent goToMainActivity = new Intent();
        goToMainActivity.putExtra("MCQuestionCompleted", "Did Not Answer");
        setResult(RESULT_OK, goToMainActivity);
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        answerA = savedInstanceState.getString(MainActivity.ANSWERA);
        answerB = savedInstanceState.getString(MainActivity.ANSWERB);
        answerC = savedInstanceState.getString(MainActivity.ANSWERC);
        answerD = savedInstanceState.getString(MainActivity.ANSWERD);
        answerE = savedInstanceState.getString(MainActivity.ANSWERE);
        currentQuestion = savedInstanceState.getString(MainActivity.QUESTION_MC);
        correctAnswer = savedInstanceState.getInt(MainActivity.CORRECTANSWER_MC);
        questionTextView.setText(savedInstanceState.getString(MainActivity.QUESTION_MC));
        answerABtn.setText(savedInstanceState.getString(MainActivity.ANSWERA));
        answerBBtn.setText(savedInstanceState.getString(MainActivity.ANSWERB));
        answerCBtn.setText(savedInstanceState.getString(MainActivity.ANSWERC));
        answerDBtn.setText(savedInstanceState.getString(MainActivity.ANSWERD));
        answerEBtn.setText(savedInstanceState.getString(MainActivity.ANSWERE));
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(MainActivity.ANSWERA, answerA);
        outState.putString(MainActivity.ANSWERB, answerB);
        outState.putString(MainActivity.ANSWERC, answerC);
        outState.putString(MainActivity.ANSWERD, answerD);
        outState.putString(MainActivity.ANSWERE, answerE);
        outState.putString(MainActivity.QUESTION_MC, currentQuestion);
        outState.putInt(MainActivity.CORRECTANSWER_MC, correctAnswer);
        super.onSaveInstanceState(outState);
    }
}
