package com.example.ccox04.presentquiz;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.net.nsd.NsdManager;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
* File: MainActivity.java
*
*/

public class MainActivity extends AppCompatActivity implements View.OnFocusChangeListener {
    private static final String TAG = MainActivity.class.getSimpleName(); // For Debugging

    Socket socket;
    ClientInAsyncTask clientInAsyncTask;
    ClientOutAsyncTask clientOutAsyncTask;
    EditText userIDEditText;
    Button scanImageBtn;
    String ipAddressString="";
    int portInt;
    QuizInfo quizInfo;
    JSONObject jsonReader;
    Intent getMultipleChoiceActivityIntent;
    Intent getShortAnswerActivityIntent;
    Intent sendResultActivityIntent;
    private boolean canCLick = false;
    final int intentResult = 1;
    public static final String ENDMESSAGECHAR = "###";
    public static final String USERIDSTRING = "userIDString";
    public static final String QUESTION_MC = "question_mc";
    public static final String ANSWERA = "answerA";
    public static final String ANSWERB = "answerB";
    public static final String ANSWERC = "answerC";
    public static final String ANSWERD = "answerD";
    public static final String ANSWERE = "answerE";
    public static final String CORRECTANSWER_MC = "correctAnswer_mc";
    public static final String USERANSWER_MC = "userAnswer_mc";
    public static final String QUESTION_SA = "question_sa";
    public static final String ANSWER_SA = "answer_sa";
    public static final String USERANSWER_SA = "userAnswer_sa";
    public static final String QUESTIONTYPE_LIST = "questionType_list";
    public static final String QUESTIONS_LIST = "questions_list";
    public static final String ANSWERALIST = "answerAlist";
    public static final String ANSWERBLIST = "answerBlist";
    public static final String ANSWERCLIST = "answerClist";
    public static final String ANSWERDLIST = "answerDlist";
    public static final String ANSWERELIST = "answerElist";
    public static final String CORRECTANSWER_MCLIST = "correctAnswer_mclist";
    public static final String USERANSWERS_LIST = "userAnswers_list";
    public static final String ANSWER_SALIST = "answer_salist";
    public static final String SERVERIP = "serverIP";
    public static final String SERVERPORT = "serverPort";
    public static final String NUMBERQUESTIONS = "numberQuestions";
    public static final String CURRENTQUESTIONCOUNTER = "currentQuestionCounter";
    public static final String CLIENTIN_STATUS = "clientIn_status";
    public static final String CLIENTOUT_STATUS = "clientOut_status";
    public static final String CORRECTANSWERSTRING_MCLIST = "correctAnswerString_mclist";
    public static final String USERTOTALCORRECTANSWERS = "userTotalCorrectAnswers";
    public static final String USERSCORE = "userScore";
    Bundle quizBundle, resultBundle;
    int number_questions, questionCounter;
    String CurrentPhotoPath;
    public static final int REQUEST_IMAGE = 4;
    Uri uri;
    BarcodeDetector detect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userIDEditText = (EditText) findViewById(R.id.userIDEditTextMain);
        scanImageBtn = (Button) findViewById(R.id.scanImageButtonMain);
        userIDEditText.setOnFocusChangeListener(this);
        scanImageBtn.setClickable(false);
        number_questions = 1;
        questionCounter = 0;
        getMultipleChoiceActivityIntent = new Intent(this, MultipleChoiceActivity.class);
        getShortAnswerActivityIntent = new Intent(this, ShortAnswerActivity.class);
        sendResultActivityIntent = new Intent(this, Results.class);
        quizBundle = new Bundle();
        resultBundle = new Bundle();
        quizInfo = new QuizInfo();
        detect = new BarcodeDetector.Builder(getApplicationContext())
                .setBarcodeFormats(Barcode.DATA_MATRIX|Barcode.QR_CODE)
                .build();
        clientInAsyncTask = new ClientInAsyncTask();
        clientOutAsyncTask = new ClientOutAsyncTask();
    }

