package com.example.ccox04.presentquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

/*
* Author:      Chris Cox
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
* File: ShortAnswerActivity.java
*
*/

public class ShortAnswerActivity extends AppCompatActivity {

    Button submitBtn;
    TextView questionTextView;
    EditText answerEditText;
    String correctAnswer, userAnswer, currentQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_short_answer);
        submitBtn = (Button) findViewById(R.id.submit_saButton);
        questionTextView = (TextView) findViewById(R.id.question_saTextView);
        answerEditText = (EditText) findViewById(R.id.answer_saEditText);

        Intent getMainIntent = getIntent();
        Bundle getMainBundle = getMainIntent.getExtras();
        if (getMainIntent.hasExtra(MainActivity.QUESTION_SA)) {
            questionTextView.setText(getMainBundle.getString(MainActivity.QUESTION_SA));
            currentQuestion = getMainBundle.getString(MainActivity.QUESTION_SA);
            correctAnswer = getMainBundle.getString(MainActivity.ANSWER_SA);
        }
    }

    public void onClickSubmit(View view){
        userAnswer = answerEditText.getText().toString();

        Intent goToMainActivity = new Intent();
        goToMainActivity.putExtra("SAQuestionCompleted", userAnswer);
        setResult(RESULT_OK, goToMainActivity);
        finish();
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
        goToMainActivity.putExtra("SAQuestionCompleted", "Did Not Answer");
        setResult(RESULT_OK, goToMainActivity);
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        currentQuestion = savedInstanceState.getString(MainActivity.QUESTION_SA);
        questionTextView.setText(savedInstanceState.getString(MainActivity.QUESTION_SA));
        correctAnswer = savedInstanceState.getString(MainActivity.ANSWER_SA);
        userAnswer = savedInstanceState.getString(MainActivity.USERANSWER_SA);
        answerEditText.setText(savedInstanceState.getString(MainActivity.USERANSWER_SA));
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(MainActivity.QUESTION_SA, currentQuestion);
        outState.putString(MainActivity.ANSWER_SA, correctAnswer);
        outState.putString(MainActivity.USERANSWER_SA, userAnswer);
        super.onSaveInstanceState(outState);
    }
}
