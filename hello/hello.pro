TEMPLATE = app
TARGET = hello
INCLUDEPATH += .
DEFINES += QT_DEPRECATED_WARNINGS
HEADERS +=
SOURCES += main.cpp
greaterThan(QT_MAJOR_VERSION, 4): QT += widgets
greaterThan(QT_MAJOR_VERSION, 5): QT += core gui widgets
