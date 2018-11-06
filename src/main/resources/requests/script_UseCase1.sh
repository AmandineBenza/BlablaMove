#!/bin/sh
# Use: ./script_UseCase1.sh 2

$user = "machin@me.fr"
$startAdress = "Lyon"
$endAdress = "Nice"
#5 choucroutes.
$bedW ="5";
#6 patates
$bedV="6";
#bullshit
$inDays="5"
printf ">> Starting BlablaMove scenario"; sleep 1; printf "."; sleep 1; printf "."; sleep 1; printf ".\n"; echo "";

echo "1. Bob is a lambda student who wants to move.\n"

sleep $*;
echo "2. Bob needs to transport his bed from his parents house (Nice) to his new student apartment (Sophia).\n"

sleep $*;
echo "3. Bob is a smart guy: he decides to use BlablaMove.\n"

sleep $*;
echo "4. He Logins on BlablaMove: he has the right amount of points."
sleep 0.5; printf "Processing Bob login"; sleep 1; printf "."; sleep 1; printf "."; sleep 1; printf ".\n";
echo ""
# curl -H "Accept: application/json" -H "Content-type: application /json" -X POST -d '{"event" : "identify-user" , "data" : {"mail" : "Machin@me.fr" , "password" : "root"}}' "localhost:8080/BBM/OFFERS"


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
