package com.example.ccox04.presentquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Results extends AppCompatActivity implements View.OnClickListener {

    TextView score;
    TextView fluff;
    Button end;
    ImageView image;
    int userScore, totalNumberQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Intent intent = this.getIntent();
        score = (TextView)findViewById(R.id.score);
        fluff = (TextView)findViewById(R.id.fluff);
        end = (Button)findViewById(R.id.restart);
        image = (ImageView)findViewById(R.id.pic);
/*        questionNum = intent.getStringExtra(MainActivity.NUMBERQUESTIONS);
        correct = intent.getStringExtra(MainActivity.CORRECT);
        int questions = Integer.parseInt(questionNum);
        if(questions > 0)
        {
            int percent = Math.round(100*(Float.parseFloat(correct)/questions));
            setImage(percent);
            fluff.setText("Multiple choice score of:");
            score.setText(percent+"%");
        }
        else
        {
            fluff.setText("Questions will be graded by the examiner");
            */
        Intent getMainIntent = getIntent();
        Bundle getMainBundle = getMainIntent.getExtras();
        if (getMainIntent.hasExtra(MainActivity.NUMBERQUESTIONS)) {
            totalNumberQuestions = Integer.parseInt(getMainBundle.getString(MainActivity.NUMBERQUESTIONS));
            userScore = Integer.parseInt(getMainBundle.getString(MainActivity.USERTOTALCORRECTANSWERS));
        }
        userScore = (userScore / totalNumberQuestions) * 100; // Multiply by 100 to get score equiv

        fluff.setText("Your Score was:");
            //fluff.setText("Your Score was:");
            //String s = intent.getStringExtra(MultipleChoice.SCORE);
        score.setText(String.valueOf(userScore) + "%");
            //setImage(s);

        setImage();

        end.setOnClickListener(this);
    }


    private void setImage()
    {
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
    public void onClick(View view) {
        // I think we just want to keep them on the results screen instead of taking them back to start the quiz again
//        if(view.getId() == end.getId())
//        {
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);//Go back to connect screen
//        }
    }
    //Add in save and restore instance states
}

