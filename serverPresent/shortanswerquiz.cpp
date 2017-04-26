#include "shortanswerquiz.h"
#include "ui_shortanswerquiz.h"

ShortAnswerQuiz::ShortAnswerQuiz(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::ShortAnswerQuiz)
{
    ui->setupUi(this);
    presentContainer = new PresentContainer();
    connect(this, SIGNAL(startServerSignalSA(int, QString, QString, QString, QString, QString, QString, QString)), presentContainer, SLOT(startServerSlotPC(int ,QString, QString, QString, QString, QString, QString, QString)));
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
    // I need to store the question and answer values and pass them over the socket.

//    AdministerResult administerResult;
//    administerResult.setModal(true);
//    administerResult.exec();
}
