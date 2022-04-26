<%@ page import="Servlets.Driver" %>
<%@ page import="Servlets.ErrorHandling" %><%--
  Created by IntelliJ IDEA.
  User: radus
  Date: 12.04.2022
  Time: 16:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<html lang="en" xmlns:color="http://www.w3.org/1999/xhtml">
<html style="background-color:CornSilk;color:Black;text-align:center;" lang="">
<html lang="">
<head>
    <%ErrorHandling.resetErrorList();%>
    <meta charset="UTF-8">
    <title>Create your own quiz</title>
</head>
<title>Application details</title>
<h1>Quiz type</h1>
<h2>
    *********************************************************************************************************************************************************************</h2>
<h3><b>Model 1: Random quiz </b></h3>
<div></div>
Generates quiz with expressions that have random results
<br>
<br>
<button onclick=document.location="http://localhost:8080/MathQuizGenerator_war_exploded/QuizTypes/randomQuiz.jsp">
    Create quiz
</button>
<br>
<div>
</div>
<br>


<br>
<h3><b>Model 2: Result range quiz </b></h3>
<div></div>
Generates quiz with expressions with results within a selected range
<br>
<br>
<button onclick=document.location="http://localhost:8080/MathQuizGenerator_war_exploded/QuizTypes/resultRangeQuiz.jsp">
    Create quiz
</button>
<br>
<div>
</div>
<br>


<br>
<h3><b>Model 3: Fixed result quiz </b></h3>
<div></div>
Generates quiz containing expressions that have the same result
<br>
<br>
<button onclick=document.location="http://localhost:8080/MathQuizGenerator_war_exploded/QuizTypes/fixedResultQuiz.jsp">
    Create quiz
</button>
<br>
<div>
</div>
<br>


<br>
<form name="submit" action="http://localhost:8080/MathQuizGenerator_war_exploded/Navigation/Menu.html" method="POST">
    <input type="hidden" id="command" name="command" value="backToMenu">
    <input type="submit" value="Back"/>
</form>
<br>

<body>
<h2>
    *********************************************************************************************************************************************************************</h2>
</body>
</html>