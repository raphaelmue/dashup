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
    <title>Dashboard</title>
</head>
<body>
    <div id="header" style="text-align: center">
        This is the header
    </div>
    <hr>
    <ul style="list-style-type: none;
    margin: 0;
    padding: 0;
    width: 15%;
    background-color: #f1f1f1;
    position: fixed;
    height: 90%;
    overflow: auto;">
        <li><a style="display: block; color: #000; padding: 8px 16px; text-decoration: none;">A menuitem</a></li>
        <li><a href="main" style="display: block; color: #000; padding: 8px 16px; text-decoration: none;">clickable menuitem</a></li>
        <li><a href="#contact" style="display: block; color: #000; padding: 8px 16px; text-decoration: none;">third menuitem</a></li>
        <li><a href="#about" style="display: block; color: #000; padding: 8px 16px; text-decoration: none;">last menuitem</a></li>
    </ul>
    <div id="content" style="margin-left:15%;padding:1px 16px;">
    <div id="some section">
        <h1>some section</h1>
        <div style="width: 150px ;height: 150px; border: solid 2px" class="panel">
            <p>This is a panel</p>
        </div>
    </div>
    <hr>
    <div>
        <h1>another section</h1>
        <div style="width: 150px ;height: 150px; border: solid 2px; float: left; margin: 10px" class="panel">
            <p>This is a panel</p>
        </div>
        <div style="width: 150px ;height: 150px; border: solid 2px; float: left; margin: 10px" class="panel">
            <p>This is a panel</p>
        </div>
        <div style="width: 150px ;height: 150px; border: solid 2px; float: left; margin: 10px" class="panel">
            <p>This is a panel</p>
        </div>
    </div>
    </div>
<footer id="footer" style="position: fixed; left: 0; bottom: 0; height: 2%; text-align: center">
    this is the footer
</footer>
</body>
</html>
