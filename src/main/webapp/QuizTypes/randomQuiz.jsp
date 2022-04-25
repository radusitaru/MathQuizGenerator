<%@ page import="Servlets.Driver" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.io.IOException" %><%--
  Created by IntelliJ IDEA.
  User: radus
  Date: 12.04.2022
  Time: 14:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<html lang="en" xmlns:color="http://www.w3.org/1999/xhtml">
<html style="background-color:CornSilk;color:Black;text-align:center;" lang="">
<html lang="">
<head>
    <meta charset="UTF-8">

    <title>Random quiz</title>

    <h1 style="text-align: center">Random quiz</h1>
    <h2>
        *********************************************************************************************************************************************************************</h2>
    <form name="submit" action="http://localhost:8080/MathQuizGenerator_war_exploded/Driver" method="POST">
        quizName: <input type="text" name="quizName" id="quizName"/> </br>
        highestNumber: <input type="text" name="highestNumber" id="highestNumber"/> </br>
        numbersInExpression: <input type="text" name="numbersInExpression" id="numbersInExpression"/>  </br>
        numberOfExpressions: <input type="text" name="numberOfExpressions" id="numberOfExpressions"/> </br>
        operators:<br>
        add:
        <input type="checkbox" id="add" name="add" value="add">
        <label for="add"> +</label><br>
        subtract:
        <input type="checkbox" id="subtract" name="subtract" value="subtract">
        <label for="subtract"> - </label><br>
        multiply:
        <input type="checkbox" id="multiply" name="multiply" value="multiply">
        <label for="multiply"> * </label><br>
        divide:
        <input type="checkbox" id="divide" name="divide" value="divide">
        <label for="divide"> / </label><br>
        resultType:
        <input type="radio" id="Integer1" name="Integer1" value="Integer1">
        <label for="Integer1">int</label>
        <input type="radio" id="Double1" name="Double1" value="Double1">
        <label for="Double1">double</label>
        <br>
        <br>
        <input type="hidden" id="quizType" name="quizType" value="randomQuiz">
        <input type="hidden" id="command" name="command" value="generateQuiz">
        <input type="submit" value="Generate"/>
    </form>


    <b>Errors:</b> <%=Driver.errorsList.toString()%>

    <div></div>
    <br>
    <form name="submit" action="http://localhost:8080/MathQuizGenerator_war_exploded/Navigation/CreateQuiz.jsp"
          method="POST">
        <input type="hidden" id="command" name="command" value="backToMenu">
        <input type="submit" value="Back"/>
    </form>
</head>

<h2>
    *********************************************************************************************************************************************************************</h2>
<body>

</body>
</html>
