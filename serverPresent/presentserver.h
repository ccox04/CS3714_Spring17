#ifndef PRESENTSERVER_H
#define PRESENTSERVER_H

#include <QObject>
#include <QStringList>
#include <QTcpServer>

class PresentServer : public QTcpServer
{
    Q_OBJECT
public:
    explicit PresentServer(QObject *parent = 0);

protected:
    void incomingConnection(qintptr socketDescriptor) override;

public slots:
    void setQuizSlot(int correctAnswer_in, QString question_in, QString answerA_in, QString answerB_in, QString answerC_in, QString answerD_in, QString answerE_in, QString answerSA_in);

private:
    QStringList fortunes;
    QString question, answerA, answerB, answerC, answerD, answerE, answerSA;
    int correctAnswer;
};

#endif // PRESENTSERVER_H
