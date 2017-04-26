#ifndef MULTIPLECHOICEQUIZ_H
#define MULTIPLECHOICEQUIZ_H

#include <QDialog>
#include <QMessageBox>
#include "presentcontainer.h"

namespace Ui {
class MultipleChoiceQuiz;
}

class MultipleChoiceQuiz : public QDialog
{
    Q_OBJECT

public:
    explicit MultipleChoiceQuiz(QWidget *parent = 0);
    ~MultipleChoiceQuiz();

private slots:
    void on_okPushButton_clicked();

public slots:
    void updateQuizRecvCounterSlot(int count_in);
    void updateQuizSentCounterSlot(int count_in);
signals:
    void startServerSignalMC(int correctAnswer, QString question, QString answerA, QString answerB, QString answerC, QString answerD, QString answerE, QString answerSA);

private:
    Ui::MultipleChoiceQuiz *ui;
    PresentContainer *presentContainer;
};

#endif // MULTIPLECHOICEQUIZ_H
