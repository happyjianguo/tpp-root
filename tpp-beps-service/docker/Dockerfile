FROM centos:hammer-jdk1.8

MAINTAINER zhouyongwei zyw_unix@126.com

COPY docker/build $HOME/app
COPY target/tpp-beps-service-1.0.0.jar $HOME/app

RUN chown hammer:hammer -R $HOME/app

EXPOSE 9039 20919

WORKDIR $HOME/app

ENTRYPOINT ["./docker-start.sh"]

