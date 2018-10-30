<<<<<<< HEAD
dashup - Use Case Specification: Change Panel Structure
============================================
### Version 1.0

# Revision History

| Date       | Version | Description                                                            | Author        |
|------------|---------|------------------------------------------------------------------------|---------------|
| 23/10/2018 | 1.0     | Initial UCS with description, activity diagram and screen flow diagram | Joshua Schulz |

# Table of Contents

- [Change Panel Structure - Brief Description](#1-change-panel-structure) 
- [Flow Of Events](#2-flow-of-events)
    - [Basic Flow](#21-basic-flow)   
    - [Alternative Flows](#22-alternative-flows)
- [Special Requirements](#3-special-requirements)
- [Preconditions](#4-preconditions)
    - [System State](#41-system-state)
- [Postconditions](#5-postconditions) 
    - [Save changed data](#51-save-changed-data)
- [Extension Points](#6-extension-points)
=======

dashup - Use Case Change Panel structure
============================================
### Version 1.0


## Revision History

<table border="1" cellspacing="0" cellpadding="0" style="border-collapse:collapse;
 border:none;">

<tbody>

<tr>

<td width="154" valign="top" style="width:1.6in;border:solid windowtext .75pt;
  padding:0in 5.4pt 0in 5.4pt">

**Date**

</td>

<td width="77" valign="top" style="width:.8in;border:solid windowtext .75pt;
  border-left:none;padding:0in 5.4pt 0in 5.4pt">

**Version**

</td>

<td width="250" valign="top" style="width:2.6in;border:solid windowtext .75pt;
  border-left:none;padding:0in 5.4pt 0in 5.4pt">

**Description**

</td>

<td width="154" valign="top" style="width:1.6in;border:solid windowtext .75pt;
  border-left:none;padding:0in 5.4pt 0in 5.4pt">

**Author**

</td>

</tr>

<tr>

<td width="154" valign="top" style="width:1.6in;border:solid windowtext .75pt;
  border-top:none;padding:0in 5.4pt 0in 5.4pt">

23.10.2018

</td>

<td width="77" valign="top" style="width:.8in;border-top:none;border-left:none;
  border-bottom:solid windowtext .75pt;border-right:solid windowtext .75pt;
  padding:0in 5.4pt 0in 5.4pt">

1.0

</td>

<td width="250" valign="top" style="width:2.6in;border-top:none;border-left:none;
  border-bottom:solid windowtext .75pt;border-right:solid windowtext .75pt;
  padding:0in 5.4pt 0in 5.4pt">

initial UCS with basic information

</td>

<td width="154" valign="top" style="width:1.6in;border-top:none;border-left:none;
  border-bottom:solid windowtext .75pt;border-right:solid windowtext .75pt;
  padding:0in 5.4pt 0in 5.4pt">

Joshua Schulz

</td>

</tr>

<tr>

<td width="154" valign="top" style="width:1.6in;border:solid windowtext .75pt;
  border-top:none;padding:0in 5.4pt 0in 5.4pt"></td>

<td width="77" valign="top" style="width:.8in;border-top:none;border-left:none;
  border-bottom:solid windowtext .75pt;border-right:solid windowtext .75pt;
  padding:0in 5.4pt 0in 5.4pt"></td>

<td width="250" valign="top" style="width:2.6in;border-top:none;border-left:none;
  border-bottom:solid windowtext .75pt;border-right:solid windowtext .75pt;
  padding:0in 5.4pt 0in 5.4pt"></td>

<td width="154" valign="top" style="width:1.6in;border-top:none;border-left:none;
  border-bottom:solid windowtext .75pt;border-right:solid windowtext .75pt;
  padding:0in 5.4pt 0in 5.4pt"></td>

</tr>

<tr>

<td width="154" valign="top" style="width:1.6in;border:solid windowtext .75pt;
  border-top:none;padding:0in 5.4pt 0in 5.4pt"></td>

<td width="77" valign="top" style="width:.8in;border-top:none;border-left:none;
  border-bottom:solid windowtext .75pt;border-right:solid windowtext .75pt;
  padding:0in 5.4pt 0in 5.4pt"></td>

<td width="250" valign="top" style="width:2.6in;border-top:none;border-left:none;
  border-bottom:solid windowtext .75pt;border-right:solid windowtext .75pt;
  padding:0in 5.4pt 0in 5.4pt"></td>

<td width="154" valign="top" style="width:1.6in;border-top:none;border-left:none;
  border-bottom:solid windowtext .75pt;border-right:solid windowtext .75pt;
  padding:0in 5.4pt 0in 5.4pt"></td>

</tr>

<tr>

<td width="154" valign="top" style="width:1.6in;border:solid windowtext .75pt;
  border-top:none;padding:0in 5.4pt 0in 5.4pt"></td>

<td width="77" valign="top" style="width:.8in;border-top:none;border-left:none;
  border-bottom:solid windowtext .75pt;border-right:solid windowtext .75pt;
  padding:0in 5.4pt 0in 5.4pt"></td>

<td width="250" valign="top" style="width:2.6in;border-top:none;border-left:none;
  border-bottom:solid windowtext .75pt;border-right:solid windowtext .75pt;
  padding:0in 5.4pt 0in 5.4pt"></td>

<td width="154" valign="top" style="width:1.6in;border-top:none;border-left:none;
  border-bottom:solid windowtext .75pt;border-right:solid windowtext .75pt;
  padding:0in 5.4pt 0in 5.4pt"></td>

</tr>

</tbody>

</table>

**<span style="font-size:18.0pt;font-family:Arial;">  
</span>**

# Table of Contents

- [Change general layout](#1-change-panel-structure) 

- [Flow of Events](#flow-of-events)

    - [Basic Flow](#basic-flow)   

    - [Alternative Flows](#alternative-flows)

- [Preconditions](#preconditions)

    - [System State](#system-state)

- [Postconditions](#postconditions) 
    
    - [Save changed data](#save-changed-data)

- [Extension Points](#extension-points)
>>>>>>> 3e387a752b4850c0374229fa47a55d0df7e54d84
   
# 1. Change Panel Structure - Brief Description
The platform should enable the user to create and name new sections and fill these sections by the panels he/she likes. 
Furthermore it should be possible to delete sections. A rearrangement of panels should be possible by drag and drop. 
Panels can be moved between sections. The user should be able to delete panels from his/her dashboard.   

# 2. Flow of Events

## 2.1 Basic Flow

### 2.1.1 Activity Diagram
<img src="./arrange_panels.jpg" alt="Use case diagram change panel structure" />
 
<<<<<<< HEAD
### 2.1.2 Mock-Up
<img src="../layout/mockups/layout_mode.png" alt="Use case diagram marketplace" />
<br />
<img src="mockups/layout_mode_mouse_red.png" alt="Use case diagram marketplace" />
<br />
<img src="../layout/mockups/layout_mode_remove.png" alt="Use case diagram marketplace" />
=======
 <img src="./arrange_panels.jpg" alt="Use case diagram change panel structure" />
 
 ### 2.1.2 Mock-Up
 
 <img src="mockups/layout_mode.png" alt="Mockup for layout mode" />
 <img src="mockups/layout_mode_mouse_red.png" alt="Mockup to remove section" />
 <img src="mockups/layout_mode_remove.png" alt="Mockup after removing section" />
 
>>>>>>> 3e387a752b4850c0374229fa47a55d0df7e54d84
 
### 2.1.3 Narrative
There will be a "change structure mode" that can be enabled by the user while he/she is on the main dashboard. When this
mode is enabled the sections names are editable, the sections can be deleted and new ones created as shown in the 
mock-ups.

Panels can be rearranged by drag and drop. They can be moved around between all sections and even deleted from the dashboard.

The user can exit the change mode at all without saving times and roll the structure back to the previous one.

If the user decides to keep the structure he can confirm his/her changes and the data will be stored on the database.
 
## 2.2 Alternative Flows
N/A

# 3. Special Requirements
N/A

# 4. Preconditions

## 4.1 System State
Before this use-case can be performed the user has to sign in and open up his main dashboard. From now on the layout
should be changeable at all time while the user is on his/her main dashboard.

# 5. Postconditions

## 5.1 Save changed data
After the user has changed the layout of his/her dashboard the data has to be stored. This is necessary
to get a persistent change. 

# 6. Extension Points
N/A