#!/bin/sh
echo "1. Bob is a lambda student who wants to move."
echo ""

sleep $*;
echo "2. Bob needs to transport his bed from his parents house (Nice) to his new student apartment (Sophia)."
echo ""

sleep $*;
echo "3. Bob is a smart guy: he decides to use BlablaMove."
echo ""

sleep $*;
echo "4. He Logins on BlablaMove: he has the right amount of points."
echo ""
curl -H "Accept: application/json" -H "Content-type: application /json" -X POST -d '{"event" : "identify-user" , "data" : {"mail" : "Machin@me.fr" , "password" : "root"}}' "localhost:8080/BBM/OFFERS"

sleep $*;
echo "5. He fills a form with following information:"
echo "\t-start location"
echo "\t-arrival location"
echo "\t-bed's size and weight"
echo "\t-move date"
echo "\t-maximum points to spend"
echo ""
curl -H "Accept: application/json" -H "Content-type: application /json" -X POST -d '{"event" : "validate-price","data": {""}}' "localhost:8080/BBM/OFFERS"

sleep $*;
echo "6.The system give him a list of results who answer his need. (Number of points/Date/Hours/....)"
echo ""
curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"event":"consult-offers","data": {"weight": "5", "volume":"6", "date":"5" },"filters": {"weight": "2","startAddress": "startA","endAddress": "endA","maxPrice": "100"}}' "localhost:8080/BBM/OFFERS"

sleep $*;
echo "7.Bob chose a ride for his bed."
echo ""
curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"event":"ask-offer" ,"data": {"offerID": "Machin@me.fr1541337161553_50","buyerID": "client@daniel.dog","weight": "5", "volume":"6", "date":"5" }}' "localhost:8080/BBM/OFFERS"

sleep $*;
echo "8.The system answer him with a recap."
echo ""


sleep $*;
echo "9.Bob confirm."
echo ""
curl -H "Accept: application/json" -H "Content-type: application /json" -X POST -d

sleep $*;
echo "10.He receive a confirmation mail from BlablaMove : Charlie can help him to move his things."
echo ""
curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"event": "confirm-awaiting-offers" ,"data": {"transactionID": "1541337287184"}}' "localhost:8080/BBM/OFFERS"

sleep $*;
echo "11.---Ellipse---"
echo ""

sleep $*;
echo "12.At the chosen date, Charlie goes to Bob house and take his bed."
echo ""
curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"event": "claim-receipt" ,"data": {"transactionID": "1541337287184"}}' "localhost:8080/BBM/OFFERS"
curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"event": "confirm-receipt" ,"data": {"transactionID": "1541337287184"}}' "localhost:8080/BBM/OFFERS"

sleep $*;
echo "13.Charlie goes to Sophia."
echo ""

sleep $*;
echo "14.Bob receive an notification that confirm the delivery of his bed."
echo ""
curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"event": "claim-deposit" ,"data": {"transactionID": "1541337287184"}}' "localhost:8080/BBM/OFFERS"

sleep $*;
echo "15.Bob can now confirm the transaction to BlablaMove."
echo ""
curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"event": "confirm-deposit" ,"data": {"transactionID": "1541337287184"}}' "localhost:8080/BBM/OFFERS"

sleep $*;
echo "16.After the confirmation from Bob,the BalblaMove collects the points that were needed for this transaction."
