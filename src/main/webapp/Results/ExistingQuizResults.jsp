<%@ page import="Servlets.Database" %>
<%@ page import="Servlets.Driver" %><%--
  Created by IntelliJ IDEA.
  User: radus
  Date: 17.04.2022
  Time: 18:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<html lang="en" xmlns:color="http://www.w3.org/1999/xhtml">
<html style="background-color:CornSilk;color:Black;text-align:center;" lang="">
<html lang="">
<head>

    <title>Existing quiz results</title>

    <h1>Existing quiz results</h1>
    <h2>
        *********************************************************************************************************************************************************************</h2>
</head>
</head>
<b>Quiz name:</b> <%=Database.quizFromDB.get(0).getQuizName()%><br>
<b>Quiz type:</b> <%=Database.quizFromDB.get(0).getQuizType()%><br>
<b>Quiz date:</b> <%=Database.quizFromDB.get(0).getQuizDate()%><br>
<b>Quiz parameters:</b> <%=Database.quizFromDB.get(0).getParameters()%><br>
<b>Quiz expressions and results:</b> <br> <%=Database.quizFromDB.get(0).getQuizResultsAndExpressions()%><br>

<body>
<br>
<form name="submit" action="http://localhost:8080/MathQuizGenerator_war_exploded/Navigation/Menu.html" method="POST">
    <input type="hidden" id="command" name="command" value="backToMenu">
    <input type="submit" value="Back to Menu"/>
</form>
<br>
<h2>
    *********************************************************************************************************************************************************************</h2>
</body>
</html>
