dashup - Use Case Specification Marketplace
============================================
### Version 1.0

#Revision History

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

28/10/2018

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

Felix Hausberger

</td>

</tr>

</tbody>

</table>

**<span style="font-size:18.0pt;font-family:Arial;">  
</span>**

# Table of Contents

1.          Use-Case Name

1.1         Brief Description     

2.          Flow of Events

2.1         Basic Flow     

2.2         Alternative Flows     

2.2.1       < First Alternative Flow >      

2.2.2       < Second Alternative Flow >     

3.          Special Requirements

3.1      < First Special Requirement >     

4.          Preconditions

4.1      < Precondition One >  

5.          Postconditions  

5.1      < Postcondition One > 

6.          Extension Points

6.1      < Name of Extension Point >     

# 1. Use-Case Name

## 1.1 Brief Description

The use case _marketplace_ describes the functionality to browse through the internal
store of microservices within dashup. Here users can rate, comment and add microservices to 
their dashboard. 

# Flow of Events

## Basic Flow

<img src="./UCS_marketplace.jpg" alt="Use case diagram marketplace" />

## Alternative Flows

n/a

# Special Requirements

In order to add microservices to one's own dashboard through the marketplace,
a persistent internet connection is needed, to fetch data from the database.

# Preconditions

## Sufficient amount of microservices

In order to take use of the marketplace, a default amount of microservices provided by the 
community should be available. If there are only built-in dashup microservices availible, the 
actual sense of a marketplace would get lost.

## Sufficient amount of ratings

In order to get a good overview over the microservice's quality, there should be a certain quantity of
ratings provided by the community in order to fill the rating menu of a microservice.

## Logged in to dashup

In order to use the marketplace, the user must be logged in to dashup.

#  Postconditions

After the use of the marketplace, the database should by synchronized to the actions the user did. 

#  Extension Points

n/a