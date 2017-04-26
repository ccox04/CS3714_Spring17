#ifndef SHORTANSWERQUIZ_H
#define SHORTANSWERQUIZ_H

#include <QDialog>
#include <QMessageBox>
#include "presentcontainer.h"

namespace Ui {
class ShortAnswerQuiz;
}

class ShortAnswerQuiz : public QDialog
{
    Q_OBJECT

public:
    explicit ShortAnswerQuiz(QWidget *parent = 0);
    ~ShortAnswerQuiz();

private slots:
    void on_okPushButton_clicked();


public slots:
    void updateQuizRecvCounterSlot(int count_in);
    void updateQuizSentCounterSlot(int count_in);

signals:
    void startServerSignalSA(int correctAnswer, QString question, QString answerA, QString answerB, QString answerC, QString answerD, QString answerE, QString answerSA);

private:
    Ui::ShortAnswerQuiz *ui;
    PresentContainer *presentContainer;
};

#endif // SHORTANSWERQUIZ_H
