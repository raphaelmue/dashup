<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html lang="en">
    <head>
        <jsp:include page="./includes/styles.jsp" />
        <title>dashup</title>
    </head>
    <body>
        <header>
            <div class="wrapper">
                <h1 class="heading">dashup</h1>
                <nav>
                    <a href="${pageContext.request.contextPath}/marketplace">Marketplace</a>
                    <a href="${pageContext.request.contextPath}/layout">Layout</a>
                    <a href="${pageContext.request.contextPath}/profile">${email}</a>
                </nav>
                <div class="clear-float"></div>
            </div>
        </header>
        <main>
            <div class="wrapper">
                <h1>Welcome, ${name}!</h1>
            </div>
        </main>
    </body>
</html>