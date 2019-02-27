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

#Alice1 s'identifie
curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\": \"identify-user\" ,\"data\":{\"mail\":\"$driver\",\"password\":\"$password\"}}" "localhost:8080/BBM/USERS";

#Alice1 veut creer une offre et valider son prix
priceRequest=$(curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\" : \"validate-price\" , \"data\" : {\"data\" : \"x\"}, \"filters\": {\"startAddress\": \"$startAddress\",\"endAddress\": \"$endAddress\",\"maxPrice\": \"0\"}, \"identification\":{\"userID\":\"$driver\"}}" "localhost:8080/BBM/OFFERS/" | egrep -o 'F.*' );


rangePrice=$(echo $priceRequest | egrep -o '\[[0123456789]+ : [0123456789]*\]')


minPrice=$(echo $rangePrice | egrep -o  '\[[0123456789]+' | egrep -o  '[0123456789]+') 

#Alice1 cree une offre
curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\":\"create-offer\",\"data\":{\"ownerID\":\"$driver\", \"price\": \"$(echo $minPrice*2.1 | bc | cut -f1 -d\.)\", \"startCity\":\"$startAddress\", \"endCity\":\"$endAddress\", \"capacity\":\"$carV\"}, \"identification\":{\"userID\":\"$driver\"}}" "localhost:8080/BBM/OFFERS"

#Bob1 s'identifie
curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\": \"identify-user\" ,\"data\":{\"mail\":\"$client\",\"password\":\"$password\"}}" "localhost:8080/BBM/USERS";

#Bob1 consulte les offres
searchResultList=$(curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\":\"consult-offers\",\"data\": {\"weight\": \"$bedW\", \"volume\":\"$bedV\", \"date\":\"$inDays\" },\"filters\": {\"weight\": \"$bedV\",\"startAddress\": \"$startAddress\",\"endAddress\": \"$endAddress\",\"maxPrice\": \"10000\"},\"identification\":{\"userID\":\"$client\"}}" "localhost:8080/BBM/OFFERS")


firstResult=$(echo $searchResultList | jq '.[0]')


oId=$(echo $firstResult | jq '.offerID')

#Bob1 demande une offre
askValue=$(curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\":\"ask-offer\" ,\"data\": {\"offerID\": $oId,\"buyerID\": \"$client\",\"weight\": \"$bedW\", \"volume\":\"$bedV\", \"date\":\"$inDays\" }, \"identification\":{\"userID\":\"$client\"}}" "localhost:8080/BBM/OFFERS")


price=$(echo $askValue | jq '.finalPrice')

#Bob1 confirme sa commande
recap=$(curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\":\"confirm-command\" ,\"data\": {\"offerID\": $oId,\"date\":\"$inDays\", \"startAddress\":\"$startAddress\", \"endAddress\":\"$endAddress\",\"price\":\"$price\" }, \"identification\":{\"userID\":\"$client\"}}" "localhost:8080/BBM/OFFERS")

#Alice1 consulte ses demandes en attente
offersIdDriver=$(curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\": \"consult-awaiting-offers\" ,\"data\": {\"ownerID\": \"$driver\"}, \"identification\":{\"userID\":\"$driver\"}}" "localhost:8080/BBM/OFFERS")


offerIdD=$(echo $offersIdDriver | jq '.[0].transactionID')

#Alice1 confirme la demande de Bob1
curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\": \"confirm-awaiting-offers\" ,\"data\": {\"transactionID\": $offerIdD}, \"identification\":{\"userID\":\"$driver\"}}" "localhost:8080/BBM/OFFERS"


transactionID=$(echo $askValue | jq '.transactionID')

#Bob1 dit qu'il a confie son colis a Alice1
curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\": \"claim-receipt\" ,\"data\": {\"transactionID\": $transactionID}, \"identification\":{\"userID\":\"$client\"}}" "localhost:8080/BBM/OFFERS"

#Alice1 confirme qu'elle a bien recupere le colis de Bob1
curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\": \"confirm-receipt\" ,\"data\": {\"transactionID\": $offerIdD}, \"identification\":{\"userID\":\"$driver\"}}" "localhost:8080/BBM/OFFERS"

#Alice1 dit qu'elle a bien livre le colis de Bob1
curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\": \"claim-deposit\" ,\"data\": {\"transactionID\": $offerIdD}, \"identification\":{\"userID\":\"$driver\"}}" "localhost:8080/BBM/OFFERS"

#Bob1 n'a plus qu'a confirmer qu'Alice1 a bien livre son colis
#curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\": \"confirm-deposit\" ,\"data\": {\"transactionID\": $transactionID}, \"identification\":{\"userID\":\"$client\"}}" "localhost:8080/BBM/OFFERS"