//    This is used to handle the data when the application is brought back to the forefront of the users activity cycle
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        boolean isClientInRunning = savedInstanceState.getBoolean(CLIENTIN_STATUS);
        boolean isClientOutRunning = savedInstanceState.getBoolean(CLIENTOUT_STATUS);
        if (isClientInRunning) {
            clientInAsyncTask = new ClientInAsyncTask();
            clientInAsyncTask.execute();
        }
        if (isClientOutRunning) {
            clientOutAsyncTask = new ClientOutAsyncTask();
            clientOutAsyncTask.execute();
        }
        quizInfo.setQuestions_list(savedInstanceState.getStringArrayList(QUESTIONS_LIST));
        quizInfo.setAnswerA_list(savedInstanceState.getStringArrayList(ANSWERALIST));
        quizInfo.setAnswerB_list(savedInstanceState.getStringArrayList(ANSWERBLIST));
        quizInfo.setAnswerC_list(savedInstanceState.getStringArrayList(ANSWERCLIST));
        quizInfo.setAnswerD_list(savedInstanceState.getStringArrayList(ANSWERDLIST));
        quizInfo.setAnswerE_list(savedInstanceState.getStringArrayList(ANSWERELIST));
        quizInfo.setCorrectAnswer_mc_list(savedInstanceState.getIntegerArrayList(CORRECTANSWER_MCLIST));
        quizInfo.setCorrectAnswerString_mc_list(savedInstanceState.getStringArrayList(CORRECTANSWERSTRING_MCLIST));
        quizInfo.setAnswerSA_list(savedInstanceState.getStringArrayList(ANSWER_SALIST));
        quizInfo.setUserAnswers_list(savedInstanceState.getStringArrayList(USERANSWERS_LIST));
        quizInfo.setQuestion_type_list(savedInstanceState.getIntegerArrayList(QUESTIONTYPE_LIST));
        quizInfo.setServerIP(savedInstanceState.getString(SERVERIP));
        quizInfo.setUserID(savedInstanceState.getString(USERIDSTRING));
        ipAddressString = savedInstanceState.getString(SERVERIP);
        quizInfo.setServerPort(savedInstanceState.getInt(SERVERPORT));
        portInt = savedInstanceState.getInt(SERVERPORT);
        number_questions = savedInstanceState.getInt(NUMBERQUESTIONS);
        questionCounter = savedInstanceState.getInt(CURRENTQUESTIONCOUNTER);
        super.onRestoreInstanceState(savedInstanceState);
    }

//    This is used to save all the data when the user places this activity to the back of its lifecycle
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (clientInAsyncTask != null && clientInAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            outState.putBoolean(CLIENTIN_STATUS, true);
        } else {
            outState.putBoolean(CLIENTIN_STATUS, false);
        }
        if (clientOutAsyncTask != null && clientOutAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            outState.putBoolean(CLIENTOUT_STATUS, true);
        } else {
            outState.putBoolean(CLIENTOUT_STATUS, false);
        }
        outState.putStringArrayList(QUESTIONS_LIST, quizInfo.getQuestions_list());
        outState.putStringArrayList(ANSWERALIST, quizInfo.getAnswerA_list());
        outState.putStringArrayList(ANSWERBLIST, quizInfo.getAnswerB_list());
        outState.putStringArrayList(ANSWERCLIST, quizInfo.getAnswerC_list());
        outState.putStringArrayList(ANSWERDLIST, quizInfo.getAnswerD_list());
        outState.putStringArrayList(ANSWERELIST, quizInfo.getAnswerE_list());
        outState.putIntegerArrayList(CORRECTANSWER_MCLIST, quizInfo.getCorrectAnswer_mc_list());
        outState.putStringArrayList(CORRECTANSWERSTRING_MCLIST, quizInfo.getCorrectAnswerString_mc_list());
        outState.putStringArrayList(ANSWER_SALIST, quizInfo.getAnswerSA_list());
        outState.putStringArrayList(USERANSWERS_LIST, quizInfo.getUserAnswers_list());
        outState.putIntegerArrayList(QUESTIONTYPE_LIST, quizInfo.getQuestion_type_list());
        outState.putString(SERVERIP, ipAddressString);
        outState.putInt(SERVERPORT, portInt);
        outState.putInt(NUMBERQUESTIONS, number_questions);
        outState.putInt(CURRENTQUESTIONCOUNTER, questionCounter);
        outState.putString(USERIDSTRING, quizInfo.getUserID());
        super.onSaveInstanceState(outState);
    }
    Bitmap bitmap;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == this.REQUEST_IMAGE && resultCode == RESULT_OK)//The result is for the scanning image
        {
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            mediaScanIntent.setData(uri);
            this.sendBroadcast(mediaScanIntent);
            int targetW = Resources.getSystem().getDisplayMetrics().widthPixels;
            int targetH = Resources.getSystem().getDisplayMetrics().heightPixels;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(CurrentPhotoPath, bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH= bmOptions.outHeight;
            int scale = Math.min(photoW/targetW, photoH/targetH);
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scale;
            bmOptions.inPurgeable = true;
            bitmap = BitmapFactory.decodeFile(CurrentPhotoPath, bmOptions);//code is in a bitmap
            Matrix matrix = new Matrix();
            matrix.postRotate(270);//Change to 90 for Chris
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap,600,600,true);//Change to 800, 800 for Chris front camera
            Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap , 0, 0, scaledBitmap .getWidth(), scaledBitmap .getHeight(), matrix, true);
            Frame frame = new Frame.Builder().setBitmap(rotatedBitmap).build();
            SparseArray<Barcode> bars = detect.detect(frame);
            if (!detect.isOperational() || bars.size() == 0) {
                Toast.makeText(this, "Retake image. No code found",Toast.LENGTH_SHORT).show();
            }
            else {
                Barcode server = bars.valueAt(0);
                String str = server.displayValue;
                String[] s = str.split("|");//Not working correctly, but it will do
                String processedString = "";
                boolean starter = false;
                boolean porter = false;
                for(int i = 0; i < s.length;i++) {
                    if(!starter) {
                        if(s[i].compareTo(".") == 0)
                        {
                            starter = true;
                        }
                    }
                    else {
                        if(s[i].compareTo("|") == 0) {
                            porter = true;
                        }
                        else if(!porter) {
                            ipAddressString = ipAddressString + s[i];
                        }
                        else {
                            processedString = processedString + s[i];
                        }
                    }
                }
                portInt = Integer.parseInt(processedString);
                onConnect();
            }

        }
