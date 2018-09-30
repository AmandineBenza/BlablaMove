Software Architecture  	         --
Master II - IFI - AL 			 --
2018 - 2019			 			 --
University Nice-Sophia-Antipolis --
								 --
			- BILLING - 		 --
								 --
BENZA Amandine					 --
TOUTAIN Xavier					 --
MEERSMAN Rudy					 --
FORNALI Damien					 --
-----------------------------------


> Week 1 - 27/09/18

--- Brainstorm billing (points):

- Direct travel
- Travel with connection
- Needs to travel right now but don't have points (credit ?)

. To take in account (billing calculation):  
- Weight and space taken [to transport object caracteristics]
- Travel distance (fuel ?) 
	- Speed option computed on above parameters

> Billing after delivery
> Auctioning setup ? (enchères)
> Date of expiration ?
---

--- Technologies && architecture ---

---
Mock: minimum, can be hardcoded
Base: minimal system
GO:   Full system

DB:   database
EP:   extern provider
---

. Minimum architecture for demonstration purposes.
. Focus on billing, mock/base rest

- Users management [Base] 				- DB 
	- Volume assessment ?			

- Road management  [Base]				- Computing + (DB + EP)
- Billing system   [GO]					- Computing
	- Object weight
	- Object size
	- Travel destination

- Insurance system [Mock]				- DB + computing ?
	+ broken ; goods not delivered
				- steal from transporter
				- steal from extern
	- Warnings
		- banishment
-  
- Web service for users [Front-end]
	- Billing computation... [Back-end]

- Language: Java
- Database: SQL Server
- Web service builder: Springboot
- Extern provider (map API) ?


---------- Use Cases ----------

Use Case 1 - Bob : 

* Bob is a lambda student who wants to move
* Bob need to transport his bed from is parents house (Nice) to is new student appartment (Sophia)
* Bob is a smart guy so he decide to use BlablaMove
* He log in on BlablaMove : he has the right amount of points.
* He fill a form where he give some informations : the start point of the things he want to move, the arrival point, the size of his bed, the weight of his bed and when he wants to move (range ?) 
* The system give him a list of results who answer his need. (Number of points/Date/Hours/...)
* Bob chose a ride for his bed
* The system answer him with a recap
* Bob confirm 
* He receive a confirmation mail from BlablaMove : Charlie can help him to move his things
* --- Ellipse ---
* At the chosen date, Charlie goes to Bob house and take his bed
* Charlie goes to Sophia
* Bob receive an notification that confirm the delivery of his bed
* Bob can now confirm the transaction to BlaBlaMove

Use Case 2 - Alice : 

* Alice is a student who lives in Nice and goes to Sophia in car every day for her studies
* She decide to create an account to BlablaMove to help others student to move their things 
* She create her account and specify the type of car (5 places, medium) and her disponibilities. 
* On BlaBlaMove she offers to transport things between Nice and Sophia every day, between 7:30 am (Nice) and 8:30 am (Sophia). 
* One day she receives a mail from BlaBlaMove : Dimitri wants her to transport a box from Nice to Sophia at a certain date.
* She agrees to do it and confirm on BlablaMove.
* --- Ellipse ---
* At the chosen date, she goes to Dimitri's house and take his box in her car 
* She goes to Sophia and leave the box where Dimitri told her to. 
* She confirm on BlablaMove that she delivered the box.
* She receives points for the delivery.

Use Cases variations to handle :

* Bob choose Alice to move his bed but, in the end, Alice isn't available and refuse (She cancel on BlabalMove).
* Bob choose Alice but want to cancel the transfert after confirmation.
* Bob or Alice aren't at the meeting point. 
* The package is not delivered or is broken during the transfert.
* Bob want to move something but he don't have enough points.
* One car isn't enough to get the box from A to B. (Connection necessary) 

----------

- WEEK 1 :  ![#c5f015](https://placehold.it/15/c5f015/000000?text=+)
(30/09/18)

>>>>> What was achieved this week ? 

- We defined use cases (Alice & Bob)
- We defined the services our API will provide to the users (Using Alice & Bob use cases)
- We have begun to talk about the architecture of our API and the technologies we will use.

>>>>> What is planned for the following week ?

- We will continue to think about our architectural and technological choices 
- We will planify our future objectives week by week 

>>>>> What are the blockers and risks ?

- One risk is that our Scope may not be well defined
