#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include "presentcontainer.h"
#include "multiplechoicequiz.h"
#include "shortanswerquiz.h"

namespace Ui {
class MainWindow;
}

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    explicit MainWindow(QWidget *parent = 0);
    ~MainWindow();

public slots:
    void addQuestionSlotMW(int correctAnswer, int type, QString question, QString answerA, QString answerB, QString answerC, QString answerD, QString answerE, QString answerSA);

private slots:
    void on_okPushButton_clicked();

    void on_startServerPushButton_clicked();

signals:
    void startServerSignal();
    void addQuestionSignal(int correctAnswer, int type_out, QString question, QString answerA, QString answerB, QString answerC, QString answerD, QString answerE, QString answerSA);

private:
    Ui::MainWindow *ui;
    PresentContainer *presentContainer;
    MultipleChoiceQuiz *multipleChoiceUI;
    ShortAnswerQuiz *shortAnswerUI;
};

#endif // MAINWINDOW_H
