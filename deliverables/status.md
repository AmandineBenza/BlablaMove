Software Architecture<br>  	        
Master II - IFI - AL > 2018 - 2019<br>
University Nice-Sophia-Antipolis / Polytech<br> 

Members:			 
* BENZA Amandine<br>				 
* TOUTAIN Xavier<br>					 
* MEERSMAN Rudy<br>					 
* FORNALI Damien<br>
-----------------------------------


> Week 39 - 27/09/18

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


----------------------
----------------------

> Week 40 & 41 - 03/10/18 

------------ 

Brainstorming RoadMap :

* Done : ![#9BC09D](https://placehold.it/15/9BC09D/000000?text=+)
* To do quickly : ![#8B2E2E](https://placehold.it/15/8B2E2E/000000?text=+)
* To do later : ![#F1C40F](https://placehold.it/15/F1C40F/000000?text=+)


- Week 38 : Choose a topic, build the teams ![#9BC09D](https://placehold.it/15/9BC09D/000000?text=+)
- Week 39 : Define Scope, users. Identify Risk area, investigations, define use cases ![#9BC09D](https://placehold.it/15/9BC09D/000000?text=+)
- Week 40 : General Architecture, technology choice, external interfaces ![#8B2E2E](https://placehold.it/15/8B2E2E/000000?text=+)
	- General Architecture : Will be in architecture.pdf
	- Technological Choices : Java, SpringBoot, SQL Server (JPA plugin)
	- External interfaces : ? 

- Week 41 : Component details, internal interfaces. Ext system mocked ![#8B2E2E](https://placehold.it/15/8B2E2E/000000?text=+) 
- Week 42 : Contiuous integration, Env. built, Walking Skeleton ![#F1C40F](https://placehold.it/15/F1C40F/000000?text=+)
- Week 43 : Main risk mitigated ![#F1C40F](https://placehold.it/15/F1C40F/000000?text=+)
- Week 44 : Coding enough of the rest for the POC ![#F1C40F](https://placehold.it/15/F1C40F/000000?text=+)
- Week 45 : POC complete ![#F1C40F](https://placehold.it/15/F1C40F/000000?text=+)
- Week 46 : // ?

- Week 47 : // PFE & APPRENTICESHIP
- Week 48 : // PFE & APPRENTICESHIP
- Week 49 : // PFE & APPRENTICESHIP
- Week 50 : // PFE & APPRENTICESHIP

- Week 51 :
- Week 1 : 
- Week 2 : Architecture Change ![#8B2E2E](https://placehold.it/15/8B2E2E/000000?text=+)
- Week 3 : Demo with architecture changment ![#8B2E2E](https://placehold.it/15/8B2E2E/000000?text=+)
- Week 4 :
- Week 5 :
- Week 6 :
- Week 7 :
- Week 8 :
- Week 9 :

----------------------

----------------------

> Week 42 - 16/10/18 
 

- Is BBM its own client or client of another company ?


----------------------


- WEEK 39 :  ![#c5f015](https://placehold.it/15/c5f015/000000?text=+)
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

----------------------

- WEEK 40 & 41 :  ![#c5f015](https://placehold.it/15/c5f015/000000?text=+)
(10/10/18)

>>>>> What was achieved this week ? 

- We have modified our use cases to focus more on billing (our part).
- We have defined a road map until the week 45 (POC).
- We have defined some technological choices and a architecture overview.

>>>>> What is planned for the following week ?

- As we say on our roadmap, we will start our implementation of BlaBlaMove.

>>>>> What are the blockers and risks ?

- We have not yet divided the work between us, so it must be done quickly.

----------------------


- WEEK 42 : ![#ffc900](https://placehold.it/15/ffc900/000000?text=+)
(16/10/18)

>>>>> What was achieved this week ? 

 - We have talked a bit more about the billing system 
 - We have separated the tasks we needed to do for this week
 - We have begun our implementation and put in place a preliminary architecture for our project 

>>>>> What is planned for the following week ?

 - We will continue our implementation of BlablaMove
 - We will continue to follow our RoadMap 

>>>>> What are the blockers and risks ?

 - We will need to be careful and to manage/anticipate the scale-up of our project
 - We will also need to determinate a good data architecture 

----------------------

 WEEK 43 : ![#ffc900](https://placehold.it/15/ffc900/000000?text=+)
(24/10/18)

>>>>> What was achieved this week ?
 - Establishment of application user entry points
 - Establishment of internal communication routing basis
 - Establishment of data structures used inside our business logic
 - Establishment of a temporary NoSQL database

>>>>> What is planned for the following week ?
 - Switching from NoSQL database to transactional one
 - Speed up on implementing the core business logic

>>>>> What are the blockers and risks ?
 - Fix sql driver / springboot coupling in order to mount a transactional database
 - Late on code content

 ----------------------

WEEK 44 : ![#ffc900](https://placehold.it/15/ffc900/000000?text=+)
(24/10/18)

>>>>> What was achieved this week ?
 - Improvement of application user entry points
 - Following of internal communication routing
 - Following of data structures used inside our business logic
 - Containerization of application

>>>>> What is planned for the following week ?
 - Speed up on implementing the core business logic
 - Start to truly focus on billing module
 - Create a proper first A to Z small scenario

>>>>> What are the blockers and risks ?
 - Late on code content
 - Blocker/risk is the amount of projects to realize in parallel, it forces us to divide our time between all the projects. Result is that we work a little bit on each project but cannot be really productive.

----------------------

WEEK 45 :  ![#c5f015](https://placehold.it/15/c5f015/000000?text=+)
(07/11/18)

>>>>> What was achieved this week ?
 - Finalisations of Bob and Alice scenarios
 - Realisation of scripted scenarios (to finish)
 - Following of internal communication routing
 - Start of presentation and demo
 
----------------------

POC DEMO 

----------------------

WEEK 51 :  ![#c5f015](https://placehold.it/15/c5f015/000000?text=+)
(21/12/2018) 

>>>>> What was achieved this week ?
- Beginning of the consideration of the change imposed by the client 
-> Change from NoSQL to SQL  (MongoDB to MySQL)
-> Idea of system of data duplication (Master/Slave) 
-> Idea of assembling financial operations within a single SQL request for a simplified rollback (credit and debit)

>>>>> What is planned for the following week ?
- Establish a new architecture taking the changes into account 
- Refactor the current database-related code 
- Validate that the previous use cases are still working 

>>>>> What are the blockers and risks ?
- There's a risk that the transition from Mongo to MySQL  breaks our current use cases

----------------------

WEEK 2 :  ![#ffc900](https://placehold.it/15/ffc900/000000?text=+)
(11/01/2019)


----------------------

WEEK 3 :  
(18/01/2019)
