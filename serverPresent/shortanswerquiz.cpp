#include "shortanswerquiz.h"
#include "ui_shortanswerquiz.h"

ShortAnswerQuiz::ShortAnswerQuiz(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::ShortAnswerQuiz)
{
    ui->setupUi(this);
    presentContainer = new PresentContainer();
    connect(this, SIGNAL(startServerSignalSA(int, QString, QString, QString, QString, QString, QString, QString)), presentContainer, SLOT(startServerSlotPC(int ,QString, QString, QString, QString, QString, QString, QString)));
    connect(presentContainer, SIGNAL(updateQuizRecvCounterSignal(int)), this, SLOT(updateQuizRecvCounterSlot(int)));
    connect(presentContainer, SIGNAL(updateQuizSentCounterSignal(int)), this, SLOT(updateQuizSentCounterSlot(int)));
}

ShortAnswerQuiz::~ShortAnswerQuiz()
{
    delete presentContainer;
    delete ui;
}

void ShortAnswerQuiz::on_okPushButton_clicked()
{
    QMessageBox Msgbox;
    Msgbox.setText("Short Answer Quiz has been administered");
    Msgbox.exec();
    QString question = ui->shortAnswerQuestionLineEdit->text();
    QString answer = ui->shortAnswerAnwserLineEdit->text();
    emit startServerSignalSA(0, question, "NA", "NA", "NA", "NA", "NA", answer);
}

// This is to update the received quiz counter on the professors GUI
void ShortAnswerQuiz::updateQuizRecvCounterSlot(int count_in){
    ui->quizReceivedLcdNumber->display(QString::number(count_in));
}

void ShortAnswerQuiz::updateQuizSentCounterSlot(int count_in){
    ui->quizSentLcdNumber->display(QString::number(count_in));
}
