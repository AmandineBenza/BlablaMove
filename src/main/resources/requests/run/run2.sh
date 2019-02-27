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

# Pause
sleep 2;

# Get transaction ID
transaction=$(curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\":\"consult-transaction\",\"data\": {\"ownerID\": \"$driver\", \"buyerID\": \"$client\", \"itemVolume\": \"$bedV\", \"itemWeight\": \"$bedW\"}}" "localhost:8080/BBM/ADMIN")
transactionID=$(echo $transaction | jq '.transactionID')

# Last step: Bob confirmation, need to identify first
bobIdentification=$(curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\": \"identify-user\" ,\"data\":{\"mail\":\"$client\",\"password\":\"$password\"}}" "localhost:8080/BBM/USERS");
printf ">> Bob confirms item reception";sleep 1; printf ".";sleep 1;printf ".";sleep 1;printf ".\n";
bobConfirmation=$(curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\": \"confirm-deposit\" ,\"data\": {\"transactionID\": $transactionID}, \"identification\":{\"userID\":\"$client\"}}" "localhost:8080/BBM/OFFERS");
echo "Bob confirms he receives the item !\n";
echo "BlablaMove response: \n$bobConfirmation\n";

