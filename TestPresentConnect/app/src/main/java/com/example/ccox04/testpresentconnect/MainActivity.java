package com.example.ccox04.testpresentconnect;

import android.app.Activity;
import android.net.nsd.NsdManager;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName(); // For Debugging

    Socket socket;
    ClientAsyncTask clientAsyncTask;
    Button connectBtn;
    EditText portEditText, ipEditText;
    String ipAddressString, portString;
    TextView quizTextView;

    String ms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectBtn = (Button) findViewById(R.id.connectButton);
        portEditText = (EditText) findViewById(R.id.portEditText);
        ipEditText = (EditText) findViewById(R.id.ipEditText);
        quizTextView = (TextView) findViewById(R.id.quizInfoTextView);

        clientAsyncTask = new ClientAsyncTask();
    }

    @Override
    protected void onDestroy() {
        if(clientAsyncTask != null && clientAsyncTask.getStatus() == AsyncTask.Status.RUNNING){
            clientAsyncTask.cancel(true);
            clientAsyncTask = null;
        }
        super.onDestroy();
    }

    public void onClickConnect(View view){
        ipAddressString = ipEditText.getText().toString();
        portString = portEditText.getText().toString();
        if(clientAsyncTask.getStatus() != AsyncTask.Status.RUNNING){
            //Log.d(TAG,"MainActivity : in If case 0");
            clientAsyncTask = new ClientAsyncTask();
            clientAsyncTask.execute();
        }
        // Killing async task and starting a new one if one was running
        else{
            //Log.d(TAG,"MainActivity : in Else case 0");
            //Thread.interrupted();
            clientAsyncTask.cancel(true);
            clientAsyncTask = null;
            clientAsyncTask = new ClientAsyncTask();
            clientAsyncTask.execute();
        }
        quizTextView.setText(ms);
    }

    private class ClientAsyncTask extends AsyncTask<Integer, Integer, Void>{

        @Override
        protected void onPostExecute(Void aVoid) {
            clientAsyncTask = new ClientAsyncTask();
            super.onPostExecute(aVoid);
        }


        @Override
        protected Void doInBackground(Integer... params) {
            Log.d(TAG,"PlayingActivity : Entered doInBackground");
            try{
                socket = new Socket(ipAddressString, Integer.parseInt(portString));
                InputStream inputStream = socket.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                ms = bufferedReader.readLine(); // This is the data that comes in from the server...This will populate the GUI using a handler
                DataOutputStream DOS = new DataOutputStream(socket.getOutputStream());
                DOS.writeUTF("HELLO_WORLD"); // This is where we write the answer back to the server. The server then logs all answers to a text file.
                socket.close();
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
            return null;
        }
    }

}
