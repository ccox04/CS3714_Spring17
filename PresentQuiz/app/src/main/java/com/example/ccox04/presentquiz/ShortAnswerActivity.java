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
        //Log.d(TAG, "PlayingActivity onCreate: BEFORE IF Background Song.");
        if (getMainIntent.hasExtra(MainActivity.QUESTION_SA)) {
            //Log.d(TAG, "PlayingActivity onCreate: Printing Background Song.");
            questionTextView.setText(getMainBundle.getString(MainActivity.QUESTION_SA));
            currentQuestion = getMainBundle.getString(MainActivity.QUESTION_SA);
            correctAnswer = getMainBundle.getString(MainActivity.ANSWER_SA);
        }
    }

    public void onClickSubmit(View view){
        userAnswer = answerEditText.getText().toString();
        if(Objects.equals(userAnswer, correctAnswer)){
            showShrtToast("Submitted: You Answered Correctly! :)");
        }
        else{
            showShrtToast("Submitted: You answer has to be checked by the professor.");
        }
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
