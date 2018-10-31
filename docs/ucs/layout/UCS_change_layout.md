dashup - Use Case Specification: Change Layout
============================================
### Version 1.0

# Revision History

| Date       | Version | Description                                                            | Author        |
|------------|---------|------------------------------------------------------------------------|---------------|
| 28/10/2018 | 1.0     | Initial UCS with description, activity diagram and screen flow diagram | Joshua Schulz |

# Table of Contents

- [Change Layout - Brief Description](#1-change-layout---brief-description) 
- [Flow Of Events](#2-flow-of-events)
    - [Basic Flow](#21-basic-flow)
    - [Alternative Flows](#22-alternative-flows)
- [Special Requirements](#3-special-requirements)
- [Preconditions](#4-preconditions)
    - [System State](#41-system-state)
- [Postconditions](#5-postconditions) 
    - [Save Changed Data](51-save-changed-data) 
- [Extension Points](#6-extension-points)
 
# 1. Change Layout - Brief Description
This use-case allows to user to change the layout and the arrangement of sections and panels on his/her
personal dashboard. The user should be able to change the basic appearance of the board, e.g. the main color or 
font. These settings can be changed in a little layout menu. 

# 2. Flow of Events

## 2.1 Basic Flow

### 2.1.1 Activity Diagram
<img src="./change_layout.jpg" alt="Use case diagram change layout" />

### 2.1.2 Mock-Up

<img src="mockups/layout_settings_2.png" alt="Mockup for change of layout" />

### 2.1.3 Narrative
The user can open up a little menu as shown in the mock-up. In this menu he/she can edit the general layout settings
for his/her main dashboard. If the user edited some of the settings the dashbord and the menu will be shown with
the adjusted layout settings. If the change is confirmed the data will be stored on the database, if not the previous
settings will be restored and there will be no changes on the database.

## 2.2 Alternative Flows
N/A

# 3. Special Requirements
N/A

# 4. Preconditions

## 4.1 System State
Before this use-case can be performed the user has to sign in and open up his main dashboard. From now on the layout
should be changeable at all time while the user is on his/her main dashboard.

#  5. Postconditions

## 5.1 Save changed Data
After the user has changed the layout or the structure of his/her dashboard the data has to be stored. This is necessary
to get a persistent change. 

#  6. Extension Points
N/A