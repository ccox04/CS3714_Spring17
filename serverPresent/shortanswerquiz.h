#ifndef SHORTANSWERQUIZ_H
#define SHORTANSWERQUIZ_H

#include <QDialog>
#include <QMessageBox>
//#include "presentcontainer.h"
//#include "mainwindow.h"

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

signals:
    void addQuestionSignalSA(int correctAnswer, int type, QString question, QString answerA, QString answerB, QString answerC, QString answerD, QString answerE, QString answerSA);

private:
    Ui::ShortAnswerQuiz *ui;
//    MainWindow *mainWindow;
//    PresentContainer *presentContainer;
    int QUESTIONTYPE_SA = 1;
};

#endif // SHORTANSWERQUIZ_H
