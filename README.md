# My Personal Project: CPSC 210

## Emma's Travel Guide to Airlines

### Task 2

- **What will the application do?**
  
<p> This application will allow the user to create a new flight to track the list of flights that are scheduled to 
departure with specified details. Additionally, it will give the opportunity for an airline to have an organized flight 
management system to easily create, add, remove, search and view all the upcoming flights to be of a significant guide 
for travel. </p>

- **Who will use it?** 

<p> This application is open to any interested airlines that desire to enhance airline systems for the benefit of 
creating an environment of organization. <p/>

 - **Why is this project of interest to you?**

<p> This project is of interest to me because one of my aspirations in my future career is to work in the field
of airline traffic. I've always loved to travel with both my family and friends, and for most travellers, vacations 
always start on picking a destination and transportation to get there. From then on, certain sightseeing locations flow 
from there that are of interest. Therefore, with the interconnection of both my aspirations and hobbies, this
project was seemingly a perfect fit. </p>


### Task 3

 **User Stories** 


1. As a user, I want to be able to create a new flight for an airline with details of its name, destination, duration, 
date, time and maximum number of seats in the plane. 

2. As a user, I want to be able to add a flight for the specific airline to a list of scheduled flights. 

3. As a user, I want to be able to search the list of scheduled flights that are available for a specific destination 
    that is of interest.

4. As a user, I want to be able to remove a flight from the list of scheduled flights. 

5. As a user, I want to be able to view the details of one given flight specified from the listed schedule. 

6. As a user, I want to be able to save my list of scheduled flights to file. 

7. As a user, I want to be able to reload my list of scheduled flights from the file. 

8. As a user, I want to be able to view all saved flights within the list of scheduled flights that have been reloaded.

### Phase 4: Task 2

**Event Logging Sample**

Wed Nov 24 21:16:41 PST 2021
Added new flight: The Big Apple to List of Flights

Wed Nov 24 21:16:41 PST 2021
Added new flight: New York New York! to List of Flights

Wed Nov 24 21:16:41 PST 2021
Added new flight: Emma's Flight to List of Flights

Wed Nov 24 21:16:51 PST 2021
Removed flight: Emma's Flight from List of Flights

Wed Nov 24 21:17:04 PST 2021
Removed flight: The Big Apple from List of Flights

Wed Nov 24 21:17:44 PST 2021
Added new flight: Hello Moscow! to List of Flights

Wed Nov 24 21:17:54 PST 2021
Added new flight: New York New York! to List of Flights

Wed Nov 24 21:17:54 PST 2021
Added flight: New York New York! to a new list of the given destination

Wed Nov 24 21:17:54 PST 2021
Added new flight: New York New York! to List of Flights

Wed Nov 24 21:17:54 PST 2021
Added flight: New York New York! to a new list of the given destination

Wed Nov 24 21:18:02 PST 2021
Removed flight: New York New York! from List of Flights

Process finished with exit code 0

### Phase 4: Task 3

**UML Class Diagram - Project Design Reflection**

<p> If I had more time to work on this project, I would refactor my GUI class mainly. Within my Airline GUI class, I 
have incorporated all code from setting up a JFrame to methods on all actions that are controlled through the user, with 
inner classes within the class itself as well. What I have thought about doing in order to improve the design of my 
project is to either: 

1. Divide into two sub-classes, one where the class is responsible for the design of my project. This ranges from color,
adding panels, buttons, frame and all the aspects of adding specific aesthetic features. The second class would be 
responsible for control. Meaning, it would include such code for action listeners, like what the program will do when
a certain button is clicked, whether that is adding a flight or removing a flight to/from a list of flights 
(for example).

2. Create an abstract class, which could be called "Tools", for example. Therefore, for each user story - add, remove,
view, search, save, load all flights will have its own class. Each of the classes will extend this "Tools" abstract 
class (like AddTool), and be responsible within its own class how it would behave in the case of where the user clicks
its corresponding button. The Airline GUI and Main class within the UI package of my project would still exist and be 
responsible for instantiating the graphical interface, and designing the components within the window.


In these cases, it would be beneficial in such ways:

- Clearer to the eye - design. It'll appear aesthetically clear in the structure of my project, 
since each class is responsible for its own task for the GUI when prompted by the user.

- Easier to debug. Since rather than having all the code for the entire GUI in one class basically, in this case 
since it is all divided to its own classes, it will be a lot easier to catch errors and debug code as each class itself
will be designated to a specific "duty".</p>


 


