#include "presentcontainer.h"

PresentContainer::PresentContainer(QObject *parent) :
    QObject(parent)
{
    // Connect slots and signals
    // Init variables
    presentServer = new PresentServer();
    connect(this, SIGNAL(setQuizSignal(int,QString,QString,QString,QString,QString,QString,QString)), presentServer, SLOT(setQuizSlot(int,QString,QString,QString,QString,QString,QString,QString)));
}

PresentContainer::~PresentContainer(){
    // clear variables
    // kill server
    delete presentServer;
}

void PresentContainer::startServerSlotPC(int correctAnswer, QString question, QString answerA, QString answerB, QString answerC, QString answerD, QString answerE, QString answerSA){
    emit setQuizSignal(correctAnswer, question, answerA, answerB, answerC, answerD, answerE, answerSA);
    if (!presentServer->listen()) {
        QMessageBox Msgbox;
        Msgbox.setText("ERROR: Server is NOT listening");
        Msgbox.exec();
//        QMessageBox::critical(this, tr("Present Server"),
//                              tr("Unable to start the server: %1.")
//                              .arg(presentServer->errorString()));
//        close();
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
//    statusLabel->setText(tr("The server is running on\n\nIP: %1\nport: %2\n\n"
//                            "Run the Fortune Client example now.")
//                         .arg(ipAddress).arg(presentServer->serverPort()));
    QMessageBox Msgbox;
    Msgbox.setText(tr("The server is running on\n\nIP: %1\nport: %2\n\n") .arg(ipAddress).arg(presentServer->serverPort()));
    Msgbox.exec();
}
