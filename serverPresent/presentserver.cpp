#include "presentserver.h"
#include "presentthread.h"

PresentServer::PresentServer(QObject *parent)
    : QTcpServer(parent)

{
    question = "NA";
    answerA = "NA";
    answerB = "NA";
    answerC = "NA";
    answerD = "NA";
    answerE = "NA";
    answerSA = "NA";
    type = 0;
    correctAnswer = 0;
    quizCounterRecv = 0;
    quizCounterSent = 0;
    number_of_questions = 0;
    qDebug() << "INSIDE OF PRESENT SERVER CONSTRUCTOR" + QString(correctAnswer) + question + answerA + answerB + answerC + answerD + answerE + answerSA;
}

void PresentServer::addQuestionSlot(int correctAnswer_in, int type_in, QString question_in, QString answerA_in, QString answerB_in, QString answerC_in, QString answerD_in, QString answerE_in, QString answerSA_in){
    question = question_in;
    answerA = answerA_in;
    answerB = answerB_in;
    answerC = answerC_in;
    answerD = answerD_in;
    answerE = answerE_in;
    answerSA = answerSA_in;
    correctAnswer = correctAnswer_in;
    type = type_in;

    answerA_List.append(answerA_in);
    answerB_List.append(answerB_in);
    answerC_List.append(answerC_in);
    answerD_List.append(answerD_in);
    answerE_List.append(answerE_in);
    answerSA_List.append(answerSA_in);
    correctAnswerMC_List.append(correctAnswer_in);
    type_List.append(type_in);
    question_List.append(question_in);

    number_of_questions = number_of_questions + 1;
    qDebug() << "INSIDE OF PRESENT SERVER addQuestionSlot: Number of questions: " << number_of_questions << endl;
    qDebug() << "INSIDE OF PRESENT SERVER addQuestionSlot" + QString(correctAnswer) + type + question + answerA + answerB + answerC + answerD + answerE + answerSA;
}

void PresentServer::incomingConnection(qintptr socketDescriptor)
{
    qDebug() << "INSIDE OF PRESENT SERVER incomingConnection" + QString(correctAnswer) + type + question + answerA + answerB + answerC + answerD + answerE + answerSA;
    QJsonObject containerJsonObj;
    QJsonObject numberObj;

    numberObj["question_number"] = number_of_questions; // Number from GUI each time the professor adds a question
    containerJsonObj.insert("number",numberObj);
    qDebug() << "INSIDE OF PRESENT SERVER incomingConnection: Number of questions: " << number_of_questions << endl;
    for(int i = 0; i < number_of_questions; i++){
        QJsonObject quizObj;
        qDebug() << "INSIDE OF PRESENT SERVER incomingConnection: i: " << i << endl;
        if(type_List.at(i) == QUESTIONTYPE_MC){ // The question is multiple choice
        quizObj["question"] = question_List.at(i);
        quizObj["answerA"] = answerA_List.at(i);
        quizObj["answerB"] = answerB_List.at(i);
        quizObj["answerC"] = answerC_List.at(i);
        quizObj["answerD"] = answerD_List.at(i);
        quizObj["answerE"] = answerE_List.at(i);
        quizObj["correctAnswer"] = QString::number(correctAnswerMC_List.at(i));
        QString tempQuizNameMC = QUIZNAME_MC + QString::number(i);
        containerJsonObj.insert(tempQuizNameMC, quizObj); // this will be "quiz_mc" + question_number
        }
        if(type_List.at(i) == QUESTIONTYPE_SA){ // The question is short answer
            quizObj["question"] = question_List.at(i);
            quizObj["answer"] = answerSA_List.at(i);
            QString tempQuizNameSA = QUIZNAME_SA + QString::number(i);
            containerJsonObj.insert(tempQuizNameSA, quizObj); // this will be "quiz_sa" + question_number
        }
    }


    QJsonDocument containerJsonDoc(containerJsonObj);
    QString containerJsonString = containerJsonDoc.toJson().append(ENDMESSAGECHAR); // Making json a string and adding end message characters
    PresentThread *thread = new PresentThread(socketDescriptor, containerJsonString, this);

    connect(thread, SIGNAL(finished()), thread, SLOT(deleteLater()));
    connect(thread,SIGNAL(updateQuizRecvCounterSignal(int)), this, SLOT(updateQuizRecvCounterSlot(int)));
    connect(thread, SIGNAL(updateQuizSentCounterSignal(int)), this, SLOT(updateQuizSentCounterSlot(int)));
    thread->start();
}

// This is to update the received quiz counter on the professors GUI
void PresentServer::updateQuizRecvCounterSlot(int count_in){
    quizCounterRecv = quizCounterRecv + count_in;
    emit updateQuizRecvCounterSignal(quizCounterRecv);
}

// This is to update the sent quiz counter on the professors GUI
void PresentServer::updateQuizSentCounterSlot(int count_in){
    quizCounterSent = quizCounterSent + count_in;
    emit updateQuizSentCounterSignal(quizCounterSent);
}

