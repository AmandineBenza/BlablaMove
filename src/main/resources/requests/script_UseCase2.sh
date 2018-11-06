#!/bin/bash
echo "1.Alice is a student who lives in Nice and goes to Sophia in car every day for her studies."
echo ""

sleep $*;
echo "2.She decide to create an account to BlablaMove to help others student to move their things."
echo ""

sleep $*;
echo "3.She create her account and specify the type of car(5 places,medium) and her disponibilities."
echo ""

sleep $*;
echo "4.On BlablaMove she offers to transport things between Nice and Sophia every day, between 7:30 am (Nice) and 8:30 am (Sophia)."
echo ""
curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{"event":"create-offer","data":{"ownerID":"Machin@me.fr", "status":"AWAITING_CONFIRMATION", "price":"0", "startCity":"startA", "endCity":"endA", "capacity":"3" }}" "localhost:8080/BBM/OFFERS"

sleep $*;
echo "5.When she makes the offer, BlablaMove suggests a amount of point she should charge for the delivery. (Based on the number of points that are usually charged for this distance.)"
echo ""


sleep $*;
echo "6.She can choose to charge the amount BlablaMove suggest her, or she can make a new offer. The number of points she can charge for a delivery will be in a certain range proposed by the system, it can't be to expensive compare to the average offers."
echo ""

sleep $*;
echo "7.One day she receives a mail form BlablaMove : Dimitri wants her to transport a box from Nice to Sophia at a certain date."
echo ""

sleep $*;
echo "8.She agrees to do it and confirm on BlablaMove."
echo ""

sleep $*;
echo "9.---Ellipse---"
echo ""

sleep $*;
echo "10.At the chosen date, she goes to Dimitri's house and take his box in her car."
echo ""

sleep $*;
echo "11.She goes to Sophia and leave the box where Dimitri told her to."
echo ""

sleep $*;
echo "12.She confirm on BlablaMove that she delivered the box."
echo ""

sleep $*;
echo "13.She receives points for the delivery."