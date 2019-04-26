# Dashup - Test Plan
============================================
### Version 1.0

# Revision history

| Date       | Version | Description                                          | Author           |
|------------|---------|------------------------------------------------------|------------------|
| 25/04/2019 | 1.0     | Initial Documentation                                | Raphael Müßeler  |

#Table of Contents

- Introduction
    - Purpose
    - Scope
    - Intended Audience
    - Document Terminology and Acronyms
    - References
- Evaluation Mission and Test Motivation
    - Background
    - Evaluation Mission
    - Test Motivators
- Target Test Items
- Outline of Planned Tests
    - Outline of Test Inclusions
    - Outline of other candidates for Potential Inclusion
    - Outline of Test Exclusions
- Test Approach
    - Testings Techniques and Types
        - Unit Testing
        - User Interface Testing
        - Performance Profiling
- Entry and Exit Testing
    - Test Plan
        - Test Plan Entry Criteria
        - Test Plan Exit Criteria
- Deliverables
    - Test Evaluation Summeries
    - Reporting on Test Coverage 
    - Perceived Quality Reports
    - Incident Logs and Change Requests
    - Smoke Test Suite and Supporting Test Scripts
- Testing Workflow
- Environmental Needs
    - Base System Hardware
    - Base Software Elements in the Test Environment
    - Productivity and Support Tools
- Responsibilities, Staffing, and Training Needs
    - People and Roles
    - Staffing and Training Needs
- Iteration Milestones
- Risks, Dependencies, Assumptions, and Constraints
- Management Process and Procedures
    
## 1 Introduction

## 1.1 Purpose

The purpose of the Iteration Test Plan is to gather all of the information necessary to plan and control the test effort for a given iteration. It describes the approach to testing the software, and is the top-level plan generated and used by managers to direct the test effort.

This _Test Plan_ for **dashup** supports the following objectives:

- Identifies the items that should be targeted by the tests.
- Identifies the motivation for and ideas behind the test areas to be covered.
- Outlines the testing approach that will be used.
- Identifies the required resources and provides an estimate of the test efforts.

## 1.2 Scope

Within the **dashup** project we use unit and integration testing. Unit tests have to purpose to test single logical units primarily within the backend, since there is the main logic placed. Integration tests help us to test and end-to-end scenario. Furthermore they test the 2 other layers: views and controllers.

This means that we are only for functionality (logic), usability, and reliability. Since our tests run on a CI server that measures time, we are able to easily check if we have a performance flaw.  

## 1.3 Intended Audience

This test plan is written generally for ourselves as contributors. As this document is on a technical level, the general target audience is more advanced readers with the necessary background knowledge. 

### 1.4 Document Termonlogy and Acronyms

| Abbrevation | Description                            |
| ----------- | -------------------------------------- |
| CI          | Continuous integration                 |
| n/a         | not applicable                         |

## 1.5 References

| Title                          | Date       |
| ------------------------------ | ---------- |
| Blog                           | 25/04/2019 |
| GitHub                         | 25/04/2019 |
| Software Architecture Document | 08/04/2018 |

## 2 Evaluation Mission and Test Motivation

### 2.1 Background

Unit and integration tests allow us to keep track of issues and bugs, when there are future changes. Their code coverage gives us information about how well our application is covered with tests. When deploying our application without any test failures, we can at least be sure, that all of our tests cases are working correctly. 

Furthermore, when using an CI server, we become aware of issues of bugs, if some tests fail. We benefit from this as we can save time and effort. 

### 2.2 Evaluation Mission

With the help of tests, we are able to provide a stable and working version of our application. This helps us to certify to a standard for our customers. As already said, tests also help us to find possible issues or bugs. 

### 2.3 Test Motivators

Quality as well as stability are the main reason for us to implement tests. Besides it helps us within the process of development as we can detect bugs without spending time on manual testing. 

## 3 Target Test Items

The listing below identifies those test itemssoftware, hardware, and supporting product elements that have been identified as targets for testing. This list represents what items will be tested:

- Java backend functionality and logic
- Frontend usability and functionality

## 4 Outline of Planned Tests

### 4.1 Outline of Test Inclusions

We will test units of our backend and the integration between frontend and backend. The integration tests of course do test the frontend as well. 

### 4.2  Outline of other Candidates for Potential Inclusion

By now, we do not have any performance tests. We can only keep track of the time measurements that the CI server creates when testing. If this is not going to be out of the ordinary, there is no need for those tests. 

### 4.3 Outline of Exclusions

We will not tests our widgets and their components. This would require the creation of a new testing environment that is not based on Java and thus more resources to implement these tests. 

## 5 Test Approach

### 5.1 Testing Techniques and Types

#### 5.1.1 Unit Testing

Unit tests describe automated tests that test only the smallest units of software or logic. All dependencies of this smallest unit are mocked, so that only the function of the smallest unit is tested. These smallest units are mostly methods of different classes. The intention of unit tests is to test the functional parts of a software for correctness. 

|                        | Description                                                 |
| ---------------------- | ----------------------------------------------------------- |
| Technique Objective    | Ensuring that the implemented unit works as expected.       |
| Technique              | Verifying that the expected result and no error occur.      |
| Oracles                | Test logs, console printing, code coverage                  |
| Required Tools         | JUnit Jupiter (Maven Dependency)                            |
| Success Criteria       | Tests must pass and coverage must be higher than threshold. |
| Special Considerations | -                                                           |


#### 5.1.2 User Interface Testing

There is also the possibility to test the user interface of a software. Depending on the language and framework used, there is usually a corresponding UI test framework. This tests whether the user interface functions properly and whether the user input is handled correctly.