//        This checks to see if a question was answered and determines if another question needs to be answered.  Ultimatley
//        sends the data out to the server through ClientOutAsnycTask
        else if(resultCode != 0){
            questionCounter++;
            if (data.hasExtra("MCQuestionCompleted")) {
                quizInfo.setUserAnswerMC(data.getStringExtra("MCQuestionCompleted"));
                checkNumberQuestions();
            } else if (data.hasExtra("SAQuestionCompleted")) {
                quizInfo.setUserAnswerSA(data.getStringExtra("SAQuestionCompleted"));
                checkNumberQuestions();
            }
            if (questionCounter == number_questions) {
                scanImageBtn.setEnabled(false);
                if (clientInAsyncTask != null && clientInAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
                    clientInAsyncTask.cancel(true);
                    clientInAsyncTask = null;
                }
                if (clientOutAsyncTask.getStatus() != AsyncTask.Status.RUNNING) {
                    clientOutAsyncTask = new ClientOutAsyncTask();
                    clientOutAsyncTask.execute();
                }
                // Killing async task and starting a new one if one was running
                else {
                    clientOutAsyncTask.cancel(true);
                    clientOutAsyncTask = null;
                    clientOutAsyncTask = new ClientOutAsyncTask();
                    clientOutAsyncTask.execute();
                }
            }
        }
    }

