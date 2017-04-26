#ifndef MULTIPLECHOICEQUIZ_H
#define MULTIPLECHOICEQUIZ_H

#include <QDialog>

namespace Ui {
class MultipleChoiceQuiz;
}

class MultipleChoiceQuiz : public QDialog
{
    Q_OBJECT

public:
    explicit MultipleChoiceQuiz(QWidget *parent = 0);
    ~MultipleChoiceQuiz();

private:
    Ui::MultipleChoiceQuiz *ui;
};

#endif // MULTIPLECHOICEQUIZ_H
