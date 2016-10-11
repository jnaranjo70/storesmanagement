FROM tenxtech/java:8
VOLUME /tmp
EXPOSE 9888
ADD homes-ms-storesmanagement.jar app.jar
COPY run.sh /usr/bin/run.sh

ENV SPRING_PROFILES_ACTIVE="dev,docker"
ENV JAVA_OPTS="-Xmx512m"

RUN bash -c 'touch /app.jar' && chmod a+x /usr/bin/run.sh

ENTRYPOINT ["/usr/bin/run.sh"]