//    This checks to see how many if any questions are left to be answered by the student
    private void checkNumberQuestions(){
        if(questionCounter < number_questions){
            // Send out intent to ask another question
            // This opens up multiple choice question
            if(Objects.equals(quizInfo.getQuestion_type_list().get(questionCounter), 0)) {
                Intent getMultipleChoiceIntent = new Intent(this, MultipleChoiceActivity.class);
                Bundle quizMCBundle = new Bundle();
                quizMCBundle.putString(ANSWERA, quizInfo.getAnswerA_list().get(questionCounter));
                quizMCBundle.putString(ANSWERB, quizInfo.getAnswerB_list().get(questionCounter));
                quizMCBundle.putString(ANSWERC, quizInfo.getAnswerC_list().get(questionCounter));
                quizMCBundle.putString(ANSWERD, quizInfo.getAnswerD_list().get(questionCounter));
                quizMCBundle.putString(ANSWERE, quizInfo.getAnswerE_list().get(questionCounter));
                quizMCBundle.putInt(CORRECTANSWER_MC, quizInfo.getCorrectAnswer_mc_list().get(questionCounter));
                quizMCBundle.putString(QUESTION_MC, quizInfo.getQuestions_list().get(questionCounter));
                getMultipleChoiceIntent.putExtras(quizMCBundle);
                startActivityForResult(getMultipleChoiceIntent, intentResult);
            }
            // This opens up short answer question
            else if(Objects.equals(quizInfo.getQuestion_type_list().get(questionCounter), 1)) {
                Intent getShortAnswerIntent = new Intent(this, ShortAnswerActivity.class);
                Bundle quizSABundle = new Bundle();
                quizSABundle.putString(QUESTION_SA, quizInfo.getQuestions_list().get(questionCounter));
                quizSABundle.putString(ANSWER_SA, quizInfo.getAnswerSA_list().get(questionCounter));
                getShortAnswerIntent.putExtras(quizSABundle);
                startActivityForResult(getShortAnswerIntent, intentResult);
            }
        }
        Log.d(TAG,"MainActivity : Leaving checkNumberQuestions");
    }

    @Override
    protected void onDestroy() {
        if(clientInAsyncTask != null && clientInAsyncTask.getStatus() == AsyncTask.Status.RUNNING){
            clientInAsyncTask.cancel(true);
            clientInAsyncTask = null;
        }
        if(clientOutAsyncTask != null && clientOutAsyncTask.getStatus() == AsyncTask.Status.RUNNING){
            clientOutAsyncTask.cancel(true);
            clientOutAsyncTask = null;
        }
        super.onDestroy();
    }


    public void onConnect(){
        if (clientInAsyncTask.getStatus() != AsyncTask.Status.RUNNING) {
            clientInAsyncTask = new ClientInAsyncTask();
            clientInAsyncTask.execute();
        }
        // Killing async task and starting a new one if one was running
        else {
            clientInAsyncTask.cancel(true);
            clientInAsyncTask = null;
            clientInAsyncTask = new ClientInAsyncTask();
            clientInAsyncTask.execute();
        }
    }

//    This is used to tell if the user clicked the button
    public void onScan(View view)
    {
        if(view.getId() == this.scanImageBtn.getId() && canCLick &&(this.userIDEditText.getText().toString().length() != 0)) {
            quizInfo.setUserID(this.userIDEditText.getText().toString());
            takepic();
        }
    }

//    This is whats used to take the image
    private void takepic()
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.file",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                uri = photoURI;
                startActivityForResult(takePictureIntent, REQUEST_IMAGE);
            }
        }
    }

//    This is used to create the file to store the image to decode
    private File createFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        CurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(Objects.equals(v.getId(), userIDEditText.getId())){
            scanImageBtn.setClickable(true);
            canCLick = true;
        }
    }

