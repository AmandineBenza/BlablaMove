#!/bin/sh
# Use: ./script_UseCase1.sh 2

#curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"event": "create-user" ,"data": {"name":"Bob", "mail":"Bob@me.fr","phone":"0675767778","password":"passwordoverop"}}' "localhost:8080/BBM/USERS
#curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"event": "create-user" ,"data": {"name":"Alice", "mail":"Alice@me.fr","phone":"0642424242","password":"passwordultraop"}}' "localhost:8080/BBM/USERS

$client = "Bob@me.fr"
$driver = "Alice@me.fr"
$startAdress = "Nice"
$endAdress = "Sophia"

$bedW ="5";

$bedV="6";

$inDays="5"
printf ">> Starting BlablaMove scenario"; sleep 1; printf "."; sleep 1; printf "."; sleep 1; printf ".\n"; echo "";

echo "1. Alice is a student who lives in Nice and goes to Sophia in car every day for her studies.\n"
sleep $*;

echo "2. On BlablaMove she created one offer to transport things between Nice and Sophia every day, between 7:30 am (Nice) and 8:30 am (Sophia)."
echo "\t-start location"
echo "\t-arrival location"
echo "\t-price"
echo "\t-capacity of car"
echo ""

#curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d ''{"event":"create-offer","data":{"ownerID":"Alice@me.fr", "status":"AWAITING_CONFIRMATION", "price":"0", "startCity":"Nice", "endCity":"Sophia", "capacity":"3" }}'' "localhost:8080/BBM/OFFERS"

echo "5. When she makes the offer, BlablaMove suggests nn amount of point she should charge for the delivery. Based on the number of points that are usually charged for this distance.\n"

sleep $*;
echo "6. She can choose to charge the amount BlablaMove suggest her, or she can make a new offer. The number of points she can charge for a delivery will be in a certain range proposed by the system, it can't be too expensive compare to the average offers.\n"

sleep $*;
echo "1. Bob is a lambda student who wants to move.\n"
sleep $*;

sleep $*;
echo "2. Bob needs to transport his bed from his parents house (Nice) to his new student apartment (Sophia).\n"

sleep $*;
echo "3. Bob is a smart guy: he decides to use BlablaMove.\n"

sleep $*;
echo "4. He Logins on BlablaMove: he has the right amount of points."
sleep 0.5; printf "Processing Bob login"; sleep 1; printf "."; sleep 1; printf "."; sleep 1; printf ".\n";
echo ""
# curl -H "Accept: application/json" -H "Content-type: application /json" -X POST -d '{"event" : "identify-user" , "data" : {"mail" : "Bob@me.fr" , "password" : "passwordoverop"}}' "localhost:8080/BBM/OFFERS"


echo "\" \""
 
sleep $*;
echo "5. He fills a form with following information:"
echo "\t-start location"
echo "\t-arrival location"
echo "\t-bed's size and weight"
echo "\t-move date"
echo "\t-maximum points to spend"
sleep 0.5; printf "Processing form"; sleep 1; printf "."; sleep 1; printf "."; sleep 1; printf ".\n";
echo ""

#Bob is a really rich boy he allway have money so he put a filter at 10000 points
$searchResultList = curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\":\"consult-offers\",\"data\": {\"weight\": \"$bedW\", \"volume\":\"$bedV\", \"date\":\"$inDays\" },\"filters\": {\"weight\": \"$bedV\",\"startAddress\": \"$startAdress\",\"endAddress\": \"$endAdress\",\"maxPrice\": \"10000\"}}" "localhost:8080/BBM/OFFERS"

$firstResult = echo $searchResultList | jq '.[0]'


sleep $*;
echo "6. The system proposes a list of results that match his requirements."
sleep 0.5; printf "Retrieving relevant offers"; sleep 1; printf "."; sleep 1; printf "."; sleep 1; printf ".\n";
echo ""


sleep $*;
echo "7. Bob choses a ride for his bed.\n"
$

$askValue = curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\":\"ask-offer\" ,\"data\": {\"offerID\": \"$(echo $firstResult |jq '.offerID'\",\"buyerID\": \"$user\",\"weight\": \"$bedW\", \"volume\":\"$bedV\", \"date\":\"$inDays\" }}" "localhost:8080/BBM/OFFERS"



sleep $*;
echo "8. The system answers with a recap.\n"
# curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"event":"confirm-command" ,"data": {"offerID": "Machin@me.fr1541337161553_50","date":"5","startAddress": "startA","endAddress": "endA","price": "10" }}' "localhost:8080/BBM/OFFERS"

sleep $*;
echo "9. Bob confirms.\n"
# curl -H "Accept: application/json" -H "Content-type: application /json" -X POST -d

sleep $*;
echo "10. He receives a confirmation mail from BlablaMove : Charlie can help him to move his things.\n"
#Non
# curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"event": "confirm-awaiting-offers" ,"data": {"transactionID": "1541544645343"}}' "localhost:8080/BBM/OFFERS"

sleep $*;
echo ">> Ellipse !\n"

sleep $*;
echo "12. At the chosen date, Charlie goes to Bob house and takes his bed.\n"
$transactionID = echo $askValue | jq '.transactionID'
curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\": \"claim-receipt\" ,\"data\": {\"transactionID\": \"$transactionID\"}}" "localhost:8080/BBM/OFFERS"

# curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"event": "confirm-receipt" ,"data": {"transactionID": "1541337287184"}}' "localhost:8080/BBM/OFFERS"

sleep $*;
echo "13. Charlie goes to Sophia.\n"

sleep $*;
echo "14. Bob receives a notification that confirms the delivery of his bed.\n"
# curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"event": "claim-deposit" ,"data": {"transactionID": "1541337287184"}}' "localhost:8080/BBM/OFFERS"

sleep $*;
echo "15. Bob can now confirm the transaction to BlablaMove.\n"
curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\": \"confirm-deposit\" ,\"data\": {\"transactionID\": \"$transactionID\"}}" "localhost:8080/BBM/OFFERS"

sleep $*;
echo "16. Finally, after Bob's confirmation, BlablaMove performs a points transaction.\n"

echo ">> End of scenario !"
