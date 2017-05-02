#ifndef MULTIPLECHOICEQUIZ_H
#define MULTIPLECHOICEQUIZ_H

#include <QDialog>
#include <QMessageBox>

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

signals:
    void addQuestionSignalMC(int correctAnswer, int type_out, QString question, QString answerA, QString answerB, QString answerC, QString answerD, QString answerE, QString answerSA);

private:
    Ui::MultipleChoiceQuiz *ui;
    int QUESTIONTYPE_MC = 0;
};

#endif // MULTIPLECHOICEQUIZ_H
