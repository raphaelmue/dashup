<%--
  Created by IntelliJ IDEA.
  User: D070546
  Date: 10.11.2018
  Time: 13:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link  rel="stylesheet" type="text/css" href="./../styles/mainDashboard.css" />
    <title>Dashboard</title>
</head>
<body>
    <div id="header">
        Personal Dashboard
    </div>
    <hr>
    <ul class="menubar">
        <li><a style="display: block; color: #000; padding: 8px 16px; text-decoration: none;">A menuitem</a></li>
        <li><a href="main" style="display: block; color: #000; padding: 8px 16px; text-decoration: none;">clickable menuitem</a></li>
        <li><a href="#contact" style="display: block; color: #000; padding: 8px 16px; text-decoration: none;">third menuitem</a></li>
        <li><a href="#about" style="display: block; color: #000; padding: 8px 16px; text-decoration: none;">last menuitem</a></li>
    </ul>
    <div id="content">
    <div id="some section">
        <h1>some section</h1>
        <div class="sectionContent">
        <div  class="panel">
            <p>This is a panel</p>
        </div>
        </div>
    </div>
    <hr>
    <div>
        <h1>another section</h1>
        <div class="sectionContent">
        <div  class="panel">
            <p>This is a panel</p>
        </div>
        <div  class="panel">
            <p>This is a panel</p>
        </div>
        <div  class="panel">
            <p>This is a panel</p>
        </div>
        </div>
    </div>
    </div>
<footer id="footer">
    this is the footer
</footer>
</body>
</html>
