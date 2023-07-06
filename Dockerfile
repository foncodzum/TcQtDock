FROM theshadowx/qt5:18.04
LABEL maintainer="lixonik"
LABEL version="0.1"
RUN apt update && \
    apt upgrade -y && \
    apt install nano
WORKDIR /home/root/hello/
COPY ./hello/ .
RUN qmake && \
    make