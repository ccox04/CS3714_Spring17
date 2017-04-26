#ifndef SHORTANSWERQUIZ_H
#define SHORTANSWERQUIZ_H

#include <QDialog>

namespace Ui {
class ShortAnswerQuiz;
}

class ShortAnswerQuiz : public QDialog
{
    Q_OBJECT

public:
    explicit ShortAnswerQuiz(QWidget *parent = 0);
    ~ShortAnswerQuiz();

private:
    Ui::ShortAnswerQuiz *ui;
};

#endif // SHORTANSWERQUIZ_H
