package com.example.chuck.quizproject;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by chuck on 4/26/2017.
 */

public class Shortans extends AppCompatActivity implements View.OnFocusChangeListener, View.OnClickListener {
    EditText string;
    TextView question;
    String[] quest;
    String answer;
    Button sendAnswer;
    int resumed;
    public static final String ANSWER = "answer";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shortans);
        string = (EditText)findViewById(R.id.string);
        question = (TextView)findViewById(R.id.quest);
        string.setOnFocusChangeListener(this);
        sendAnswer = (Button)findViewById(R.id.confirm);
        sendAnswer.setOnClickListener(this);
        answer = null;
        Intent intent = getIntent();
        quest = intent.getStringArrayExtra(QRScreen.QUESTIONS);
        question.setText(quest[0]);
        resumed = 0;
    }

    @Override
    public void onFocusChange(View view, boolean b) {//The edittext is done editing.
        if(view.getId() == string.getId())
        {
            answer = string.getText().toString();
        }

    }

    //May change based on if we implement multiple questions for short answer
    @Override
    public void onClick(View view) {
        if(view.getId() == sendAnswer.getId())
        {
            if(answer != null)
            {
                Intent intent = new Intent(this, Results.class);
                startActivity(intent);
            }
            else
            {
                Toast toast = new Toast(this);
                toast.makeText(this, "Enter an answer for submission", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,0);
                toast.show();
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        Toast toast = new Toast(this);
        toast.makeText(this, "Answer must be submitted. No going back", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,0);
        toast.show();
    }

    @Override
    public void onResume()
    {
        if(resumed == 0)
        {
            resumed = 1;
        }
        else
        {
            Toast.makeText(this, "Resumed", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {

        }
    }

}
