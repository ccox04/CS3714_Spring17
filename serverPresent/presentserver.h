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
    void addQuestionSlot(int correctAnswer_in, int type_in, QString question_in, QString answerA_in, QString answerB_in, QString answerC_in, QString answerD_in, QString answerE_in, QString answerSA_in);
private:
    QStringList answerA_List, answerB_List, answerC_List, answerD_List, answerE_List, question_List, answerSA_List;
    QList<int> correctAnswerMC_List, type_List;
    QString question, answerA, answerB, answerC, answerD, answerE, answerSA;
    int correctAnswer, type;
    int quizCounterRecv;
    int quizCounterSent;
    QString ENDMESSAGECHAR = "###\n";
    int QUESTIONTYPE_MC = 0;
    int QUESTIONTYPE_SA = 1;
    QString QUIZNAME_MC = "quiz_mc";
    QString QUIZNAME_SA = "quiz_sa";
    int number_of_questions;
};

#endif // PRESENTSERVER_H
