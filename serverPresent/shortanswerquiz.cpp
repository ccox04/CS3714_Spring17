#include "shortanswerquiz.h"
#include "ui_shortanswerquiz.h"

ShortAnswerQuiz::ShortAnswerQuiz(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::ShortAnswerQuiz)
{
    ui->setupUi(this);
}

ShortAnswerQuiz::~ShortAnswerQuiz()
{
    delete ui;
}

void ShortAnswerQuiz::on_okPushButton_clicked()
{
    QMessageBox Msgbox;
    Msgbox.setText("Short Answer Question Added to Quiz");
    Msgbox.exec();
    QString question = ui->shortAnswerQuestionLineEdit->text();
    QString answer = ui->shortAnswerAnwserLineEdit->text();
    emit addQuestionSignalSA(0, QUESTIONTYPE_SA, question, "NA", "NA", "NA", "NA", "NA", answer);
    this->close();
}
