FROM openjdk:11
RUN mkdir /code
COPY build/libs /code
ENTRYPOINT [ "sh", "-c", "java -jar -Xmx2048m -server -XX:+UseG1GC /code/*.jar" ]