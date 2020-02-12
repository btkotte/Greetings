# Greeting API

## Requirements

For building and running the application you need:

- [Spring Boot 2.2.4](https://spring.io/blog/2020/01/20/spring-boot-2-2-4-released)
- [JDK 11](https://www.oracle.com/technetwork/java/javase/downloads/jdk11-downloads-5066655.html)
- [Maven 3](https://maven.apache.org)
- [Docker](https://www.docker.com)

## Running the application locally
you can run on CLI using the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like:

```shell
mvn spring-boot:run
```
The server is configured to starts on port 5000

## Building and Running with docker

Start docker and from the project root directory, execute in terminal

```shell
mvn clean verify
docker build -t greetingapp .
docker run -p 5000:5000 greetingapp
```

## REST API
In this API, a greeting message is sent based on the account and type

```http
GET /greeting?account=business&type=big
GET /greeting?account=personal&id=123
```

| Parameter | Type | Description |
| :--- | :--- | :--- |
| `account` | `string` | **Required**. Allowed values: business, personal |
| `type` | `string` | **Required if account=business**. Allowed values: big, small |
| `id` | `long` | **Required if account=personal**. The userid number|

## Error Responses

Many API endpoints return the JSON representation of error
```javascript
{
    "status" : int,
    "reason" : string,
    "message" : string
}
```

The `status` attribute contains the http status code

The `reason` attribute describes short information regarding the error

The `message` attribute contains detailed error message

## Status Codes

The following status codes are returned in the API:

| Status Code | Description |
| :--- | :--- |
| 200 | `OK` |
| 400 | `BAD REQUEST` |
| 500 | `INTERNAL SERVER ERROR` |
