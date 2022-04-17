<%@ page import="Servlets.Database" %><%--
  Created by IntelliJ IDEA.
  User: radus
  Date: 12.04.2022
  Time: 16:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Select existing quiz</title>
</head>

<form name="submit" action="http://localhost:8080/MathQuizGenerator_war_exploded/Database" method="POST">
    quizName*: <input type="text" name="quizName" id="quizName"/>  </br>
    <input type="submit" value="Submit"/>
</form>
<body>




</body>
</html>
