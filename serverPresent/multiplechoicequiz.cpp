#include "multiplechoicequiz.h"
#include "ui_multiplechoicequiz.h"

MultipleChoiceQuiz::MultipleChoiceQuiz(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::MultipleChoiceQuiz)
{
    ui->setupUi(this);
    presentContainer = new PresentContainer();
    connect(this, SIGNAL(startServerSignalMC(int, QString, QString, QString, QString, QString, QString, QString)), presentContainer, SLOT(startServerSlotPC(int, QString, QString, QString, QString, QString, QString, QString)));
    connect(presentContainer, SIGNAL(updateQuizRecvCounterSignal(int)), this, SLOT(updateQuizRecvCounterSlot(int)));
    connect(presentContainer, SIGNAL(updateQuizSentCounterSignal(int)), this, SLOT(updateQuizSentCounterSlot(int)));
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
}

// This is to update the received quiz counter on the professors GUI
void MultipleChoiceQuiz::updateQuizRecvCounterSlot(int count_in){
    ui->quizReceivedLcdNumber->display(QString::number(count_in));
}

// This is to update the sent quiz counter on the professors GUI
void MultipleChoiceQuiz::updateQuizSentCounterSlot(int count_in){
    ui->quizSentLcdNumber->display(QString::number(count_in));
}

