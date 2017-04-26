#ifndef PRESENTTHREAD_H
#define PRESENTTHREAD_H

#include <QThread>
#include <QTcpSocket>

class PresentThread : public QThread
{
    Q_OBJECT

public:
    PresentThread(int socketDescriptor, const QString &fortune, QObject *parent); // change forutune to QString question/answer

    void run() override;

signals:
    void error(QTcpSocket::SocketError socketError);

private:
    int socketDescriptor;
    QString text;
};

#endif // PRESENTTHREAD_H
