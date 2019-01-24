#Documentation of Christmas Beverage Store application

##Specifications

- Java Enterprise Edition project (JavaEE)
- Frameworks used: JPA - Java Persistence API, JMS - Java Message Service, JTA - Java Transaction API
- Build tool: Gradle
- Deployment method: ear
- Server: glassfish 4.1.2
- Database: Postgressql
- Three seperate packages: backend, frontend, shared

##Minimal setup:

- Execute the global build via `gradlew build`
- Deploy the EAR from `ear/build/libs` inside Glassfish, e.g., copy to `%GLASSFISH_HOME%\glassfish\domains\domain1\autodeploy`
- Open `http://localhost:8080/frontend/main.jsp` to see the initial UI of the frontend project
- Open 'http://localhost:8080/frontend/backendUI.jsp' to see the salesman backend interface

##How it works
######Frontend

The frontend package encapsulates all the interconnection between the user(Beverage Store user, Salesman user) and the backend logic i.e the application logic.
For the Beverages Store user:
The Christmas Beverages Store's user plays the role of a buyer on an online shop. He chooses his goods i.e beverages, and the respective quantities through a **frontend user interface build as a jsp file**.
In this case, a JSP file is an HTML page containing a reference to **Java servlets**. The application uses these servlets, which manage the requests coming from the jsp file and the responses for these requests.
There are two jsp files included in this project solution: 1. The Beverage Store's user interface, which places online orders, 2. The backend Salesman interface which manages the Store goods. Both of these jsp-s, point to respective servlets,
called **BeveragesServlet** and **SalesmanServlet**.
-The Beverage Store user interface:
The interface is build on HTML5, styled by Bootstrap and some CSS3.
The meaning of this interface is to provide an easy **order placing procedure** to the users using it.
It contains rows which introduce all attributes of the available beverages. (It is not dynamic and it is not responsive when adding or removing beverages). 
It is only used to illustrate the functionality required. 
As soon as a checkbox is ticked, a beverage is choosen, and therefore the respective quantity should be added as well. This can be done for any of the beverages, there can be chosen more than one. When the button of type submit, **Place Order**, is clicked
the from is submitted. The form is coded in the way that as soon as it is submitted it redirects to the respective servlet(Beverages Servlet), and this is done by specifying the form action by the servlet path. Furthermore, the method of this form is set to POST, which indicates that
as soon as the servlet is reached, it should execute the **doPost** method.

-The BeveragesServlet:
The Beverages Servlet is reached when an order is placed. It contains three methods: doGet, doPost, doDelete. The core functionality of this servlet is established by the doPost method, which is reached by the main.jsp file.
The functionalty of this method is to get an order request, send it to a **JMS Queue** which will be then persised to the database as a new order. Since **beverage** and **orders** are seperated entities on the database, the order placement
can't be done directly with the data retrieved by the interface. Each order which is going to be persist on the database has to fulfil all the attributes of the order entity, which includes: the number of beverages and the order issue date(the id of the order is self-generated).
For this reason, each beverage taken from the interface, will create a Beverage object. Depending on the given quantity, more than one object is going to be created. These Beverage objects will then be added to a list of items,
which will be passed as a parameter when creating the CustomerOrder object. Finally, this order will be send via JMS to the **Message Driven Bean**, 'OrderMessageDrivenBean' and thus persisted into the database.
-The Salesman Backend Interface:
This interface provides the salesmans(more than one) with the ability to manage their goods and also includes a **Bussiness Intelligence** functionality.
This jsp file redirects to the Salesman Servlet, through the form action, and invokes the doPost method of this servlet, by specifying the form method as POST.
It containes all the needed input fields and button which invoke the following methods: 
**1.** Overview of all beverages 
**2.** Add new beverage.
**3.** Assign incentive to a beverage.
**4.** Add new Incentive.
**5.** Edit existing Incentive.
**6.** Delete Incentive.
Also, for the BI functionallity it invokes the following methods:
**7.** A report showing the overall summarized revenue of all orders.
**8.** The revenue of all orders broken down to the different incentive types i.e promotional gift, trial package, no incentive.
All the buttons of this interface are of the type submit, and thus everytime a button is clicked, no matter which, the form redirects to the servlet.
-The Salesman Servlet
Contains only one method: doPost
The method is reached as soon as the form is submitted. 
The servlet's doPost method diferentiate through the buttons of the jsp, meaning, for each request coming from the jsp, 
it gets the button's parameter(and not only) by request.getParameter(name of the button) method and checks for the values of this button. Depending on the value, it invokes
the respective method for that button. Worth to mention: all button's names are the same, only the value parameter differs.
All the methods above are **Enterprise Java Bean**(EJB) methods, declared on the respective ejb interface(shared package) and implemented on the respecive beans(backend package).
-The web.xml file
The web.xml file is a file written in xml which manages the servlet declarations. For each servlet, the servlet name, class/jsp-file, and the url pattern of this servlet is configured. Since there are two servlets
in this project solution, there are two different configurations for each.


