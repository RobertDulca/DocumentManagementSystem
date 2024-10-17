# ExerciseChecker for Java

Command Line application to automatically run exercise-checks in FHTW programming courses in Java.

**Remarks**: This project is a fork of [walliscb-projectspace/ExerciseChecker](https://git.technikum-wien.at/walliscb-projectspace/exercisechecker) 
but implemented as console-application only, without Spring Boot framework.

## Pre-requisites

Ensure that all software is also directly executable per command-line, so check the correct settings of the PATH.
* Java 17 JDK 
* Git CLI 
* Maven

## Setup

* Create a config.json file based on the [config.sample.json](config.sample.json) file.  
  (defines necessary GitHub username and access-token; output directory for all the downloaded files)

### Configuration

The tool works with configuration-files as JSON or YAML files 
which have to be prepared prior to execute the tool:
* **Assignments**: [assignments/](assignments) - represents a Moodle-Assignment Item
  - defines the courseName and assignmentName and various Tasks to be checked
  - for the task definitions see the corresponding implementation classes in [src/main/java/at/fhtw/swkom/exercisechecker/service/tasks/impl/](src/main/java/at/fhtw/swkom/exercisechecker/service/tasks/impl)
  - each task also gets a descriptive title, grading information and parameters as key/value pairs
* **Submissions**: [submissions/](submissions) - represents config for student submission of a Moodle-Assignment
  - see [submissions.sample.json](submissions.sample.json) for a sample
  - contains a list of students and provided meta-data about the submission,
  - which is (currently manually-) exported from the Moodle Course
  - Remark: you may provide one student submission directly by passing the handed-in zip-archive as -p parameter

Hint: You may generate sample config-files using the ConfigurationGenerator. [src/main/java/at/fhtw/swkom/exercisechecker/courses/ConfigurationGenerator](src/main/java/at/fhtw/swkom/exercisechecker/courses/ConfigurationGenerator.java)

### Build the application:
* ```mvn clean package```  
  to build the sources


## Run the Checker

### Syntax
```
ExerciseChecker for Java

Command Line application to automatically run exercise-checks in FHTW programming courses in Java.

Usage: java -jar ExerciseChecker.jar [OPTIONS]

--help,-h,-?  Show this help text and exit
--assignment <filename>, -a <filename>  load an assignment configuration file
--submission <filename.zip>, -p <filename.zip> load a student-submission source-code archive
--submission <directory>, -p <directory> load a student-submission source-code directory
--submissions <filename>, -s <filename> load an submissions file
--submissions <directory>, -s <directory> use directory with all student-submissions as source-code (will auto-generate a submissions-file)
--moodle-archive <filename>, -m <filename> use a moodle-generated zip archive containing all submissions
--maven-pom <filename> <filename> use provided pom.xml if missing

```

ATTENTION: don't forget to supply the cmd-line args
* -a assignment.json
* -s submissions.json


### Run the tool:

* Show the syntax help:  
  ```
  java -jar ExerciseChecker.jar -h
  ```
  to directly execute the jar-file

* Run tool using assignment & submissions config files:  
  ```
  java -jar ExerciseChecker.jar -a assignments/test/Sprint1.json -s asignments/test/Submissions.json
  ```

* Run the tool providing a student's submission directly using a ZIP-archive  
  ```
  java -jar ExerciseChecker.jar -a assignments/test/Sprint1.yml -p src/test/resources/test-walliscb.zip
  ```

* Run the tool providing a student's submission directly using the project directory  
  ```
  java -jar ExerciseChecker.jar -a assignments/test/Sprint1.yml -p C:\test\BIF5-SWKOM\Java\paperlessrest
  ```

* Run the tool providing the downloaded ZIP-Archive from the Moodle-LMS  
  ```
  java -jar ExerciseChecker.jar -a assignments/sam/JPA.yml -m "C:\Users\wallisch\Downloads\BIC-BB-4B-SS2024-SAM-DE142981-SAM_06(P).zip" -s "C:\stud\BIC4-SAM-SS24_B\REST"
  ```

* Run the tool providing the downloaded ZIP-Archive from the Moodle-LMS and support a pom.xml (if missing)  
  ```
  java -jar ExerciseChecker.jar -a assignments/sam/JPA.yml -m "C:\Users\wallisch\Downloads\BIC-BB-4B-SS2024-SAM-DE142981-SAM_06(P).zip" -s "C:\stud\BIC4-SAM-SS24_B\REST" -maven-pom assignments/sam/pom.xml
  ```

## Results

The tool runs
all defined checks 
on the submissions (passed by the submission.json file)
and store the results to the defined output-directory:

* **all-results.csv** - summary table with all submissions (in the rows) and the corresponding check results (in the columns)
* **all-results.json** - the more detailed and hierarchical view on the results

* **<submissionname>-results.csv** - summary table with all check-results per submission
* **<submissionname>-results.json** - the more detailed and hierarchical view on the check-results per submission
