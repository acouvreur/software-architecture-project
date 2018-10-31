# Project's status

Last updated : *Wednesday, October 24*

## Summary

- [Week 4](#week-4)
- [Week 3](#week-3)
- [Week 2](#week-2)
- [Week 1](#week-1)

## Template

```markdown
## Week n 

### What was done

* we did this
* and this

### What is planned for next week

* we will do this
* and this

### Issues

* we tried to do this but we figured out it was not the right way
* we didn't have time to do this

### Risk

* if this fails it won't be good

### RYG

![alt text](./resources/green-flag-transparent.png "Everything is fine !")
![alt text](./resources/yellow-flag-transparent.png "We might focus just a little bit more !")
![alt text](./resources/red-flag-transparent.png "We'll not make it, send help !")

```
## Week 4

### What was done

* Basic interface were implemented
* Each service is implemented on its own branch (see feature/theService), which helps validate code independently
* Each service must be validated/reviewed by the team before getting merged 
* Initial tests are written down and tested through a CI tool
* Accounting, matching(mock), announcement, billing(mock), course are done for a walking skeleton

### What is planned for next week

* Main service tracking being developed by 2 people
* 2 other people on Kafka integration and Cucumber tests
* merge finished services


### Issues

__n/a__

### Risk

This week may be harder than the others, we need to get it done with rigorous tests or we might see new issues later.

### RYG

![alt text](./resources/green-flag-transparent.png "Everything is fine !")

## Week 3

### What was done

* Created the class diagram of the system to see relation
* We have established that we will start by the integration of our services by defining interfaces, etc. (critical part)
* Then we will look at the implementation of tracking (second critical part)

### What is planned for next week

* walking skeleton operational
* unit test
* interaction between services is done
* integration tests is planned
* all tests are already under a CI/CD Tool control (Travis)

### Issues

__n/a__

### Risk

Coordination, rigorous tests are needed, this is where we built the bottom of the application, if we fail this it could be a problem.
But as we have multiple services, the issue can be easily identified and corrected.

### RYG

![alt text](./resources/green-flag-transparent.png "Everything is fine !")

## Week 2 

### What was done

* Scope of the project defined and approved
* Defined first two scenarios and selected the one to be developed 
* Created the component diagram of the system
* Created the planning for the following weeks
* Starting thinking about the possible technologies to be used

### What is planned for next week

* Decide for final technologies to be used
* External and internal interfaces
* Mocked external systems

### Issues
__n/a__

### Risk
__n/a__

### RYG

![alt text](./resources/green-flag-transparent.png "Everything is fine !")



## Week 1

### What was done

* System's architecture as a [use case that exposes interfaces for users, students and drivers](./resources/Software%20Architecture%20Project.vpp)
* Defined:
	* [Use cases](./resources/Student_User_Driver.png) of the system
	* Users of the system: student and driver
	* Scope of our variant
* Started to define the different [scenarios](./resources/scenarios.txt) to be developed
* Started doing an activity diagram for our potential first scenario
* [ORM Diagram](./resources/Class%20Diagram1) that represents our entities (WIP)

#### Scope

- [x] __Account managing__ : account creation, possibility to login/logout and profile management
- [x] __Announcement managing__ : for the both kind of users(student and driver) gives the possibility to post the announcement (accordingly: a need to move goods, an offers of free space for transportation)  and subsequently edit this announcement or remove it 
- [ ] __Matching system goods/routes__ : assign a driver to a good delivery **mocked naive way**
- __Good tracking__ :
	- [x] notifications on start
	- [x] notifications on "waypoints"
	- [x] notifications when another driver picks up the good
	- [x] notifications on incidents
	- [x] notifications on arrival
	- [x] send message to the driver and vice versa
	- [x] receiver can also track the goods
- [ ] __Billing__ : users have credit and can exchange it to create announcement and get matched  **mocked**
- [ ] __Volume assessment__ : estimates the volume of goods **mocked**

#### Personae 

- *Lucas* is a student living in **Sophia** that needs to send his bike to his brother *Charles* in **Paris**
- *Charles* is *Lucas*'s brother living in **Paris**
- *Austin* is a student living in **Nice**, leaving to **Lyon** soon
- *Mila* is a student living in **Lyon**, leaving to **Paris** soon
- *Hope* is a student living in **Nice**, leaving to **Paris** soon

#### Scenarios

##### Good tracking simple route

1. *Lucas* create an announcement stating :
	- Bike 2 wheels, 8kgs
	- Sophia, 10km radius, before October the 12th
	- Paris 10th arrondissement, before December the 24th
	- picked up by *Charles*
2. Systems find a matching route with *Hope*
3. *Lucas* and *Hope* are notified and agree, all the announcements change status
4. *Lucas* meets *Hope* and gives the Bike
5. *Hope* leaves the day after and notifies on the app that he is leaving
6. *Hope* meets *Charles* and proceed to exchange the bike
	1. *Hope* notifies that he has completed his job
	2. *Charles* notifies she has received the bike, *Hope* get points

##### Good tracking combined route

1. *Lucas* create an announcement stating :
	- Bike 2 wheels, 8kgs
	- Sophia, 10km radius, before October the 12th
	- Paris 10th arrondissement, before December the 24th
	- picked up by *Charles*
2. Systems find a matching route combination with *Austin* then *Mila*
3. *Lucas*, *Austin* and *Mila* are notified and agree, all the announcements change status
4. *Lucas* meets *Austin* and gives the Bike
5. *Austin* leaves the day after and notifies on the app that he is leaving
6. *Austin* meets *Mila* and proceed to exchange the bike
	1. *Austin* notifies that he has completed his job
	2. *Mila* notifies she has received the bike, *Austin* get points
7. *Mila* leaves the day after and notifies on the app that he is leaving
8. *Mila* drives through **Dijon** and notifies the waypoint
9. *Mila* arrives at **Paris** and meet *Charles*
	1. *Mila* notifies that he has completed his job
	2. *Charles* notifies she has received the bike, *Mila* get points 


##### Good tracking without notifying departure

##### Good tracking without notifying arrival


### What is planned for next week

* Refine and decide for final scenarios to be developed
* Determine the general architecture
* Decide about the technologies to be used
* Create? Code? external interfaces

### Issues

__n/a__

### Risk

__n/a__

### RYG

![alt text](./resources/yellow-flag-transparent.png "We might focus just a little bit more !")
