#include "mainwindow.h"
#include "ui_mainwindow.h"
#include "multiplechoicequiz.h"
#include "shortanswerquiz.h"

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::on_okPushButton_clicked()
{
    // if radio button pressed etc
    if(ui->multipleChoiceRadioButton->isChecked()){
        MultipleChoiceQuiz multipleChoiceQuiz;
        multipleChoiceQuiz.setModal(true);
        multipleChoiceQuiz.exec();
    }
    else if(ui->shortAnswerRadioButton->isChecked()){
            ShortAnswerQuiz shortAnswerQuiz;
            shortAnswerQuiz.setModal(true);
            shortAnswerQuiz.exec();
    }
    else{
        // Print the user did not select a quiz type
    }
}