|                        | Description                                                 |
| ---------------------- | ----------------------------------------------------------- |
| Technique Objective    | Ensuring that the implemented UI works as expected.         |
| Technique              | Verifying that the expected result and no error occur.      |
| Oracles                | Test logs, console printing, watching tests, code coverage  |
| Required Tools         | Selenium (Maven Dependency)                                 |
| Success Criteria       | Tests must pass and coverage must be higher than threshold. |
| Special Considerations | Running UI tests headless on a CI server without an UI      |

#### 5.1.3 Performance Profiling

Performance is not being tested at the moment.

## 6 Entry and Exit Criteria

## 6.1 Test Plan

### 6.1.1 Test Plan Entry Criteria

If a contributors commits new changes, they will automatically be tested. This build and test process is executed on our CI server, which is in this case Jenkins. 

### 6.1.2 Test Plan Exit Criteria

The test process should not be exited. Even if there are failures at the end, the other tests will run anyway to determine which tests failed. 

 
## 7 Deliverables

### 7.1 Test Evaluation Summaries

For our application we are using Jenkins as CI tool. On the one hand Jenkins performs the build process and on the other hand Jenkins executes unit and integration tests. 

| CI Service | Badge |
| ---------- | ----- |
| Jenkins    | [![Build Status](http://jenkins.raphael-muesseler.de/buildStatus/icon?job=dashup%2Fmaster)](http://jenkins.raphael-muesseler.de/job/dashup/job/master/) |

The following image shows how the tests are running with JUnit Jupiter in IntelliJ, whereas the second image shows build process in Jenkins.

<img src="intellij_tests.png" alt="JUnit Tests in IntelliJ" />

<img src="jenkins_tests.png" alt="Build Process in Jenkins" />

### 7.2 Reporting on Test Coverage

If the Jenkins build was successful, the code coverage will be reported to the GitHub plugin _Codacy_. This badge shows the current code coverage on the master branch: 

[![Codacy Badge](https://api.codacy.com/project/badge/Coverage/10a932c8811c4dd48cbd1c09c3f44703)](https://www.codacy.com/app/dashup/dashup?utm_source=github.com&utm_medium=referral&utm_content=raphaelmue/dashup&utm_campaign=Badge_Coverage)

### 7.3 Perceived Quality Report

We are measuring our code quality with the help of _Codacy_. It checks for clean code and security or performance improvements. The following badge shows the quality of the code on the master branch: 

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/f592e1c013d3405d9bbc773a4ffc8c95)](https://app.codacy.com/app/raphael_6/dashup?utm_source=github.com&utm_medium=referral&utm_content=raphaelmue/dashup&utm_campaign=Badge_Grade_Settings)

### 7.4 Incident Logs and Change Requests

Codacy and Jenkins are integrated into GitHub's pull request. If a new feature is implemented, all these checks will be executed when there is a new pull request for this feature. Furthermore, there is even a build for merging the branch into the master:

<img src="github_checks.png" alt="GitHub Checks in Pull Request" />

### 7.5 Smoke Test Suite and Supporting Test Scripts

Each master build publishes a code coverage result, that can be seen also here: 

<img src="codacy_coverage.png" alt="Code Coverage in Codacy" />


## 8 Testing Workflow

Each contributor has the opportunity to execute the tests locally, since there are run configurations on the GitHub repository. This helps a lot, when implementing tests and new features.

Furthermore, after each commit, all tests will be executed. 

##9 Environmental Needs

### 9.1 Base System Hardware

There is no specific hardware required, but a good hardware leads to the fact that all test will run much faster.

### 9.2 Base Software Elements in the Test Environment

The following base software elements are required in the test environment for this Test Plan.

| Software Element Name | Version     | Type                                    |
| --------------------- | ----------- | --------------------------------------- |
| Java                  | 11          | Programming Language                    |
| Maven                 | 3.5         | Dependency Management Plugin for Java   |
| MariaDB               | 10.3        | SQL Database                            |
| ChromeDriver          | 74.0.3729.6 | Driver to run selenium tests in Chrome  |
| FirefoxDriver         | v0.24.0     | Driver to run selenium tests in Firefox |

### 9.3 Productivity and Support Tools

The following tools will be employed to support the test process for this Test Plan.

| Tool Category or Type                  | Tool Brand Name                                                  |
| -------------------------------------- | ---------------------------------------------------------------- |
| Code Hoster                            | [GitHub](https://www.github.com/raphaelmue/dashup/)              |
| Test Coverage and Code Quality Monitor | [Codacy](https://app.codacy.com/project/dashup/dashup/dashboard) |
| Continuous integration tool            | [Jenkins](https://jenkins.raphael-muesseler.de/)                 |

## 10 Responsibilities, Staffing and Training Needs

### 10.1 People and Roles

This table displays the staffing assumptions for the test effort of our project.

| Role          | Minimum Resources Recommended | Specific Responsibilities                                               |
| ------------- | :---------------------------: | ----------------------------------------------------------------------- |
| Test Manager  | 1                             | Ensures that testing is complete and conducted for the right motivators |
| Test Analyst  | 1                             | Selects what to test based on the motivators                            |
| Test Designer | 1                             | Decides what tests should be implemented                                |
| Tester        | 3                             | Implements and runs the tests                                           |

### 10.2 Staffing and Training Needs
    
The following trainings are needed for writing tests:
    
- Writing JUnit tests
- Writing selenium tests

## 11 Iteration Milestones

Our goal is to keep instruction coverage above **70%** and branch coverage above **50%** for the classes we are testing.

## 12 Risks, Dependencies, Assumptions and Constraints

Some use cases might be very hard to test. Furthermore testing all branches is not applicable for this project, since we do not have that much resources.