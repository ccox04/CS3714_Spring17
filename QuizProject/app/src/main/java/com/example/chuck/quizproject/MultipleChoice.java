package com.example.chuck.quizproject;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by chuck on 4/26/2017.
 */

public class MultipleChoice extends AppCompatActivity implements View.OnClickListener{
    Button a,b,c,d,e;
    TextView question;
    int choice;
    String[] ans;
    String[] questions;//To hold the questions of the
    String[] studentAns;
    int count;
    int correct;//TO hold the score of the user
    int Toasty;
    int Toastx;//For changing toast location
    int number;
    int resumed;
    boolean chosen;
    Toast toast;
    public static final String SCORE = "score";
    public static final String STUDENTANSWERS = "studentanswers";
    //public static final String ID = "id";
    public static final String TOTAL = "total";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multiplechoice);
        choice = 0;
        a = (Button)findViewById(R.id.A);
        b = (Button)findViewById(R.id.B);
        c = (Button)findViewById(R.id.C);
        d = (Button)findViewById(R.id.D);
        e = (Button)findViewById(R.id.E);
        question = (TextView) findViewById(R.id.question);
        a.setOnClickListener(this);
        b.setOnClickListener(this);
        c.setOnClickListener(this);
        d.setOnClickListener(this);
        e.setOnClickListener(this);
        //Not sure how you getting the answers from the QR code but need an intent
        Intent intent = getIntent();
        ans = intent.getStringArrayExtra(QRScreen.ANSWERS);
        questions = intent.getStringArrayExtra(QRScreen.QUESTIONS);
        choice = 0;
        count = 0;
        correct = 0;
        number = Math.min(ans.length,questions.length);
        question.setText(questions[0]);
        Toastx = Gravity.TOP;
        Toasty = Gravity.CENTER_HORIZONTAL;
        chosen = false;
        resumed = 0;
    }
    //Method to get the clicks of the answers
    @Override
    public void onClick(View view) {
        if (view.getId() == a.getId())
        {
            if(choice != 1 || !chosen)//User needs to confirm answer
            {
                choice = 1;
                a.setTextColor(Color.RED);
                a.setShadowLayer(5, 0, 0,Color.RED);//Make the
                toast.makeText(this, "Click A again to confirm answer",Toast.LENGTH_SHORT);
                toast.setGravity(Toastx|Toasty, 0,0);//Show toast in the top center of screen if in portrait.
                toast.show();
                chosen = true;
            }
            else if(chosen && choice == 1)//They selected A again
            {
                chosen = false;
                toast.cancel();//Cancel the toast if it is showing
                choice = 0;
                studentAns[count] = "A";
                a.setShadowLayer(0,0,0,Color.WHITE);
                a.setTextColor(Color.WHITE);
                if(studentAns[count].compareToIgnoreCase(questions[count]) == 0)
                {
                    correct++;
                }
                count++;
                if(count >= number)//Max number of questions were answered, go to the results screen
                {
                    goToResults();
                }
                else
                {
                    question.setText(questions[count]);
                }
            }
        }
        else if(view.getId() == b.getId())
        {
            if(choice != 2 || !chosen)
            {
                choice = 2;
                b.setTextColor(Color.RED);
                b.setShadowLayer(5, 0, 0,Color.RED);//Make the
                toast.makeText(this, "Click B again to confirm answer",Toast.LENGTH_SHORT);
                toast.setGravity(Toastx|Toasty, 0,0);//Show toast in the top center of screen if in portrait.
                toast.show();
                chosen = true;
            }
            else if(chosen && choice ==2)
            {
                chosen = false;
                toast.cancel();//Cancel the toast if it is showing
                choice = 0;
                studentAns[count] = "B";
                b.setShadowLayer(0,0,0,Color.WHITE);
                b.setTextColor(Color.WHITE);
                if(studentAns[count].compareToIgnoreCase(questions[count]) == 0)
                {
                    correct++;
                }
                count++;
                if(count >= number)//Max number of questions were answered, go to the results screen
                {
                    goToResults();
                }
                else
                {
                    question.setText(questions[count]);
                }
            }
        }
        else if(view.getId() == c.getId())
        {
            if(choice != 3 || !chosen)////////////////////////////////////////////////////////////////////////
            {
                choice = 3;
                c.setTextColor(Color.RED);
                c.setShadowLayer(5, 0, 0,Color.RED);//Make the
                toast.makeText(this, "Click C again to confirm answer",Toast.LENGTH_SHORT);
                toast.setGravity(Toastx|Toasty, 0,0);//Show toast in the top center of screen if in portrait.
                toast.show();
                chosen = true;
            }
            else if(chosen && choice == 3)
            {
                chosen = false;
                toast.cancel();//Cancel the toast if it is showing
                choice = 0;
                studentAns[count] = "C";
                c.setShadowLayer(0,0,0,Color.WHITE);
                c.setTextColor(Color.WHITE);
                if(studentAns[count].compareToIgnoreCase(questions[count]) == 0)
                {
                    correct++;
                }
                count++;
                if(count >= number)//Max number of questions were answered, go to the results screen
                {
                    goToResults();
                }
                else
                {
                    question.setText(questions[count]);
                }
            }
        }
        else if(view.getId() == d.getId())
        {
            if(choice != 4 || !chosen)
            {
                choice = 4;
                c.setTextColor(Color.RED);
                c.setShadowLayer(5, 0, 0,Color.RED);//Make the
                toast.makeText(this, "Click D again to confirm answer",Toast.LENGTH_SHORT);
                toast.setGravity(Toastx|Toasty, 0,0);//Show toast in the top center of screen if in portrait.
                toast.show();
                chosen = true;
            }
            else if(chosen && choice == 4)
            {
                chosen = false;
                toast.cancel();//Cancel the toast if it is showing
                choice = 0;
                studentAns[count] = "D";
                c.setShadowLayer(0,0,0,Color.WHITE);
                c.setTextColor(Color.WHITE);
                if(studentAns[count].compareToIgnoreCase(questions[count]) == 0)
                {
                    correct++;
                }
                count++;
                if(count >= number)//Max number of questions were answered, go to the results screen
                {
                    goToResults();
                }
                else
                {
                    question.setText(questions[count]);
                }
            }
        }
        else if(view.getId() == e.getId())
        {
            if(choice != 5 || !chosen)
            {
                choice = 5;
                c.setTextColor(Color.RED);
                c.setShadowLayer(5, 0, 0,Color.RED);//Make the
                toast.makeText(this, "Click E again to confirm answer",Toast.LENGTH_SHORT);
                toast.setGravity(Toastx|Toasty, 0,0);//Show toast in the top center of screen if in portrait.
                toast.show();
                chosen = true;
            }
            else if(choice ==5 && chosen)
            {
                chosen = false;
                toast.cancel();//Cancel the toast if it is showing
                choice = 0;
                studentAns[count] = "E";
                c.setShadowLayer(0,0,0,Color.WHITE);
                c.setTextColor(Color.WHITE);

                if(studentAns[count].compareToIgnoreCase(questions[count]) == 0)
                {
                    correct++;
                }
                count++;
                if(count >= number)//Max number of questions were answered, go to the results screen
                {
                    goToResults();
                }
                else
                {
                    question.setText(questions[count]);
                }
            }
        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {

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
            Toast.makeText(this, "Resumed", Toast.LENGTH_SHORT).show();//To change
        }
    }

    //To start the end of quiz results screen
    private void goToResults()
    {
        //Need to figure out how to send results to the server before going to the Results screen
        Intent send = new Intent(this, Results.class);
        send.putExtra(SCORE, Math.round(((float)correct)/number));
        startActivity(send);
    }
}
