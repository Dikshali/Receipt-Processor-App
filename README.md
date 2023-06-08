# Receipt-Processor-App

## Technologies

* Java
* Spring boot
* Gradle

## Getting started with Development Environment

### Running containers

You will need [`docker`](https://docs.docker.com/engine/install/) installed on your machine.

### Building

Build the backend builder container image

    sudo docker build -t receipt-processor .

### Running webapp

    # Runs locally on port 8080
    sudo docker run -p 8080:8080 receipt-processor

### API

Download [`Postman`](https://www.postman.com/downloads/postman-agent/) to hit the endpoints

#### Endpoint: Process Receipts

* Path: `http://localhost:8080/receipts/process`
* Method: `POST`
* Payload: Receipt JSON

#### Endpoint: Get Points

* Path: `http://localhost:8080/receipts/{id}/points`
* Method: `GET`