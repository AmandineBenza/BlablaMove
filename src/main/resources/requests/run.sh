#!/bin/sh

client="Bob@mail.fr"
driver="Alice@mail.fr"
startAddress="Nice"
endAddress="Sophia"
carV="10" ;

bedW="5";
bedV="6";
inDays="5";

printf ">> Starting BlablaMove scenario"; sleep 1; printf "."; sleep 1; printf "."; sleep 1; printf ".\n"; echo "";

echo "A.1. Alice creates one offer to transport items between Nice and Sophia every day, between 7:30 am (Nice) and 8:30 am (Sophia)."
echo "She has to inquire the following information:";
echo "\t- start location"
echo "\t- arrival location"
echo "\t- price"
echo "\t- capacity of car\n"

priceRequest=$(curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\" : \"validate-price\" , \"data\" : {\"data\" : \"x\"}, \"filters\": {\"startAddress\": \"$startAdress\",\"endAddress\": \"$endAddress\",\"maxPrice\": \"0\"}}" "localhost:8080/BBM/OFFERS/" | grep -o -P 'F.*' );

echo $priceRequest
echo "";

rangePrice=$(echo $priceRequest | grep -o -P '\[\d* : \d*\]')
minPrice=$(echo $rangePrice | grep -o -P '\[\d*' | grep -o -P '\d*')

curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\":\"create-offer\",\"data\":{\"ownerID\":\"$driver\", \"price\": \"$(echo $minPrice*2.1 | bc | cut -f1 -d\.)\", \"startCity\":\"$startAddress\", \"endCity\":\"$endAddress\", \"capacity\":\"$carV\" }}" "localhost:8080/BBM/OFFERS"

echo "\n";

echo "B.1. Bob Logins on BlablaMove: he has the right amount of points."
sleep 0.5; printf "Processing Bob login"; sleep 1; printf "."; sleep 1; printf "."; sleep 1; printf ".\n";
echo ""

curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\" : \"identify-user\" , \"data\" : {\"mail\" : \"$client\" , \"password\" : \"DWpasswOrdL\"}}" "localhost:8080/BBM/OFFERS"
 
sleep $*;
echo "B.2. He fills a form with following information:"
echo "\t- start location"
echo "\t- arrival location"
echo "\t- bed's size and weight"
echo "\t- move date"
echo "\t- maximum points to spend"
sleep 0.5; printf "Processing form"; sleep 1; printf "."; sleep 1; printf "."; sleep 1; printf ".\n";
echo ""


sleep $*;
echo "B.3. The system proposes a list of results that match his requirements."
sleep 0.5; printf "Retrieving relevant offers"; sleep 1; printf "."; sleep 1; printf "."; sleep 1; printf ".\n";
echo ""

#Bob is a really rich boy he allways have money so he put a filter at 10000 points
searchResultList=$(curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\":\"consult-offers\",\"data\": {\"weight\": \"$bedW\", \"volume\":\"$bedV\", \"date\":\"$inDays\" },\"filters\": {\"weight\": \"$bedV\",\"startAddress\": \"$startAddress\",\"endAddress\": \"$endAddress\",\"maxPrice\": \"10000\"}}" "localhost:8080/BBM/OFFERS")
echo $searchResultList

firstResult=$(echo $searchResultList | jq '.[0]')

sleep $*;
echo "",
echo "B.4. Bob choses a ride for his bed and confirm."

oId=$(echo $firstResult | jq '.offerID')
echo "";

askValue=$(curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\":\"ask-offer\" ,\"data\": {\"offerID\": $oId,\"buyerID\": \"$client\",\"weight\": \"$bedW\", \"volume\":\"$bedV\", \"date\":\"$inDays\" }}" "localhost:8080/BBM/OFFERS")

echo $askValue
echo "\n";

sleep $*;
echo "B.5. The system answers with a recap."

price=$(echo $askValue | jq '.finalPrice')
echo "";
recap=$(curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\":\"confirm-command\" ,\"data\": {\"offerID\": $oId,\"date\":\"$inDays\", \"startAddress\":\"$startAddress\", \"endAddress\":\"$endAddress\",\"price\":\"$price\" }}" "localhost:8080/BBM/OFFERS")
<<<<<<< HEAD

echo $recap

=======
echo $recap;
echo "";
>>>>>>> branch 'master' of https://github.com/Damoy/BlablaMove.git

sleep $*;

echo "A.2. One day she receives a mail form BlablaMove : Bob wants her to transport a box from Nice to Sophia at a certain date.\n"

offersIdDriver=$(curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\": \"consult-awaiting-offers\" ,\"data\": {\"ownerID\": \"$driver\"}}" "localhost:8080/BBM/OFFERS")

echo $offersIdDriver
offerIdD=$(echo $offersIdDriver | jq '.[0].transactionID')

echo "\n";
sleep $*;
echo "A.3. She agrees to do it and confirm on BlablaMove.\n"

curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\": \"confirm-awaiting-offers\" ,\"data\": {\"transactionID\": $offerIdD}}" "localhost:8080/BBM/OFFERS"


sleep $*;
echo "";
echo "At the chosen date, Alice goes to Bob house and takes his bed."

transactionID=$(echo $askValue | jq '.transactionID')
echo "";

curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\": \"claim-receipt\" ,\"data\": {\"transactionID\": $transactionID}}" "localhost:8080/BBM/OFFERS"

curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\": \"confirm-receipt\" ,\"data\": {\"transactionID\": $offerIdD}}" "localhost:8080/BBM/OFFERS"

echo "";
echo "A.4. Alice confirms on BlablaMove that she delivered the box.\n"

curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\": \"claim-deposit\" ,\"data\": {\"transactionID\": $offerIdD}}" "localhost:8080/BBM/OFFERS"

sleep $*;

echo "";
echo "B.6. Bob can now confirm the transaction to BlablaMove.\n"
curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\": \"confirm-deposit\" ,\"data\": {\"transactionID\": $transactionID}}" "localhost:8080/BBM/OFFERS"

echo "\n";
sleep $*;

echo ">> End of scenario !\n"
