#include "presentcontainer.h"

PresentContainer::PresentContainer(QObject *parent) :
    QObject(parent)
{
    // Connect slots and signals
    // Init variables
    presentServer = new PresentServer();
    connect(this, SIGNAL(addQuestionSignalPC(int, int, QString,QString,QString,QString,QString,QString,QString)), presentServer, SLOT(addQuestionSlot(int, int,QString,QString,QString,QString,QString,QString,QString)));
    connect(presentServer, SIGNAL(updateQuizRecvCounterSignal(int)), this, SLOT(updateQuizRecvCounterSlot(int)));
    connect(presentServer, SIGNAL(updateQuizSentCounterSignal(int)), this, SLOT(updateQuizSentCounterSlot(int)));
}

PresentContainer::~PresentContainer(){
    // clear variables
    // kill server
    delete presentServer;
}

// This adds another question to the quiz
void PresentContainer::addQuestionSlotPC(int correctAnswer, int type, QString question, QString answerA, QString answerB, QString answerC, QString answerD, QString answerE, QString answerSA){
    emit addQuestionSignalPC(correctAnswer, type, question, answerA, answerB, answerC, answerD, answerE, answerSA);
}

void PresentContainer::startServerSlotPC(){
    if (!presentServer->listen()) {
        QMessageBox Msgbox;
        Msgbox.setText("ERROR: Server is NOT listening");
        Msgbox.exec();
        return;
    }
    QString ipAddress;
    QList<QHostAddress> ipAddressesList = QNetworkInterface::allAddresses();
    // use the first non-localhost IPv4 address
    for (int i = 0; i < ipAddressesList.size(); ++i) {
        if (ipAddressesList.at(i) != QHostAddress::LocalHost &&
            ipAddressesList.at(i).toIPv4Address()) {
            ipAddress = ipAddressesList.at(i).toString();
            break;
        }
    }
    // if we did not find one, use IPv4 localhost
    if (ipAddress.isEmpty()){
        ipAddress = QHostAddress(QHostAddress::LocalHost).toString();
    }
    QMessageBox Msgbox;
    Msgbox.setText(tr("The server is running on\n\nIP: %1\nport: %2\n\n") .arg(ipAddress).arg(presentServer->serverPort()));
    Msgbox.exec();
}

// This is to update the received quiz counter on the professors GUI
void PresentContainer::updateQuizRecvCounterSlot(int count_in){
    emit updateQuizRecvCounterSignal(count_in);
}

// This is to update the sent quiz counter on the professors GUI
void PresentContainer::updateQuizSentCounterSlot(int count_in){
    emit updateQuizSentCounterSignal(count_in);
}

