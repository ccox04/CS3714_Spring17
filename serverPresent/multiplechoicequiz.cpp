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
