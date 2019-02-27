#!/bin/sh

client="Bob@mail.fr"
driver="Alice@mail.fr"
password="DWpasswOrdL"

startAddress="Nice"
endAddress="Sophia"
carV="10" ;

bedW="5";
bedV="6";
inDays="5";

# See Driver account
printf ">> Retrieving \"$driver\" (driver) account";sleep 1; printf ".";sleep 1;printf ".";sleep 1;printf ".\n";
driverAccount=$(curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\":\"consult-user\",\"data\": {\"mail\": \"$driver\"}}" "localhost:8080/BBM/ADMIN")
driverPoints=$(echo $driverAccount | jq '.pointsAmount')
echo "Driver points: $driverPoints";
printf "\n";

# Pause
sleep 2;

# See Client account
printf ">> Retrieving \"$client\" (client) account";sleep 1; printf ".";sleep 1;printf ".";sleep 1;printf ".\n";
clientAccount=$(curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\":\"consult-user\",\"data\": {\"mail\": \"$client\"}}" "localhost:8080/BBM/ADMIN")
clientPoints=$(echo $clientAccount | jq '.pointsAmount')
echo "Client points: $clientPoints";
printf "\n";