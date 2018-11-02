#!/bin/sh

curl -H 'Accept: application/json' -H 'Content-type: application/json' -X POST \
-d "{'event':'create-user','data':{'mail':'itsdamoy@gmail.com','name':'Damoy',\
'address':'Zaap Astrub','phone':'666','password':'pass'}}" \
'localhost:8080/BBM/USERS/'
