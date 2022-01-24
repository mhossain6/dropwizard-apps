FROM openjdk:11

ENV APP_HOME /app

RUN groupadd -r app && useradd -r -gapp app
RUN mkdir -m 0755 -p ${APP_HOME}/bin
RUN mkdir -m 0755 -p ${APP_HOME}/db
RUN mkdir -m 0755 -p ${APP_HOME}/config
RUN mkdir -m 0755 -p ${APP_HOME}/logs

COPY ./../target/tasker-client-0.0.1-SNAPSHOT.jar ${APP_HOME}/bin/
COPY docker-entrypoint.sh /

RUN chown -R app:app ${APP_HOME}
RUN chmod +x /docker-entrypoint.sh

EXPOSE 8080

WORKDIR ${APP_HOME}
CMD ["/docker-entrypoint.sh"]