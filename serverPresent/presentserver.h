#ifndef PRESENTSERVER_H
#define PRESENTSERVER_H

#include <QObject>

class PresentServer : public QObject
{
    Q_OBJECT
public:
    explicit PresentServer(QObject *parent = 0);

signals:

public slots:
};

#endif // PRESENTSERVER_H