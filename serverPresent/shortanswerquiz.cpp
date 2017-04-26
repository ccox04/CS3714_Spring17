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
