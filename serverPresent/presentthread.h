#ifndef PRESENTTHREAD_H
#define PRESENTTHREAD_H

#include <QThread>
#include <QTcpSocket>
#include <QtNetwork>
#include <QDate>
#include <QFile>

class PresentThread : public QThread
{
    Q_OBJECT

public:
    PresentThread(int socketDescriptor, const QString &quiz, QObject *parent); // change forutune to QString question/answer
    void sendQuizSlot();

    void run() override;

signals:
    void error(QTcpSocket::SocketError socketError);

public slots:
    void readyRead();
    void disconnected();

private:
    QTcpSocket *socket;
    int socketDescriptor;
    QString text;
};

#endif // PRESENTTHREAD_H
