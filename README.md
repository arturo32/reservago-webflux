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
