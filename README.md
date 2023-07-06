# TcQtDock
## How to start?
```
$ git clone https://github.com/foncodzum/TcQtDock.git
$ docker build --build-arg DISPLAY=$DISPLAY .
$ docker run -it --name <name> -v /tmp/.X11-unix:/tmp/.X11-unix:ro <image>
```
