FROM openjdk:17-jdk-slim
RUN mkdir /singlelilnebot
COPY . /singlelinebot/
WORKDIR ./singlelinebot/build/libs
EXPOSE 8081
ENTRYPOINT ["java","-jar","./singlelinebot.jar"]
