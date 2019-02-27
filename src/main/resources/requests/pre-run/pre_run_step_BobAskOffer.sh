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

#Bob1 veut confirmer sa commande => Il faut que la demande de Bob apparaisse meme si il y a eu un crash de BD avant sa validation 
#recap=$(curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\":\"confirm-command\" ,\"data\": {\"offerID\": $oId,\"date\":\"$inDays\", \"startAddress\":\"$startAddress\", \"endAddress\":\"$endAddress\",\"price\":\"$price\" }, \"identification\":{\"userID\":\"$client\"}}" "localhost:8080/BBM/OFFERS")