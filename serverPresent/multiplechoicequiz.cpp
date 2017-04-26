#include "multiplechoicequiz.h"
#include "ui_multiplechoicequiz.h"

MultipleChoiceQuiz::MultipleChoiceQuiz(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::MultipleChoiceQuiz)
{
    ui->setupUi(this);
    presentContainer = new PresentContainer();
//    connect(this, SIGNAL(startServerSignalMC()), presentContainer, SLOT(startServerSlotPC()));
    connect(this, SIGNAL(startServerSignalMC(int, QString, QString, QString, QString, QString, QString, QString)), presentContainer, SLOT(startServerSlotPC(int, QString, QString, QString, QString, QString, QString, QString)));

}

MultipleChoiceQuiz::~MultipleChoiceQuiz()
{
    delete presentContainer;
    delete ui;
}

void MultipleChoiceQuiz::on_okPushButton_clicked()
{
    int correctAnswer;
    QMessageBox Msgbox;
    Msgbox.setText("Multiple Choice Quiz has been administered");
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

    emit startServerSignalMC(correctAnswer, question, answerA, answerB, answerC, answerD, answerE, "NA");
    // I need to store the question and answer values and pass them over the socket.
//    AdministerResult administerResult;
//    administerResult.setModal(true);
//    administerResult.exec();
}
