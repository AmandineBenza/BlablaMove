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

- ![#c5f015](https://placehold.it/15/c5f015/000000?text=+) `#c5f015`

- Web service for users [Front-end]
	- Billing computation... [Back-end]

- Language: Java
- Database: SQL Server
- Web service builder: Springboot
- Extern provider (map API) ?
