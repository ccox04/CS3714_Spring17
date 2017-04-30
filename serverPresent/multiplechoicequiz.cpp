#include "multiplechoicequiz.h"
#include "ui_multiplechoicequiz.h"

MultipleChoiceQuiz::MultipleChoiceQuiz(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::MultipleChoiceQuiz)
{
    ui->setupUi(this);
}

MultipleChoiceQuiz::~MultipleChoiceQuiz()
{
    delete ui;
}

void MultipleChoiceQuiz::on_okPushButton_clicked()
{
    int correctAnswer;
    QMessageBox Msgbox;
    Msgbox.setText("Multiple Choice Question Added to Quiz");
    Msgbox.exec();
    QString question = ui->multipleChoiceQuestionLineEdit->text();
    QString answerA = ui->choiceALineEdit->text();
    QString answerB = ui->choiceBLineEdit->text();
    QString answerC = ui->choiceCLineEdit->text();
    QString answerD = ui->choiceDLineEdit->text();
    QString answerE = ui->choiceELineEdit->text();
    if(ui->choiceARadioButton->isChecked()){
        correctAnswer = 1;
    }
    else if(ui->choiceBRadioButton->isChecked()){
        correctAnswer = 2;
    }
    else if(ui->choiceCRadioButton->isChecked()){
        correctAnswer = 3;
    }
    else if(ui->choiceDRadioButton->isChecked()){
        correctAnswer = 4;
    }
    else if(ui->choiceERadioButton->isChecked()){
        correctAnswer = 5;
    }

    emit addQuestionSignalMC(correctAnswer, QUESTIONTYPE_MC, question, answerA, answerB, answerC, answerD, answerE, "NA");
    this->close();
}

