package com.example.ccox04.presentquiz;

import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by StepDadddy on 4/29/2017.
 */

public class QuizInfo {
    String serverIP, userID, question_mc, question_sa, answerA, answerB, answerC, answerD, answerE, answerSA, userAnswerMC, userAnswerSA;
    ArrayList<Integer> question_type_list = new ArrayList<>();  // 0 = Multiple Choice;  1 = Short Answer
    ArrayList<String> questions_list = new ArrayList<>();
    ArrayList<String> answerA_list = new ArrayList<>();
    ArrayList<String> answerB_list = new ArrayList<>();
    ArrayList<String> answerC_list = new ArrayList<>();
    ArrayList<String> answerD_list = new ArrayList<>();
    ArrayList<String> answerE_list = new ArrayList<>();
    ArrayList<Integer> correctAnswer_mc_list = new ArrayList<>();  // 1 = answerA,  2 = answerB,  3 = answerC,  4 = answerD,  5 = answerE
    ArrayList<String> correctAnswerString_mc_list = new ArrayList<>();  // 1 = answerA,  2 = answerB,  3 = answerC,  4 = answerD,  5 = answerE
    ArrayList<String> answerSA_list = new ArrayList<>();
    ArrayList<String> userAnswers_list = new ArrayList<>();
    int serverPort, number_questions, question_type, correctAnswer_mc;

    public QuizInfo(){
        serverIP = "0.0.0.0";
        serverPort = 11111;
        number_questions = 1;
    }

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public ArrayList<String> getAnswerA_list() {
        return answerA_list;
    }

    public void setAnswerA_list(ArrayList<String> answerA_list) {
        this.answerA_list = answerA_list;
    }

    public ArrayList<String> getAnswerB_list() {
        return answerB_list;
    }

    public void setAnswerB_list(ArrayList<String> answerB_list) {
        this.answerB_list = answerB_list;
    }

    public ArrayList<String> getAnswerC_list() {
        return answerC_list;
    }

    public void setAnswerC_list(ArrayList<String> answerC_list) {
        this.answerC_list = answerC_list;
    }

    public ArrayList<String> getAnswerD_list() {
        return answerD_list;
    }

    public void setAnswerD_list(ArrayList<String> answerD_list) {
        this.answerD_list = answerD_list;
    }

    public ArrayList<String> getAnswerE_list() {
        return answerE_list;
    }

    public void setAnswerE_list(ArrayList<String> answerE_list) {
        this.answerE_list = answerE_list;
    }

    public ArrayList<String> getAnswerSA_list() {
        return answerSA_list;
    }

    public void setAnswerSA_list(ArrayList<String> answerSA_list) {
        this.answerSA_list = answerSA_list;
    }

    public ArrayList<Integer> getCorrectAnswer_mc_list() {
        return correctAnswer_mc_list;
    }

    public void setCorrectAnswer_mc_list(ArrayList<Integer> correctAnswer_mc_list) {
        this.correctAnswer_mc_list = correctAnswer_mc_list;
    }

    public ArrayList<String> getQuestions_list() {
        return questions_list;
    }

    public void setQuestions_list(ArrayList<String> questions_list) {
        this.questions_list = questions_list;
    }

    public ArrayList<String> getUserAnswers_list() {
        return userAnswers_list;
    }

    public void setUserAnswers_list(ArrayList<String> userAnswers_list) {
        this.userAnswers_list = userAnswers_list;
    }

    public int getNumber_questions() {
        return number_questions;
    }

    public void setNumber_questions(int number_questions) {
        this.number_questions = number_questions;
    }

    public ArrayList<Integer> getQuestion_type_list() {
        return question_type_list;
    }

    public void setQuestion_type_list(ArrayList<Integer> question_type_list) {
        this.question_type_list = question_type_list;
    }

    public String getAnswerA() {
        return answerA;
    }

    public void setAnswerA(String answerA) {
        answerA_list.add(answerA);
        this.answerA = answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public void setAnswerB(String answerB) {
        answerB_list.add(answerB);
        this.answerB = answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public void setAnswerC(String answerC) {
        answerC_list.add(answerC);
        this.answerC = answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public void setAnswerD(String answerD) {
        answerD_list.add(answerD);
        this.answerD = answerD;
    }

    public String getAnswerE() {
        return answerE;
    }

    public void setAnswerE(String answerE) {
        answerE_list.add(answerE);
        this.answerE = answerE;
    }

    public String getAnswerSA() {
        return answerSA;
    }

    public void setAnswerSA(String answerSA) {
        answerSA_list.add(answerSA);
        this.answerSA = answerSA;
    }

    public int getCorrectAnswer_mc() {
        return correctAnswer_mc;
    }

    public void setCorrectAnswer_mc(String correctAnswer_mc) {
        correctAnswer_mc_list.add(Integer.parseInt(correctAnswer_mc));
        switch (correctAnswer_mc){
            case "1":
                correctAnswerString_mc_list.add("A");
                break;
            case "2":
                correctAnswerString_mc_list.add("B");
                break;
            case "3":
                correctAnswerString_mc_list.add("C");
                break;
            case "4":
                correctAnswerString_mc_list.add("D");
                break;
            case "5":
                correctAnswerString_mc_list.add("E");
                break;
            default:
                correctAnswerString_mc_list.add("NA");
                break;
        }
        this.correctAnswer_mc = Integer.parseInt(correctAnswer_mc);
    }

    public String getQuestion_mc() {
        return question_mc;
    }

    public void setQuestion_mc(String question_mc) {
        questions_list.add(question_mc);
        this.question_mc = question_mc;
    }

    public String getQuestion_sa() {
        return question_sa;
    }

    public void setQuestion_sa(String question_sa) {
        questions_list.add(question_sa);
        this.question_sa = question_sa;
    }

    public int getQuestion_type() {
        return question_type;
    }

    public void setQuestion_type(int question_type) {
        question_type_list.add(question_type);
        this.question_type = question_type;
    }


    public String getUserAnswerSA() {
        return userAnswerSA;
    }

    public void setUserAnswerSA(String userAnswerSA) {
        userAnswers_list.add(userAnswerSA);
        this.userAnswerSA = userAnswerSA;
    }

    public ArrayList<String> getCorrectAnswerString_mc_list() {
        return correctAnswerString_mc_list;
    }

    public void setCorrectAnswerString_mc_list(ArrayList<String> correctAnswerString_mc_list) {
        this.correctAnswerString_mc_list = correctAnswerString_mc_list;
    }

    public String getUserAnswerMC() {
        return userAnswerMC;
    }

    public void setUserAnswerMC(String userAnswerMC) {
        userAnswers_list.add(userAnswerMC);
        this.userAnswerMC = userAnswerMC;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
