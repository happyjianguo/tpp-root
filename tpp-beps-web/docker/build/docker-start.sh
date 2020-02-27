#!/bin/bash
cp /etc/hosts /etc/hosts.temp
sed -i "s/.*$(hostname)/$DOCKER_IP $(hostname)/" /etc/hosts.temp
cat /etc/hosts.temp > /etc/hosts

chown hammer:hammer -R log

su - hammer -c "cd app;nohup java -Xms128m -Xmx256m -XX:+PrintGCDetails -Xloggc:./log/gc.log -jar tpp-beps-web-1.0.0.jar >/dev/null 2>&1 &"

tail -f /dev/null