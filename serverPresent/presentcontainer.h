#ifndef PRESENTCONTAINER_H
#define PRESENTCONTAINER_H

#include "presentserver.h"
#include <QMessageBox>
#include <QtNetwork>
class PresentContainer : public QObject
{
    Q_OBJECT
public:
    explicit PresentContainer(QObject *parent = 0);
    ~PresentContainer();

signals:
    void setQuizSignal(int correctAnswer_out, QString question_out, QString answerA_out, QString answerB_out, QString answerC_out, QString answerD_out, QString answerE_out, QString answerSA_out);

public slots:
    void startServerSlotPC(int correctAnswer, QString question, QString answerA, QString answerB, QString answerC, QString answerD, QString answerE, QString answerSA); // This should be called from the quiz classes when ok is selected

private:
    PresentServer *presentServer;
};

#endif // PRESENTCONTAINER_H
