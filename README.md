# Assignment 01: Reading/Writing objects to and from XML and JSON

## [Introduction to Service Design and Engineering](https://github.com/IntroSDE) | [University of Trento](http://www.unitn.it/) 

TODO 1 paragraph about the code, 1 paragraph about the tasks the code does, and 1 paragraph - how to run it

This repository is the solution to the [first assignment](https://sites.google.com/a/unitn.it/introsde_2015-16/lab-sessions/assignments/assignment-1) of the course IntroSDE of the University of Trento. This assignment cover the following topics:

* ANT
* XML, XPATH & XML Schemas
* Mapping XML (and JSON) to (and from) Objects

### Task of the code

The class *HealthProfileReader* reads the information about the people stored in the file *people.xml*. The file has this structure:

```
<people>
  <person id="0001">
    <firstname>Marlon</firstname>
    <lastname>Jakubowski</lastname>
    <birthdate>1943-03-21T00:00:00+00:00</birthdate>
    <healthprofile>
      <lastupdate>2015-10-08T15:51:28+02:00</lastupdate>
      <weight>83.52</weight>
      <height>1.85</height>
      <bmi>24.40</bmi>
    </healthprofile>
  </person>
  
  <!-- more people -->
</people>
  
```
As soon as the file XML is stored in the memory, there methods in order to performs the following tasks:

* getWeight and getHeight: given the first name and the last name of a person, retrieves the weight and the height of this person;
* print all the details for each person stored in the file people.xml; 
* given the id, return the details of a person;
* print all people fulfilling a condition on the weight (i.e. >90);
* convert the list of Java object Person into XML (marshalling to XML);
* convert XML into a list of Java object Person (unmarshalling from XML).

### Code

*[src/HealthProfileReader.java](src/HealthProfileReader.java)*

*[src/JavatoXML.java](src/JavatoXML.java)*

*[src/XMLtoJava.java](src/XMLtoJava.java)*

*[src/HealthProfileReader.java](src/HealthProfileReader.java)*

*[src/dao/PeopleStore.java](src/dao/PeopleStore.java)*

TODO COSA CONTIENE BUILD.XML



### Installation

In order to execute this project you need the following technologies (in the brackets you see the version used to develop):

* Java (jdk1.8.0)
* ANT (version 1.9.4)

Then, clone the repository:

```
git clone https://github.com/abonte/introsde-2015-assignment-1.git && cd introsde-2015-assignment-1
```

Run the following command to execute all function of the project:
```
ant execute.evaluation
```

