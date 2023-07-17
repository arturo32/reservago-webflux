# reservago-webflux
Project made with Spring (WebFlux) for the Reactive Architectures course.

## How to initialize the environment
You will need the following technologies installed in your computer:
- Maven
- MongoDB or Docker
- Java 20

### Adding `reservago-base` to maven

First, you'll need to add the `reservago-base` to your local maven repository. You can do that by running the following command in the `reservago-base` directory:
```
mvn clean install
```

Obs: If you installed the JDK 20 using your IDE, you'll need to put its path in the `JAVA_HOME` environment variable. If you are using Linux this can be done temporarily by running this command:
```
export JAVA_HOME=path_to_your_jdk
```

### Configuring the database

If you have docker you can just run the bash script to build the image described in the `Dockerfile` of the root repository: `create_img-db.sh`. After the image is built, run it with the bash script `run-db.sh`.


## How to initialize the environment (with all services in containers)
If you have Docker, all you need to do is to run the following bash script:
```
./runAll.sh
```

If you want to stop **all the containers of your computer**, you type:
```
./stopAll.sh
```

If you want to restart all **stopped containers of your computer**, you run:
```
./startAll.sh
```

And if you change a service or with one of them for some reason stopped working, you can recreate its image and container with the following command:
```
./resetService.sh <name-of-the-service-directory>
```

Also, with you want to reset the MongoDB container:
```
./resetMongo.sh
```

## Valuable endpoints

- Eureka: `http://localhost:8761`
- Actuator: `http://localhost:8080/<service-name>/actuator`


