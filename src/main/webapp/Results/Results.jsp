<%@ page import="Servlets.Generator" %>
<%@ page import="static Servlets.Generator.generateAllExpressions" %>
<%@ page import="Servlets.Driver" %>
<%@ page import="static Servlets.Generator.allExpressionsAndResults" %>
<%@ page import="Servlets.ErrorHandling" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: radus
  Date: 17.04.2022
  Time: 18:11
  To change this template use File | Settings | File Templates.
--%>

<html>
<html lang="en" xmlns:color="http://www.w3.org/1999/xhtml">
<html style="background-color:CornSilk;color:Black;text-align:center;" lang="">
<html lang="">
<head>
    <title>Results</title>
    <h1> Quiz details and results</h1>
    <h2>
        *********************************************************************************************************************************************************************</h2>

    <h3>Quiz name:</h3>
    <b><%=ErrorHandling.quizzes.get(0).getQuizName()%>
    </b> <br>
    <h3>Quiz type:</h3>
    <b><%=ErrorHandling.quizzes.get(0).getQuizType()%>
    </b>
    <br>
    <br>

    <%=allExpressionsAndResults.toString()%>
    <br>
    <br>
    <div></div>
    <br>
    <form name="submit" action="http://localhost:8080/MathQuizGenerator_war_exploded/Driver" method="POST">
        <input type="hidden" id="command" name="command" value="generateAgain">
        <input type="submit" value="Generate again"/>
    </form>
    <div></div>
    <br>
    <form name="submit" action="http://localhost:8080/MathQuizGenerator_war_exploded/Driver" method="POST">
        <input type="hidden" id="command" name="command" value="saveQuizInDB">
        <input type="submit" value="SaveQuizInDB"/>
    </form>
    <div></div>
    <br>
    <form name="submit" action="http://localhost:8080/MathQuizGenerator_war_exploded/Navigation/CreateQuiz.jsp"
          method="POST">
        <input type="hidden" id="command" name="command" value="backToMenu">
        <input type="submit" value="Back"/>
    </form>


</head>
<body>
<h2>
    *********************************************************************************************************************************************************************</h2>

</body>
</html>
