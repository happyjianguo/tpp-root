FROM centos:hammer-jdk1.8

MAINTAINER zhouyongwei zyw_unix@126.com

COPY docker/build $HOME/app
COPY target/tpp-beps-web-1.0.0.jar $HOME/app

RUN chown hammer:hammer -R $HOME/app

EXPOSE 7024

WORKDIR $HOME/app

ENTRYPOINT ["./docker-start.sh"]

