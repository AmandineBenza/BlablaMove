#!/bin/sh
# Use: ./script_UseCase2.sh 2

printf ">> Starting BlablaMove scenario"; sleep 1; printf "."; sleep 1; printf "."; sleep 1; printf ".\n"; echo "";

echo "1. Alice is a student who lives in Nice and goes to Sophia in car every day for her studies.\n"

sleep $*;
echo "2. She decides to create an account in order to help others students to move their stuff.\n"

sleep $*;
echo "3. She specifies the type of car (small, medium) and her disponibilities.\n"

#curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"event":"create-user","data":{"user":}}' "localhost:8080/BBM/OFFERS"

sleep $*;
echo "4. On BlablaMove she offers to transport things between Nice and Sophia every day, between 7:30 am (Nice) and 8:30 am (Sophia)."
echo "\t-start location"
echo "\t-arrival location"
echo "\t-price"
echo "\t-capacity of car"
echo ""

#curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d ''{"event":"create-offer","data":{"ownerID":"Machin@me.fr", "status":"AWAITING_CONFIRMATION", "price":"0", "startCity":"startA", "endCity":"endA", "capacity":"3" }}'' "localhost:8080/BBM/OFFERS"

sleep $*;
echo "5. When she makes the offer, BlablaMove suggests nn amount of point she should charge for the delivery. Based on the number of points that are usually charged for this distance.\n"

sleep $*;
echo "6. She can choose to charge the amount BlablaMove suggest her, or she can make a new offer. The number of points she can charge for a delivery will be in a certain range proposed by the system, it can't be too expensive compare to the average offers.\n"

#TODO curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"event":"accept-system-price","data":{"response":"yes"}}' "localhost:8080/BBM/OFFERS"

sleep $*;
echo "7. One day she receives a mail form BlablaMove : Dimitri wants her to transport a box from Nice to Sophia at a certain date.\n"

#curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"event": "consult-awaiting-offers" ,"data": {"ownerID": "Machin@me.fr"}}' "localhost:8080/BBM/OFFERS"

sleep $*;
echo "8. She agrees to do it and confirm on BlablaMove.\n"

#curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"event": "confirm-awaiting-offers" ,"data": {"transactionID": "1541337287184"}}' "localhost:8080/BBM/OFFERS"

sleep $*;
echo "9. >> Ellipse !\n"

sleep $*;
echo "10. At the chosen date, she goes to Dimitri's house and take his box in her car.\n"

#curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"event": "claim-receipt" ,"data": {"transactionID": "1541337287184"}}' "localhost:8080/BBM/OFFERS"
#curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"event": "confirm-receipt" ,"data": {"transactionID": "1541337287184"}}' "localhost:8080/BBM/OFFERS"

sleep $*;
echo "11. She goes to Sophia and leave the box where Dimitri told her to.\n"

sleep $*;
echo "12. She confirms on BlablaMove that she delivered the box.\n"

#curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"event": "claim-deposit" ,"data": {"transactionID": "1541337287184"}}' "localhost:8080/BBM/OFFERS"

sleep $*;
echo "13. She receives points for the delivery."

#curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"event": "confirm-deposit" ,"data": {"transactionID": "1541337287184"}}' "localhost:8080/BBM/OFFERS"