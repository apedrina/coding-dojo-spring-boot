Spring Boot Coding Dojo
---

Welcome to the Spring Boot Coding Dojo!

### Introduction

This is a simple application that requests its data from [OpenWeather](https://openweathermap.org/) and stores the
result in a database. The current implementation has quite a few problems making it a non-production ready product.

### Requirements

- Java 11
- Docker
- Docker-compose
- Maven

### Compile

To compile the project run the commands bellow:

```bash
cd <project_root>
mvn clean install

```

<b>Warning</b>: we're using testContainers framework, so before running these above commands
be sure you've the Docker running.

### Run

To run the project first we need up either the postgresql and activeMq containers. To do so run the following commands:

```bash
cd <project_root>/docker
docker-compose up

```

Then run the commands bellow:

```bash
cd <project_root>
java -jar target/coding-dojo.jar

```

### Swagger

To try out the rest endpoints access:

```bash
http://localhost:8102/swagger-ui.html

```

### Actuator

To health and others actuator endpoints access:

```bash
http://localhost:8102/actuator

```

# API

See bellow how use our API.

### Warning

The project just make use of required params, that means, we don't use optional params like: cnt, mode, units, lang.

### Query by City name

To get the weather by city name:

```bash
http://localhost:8102/weather/London
```

or:

```bash
http://localhost:8102/weather/London,uk

```

### Query by ID

To get the weather by ID:

```bash
http://localhost:8102/weather/id/2172797

```

### Query by zip code

To get the weather by zip cod:

```bash
http://localhost:8102/weather/zip/94040,us

```

### Query by lat & lon

To get the weather by lat & lon:

```bash
http://localhost:8102/weather/lat/35/long/139

```

### Query by bbox

To get the weather by bbox:

```bash
http://localhost:8102/weather/bbox/12,32,15,37,10

```

### Historic queries

To get the all historic weather queries:

```bash
http://localhost:8102/weather/queries

```

### Historic queries by City name

To get the historic weather queries by city name:

```bash
http://localhost:8102/weather/queries/London

```