//    This AsyncTask is to send out the data to the server containing all of the quiz info along with the students PID.
//    The client disconnects from the server once the message is sent and received by the server
    private class ClientOutAsyncTask extends AsyncTask<Integer, Integer, Void>{
        @Override
        protected void onPostExecute(Void aVoid) {
            // Checking how many answers were correct
            int userCorrectTotal = 0;
            // This is to determine how many questions the user had correct
            for(int i = 0; i < number_questions; i++){
                int correctAnswerMC = quizInfo.getCorrectAnswer_mc_list().get(i);
                switch (correctAnswerMC){
                    case 1:
                        if(Objects.equals(quizInfo.getAnswerA_list().get(i), quizInfo.getUserAnswers_list().get(i))){
                            Log.d(TAG,"onPostExecute IN IF switch case 1");
                            userCorrectTotal++;
                        }
                        break;
                    case 2:
                        if(Objects.equals(quizInfo.getAnswerB_list().get(i), quizInfo.getUserAnswers_list().get(i))){
                            Log.d(TAG,"onPostExecute IN IF switch case 2");
                            userCorrectTotal++;
                        }
                        break;
                    case 3:
                        if(Objects.equals(quizInfo.getAnswerC_list().get(i), quizInfo.getUserAnswers_list().get(i))){
                            Log.d(TAG,"onPostExecute IN IF switch case 3");
                            userCorrectTotal++;
                        }
                        break;
                    case 4:
                        if(Objects.equals(quizInfo.getAnswerD_list().get(i), quizInfo.getUserAnswers_list().get(i))){
                            Log.d(TAG,"onPostExecute IN IF switch case 4");
                            userCorrectTotal++;
                        }
                        break;
                    case 5:
                        if(Objects.equals(quizInfo.getAnswerE_list().get(i), quizInfo.getUserAnswers_list().get(i))){
                            Log.d(TAG,"onPostExecute IN IF switch case 5");
                            userCorrectTotal++;
                        }
                        break;
                    default:
                        break;
                }
            }
//            This is to count how many of the questions were multiple choice to calculate the immediate score in the results screen
            int multicounter = 0;
            for(int i : quizInfo.getQuestion_type_list()) {
                if (i == 0) {
                    multicounter++;
                }
            }
//            Sending the bundle to the Results screen to display how the student did
            resultBundle.putString(NUMBERQUESTIONS, String.valueOf(multicounter));
            resultBundle.putString(USERTOTALCORRECTANSWERS, String.valueOf(userCorrectTotal));
            sendResultActivityIntent.putExtras(resultBundle);
            startActivity(sendResultActivityIntent);
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Integer... params) {
            Log.d(TAG,"PlayingActivity : Entered doInBackground");
            try{
                socket = new Socket(ipAddressString, portInt);
                String sendQuizAnswerString = "Student Identifier: " + quizInfo.getUserID() + "|";
                String userAnswerString = "Student Answered: ";
                String correctAnswerString = "Correct Answer: ";
                DataOutputStream DOS = new DataOutputStream(socket.getOutputStream());
                for(int i = 0; i < number_questions; i++) {
                    // Checking if its a multiple choice quiz
                    if (Objects.equals(0, quizInfo.getQuestion_type_list().get(i))) { // 0 = Multiple Choice
                        // Setting question and answers for Multiple choice
                        sendQuizAnswerString = sendQuizAnswerString + String.valueOf(i+1) + ". ";
                        sendQuizAnswerString =  sendQuizAnswerString + quizInfo.getQuestions_list().get(i) + "|";
                        sendQuizAnswerString =  sendQuizAnswerString + quizInfo.getAnswerA_list().get(i) + "|";
                        sendQuizAnswerString =  sendQuizAnswerString + quizInfo.getAnswerB_list().get(i) + "|";
                        sendQuizAnswerString =  sendQuizAnswerString + quizInfo.getAnswerC_list().get(i) + "|";
                        sendQuizAnswerString =  sendQuizAnswerString + quizInfo.getAnswerD_list().get(i) + "|";
                        sendQuizAnswerString =  sendQuizAnswerString + quizInfo.getAnswerE_list().get(i) + "|";
                        sendQuizAnswerString =  sendQuizAnswerString + correctAnswerString + quizInfo.getCorrectAnswerString_mc_list().get(i) + "|";
                        sendQuizAnswerString =  sendQuizAnswerString + userAnswerString + quizInfo.getUserAnswers_list().get(i) + "|";
                        byte[] quizBuffer = sendQuizAnswerString.getBytes("UTF-8");
                        DOS.write(quizBuffer, 0, quizBuffer.length); // This sends the message back to the server conataining the students answers
                        sendQuizAnswerString = "";
                    }
                    // Checking if its a short answer quiz
                    else if (Objects.equals(1, quizInfo.getQuestion_type_list().get(i))) { // 1 = Short Answer
                        // Setting question and answers for Short answer quiz
                        sendQuizAnswerString = sendQuizAnswerString + String.valueOf(i+1) + ". ";
                        sendQuizAnswerString =  sendQuizAnswerString + quizInfo.getQuestions_list().get(i) + "|";
                        sendQuizAnswerString =  sendQuizAnswerString + correctAnswerString + quizInfo.getAnswerSA_list().get(i) + "|";
                        sendQuizAnswerString =  sendQuizAnswerString + userAnswerString + quizInfo.getUserAnswers_list().get(i) + "|";
                        byte[] buf = sendQuizAnswerString.getBytes("UTF-8");
                        DOS.write(buf, 0, buf.length); // This sends the message back to the server conataining the students answers
                        sendQuizAnswerString = "";
                    }
                }
                socket.close();
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
            return null;
        }
    }

//    This AsyncTask is used to read in the data from the server and then disconnects itself. This is to keep the server from doing unecessary work
    private class ClientInAsyncTask extends AsyncTask<Integer, Integer, Void>{

        @Override
        protected void onPostExecute(Void aVoid) {
            // This opens up multiple choice question
            if(Objects.equals(quizInfo.getQuestion_type_list().get(0), 0)) {
                quizBundle.putString(ANSWERA, quizInfo.getAnswerA_list().get(0));
                quizBundle.putString(ANSWERB, quizInfo.getAnswerB_list().get(0));
                quizBundle.putString(ANSWERC, quizInfo.getAnswerC_list().get(0));
                quizBundle.putString(ANSWERD, quizInfo.getAnswerD_list().get(0));
                quizBundle.putString(ANSWERE, quizInfo.getAnswerE_list().get(0));
                quizBundle.putInt(CORRECTANSWER_MC, quizInfo.getCorrectAnswer_mc_list().get(0));
                quizBundle.putString(QUESTION_MC, quizInfo.getQuestions_list().get(0));
                getMultipleChoiceActivityIntent.putExtras(quizBundle);
                startActivityForResult(getMultipleChoiceActivityIntent, intentResult);
            }
            // This opens up short answer question
            if(Objects.equals(quizInfo.getQuestion_type_list().get(0), 1)) {
                quizBundle.putString(QUESTION_SA, quizInfo.getQuestions_list().get(0));
                quizBundle.putString(ANSWER_SA, quizInfo.getAnswerSA_list().get(0));
                getShortAnswerActivityIntent.putExtras(quizBundle);
                startActivityForResult(getShortAnswerActivityIntent, intentResult);
            }
            super.onPostExecute(aVoid);
        }


        @Override
        protected Void doInBackground(Integer... params) {
            try{
                Log.d(TAG, "IP = " + ipAddressString);
                Log.d(TAG, "PORT = " + portInt);
                socket = new Socket(ipAddressString, portInt);
                quizInfo.setServerIP(ipAddressString); // Saving the serverip and port to send data out later
                quizInfo.setServerPort(portInt);
                InputStream inputStream = socket.getInputStream();
                String response = convertStreamToString(inputStream);
                jsonReader = new JSONObject(response);
                // Checking how many questions are in this quiz
                JSONObject question_number = jsonReader.getJSONObject("number");
                quizInfo.setNumber_questions(Integer.parseInt(question_number.getString("question_number")));
                number_questions = quizInfo.getNumber_questions();
                for(int i = 0; i < number_questions; i++) {
                    // Checking if its a multiple choice quiz
                    if (jsonReader.has("quiz_mc" + i)) {
                        // Setting question and answers for Multiple choice
                        JSONObject quiz_mc = jsonReader.getJSONObject("quiz_mc" + i);
                        quizInfo.setQuestion_type(0); // 0 = Multiple Choice
                        quizInfo.setQuestion_mc(quiz_mc.getString("question"));
                        quizInfo.setAnswerA(quiz_mc.getString("answerA"));
                        quizInfo.setAnswerB(quiz_mc.getString("answerB"));
                        quizInfo.setAnswerC(quiz_mc.getString("answerC"));
                        quizInfo.setAnswerD(quiz_mc.getString("answerD"));
                        quizInfo.setAnswerE(quiz_mc.getString("answerE"));
                        quizInfo.setCorrectAnswer_mc(quiz_mc.getString("correctAnswer"));
                        // Need to add dummy values for counting purposes
                        quizInfo.setAnswerSA("NA");
                    }

                    // Checking if its a short answer quiz
                    if (jsonReader.has("quiz_sa" + i)) {
                        // Setting question and answers for Short answer quiz
                        JSONObject quiz_sa = jsonReader.getJSONObject("quiz_sa" + i);
                        quizInfo.setQuestion_type(1); // 1 = Short Answer
                        quizInfo.setQuestion_sa(quiz_sa.getString("question"));
                        quizInfo.setAnswerSA(quiz_sa.getString("answer"));
                        // Need to add dummy values for counting purposes
                        quizInfo.setAnswerA("NA");
                        quizInfo.setAnswerB("NA");
                        quizInfo.setAnswerC("NA");
                        quizInfo.setAnswerD("NA");
                        quizInfo.setAnswerE("NA");
                        quizInfo.setCorrectAnswer_mc("0");
                    }
                }
                socket.close();
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
            return null;
        }

//        This function converts the instream to a string so it can be manipulated and stored into QuizInfo
        private String convertStreamToString(InputStream inputStream) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream , StandardCharsets.UTF_8)); // Designating information is coming in using UTF_8
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            try {
                bufferedReader.readLine(); // Without this line the first line of the JSON is trash due to bit conversion between
                stringBuilder.append("{"); // Starting the string builder with a curly brace to rebuild the first line of JSON
                while (!Objects.equals(line = bufferedReader.readLine(), ENDMESSAGECHAR)){ // Reading until the message reaches the designated packet end message sequence; ###
                    stringBuilder.append(line).append('\n');
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }
    }

    public void showShrtToast(String toastString){
        Toast.makeText(this, toastString, Toast.LENGTH_SHORT).show();
    }

    public void showLongToast(String toastString){
        Toast.makeText(this, toastString, Toast.LENGTH_LONG).show();
    }

}
