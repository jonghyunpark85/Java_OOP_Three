# Java_OOP_Three

## Flight reservation system 

### Problem
A travel agency is looking to implement a flight reservation system to improve its productivity and services. You are being supplied with an existing backend JAR file. The JAR file contains the following packages: <br/>
•	sait.frs.manager – Holds class responsible for managing flights and reservations. <br/>
•	sait.frs.problemdomain – Holds classes representing a flight and a reservation. <br/>
•	sait.frs.exception – Holds exceptions that are thrown when a problem occurs. <br/> <br/>
The existing JAR file is to be placed in the lib/ directory and imported into your project. The supplied data files are to be placed in the res/ directory. Your task is to create a functional event driven program. It will allow the user to do the following:  <br/> <br/>
1.	Find flights <br/>
•	The travel agent can find a flight by providing the origin airport, the destination airport, and a day of the week the flight is departing. <br/>
2.	Make a reservation <br/>
•	The travel agent can make a flight reservation for a traveler. A reservation code will be generated and assigned to the traveler’s name and citizenship.  <br/>
3.	Find reservations <br/>
•	 The travel agent can find existing flight reservations using the reservation code, airline, and traveler name. The criteria can match any combination of the three fields. <br/>
4.	Modify a reservation <br/>
•	An existing flight reservation can be modified. The traveler name and citizenship can be updated. <br/>
•	An existing flight reservation can be soft-deleted, marking it as inactive and freeing up a seat on the flight. <br/> <br/>
 
### Details
You are supplied with the Javadoc documentation of the implemented backend JAR file containing the manager, problem domain, and exception classes. You are required to use only the backend JAR file to access and manipulate the records in the database. All required functionality exists in the classes in the existing JAR file and can be used with your graphical user interface.  <br/> <br/>
Upon the graphical user interface being launched the user can chose to either search flights and make a reservation or search for and modify a reservation.  <br/> <br/>

#### Find Flights 
The findFlights method receives as its input arguments: the originating airport, the destination airport, and the day of week. The method returns an ArrayList of any matching Flight objects. If no matches are found, the list control will be empty.

#### Make Reservation
When a travel agent selects a flight from the list, the text fields will be populated with the selected flight code, airline, day, time and cost. The travel agent will enter the traveler’s full name and citizenship. The flight code, airline, day, time and cost cannot be edited. An error message will be displayed if a reservation is to be made and no flight is selected, the name field is empty, or the citizenship field is empty.  <br/>
The makeReservation method receives as its input arguments: a Flight object, the travelers name and citizenship. An exception is thrown if the flight is completely booked, or the flight is null, or the name is empty/null, or the citizenship is empty/null. If there are no exceptions thrown a Reservation object is created, saved to the binary file and returned by the method. 

#### Find Reservations
A travel agent can search for an existing reservation that contains the specified reservation code, or airline or traveller’s full name. The list will be populated with any reservations that are found. Each row in the list displays the code of the corresponding reservation record.  <br/>
The findReservation method receives as its input arguments: reservation code, airline and traveler’s full name. The method returns an ArrayList ofmatching Reservation objects. If no matches are found, the list control will be empty.  

#### Update Reservation
When a reservation in the list is selected, the corresponding fields will be populated. These fields will display the:  <br/>
•	Reservation code  <br/>
•	Flight code  <br/>
•	Airline name  <br/>
•	Cost <br/>
•	Name <br/>
•	Citizenship <br/>
•	Status (Active or inactive) <br/> <br/>
The only fields that can be edited are the name, citizenship, and status. None of the other fields can be modified in any way by the user. After the travel agent has made any changes to the reservation, the update button will be clicked. The mutator methods in the Reservation object will be called and an error maybe displayed if an exception occurs. The persist method in the Manager class saves all Reservation and Flight objects to a binary file on the hard drive.

#### Notes
•	The Manager class generates the reservation code.  <br/>
•	Each reservation is for one seat only.  <br/>
•	The name and citizenship do not need to follow any specific format; however, they cannot be empty. <br/>
•	Each problem domain class overrides the toString() method. <br/>
•	Flight codes use the following format: (L meaning Letter, D meaning Digit)
o	LL-DDDD (I.e.: GA-1234)  <br/>
•	Reservation codes use the following format: (L meaning Letter, D meaning Digit)
o	LDDDD (i.e.: I1234)  <br/>
•	Times are in 24-hour format: 
o	HH:MM  <br/>
•	A reservation that is set to inactive is persisted and retained when the program opens again. <br/>
•	The user interface is to be designed using Swing.  <br/>
o	You may use the WindowBuilder plugin for Eclipse. 
