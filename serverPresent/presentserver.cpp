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
    correctAnswer = 0;
    quizCounterRecv = 0;
    quizCounterSent = 0;
    qDebug() << "INSIDE OF PRESENT SERVER CONSTRUCTOR" + QString(correctAnswer) + question + answerA + answerB + answerC + answerD + answerE + answerSA;
//    fortunes << tr("You've been leading a dog's life. Stay off the furniture.")
//             << tr("You've got to think about tomorrow.")
//             << tr("You will be surprised by a loud noise.")
//             << tr("You will feel hungry again in another hour.")
//             << tr("You might have mail.")
//             << tr("You cannot kill time without injuring eternity.")
//             << tr("Computers are not intelligent. They only think they are.");
}

void PresentServer::setQuizSlot(int correctAnswer_in, QString question_in, QString answerA_in, QString answerB_in, QString answerC_in, QString answerD_in, QString answerE_in, QString answerSA_in){
    question = question_in;
    answerA = answerA_in;
    answerB = answerB_in;
    answerC = answerC_in;
    answerD = answerD_in;
    answerE = answerE_in;
    answerSA = answerSA_in;
    correctAnswer = correctAnswer_in;
    qDebug() << "INSIDE OF PRESENT SERVER SETQUIZSLOT" + QString(correctAnswer) + question + answerA + answerB + answerC + answerD + answerE + answerSA;
}

void PresentServer::incomingConnection(qintptr socketDescriptor)
{
    qDebug() << "INSIDE OF PRESENT SERVER incomingConnection" + QString(correctAnswer) + question + answerA + answerB + answerC + answerD + answerE + answerSA;
//    QString fortune = fortunes.at(qrand() % fortunes.size());
    QString combined = QString(correctAnswer) + question + answerA + answerB + answerC + answerD + answerE + answerSA;
//    PresentThread *thread = new PresentThread(socketDescriptor, fortune, this);
    PresentThread *thread = new PresentThread(socketDescriptor, combined, this);
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

