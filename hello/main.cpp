# include <QtWidgets/QApplication>
# include <QtWidgets/QMainWindow>
# include <QtWidgets/QLabel>

int main(int argc, char *argv[])
{
    QApplication app(argc, argv);

    QMainWindow window;
    QLabel label("Hello, World!");
    window.setCentralWidget(&label);
    window.show();

    return app.exec();
}
