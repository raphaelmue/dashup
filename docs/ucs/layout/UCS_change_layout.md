dashup - Change Layout
============================================
### Version 1.0

<!-- Use Case Specification: **Change layout of main dashboard**-->

# Revision History

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


28/10/28


</td>

<td width="77" valign="top" style="width:.8in;border-top:none;border-left:none;
  border-bottom:solid windowtext .75pt;border-right:solid windowtext .75pt;
  padding:0in 5.4pt 0in 5.4pt">

1.0

</td>

<td width="250" valign="top" style="width:2.6in;border-top:none;border-left:none;
  border-bottom:solid windowtext .75pt;border-right:solid windowtext .75pt;
  padding:0in 5.4pt 0in 5.4pt">


Initial UCS with description, activity diagram and screen flow diagram

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
- [Change Layout](#1-change-layout---brief-description) 
- [Flow of Events](#2-flow-of-events)
    - [Basic Flow](#21-basic-flow)
    - [Mock-Up](#22-mockup)
    - [Narrative](#23-narrative)
    - [Alternative Flows](#24-alternative-flows)
- [Preconditions](#3-preconditions)
    - [System State](#31-system-state)
- [Postconditions](#4-postconditions) 
    - [Save Changed Data](41-save-changed-data) 
- [Extension Points](#5-extension-points)
 
# 1. Change Layout
## 1.1 Brief Description

This use-case allows to user to change the layout and the arrangement of sections and panels on his/her
personal dashboard. First the user should be able to change basic layouts of the board, e.g. the main color or 
font. These settings can be changed in a little layout menu. 

# 2. Flow of Events

## 2.1 Activity Diagram

<img src="./change_layout.jpg" alt="Use case diagram change layout" />

## 2.2 Mock-Up

<img src="../panelStructure/mockups/layout_settings_2.png" alt="Mockup for change of layout" />

## 2.3 Narrative
The user can open up a little menu as shown in the mock-up. In this menu he/she can edit the general layout settings
for his/her main dashboard. If the user edited some of the settings the dashbord and the menu will be shown with
the adjusted layout settings. If the change is confirmed the data will be stored on the database, if not the previous
settings will be restored and there will be no changes on the database.

## 2.4 Alternative Flows
N/A


# 3. Preconditions

## 3.1 System State
Before this use-case can be performed the user has to sign in and open up his main dashboard. From now on the layout
should be changeable at all time while the user is on his/her main dashboard.

#  4. Postconditions

## 4.1 Save changed Data
After the user has changed the layout or the structure of his/her dashboard the data has to be stored. This is necessary
to get a persistent change. 

#  5. Extension Points
N/A
