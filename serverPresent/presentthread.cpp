#include "presentthread.h"

PresentThread::PresentThread(int socketDescriptor, const QString &quiz, QObject *parent)
    : QThread(parent), socketDescriptor(socketDescriptor), text(quiz)
{
    this->socketDescriptor = socketDescriptor;
}

// This is the main thread that handles sending out the quiz and reading the results
void PresentThread::run()
{
    socket = new QTcpSocket();
    if (!socket->setSocketDescriptor(socketDescriptor)) {
        emit error(socket->error());
        return;
    }
    connect(socket, SIGNAL(readyRead()), this, SLOT(readyRead()));
    connect(socket, SIGNAL(disconnected()), this, SLOT(disconnected()));
    sendQuizSlot();
    exec();
}

void PresentThread::readyRead()
{
    QByteArray Data = socket->readAll();
    QString filename = QDate::currentDate().toString("'Quiz_'yyyy_MM_dd'.txt'");
    QFile file(filename);
    file.open(QIODevice::ReadWrite | QIODevice::Append | QIODevice::Text);
    if(!file.isOpen()){
        qDebug() << "Not opened" << endl;
    }
    Data.replace("|", "\n"); // This is the identifier to break up the incoming packet from the mobile to place it into a text file accordingly
    QTextStream outStream(&file);
    outStream << Data << endl;
    socket->flush();
}

void PresentThread::disconnected()
{
    socket->deleteLater();
    exit(0);
}

// This sends the quiz out to the mobile phone
void PresentThread::sendQuizSlot(){
    if(socket->isWritable()){
        QByteArray block;
        QDataStream out(&block, QIODevice::WriteOnly);
        out.setVersion(QDataStream::Qt_4_0);
        out << text.toLocal8Bit();
        socket->write(block);
    }
}
