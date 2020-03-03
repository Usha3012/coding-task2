# Zenhome Task 2

## Overview

- This is an containerized application . The container contains spring boot REST application
  and POSTGRESS Database.
  
  ![image overview](overview.jpg)
  
## Sequence Diagram overview

- The application is a 3-layer architecture with controller,service and repository layer
- A Typical interaction in the application looks like below

![image sequence](Sequence.jpg)


## ER-Diagram

![image er](ER-diagram.jpg)

## Class Diagram

![image class-diagram](Class-Diagram.jpg)

## Building Instruction

- Java 11+
- Docker
- Run ```mvn clean package spring-boot:repackage```
- Run ```docker-compose build```
- RUN ```docker-compose up```

