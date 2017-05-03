#include "presentcontainer.h"

PresentContainer::PresentContainer(QObject *parent) :
    QObject(parent)
{
    // Connect slots and signals
    // Init variables
    presentServer = new PresentServer();
    connect(this, SIGNAL(addQuestionSignalPC(int, int, QString,QString,QString,QString,QString,QString,QString)), presentServer, SLOT(addQuestionSlot(int, int,QString,QString,QString,QString,QString,QString,QString)));
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
    QString serverIPAddress;
    QList<QHostAddress> serverIPAddressesList = QNetworkInterface::allAddresses();
    // This checks to try and use a nonlocal IPv4 host
    for (int i = 0; i < serverIPAddressesList.size(); ++i) {
        if (serverIPAddressesList.at(i) != QHostAddress::LocalHost &&
            serverIPAddressesList.at(i).toIPv4Address()) {
            serverIPAddress = serverIPAddressesList.at(i).toString();
            break;
        }
    }
    // If no nonlocal host was found use a local IPv4
    if (serverIPAddress.isEmpty()){
        serverIPAddress = QHostAddress(QHostAddress::LocalHost).toString();
    }
    // This displays the server IP and Port in a message box to the screen
    QMessageBox Msgbox;
    Msgbox.setText(tr("The server is running on\n\nIP: %1\nport: %2\n\n") .arg(serverIPAddress).arg(presentServer->serverPort()));
    Msgbox.exec();
}

