# testing vaadin session handling in a clustered environment

this is a small demo to test and show how to handle sessions in Vaadin when the application is running in a clustered
 environment.
 
## Prerequisites
 
Docker must be installed in a version that supports native swarm mode (>=  version 12?)

## the application

The application is a simple Vaadin application, powered by Spring Boot and written in Kotlin. It has a text input, 
a button and below that a label that gets updated with the input's value when the button is clicked. Finally below 
that is a label showing the current session id.

## building and running a single instance

the application will run happily from within the IDE, but for scaling purposes it should be run using docker:

    mvn package
    (cd target && sh build-docker-image.sh)
    ./stack-up.sh
    
This builds the application and the docker image, and starts it a a service stack named _vsc_ in docker swarm mode 
running a service named _vsc_vaadin-session-clustered_. 
This can be checked with 
 
     docker service ls
     docker service ps vsc_vaadin-session-clustered
     
It can be accessed at <http://localhost:8080>. To test the behaviour, more than one browser tab/window can be opened.
 As long as there is only one instance running, theres is nothing special to see.

## running multiple instances

The problem with session handling gets visible, when the service is replicated to more than one instance:

    docker service scale vsc_vaadin-session-clustered=4
    
When playing aroud with the applicaiton, after some time there are eerror messages about outdated sessions or 
communication problems resulting from the fact that the requests from one browser window are routed to different 
backend servers.


## adding session support

the following dependencies are needed:

		<dependency>
			<groupId>org.springframework.session</groupId>
			<artifactId>spring-session</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-redis</artifactId>
			<version>${spring-boot-starter-redis.version}</version>
		</dependency>
        <dependency>
            <groupId>org.vaadin</groupId>
            <artifactId>spring-session-redis</artifactId>
            <version>1.0</version>
        </dependency>

the first adds the spring boot session support, the second adds the libraries for using redis and the third is needed
 have vaadin to the whole stuff.
 
 The main applicaiton class gets an additional `@EnableRedisHttpSession` annotation and a method
   
       @Bean
       fun vaadinSessionRewriteFilter() = VaadinSessionRewriteFilter()

After that, when the docker stack is deployed and scaled, occasionally the traffic is sent to a different host 
(browsers keep the connection open), but no more session or communication errors occur. 
