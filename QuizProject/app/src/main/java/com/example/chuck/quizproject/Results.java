package com.example.chuck.quizproject;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by chuck on 4/30/2017.
 */

public class Results extends AppCompatActivity implements View.OnClickListener {

    TextView score;
    TextView fluff;
    Button end;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);
        Intent intent = this.getIntent();
        score = (TextView)findViewById(R.id.score);
        fluff = (TextView)findViewById(R.id.fluff);
        end = (Button)findViewById(R.id.restart);
        image = (ImageView)findViewById(R.id.pic);
        if(QRScreen.branch == 1)//multiple choice
        {
            fluff.setText("Your Score was:");
            String s = intent.getStringExtra(MultipleChoice.SCORE);
            score.setText(s+"%");
            setImage(s);
        }
        else//short answer
        {
            image.setVisibility(View.INVISIBLE);
            fluff.setText("Your answer was sent for grading");
        }
        end.setOnClickListener(this);
    }

    private void setImage(String s)
    {
        int a = Integer.parseInt(s);
        if(a == 100)
        {
            image.setImageResource(R.drawable.amazing);
        }
        else if(a >= 80)
        {
            image.setImageResource(R.drawable.good);
        }
        else if(a >= 60)
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
        if(view.getId() == end.getId())
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);//Go back to connect screen
        }
    }
    //Add in save and restore instance states
}
