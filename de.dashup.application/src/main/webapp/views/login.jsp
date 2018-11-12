<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <jsp:include page="./includes/headInclude.jsp" />
    <link  rel="stylesheet" type="text/css" href="./../styles/login.css" />

    <title>Login</title>
</head>
<body>
    <div class="jumbotron jumbotron-fluid bg-primary text-white">
        <div class="container">
            <h1 class="display-4">Dashup</h1>
            <p class="lead">Your personalized tool for displaying microservices</p>
        </div>
    </div>

    <br />
    <br />

    <div class="container col-lg-4 col-lg-offset-4">
        <form action="/entry/login" method="POST">
            <div class="form-group">
                <label class="text-white" for="email">Email address</label>
                <input type="text" class="form-control" id="email" name="email" aria-describedby="emailHelp" placeholder="Enter email">
                <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
            </div>
            <div class="form-group">
                <label class="text-white" for="password">Password</label>
                <input type="password" class="form-control" id="password" name="password" placeholder="Password">
            </div>
            <div class="form-group form-check">
                <input type="checkbox" class="form-check-input" id="rememberMe">
                <label class="text-white form-check-label" for="rememberMe">Remember me to stay logged in</label>
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
    </div>

    <br />

    <div class="container col-lg-4 col-lg-offset-4"><div>
        <label class="text-white" for="google">Or just sign in with Google:</label>
        <div id="google" class="g-signin2" data-onsuccess="onSignIn" data-theme="dark"></div>
    </div>

    <jsp:include page="./includes/bodyInclude.jsp" />
</body>
</html>
