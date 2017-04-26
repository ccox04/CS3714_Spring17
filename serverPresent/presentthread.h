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
    PresentThread(int socketDescriptor, const QString &fortune, QObject *parent); // change forutune to QString question/answer

    void run() override;

signals:
    void error(QTcpSocket::SocketError socketError);
    void updateQuizRecvCounterSignal(int count_out);
    void updateQuizSentCounterSignal(int count_out);

private:
    int socketDescriptor;
    int quizCounterRecv;
    int quizCounterSent;
    QString text;
};

#endif // PRESENTTHREAD_H
