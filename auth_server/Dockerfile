FROM openjdk:8-alpine
MAINTAINER bingzhou6@gmail.com

RUN echo 'http://mirrors.aliyun.com/alpine/latest-stable/main' > /etc/apk/repositories
RUN echo '@community http://mirrors.aliyun.com/alpine/latest-stable/community' >> /etc/apk/repositories
RUN echo '@testing http://mirrors.aliyun.com/alpine/edge/testing' >> /etc/apk/repositories

RUN \
 apk add --no-cache \
	ttf-dejavu
VOLUME /tmp
ADD  target/ippse-oauth-server-latest.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS="-Djava.awt.headless=true"
CMD exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=docker -jar /app.jar