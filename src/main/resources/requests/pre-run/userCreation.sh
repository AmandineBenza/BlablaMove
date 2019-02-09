#!/bin/sh

password="DWpasswOrdL"

drivername="Alice"
clientname="Bob"
mail="@mail.fr"

for i in `seq 1 $1`;
do
    driver="$drivername$i$mail"
    curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\": \"create-user\" ,\"data\": {\"name\":\"Alice\", \"mail\":\"$driver\",\"phone\":\"0675767778\",\"password\":\"$password\"}}" "localhost:8080/BBM/USERS";
done

for i in `seq 1 $2`;
do
    client="$clientname$i$mail"
    curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\": \"create-user\" ,\"data\": {\"name\":\"Bob\", \"mail\":\"$client\",\"phone\":\"0642424242\",\"password\":\"$password\"}}" "localhost:8080/BBM/USERS";  
done