Overview of the project
-----------------------
This is an automation project designed for [AirBnB](https://www.airbnb.com) site.
It uses the concept of components to manipulate and gather data from the pages

Tests are run using testNg, and each test has a data provider and can be extended to support other cases.

**BONUS:** the project has [allure](https://docs.qameta.io/allure/) support and one can 
generate *cool* reports after running test

How to view the project
-----------------------
The project is built using java with maven support to get the needed packages
Using [intelliJ IDEA](https://www.jetbrains.com/idea) one must open the pom.xml file as a project

How to run the tests
--------------------
Using maven one uses the command `mvn clean test -DsuiteXmlFileName=suite.xml` and will run all the tests 
that are currently developped in the `TestsClass`.

Alternativly, one can open the `TestClass` class and run each test individually using testNg.

How to use allure reports
-------------------------
After running the tests, allure framework will generate `allure-results` folder in the `target` folder.

In order to see the report, run command in terminal/cmd:
1. `mvn allure:serve` this will generate the report and open it in the default browser, but will store it in a temp folder
2. `mvn allure:report` this will generate a local static site with the report (in targer/allure-report) and can be opened manually
