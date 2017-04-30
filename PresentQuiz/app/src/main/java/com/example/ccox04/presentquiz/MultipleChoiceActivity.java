package com.example.ccox04.presentquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class MultipleChoiceActivity extends AppCompatActivity {
    Button answerABtn, answerBBtn, answerCBtn, answerDBtn, answerEBtn;
    TextView questionTextView;

    String answerA, answerB, answerC, answerD, answerE, currentQuestion;

    int correctAnswer;


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
        questionTextView = (TextView) findViewById(R.id.question_mcTextView);

        Intent getMainIntent = getIntent();
        Bundle getMainBundle = getMainIntent.getExtras();
        //Log.d(TAG, "PlayingActivity onCreate: BEFORE IF Background Song.");
        if (getMainIntent.hasExtra(MainActivity.QUESTION_MC)) {
            //Log.d(TAG, "PlayingActivity onCreate: Printing Background Song.");
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
            correctAnswer = getMainBundle.getInt(MainActivity.CORRECTANSWER_MC);
        }
    }


    public void onClickAnswerA(View view){
        if(Objects.equals(1,correctAnswer)){
            showShrtToast("You Answered Correctly! :)");
        }
        else{
            showShrtToast("You Answered Incorrectly :(");
        }
        Intent goToMainActivity = new Intent();
        goToMainActivity.putExtra("MCQuestionCompleted", answerA);
        setResult(RESULT_OK, goToMainActivity);
        finish();
    }
    public void onClickAnswerB(View view){
        if(Objects.equals(2,correctAnswer)){
            showShrtToast("You Answered Correctly! :)");
        }
        else{
            showShrtToast("You Answered Incorrectly :(");
        }
        Intent goToMainActivity = new Intent();
        goToMainActivity.putExtra("MCQuestionCompleted", answerB);
        setResult(RESULT_OK, goToMainActivity);
        finish();
    }
    public void onClickAnswerC(View view){
        if(Objects.equals(3,correctAnswer)){
            showShrtToast("You Answered Correctly! :)");
        }
        else{
            showShrtToast("You Answered Incorrectly :(");
        }
        Intent goToMainActivity = new Intent();
        goToMainActivity.putExtra("MCQuestionCompleted", answerC);
        setResult(RESULT_OK, goToMainActivity);
        finish();
    }
    public void onClickAnswerD(View view){
        if(Objects.equals(4,correctAnswer)){
            showShrtToast("You Answered Correctly! :)");
        }
        else{
            showShrtToast("You Answered Incorrectly :(");
        }
        Intent goToMainActivity = new Intent();
        goToMainActivity.putExtra("MCQuestionCompleted", answerD);
        setResult(RESULT_OK, goToMainActivity);
        finish();
    }
    public void onClickAnswerE(View view){
        if(Objects.equals(5,correctAnswer)){
            showShrtToast("You Answered Correctly! :)");
        }
        else{
            showShrtToast("You Answered Incorrectly :(");
        }
        Intent goToMainActivity = new Intent();
        goToMainActivity.putExtra("MCQuestionCompleted", answerE);
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
