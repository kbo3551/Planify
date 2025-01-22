# Tomcat 9
FROM tomcat:9.0-jdk11-openjdk

# workdirectory
WORKDIR /usr/local/tomcat/webapps

# build WAR file to tomcat
COPY build/libs/planify.war ./ROOT.war

# Tomcat port
EXPOSE 8080

# Tomcat Execut
CMD ["catalina.sh", "run"]