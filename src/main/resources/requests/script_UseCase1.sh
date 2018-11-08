#!/bin/sh

client="Bob@me.fr"
driver="Alice@me.fr"
startAddress="Nice"
endAddress="Sophia"
carV="10" ;
curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"event": "create-user" ,"data": {"name":"Daniel LENEGIXT", "mail":"Alice@me.fr","phone":"0675767778","password":"passwordoverop"}}' "localhost:8080/BBM/USERS"

bedW="5";

bedV="6";

inDays="5"

printf ">> Starting BlablaMove scenario"; sleep 1; printf "."; sleep 1; printf "."; sleep 1; printf ".\n"; echo "";

echo "A.1. Alice is a student who lives in Nice and goes to Sophia in car every day for her studies.\n"
sleep $*;

echo "A.2. On BlablaMove she created one offer to transport things between Nice and Sophia every day, between 7:30 am (Nice) and 8:30 am (Sophia)."
echo "\t-start location"
echo "\t-arrival location"
echo "\t-price"
echo "\t-capacity of car"
echo ""



priceRequest=$(curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\" : \"validate-price\" , \"data\" : {\"data\" : \"x\"}, \"filters\": {\"startAddress\": \"$startAdress\",\"endAddress\": \"$endAddress\",\"maxPrice\": \"0\"}}" "localhost:8080/BBM/OFFERS/")
echo $priceRequest

rangePrice=$(echo $priceRequest | grep -o -P '\[\d* : \d*\]')
minPrice=$(echo $rangePrice | grep -o -P '\[\d*' | grep -o -P '\d*')



echo "{\"event\":\"create-offer\",\"data\":{\"ownerID\":\"$driver\", \"price\": \"$(echo $minPrice*1.1 | bc | cut -f1 -d\.)\", \"startCity\":\"$startAdress\", \"endCity\":\"$endAdress\", \"capacity\":\"$carV\" }}"

curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\":\"create-offer\",\"data\":{\"ownerID\":\"$driver\", \"price\": \"$(echo $minPrice*1.1 | bc | cut -f1 -d\.)\", \"startCity\":\"$startAdress\", \"endCity\":\"$endAdress\", \"capacity\":\"$carV\" }}" "localhost:8080/BBM/OFFERS"


echo "A.3. When she makes the offer, BlablaMove suggests nn amount of point she should charge for the delivery. Based on the number of points that are usually charged for this distance.\n"

sleep $*;
echo "A.4. She can choose to charge the amount BlablaMove suggest her, or she can make a new offer. The number of points she can charge for a delivery will be in a certain range proposed by the system, it can't be too expensive compare to the average offers.\n"

sleep $*;
echo "B.1. Bob is a lambda student who wants to move.\n"
sleep $*;

sleep $*;
echo "B.2. Bob needs to transport his bed from his parents house (Nice) to his new student apartment (Sophia).\n"

sleep $*;
echo "B.3. Bob is a smart guy: he decides to use BlablaMove.\n"

sleep $*;
echo "B.4. He Logins on BlablaMove: he has the right amount of points."
sleep 0.5; printf "Processing Bob login"; sleep 1; printf "."; sleep 1; printf "."; sleep 1; printf ".\n";
echo ""

curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\" : \"identify-user\" , \"data\" : {\"mail\" : \"$client\" , \"password\" : \"passwordoverop\"}}" "localhost:8080/BBM/OFFERS"

echo "\" \""
 
sleep $*;
echo "B.5. He fills a form with following information:"
echo "\t-start location"
echo "\t-arrival location"
echo "\t-bed's size and weight"
echo "\t-move date"
echo "\t-maximum points to spend"
sleep 0.5; printf "Processing form"; sleep 1; printf "."; sleep 1; printf "."; sleep 1; printf ".\n";
echo ""



