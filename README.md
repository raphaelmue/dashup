
<h1><img src="./images/logo/dashup_official_icon_small.png" alt="official logo" />ashup</h1>

[![Build Status](http://jenkins.raphael-muesseler.de/buildStatus/icon?job=dashup%2Fmaster)](http://jenkins.raphael-muesseler.de/job/dashup/job/master/)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/10a932c8811c4dd48cbd1c09c3f44703)](https://www.codacy.com/app/dashup/dashup?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=raphaelmue/dashup&amp;utm_campaign=Badge_Grade)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=dashup&metric=alert_status)](https://sonarcloud.io/dashboard?id=dashup)
[![Codacy Badge](https://api.codacy.com/project/badge/Coverage/10a932c8811c4dd48cbd1c09c3f44703)](https://www.codacy.com/app/dashup/dashup?utm_source=github.com&utm_medium=referral&utm_content=raphaelmue/dashup&utm_campaign=Badge_Coverage)

The dashup platform provides a quick overview over multiple widgets that can be used for productivity purposes. Its 
intention is to standardize the usage of different widgets by defining a global design and functionality policy. 
Furthermore it helps to unite different kinds of productivity tools on one central platform and adapts the visualization 
of widgets to individual needs. Each widget is represented by a widget on the dashup platform containing different 
components for data visualization and user interaction. Whereas users are provided with a collection of default widgets 
at the beginning, they can later enhance their central dashboard with further widgets from the dashup store. In addition, 
there should be the possibility to integrate self-developed widgets consuming external APIs to visualize data in 
order to make precise predictions and planning. Dashup is therefore considered to be a productivity tool, running as a 
web application on modern browsers.

## Installation

To install or deploy our application on your server, you need to do the following steps:

1. Clone our repository.
1. Install MySQL and import the mysql scheme from [here](https://github.com/raphaelmue/dashup/tree/master/docs/architectures/database/dashup_prod.sql).
1. Create a file called database.conf and copy it into `./de.dashup.model/src/main/resources/de/dashup/model/db/config`. This file must contain the following information separated by lines:
    1. Host name of database
    1. User to access database
    1. Password to access database
1. Install Java 11.
1. Install Maven 3.6.1.
1. Run `mvn clean install -DskipTests` to compile the application.
1. You can start the application by executing `bash service/restart-application.sh`.

Now you're ready to take full advantage of our application!