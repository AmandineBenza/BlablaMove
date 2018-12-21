#!/bin/sh

client="Bob@mail.fr"
driver="Alice@mail.fr"

curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\": \"create-user\" ,\"data\": {\"name\":\"Alice\", \"mail\":\"$driver\",\"phone\":\"0675767778\",\"password\":\"DWpasswOrdL\"}}" "localhost:8080/BBM/USERS";

curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\": \"create-user\" ,\"data\": {\"name\":\"Bob\", \"mail\":\"$client\",\"phone\":\"0642424242\",\"password\":\"DWpasswOrdL\"}}" "localhost:8080/BBM/USERS";
