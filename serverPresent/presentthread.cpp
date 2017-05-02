#include "presentthread.h"

PresentThread::PresentThread(int socketDescriptor, const QString &quiz, QObject *parent)
    : QThread(parent), socketDescriptor(socketDescriptor), text(quiz)
{
    this->socketDescriptor = socketDescriptor;
}

void PresentThread::run()
{
    qDebug() << "Enter run()" << endl;
    socket = new QTcpSocket();
    if (!socket->setSocketDescriptor(socketDescriptor)) {
        emit error(socket->error());
        return;
    }
    connect(socket, SIGNAL(readyRead()), this, SLOT(readyRead()));
    connect(socket, SIGNAL(disconnected()), this, SLOT(disconnected()));
    sendQuizSlot();

    exec();

//    QByteArray block;
//    QDataStream out(&block, QIODevice::WriteOnly);
//    out.setVersion(QDataStream::Qt_4_0);
//    qDebug() << "Printing Json : " << text << endl;
//    out << text.toLocal8Bit();
//    qDebug() << "Printing Json8bit : " << text.toLocal8Bit();// + endbit.toLocal8Bit();
//    qDebug() << "Printing block : " << block;
//    tcpSocket.write(block);
//    tcpSocket.waitForBytesWritten();
//    quizCounterSent++;
//    emit updateQuizSentCounterSignal(quizCounterSent);
//    tcpSocket.disconnectFromHost();








//    while(!tcpSocket.waitForReadyRead()); // waitForBytesWritten() might also be a possibility
//    quizCounterRecv++; // incrementing quiz counter to display
//    QString filename = QDate::currentDate().toString("'Quiz_'yyyy_MM_dd'.txt'");
//    QFile file(filename);
//    file.open(QIODevice::ReadWrite | QIODevice::Append | QIODevice::Text);
//    if(!file.isOpen()){
//        qDebug() << "Not opened" << endl;
//    }
//    QTextStream outStream(&file);
//    outStream << tcpSocket.readAll() << endl;
//    qDebug() << "outStreaming Data " << endl;
//    emit updateQuizRecvCounterSignal(quizCounterRecv);
//    tcpSocket.disconnectFromHost();
//    if(tcpSocket.state() == QAbstractSocket::UnconnectedState || tcpSocket.waitForDisconnected()){
//        qDebug() << "Disconnected socket from thread" << endl;
//    }
    qDebug() << "Leaving  run()" << endl;
}

void PresentThread::readyRead()
{
    QByteArray Data = socket->readAll();
    qDebug() << "readyRead" << endl;
    QString filename = QDate::currentDate().toString("'Quiz_'yyyy_MM_dd'.txt'");
    QFile file(filename);
    file.open(QIODevice::ReadWrite | QIODevice::Append | QIODevice::Text);
    if(!file.isOpen()){
        qDebug() << "Not opened" << endl;
    }
//    QString socketIn = QString::fromLocal8Bit(Data);
    qDebug() << "Data Before: " << Data << endl;
    Data.replace("|", "\n");
    qDebug() << "Data After: " << Data << endl;
    QTextStream outStream(&file);
    outStream << Data << endl;
    qDebug() << "outStreaming Data " << endl;
    socket->flush();
//    socket->disconnectFromHost();
//    if(socket->state() == QAbstractSocket::UnconnectedState || socket->waitForDisconnected()){
//        qDebug() << "Disconnected socket from thread" << endl;
//    }
}

void PresentThread::disconnected()
{
    qDebug() << socketDescriptor << " Disconnected";
    socket->deleteLater();
    exit(0);
}

void PresentThread::sendQuizSlot(){
    if(socket->isWritable()){
        QByteArray block;
        QDataStream out(&block, QIODevice::WriteOnly);
        out.setVersion(QDataStream::Qt_4_0);
        qDebug() << "Printing Json : " << text << endl;
        out << text.toLocal8Bit();
        qDebug() << "Printing Json8bit : " << text;
        socket->write(block);
//        socket->waitForBytesWritten();
//        socket->flush();
//        socket->disconnectFromHost();
    }
}
