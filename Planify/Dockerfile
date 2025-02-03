# Tomcat 9
FROM tomcat:9.0-jdk11-openjdk

# Timezone 설정
RUN ln -sf /usr/share/zoneinfo/Asia/Seoul /etc/localtime \
    && echo "Asia/Seoul" > /etc/timezone

# workdirectory
WORKDIR /usr/local/tomcat/webapps

# build WAR file to tomcat
COPY build/libs/planify.war ./ROOT.war

# Tomcat port
EXPOSE 8080

# Tomcat Execut
CMD ["catalina.sh", "run"]