sleep $*;
echo "B.6. The system proposes a list of results that match his requirements."
sleep 0.5; printf "Retrieving relevant offers"; sleep 1; printf "."; sleep 1; printf "."; sleep 1; printf ".\n";
echo ""

#Bob is a really rich boy he allway have money so he put a filter at 10000 points
searchResultList=$(curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\":\"consult-offers\",\"data\": {\"weight\": \"$bedW\", \"volume\":\"$bedV\", \"date\":\"$inDays\" },\"filters\": {\"weight\": \"$bedV\",\"startAddress\": \"$startAdress\",\"endAddress\": \"$endAdress\",\"maxPrice\": \"10000\"}}" "localhost:8080/BBM/OFFERS")
echo $searchResultList

firstResult=$(echo $searchResultList | jq '.[0]')

sleep $*;
echo "B.7. Bob choses a ride for his bed.\n"

oId=$(echo $firstResult | jq '.offerID')

askValue=$(curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\":\"ask-offer\" ,\"data\": {\"offerID\": $oId,\"buyerID\": \"$user\",\"weight\": \"$bedW\", \"volume\":\"$bedV\", \"date\":\"$inDays\" }}" "localhost:8080/BBM/OFFERS")

echo $askValue


sleep $*;
echo "B.8. The system answers with a recap.\n"

sleep $*;
echo "B.9. Bob confirms.\n"
# curl -s-H "Accept: application/json" -H "Content-type: application /json" -X POST -d

sleep $*;
echo "A.5. One day she receives a mail form BlablaMove : Dimitri wants her to transport a box from Nice to Sophia at a certain date.\n"

offersIdDriver=$(curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\": \"consult-awaiting-offers\" ,\"data\": {\"ownerID\": \"$driver\"}}" "localhost:8080/BBM/OFFERS")

echo $offersIdDriver
offerIdD=$(echo $offersIdDriver | jq '.[0].transactionID')

sleep $*;
echo "A.6. She agrees to do it and confirm on BlablaMove.\n"

curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\": \"confirm-awaiting-offers\" ,\"data\": {\"transactionID\": $offerIdD}}" "localhost:8080/BBM/OFFERS"


sleep $*;
echo "B.10. He receives a confirmation mail from BlablaMove : Charlie can help him to move his things.\n"


sleep $*;
echo ">> Ellipse !\n"

sleep $*;
echo "At the chosen date, Alice goes to Bob house and takes his bed.\n"


transactionID=$(echo $askValue | jq '.transactionID')


curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\": \"claim-receipt\" ,\"data\": {\"transactionID\": $transactionID}}" "localhost:8080/BBM/OFFERS"

curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\": \"confirm-receipt\" ,\"data\": {\"transactionID\": $offerIdD}}" "localhost:8080/BBM/OFFERS"


echo "B.11. Alice goes to Sophia with Bob and leave him and his box at the right place.\n"
sleep $*;

echo "A.7 She confirms on BlablaMove that she delivered the box.\n"

curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\": \"claim-deposit\" ,\"data\": {\"transactionID\": $offerIdD}}" "localhost:8080/BBM/OFFERS"

sleep $*;
echo "B.12. Bob receives a notification that confirms the delivery of his bed.\n"
# curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"event": "claim-deposit" ,"data": {"transactionID": "$transactionID"}}' "localhost:8080/BBM/OFFERS"

sleep $*;

echo "B.13. Bob can now confirm the transaction to BlablaMove.\n"
curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\": \"confirm-deposit\" ,\"data\": {\"transactionID\": $transactionID}}" "localhost:8080/BBM/OFFERS"

echo "16. Finally, after Bob's confirmation, BlablaMove performs a points transaction.\n"
sleep $*

echo "A.8. Alice receives points for the delivery."
#curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"event": "confirm-deposit" ,"data": {"transactionID": "$transactionID"}}' "localhost:8080/BBM/OFFERS"
sleep $*;

echo ">> End of scenario !"
