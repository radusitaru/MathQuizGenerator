<%@ page import="Servlets.Database" %><%--
  Created by IntelliJ IDEA.
  User: radus
  Date: 12.04.2022
  Time: 16:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<html lang="en" xmlns:color="http://www.w3.org/1999/xhtml">
<html style="background-color:CornSilk;color:Black;text-align:center;" lang="">
<html lang="">
<head>
    <h1>Existing quiz in database</h1>
    <h2>
        *********************************************************************************************************************************************************************</h2>
</head>
<h1><b>Search by quiz name</b></h1>
<br>
<br>
<form name="submit" action="http://localhost:8080/MathQuizGenerator_war_exploded/Driver" method="POST">
    quizName*: <input type="text" name="quizName" id="quizName"/>  </br>
    <input type="hidden" id="command" name="command" value="dbQuizName">
    <br>
    <input type="submit" value="Search"/>
</form>
<body>
<br>
<form name="submit" action="http://localhost:8080/MathQuizGenerator_war_exploded/Navigation/Menu.html" method="POST">
    <input type="hidden" id="command" name="command" value="backToMenu">
    <input type="submit" value="Back"/>
</form>
<br>
<h2>
    *********************************************************************************************************************************************************************</h2>

</body>
</html>
