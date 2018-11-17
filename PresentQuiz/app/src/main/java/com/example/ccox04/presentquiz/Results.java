package com.example.ccox04.presentquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/*
* Author:      Charles Ritchie
* Contributor: Chris Cox
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
* File: Results.java
*
*/

public class Results extends AppCompatActivity {

    TextView score;
    TextView fluff;
    ImageView image;
    int userScore, totalNumberQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        score = (TextView)findViewById(R.id.score);
        fluff = (TextView)findViewById(R.id.fluff);

        image = (ImageView)findViewById(R.id.pic);
        Intent getMainIntent = getIntent();
        Bundle getMainBundle = getMainIntent.getExtras();
        if (getMainIntent.hasExtra(MainActivity.NUMBERQUESTIONS)) {
            totalNumberQuestions = Integer.parseInt(getMainBundle.getString(MainActivity.NUMBERQUESTIONS));
            userScore = Integer.parseInt(getMainBundle.getString(MainActivity.USERTOTALCORRECTANSWERS));
        }
        if(totalNumberQuestions != 0) {
            float abc = ((float) userScore / totalNumberQuestions) * 100; // Multiply by 100 to get score equiv
            userScore = Math.round(abc);
            fluff.setText("Your multiple choice score was:");
            score.setText(String.valueOf(userScore) + "%");
        }
        else
        {
            fluff.setText("Instructor will grade questiosn");
        }
        setImage();
    }


    private void setImage() {
        if(userScore == 100){
            image.setImageResource(R.drawable.amazing);
        }
        else if(userScore >= 80)
        {
            image.setImageResource(R.drawable.good);
        }
        else if(userScore >= 60)
        {
            image.setImageResource(R.drawable.close);
        }
        else
        {
            image.setImageResource(R.drawable.bad);
        }
    }




    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        userScore = savedInstanceState.getInt(MainActivity.USERSCORE);
        totalNumberQuestions = savedInstanceState.getInt(MainActivity.NUMBERQUESTIONS);
        fluff.setText("Your Score was:");
        score.setText(String.valueOf(userScore) + "%");
        setImage();
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(MainActivity.USERSCORE, userScore);
        outState.putInt(MainActivity.NUMBERQUESTIONS, totalNumberQuestions);
        super.onSaveInstanceState(outState);
    }
}

