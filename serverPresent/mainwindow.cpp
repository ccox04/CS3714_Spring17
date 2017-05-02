#include "mainwindow.h"
#include "ui_mainwindow.h"
#include "multiplechoicequiz.h"
#include "shortanswerquiz.h"

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    presentContainer = new PresentContainer();
    multipleChoiceUI = new MultipleChoiceQuiz();
    shortAnswerUI = new ShortAnswerQuiz();

    connect(multipleChoiceUI, SIGNAL(addQuestionSignalMC(int, int, QString, QString, QString, QString, QString, QString, QString)), this, SLOT(addQuestionSlotMW(int, int, QString, QString, QString, QString, QString, QString, QString)));
    connect(shortAnswerUI, SIGNAL(addQuestionSignalSA(int,int,QString,QString,QString,QString,QString,QString,QString)), this, SLOT(addQuestionSlotMW(int, int, QString, QString, QString, QString, QString, QString, QString)));
    connect(this, SIGNAL(startServerSignal()), presentContainer, SLOT(startServerSlotPC()));
    connect(this, SIGNAL(addQuestionSignal(int,int,QString,QString,QString,QString,QString,QString,QString)), presentContainer, SLOT(addQuestionSlotPC(int,int,QString,QString,QString,QString,QString,QString,QString)));
}

MainWindow::~MainWindow()
{
    delete multipleChoiceUI;
    delete shortAnswerUI;
    delete ui;
}

void MainWindow::on_okPushButton_clicked()
{
    // if radio button pressed etc
    if(ui->multipleChoiceRadioButton->isChecked()){
        multipleChoiceUI->show();
    }
    else if(ui->shortAnswerRadioButton->isChecked()){
        shortAnswerUI->show();
    }
}

void MainWindow::on_startServerPushButton_clicked()
{
    emit startServerSignal();
}
void MainWindow::addQuestionSlotMW(int correctAnswer, int type, QString question, QString answerA, QString answerB, QString answerC, QString answerD, QString answerE, QString answerSA){
    emit addQuestionSignal(correctAnswer, type, question, answerA, answerB, answerC, answerD, answerE, answerSA);
}
