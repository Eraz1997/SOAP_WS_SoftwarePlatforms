FROM tomcat:8.5-jre11

ENV CATALINA_BASE $CATALINA_HOME
ENV AXIS2_SERVICES $CATALINA_HOME/webapps/axis2/WEB-INF/services
ENV AXIS2_HOME $CATALINA_HOME/webapps/axis2
EXPOSE 8080/tcp

RUN mkdir -p /tmp/axis2
RUN cd /tmp/axis2
RUN wget http://apache.panu.it/axis/axis2/java/core/1.7.9/axis2-1.7.9-war.zip -O axis2.zip
RUN unzip axis2.zip -d /tmp/axis2/
RUN cp /tmp/axis2/axis2.war $CATALINA_HOME/webapps/
RUN rm -rf /tmp/axis2 && mkdir $CATALINA_HOME/webapps/axis2 && unzip $CATALINA_HOME/webapps/axis2.war -d $CATALINA_HOME/webapps/axis2
COPY ./WebService/CryptoWS.aar $CATALINA_HOME/webapps/axis2/WEB-INF/services/
COPY ./WebService/SnifferWS.aar $CATALINA_HOME/webapps/axis2/WEB-INF/services/

RUN apt-get update && apt-get install -y tcpdump 

