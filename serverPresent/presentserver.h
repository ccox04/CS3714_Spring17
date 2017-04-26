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

signals:
    void updateQuizRecvCounterSignal(int count_out);
    void updateQuizSentCounterSignal(int count_out);

public slots:
    void setQuizSlot(int correctAnswer_in, QString question_in, QString answerA_in, QString answerB_in, QString answerC_in, QString answerD_in, QString answerE_in, QString answerSA_in);
    void updateQuizRecvCounterSlot(int count_in);
    void updateQuizSentCounterSlot(int count_in);
private:
    QStringList fortunes;
    QString question, answerA, answerB, answerC, answerD, answerE, answerSA;
    int correctAnswer;
    int quizCounterRecv;
    int quizCounterSent;
};

#endif // PRESENTSERVER_H
