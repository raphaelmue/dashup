dashup - Use Case Specification: Add API
============================================
### Version 1.0

# Revision History

| Date       | Version | Description                                                            | Author           |
|------------|---------|------------------------------------------------------------------------|------------------|
| 11/11/2018 | 1.0     | Initial UCS with description, activity diagram and screen flow diagram | Sven Leonhard |

# Table of Contents

- [Add API - Brief Description](#1-marketplace---brief-description) 
- [Flow Of Events](#2-flow-of-events)
    - [Basic Flow](#21-basic-flow)  
    - [Alternative Flows](#22-alternative-flows)
- [Special Requirements](#3-special-requirements)
- [Preconditions](#4-preconditions)
    - [Logged in to dashup](#41-logged-in-to-dashup)
- [Postconditions](#5-postconditions) 
- [Extension Points](#6-extension-points)
   
# 1. Marketplace - Brief description
The use case _add_api_ describes the functionality of connecting an API in order to fetch data from a server to display information of a microservice wthin a panel on dashup.
The user can add the URL of an API, parameters and the authentication information. A new API only can be added to dashup after a succesful conection check.

# 2. Flow of Events

## 2.1 Basic Flow

### 2.1.1 Activity Diagram
<img src="./add_api.png" alt="Use case diagram add api" />

### 2.1.2 Mock-Up
<img src="./mockups/add_api_start.png" alt="Add API Start" />
<br />
<img src="./mockups/add_api_fuel.png" alt="Add Paramters with example" />
<br />
<img src="./mockups/add_api_check_no.png" alt="Check Connection with empty text area" />
<br />
<img src="./mockups/add_api_check_basic.png" alt="Basic Authentication" />
<br />
<img src="./mockups/add_api_check_error.png" alt="Error message for connection check" />
<br />
<img src="./mockups/add_api_check_sucess.png" alt="Data of API response in a text area" />

### 2.1.3 Narrative

N/A

## 2.2 Alternative Flows
N/A

# 3. Special Requirements
In order to add an API, the information for a succesfull connection must be available. This includes the URL, in most cases parameters, authentication type and possibly credentials. 

# 4. Preconditions

## 4.1 Logged in to dashup
Before this use case can be performed the user has to be logged in.


# 5. Postconditions
After the addtion of a new API the access data have to be stored in the database. 

# 6. Extension Points
N/A