######Shared
The shared folder is devided into two different packages: model and persistence.
-Model
The model package contains all the object classes for this projects: Beverage, CustomerOrder, Incentive, PromotionalGift, TrialPackage.
The Incentive class is extended by the PromotionalGift class and the TrialPackage class. Meaning the last two classes inherit from the Incentive class.
The Incentive object has only a name.
All objects have their respective set and get methods.
-Persistence
The persistence package contains the interfaces implemented by EJBs respectively.
Each interface contains the respective EJB methods. The methods are only declared i.e they do not specify a body. The implementation is made inside the respecive bean in the backend.


######Backend 
The backend is the most important folder of this project. It contains all the EJBs and all the implementation of the JPA framework.
It is again divided in two packages: beans and entities.
-Entities
The entities package implements all the entities and the relations between this entities.
The main purpose of this framework is database mapping. For each table on the database, there exists an entity representing that particular table.
Each entity class implements the Serializable 
For an entity to be known as an entity from the JPA, it should be annotated by the @Entity annotation. There exist a lot of other annotations, some of them used on this project to make a 
good and neat database mapping. The @Table annotation is one of the most important, it basically specifies the respecive database table for a given entity.
All ids of all entities which are primary keys are usually declared as self generaged. Columns are mapped by @Column annotation.
Relationships between tables are of four kinds: @OneToOne, @ManyToOne, @OneToany, @ManyToMany. A table may have more than one relation to other tables.
In the case of BeverageEntity, which is the entity representing beverages table in the database, there are two relation, a @ManyToOne relation with the iniciative table in the database, and a @ManyToMany to the Orders table.
We can say that an Entity is the **owning side** if that entity contains the foreign key of the other entity. BeveragesEntity is the owner side, since it contains the init_name column, which is the incentive name, the primary key of the Incentive Entity.
BeverageEntity has a @OneToMany realtion to IncentiveEntity since an incentive can be found in many beverages, but a beverage can only have one incentive assigned.
BeverageEntity has also a @ManyToMany relation to Orders, since an order can have many beverages and a beverage can be found in many orders. Furthermore, a @ManyToMany relation, requires three tables, the first two of the respective entities, and new one created, which contains the primary keys of the entites.
In this case the new table is going to be **ordering_table**.
It is sufficient to provide just one direction of the relation, however, for this project **bidirectional association** is provided. That means, both sides specify the relation, using the respective annotation and rules.
Moreover, in order for the data relationships between entities to not be loaded at once, the FetchType.LAZY is specified for all relations. Collections are only loaded when needed with fatch type LAZY. 
The opposite happens with fetch type EAGER, where collections are loaded at once, even if not needed/used.
In order to perform CRUD operations to this entites, a SQL like query language is used. For this, and for a better understanding of the code, named queries are used. 
@NamedQuery(name, query) is an annotation that lets one declare a named query, to further use it when needed.
-Beans
Enterprise JavaBeans (EJB) is one of several Java APIs for modular construction of enterprise software, as this one.
EJBs are server side software components that encapsulate the application logic. They are managed by the EJB container.
In this project there are four beens, from which three **session beans** and one **message driven bean**.
**The session beans:**
**1.**BeverageManagementBean
**2.**IncentiveManagementBean
**3.**SalesManagementBean
**The Message Driven Bean:**
**4.**OrderMessageDrivenBean
-The BeverageManagementBean
It is of type **Stateless** since:
**1.**The bean state should save no data for the client.
**2.**The bean performs a generic task for all clients invoking it.
This bean implements the **Remote** BeverageManagement interface.
The interface is a Remote interface and not a **Local** one since since the clients run in different machines and different JVMs from the bean.
These clients almost always run on machines other than those on which the GlassFish Server is running. 
This bean, implements the methods of the respecive interface: 
1. create(String n, String i, String m, Double p, Integer q) which
takes all parameters of a Beverage object and creates a new object to persist on the database.
2.viewBeverages() returns a list of all beverages read on the database(uses the respecive named query 'viewAll')
3.assign(String inc_name, String bev_name) which takes the beverage name for which an incentive is going to be assigned and the incentive name.
-The IncentiveManagementBean
It is of type **Stateless** since:
**1.**The bean state should save no data for the client.
**2.**The bean performs a generic task for all clients invoking it.
This bean implements the **Remote** BeverageManagement interface.
The interface is a Remote interface and not a **Local** one since since the clients run in different machines and different JVMs from the bean.
These clients almost always run on machines other than those on which the GlassFish Server is running. 
Methods:
1.createInitiativePromo(String promoname), creates a new incentive of type promotional gift in the database. Takes as parameter only the name of the preferred incentive.
2.createInitiativeTrial(String trialname), creates a new incentive of type trial package in the database. Takes as parameter the name of the trial we want to add.
3.editInitiative(String inc_name, String newname), edits an incentive by updating it. Gets the actual name of the incentive and the new name we want to assign it to, and updates the incentive.
This functionality could also be done by the 'merge' method since it is an update, here however a named query is specified to do the update.
4.deleteInitiative(String inc_name) deletes an incentive from the database. However, if an incentive is assigned to an order it can't be deleted.
To know if an incentive is assigned to an order we first check if that incentive is assigned to any of the beverages, and then, if yes, we check if those beverages
which contain this incentive, are assigned to any order. This, by using the beverage ids and the order ids from the new created table **odering_table**.
To check if an incentive belongs to a beverage, a named query is created (incentiveBelongsToBeverage).
Same for checking if a beverage belongs to an order.(beverageBelongsToOrder).
By using lists, we can add the query results into lists and then iterate thorugh them to check for what we're interested.
Finally, to delete an Incentive the named query 'deleteIncentive' is used.(instead of remove).
-The SalesManagementBean
It is of type **Stateless** since:
**1.**The bean state should save no data for the client.
**2.**The bean performs a generic task for all clients invoking it.
This bean implements the **Remote** BeverageManagement interface.
The interface is a Remote interface and not a **Local** one since since the clients run in different machines and different JVMs from the bean.
These clients almost always run on machines other than those on which the GlassFish Server is running. 
Methods:
1.ordersRevenue() returns a result of type double for the summarized revenue of all all orders. 
First it uses a named query 'allOrderIDs' which saves all order ids and the query result is added to a list.
Then, it iterates through the list, executing the named query 'allPricesOfOrder' which selects the price of all beverages for each order. A join between beverages and ordering_table 
was required.
2.brokenDownRevenue() returns a string, in the form of the statement, revenue for each incentive type.
For this method, first we get all beverages ordered byb the named query 'allBeverageIDsOrdered',add them on a list.
Iterate through this list to find beverages with one of the three types of beverages by executing the named queries: beveragesWithPromoGifts, beveragesWithTrails, beveragesWithoutIncentives respecively.
For each result, we save it on a list and check the price of these beverages(pricesOfSelectedBeverages).
-OrderMessageDrivenBean
This is the only Message Driven Bean of this project. 
It specifies a JMS connnection factory and a Queue, which exist in the server.
It implements the method: onMessage(Message message) which can be seen as a listener that listens to a message that comes. 
It takes this message as a parameter and operates on it. In this project, as soon as a message arrives in the queue (from the BeveragesServlet), it persist the order to the database.


-Transactions(JTA)
For all the implemented EJBs transactions are used. 
For this, an object of UserTransaction is created, and for each CRUD operation, the transaction begins, commits and rollsback if an exception is thrown while trying to execute 
an operation.




