FROM java
MAINTAINER cristik1991@email.com
ADD demo.jar /demo.jar
WORKDIR /
CMD ["java", "-version"]
#ENTRYPOINT ["java", "-jar", "${project.build.finalName}.jar"]
ENTRYPOINT ["java", "-jar", "demo.jar"]