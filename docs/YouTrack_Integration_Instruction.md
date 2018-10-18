#How to integrate YouTrack to Intellij
Go to 'Tools' in toolbar.
 
Choose 'Tasks & Context'.

Click 'Configure Server...'

Click the plus (+) button on the right side.

Choose 'YouTrack' and enter the Server URL (youtrack.dashup.de) , your username and password.

It is possible to configure some other features for VCS and so on here...

Input field 'Search' specifies which tasks you´ll see. Feel free to change that. 

Test your connection by clicking 'Test'. After that confirm with 'OK'.

With Alt+Shift+N you can open your tasks list and check out the one you want to work on or create a new one.

Feel free to use the additional features like automated branch creation and check out...

##Integration to git
To add direct links to YouTrack in git history go to settings and choose 'Version Control'.

Choose 'Issue Navigation' and click the plus (+) button on the right.

Provide the URL again and click 'OK'. The Issues in your git history should be links to YouTrack,
if they are created with the YouTrack-Plugin.
