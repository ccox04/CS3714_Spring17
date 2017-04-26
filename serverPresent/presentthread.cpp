#include "presentthread.h"

PresentThread::PresentThread(int socketDescriptor, const QString &fortune, QObject *parent)
    : QThread(parent), socketDescriptor(socketDescriptor), text(fortune)
{
    quizCounterSent = 0;
    quizCounterRecv = 0;
}

void PresentThread::run()
{
    qDebug() << "Enter run()" << endl;
    QTcpSocket tcpSocket;
    if (!tcpSocket.setSocketDescriptor(socketDescriptor)) {
        emit error(tcpSocket.error());
        return;
    }
    QByteArray block;
    QDataStream out(&block, QIODevice::WriteOnly);
    out.setVersion(QDataStream::Qt_4_0);
    out << text;
    tcpSocket.write(block);
    quizCounterSent++;
    emit updateQuizSentCounterSignal(quizCounterSent);
    while(!tcpSocket.waitForReadyRead()); // waitForBytesWritten() might also be a possibility
    quizCounterRecv++; // incrementing quiz counter to display
    QString filename = QDate::currentDate().toString("'Quiz_'yyyy_MM_dd'.txt'");
    QFile file(filename);
    file.open(QIODevice::ReadWrite | QIODevice::Append | QIODevice::Text);
    if(!file.isOpen()){
        qDebug() << "Not opened" << endl;
    }
    QTextStream outStream(&file);
    outStream << tcpSocket.readAll() << endl;
    qDebug() << "outStreaming Data " << endl;
    emit updateQuizRecvCounterSignal(quizCounterRecv);
    tcpSocket.disconnectFromHost();
    if(tcpSocket.state() == QAbstractSocket::UnconnectedState || tcpSocket.waitForDisconnected()){
        qDebug() << "Disconnected socket from thread" << endl;
    }
    qDebug() << "Leaving  run()" << endl;
}
