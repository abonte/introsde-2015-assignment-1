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

1. getWeight and getHeight: given the first name and the last name of a person, retrieves the weight and the height of this person;
2. print all the details for each person stored in the file people.xml; 
3. given the id, return the details of a person;
4. print all people fulfilling a condition on the weight (i.e. >90);
5. convert the list of Java object Person into XML (marshalling to XML);
6. convert XML into a list of Java object Person (unmarshalling from XML);
7. convert the list of Java object Person into JSON (marshalling to JSON).

### Installation

In order to execute this project you need the following technologies (in the brackets you see the version used to develop):

* Java (jdk1.8.0)
* ANT (version 1.9.4)

Then, clone the repository:

```
git clone https://github.com/abonte/introsde-2015-assignment-1.git && cd introsde-2015-assignment-1
```

Run the following command to execute all function of the project (except task 1):
```
ant execute.evaluation
```

### Usage
This project use an [ant build script](build.xml) to automate the compilation and the execution of specific part of the Java application.
```
ant execute.evaluation
```
This command performs the following action:

* download and install ivy (dependency manager) and resolve the dependencies;
* create a build directory and compile the code in the src folder;
* call others target defined in the build file: `execute.printPeople`, `execute.printPerson`, `execute.getPersonByWeight`, `execute.JavatoXML`, `execute.XMLtoJava`, `execute.JavatoJson`.

You can also execute specific task. Before that you have to execute
```
ant compile
```
and then one of the following command:

* `ant execute.printPeople` performs task 2;
* `ant execute.printPerson` performs task 3 with id=5;
* `ant execute.getPersonByWeight` performs task returning all person with the weight > 90;
* `ant execute.JavatoXML` performs task 5;
* `ant execute.XMLtoJava` performs task 6;
* `ant execute.JavatoJson` performs task 7.

TODO METTERE DI GENERATE

### Code

*[src/HealthProfileReader.java](src/HealthProfileReader.java)*: contains the code to execute task 1,2,3,4 of the previous list;

*[src/JavatoXML.java](src/JavatoXML.java)*: class to execute task 5;

*[src/XMLtoJava.java](src/XMLtoJava.java)*: class to execute task 6;

*[src/dao/PeopleStore.java](src/dao/PeopleStore.java)* contains the data access object;

*[src/JavatoJson.java](src/JavatoJson.java)*: class to execute task 7. 