<%@ page import="Servlets.Adaptor" %>
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
<head>
    <meta charset="UTF-8">

    <title>Random quiz</title>
    <div>

        <b>Random quiz</b><br>
        <br>
    </div>
    <form name="submit" action="http://localhost:8080/MathQuiz_war_exploded/Adaptor" method="POST">
        quizName*: <input type="text" name="quizName" id="quizName"/> </br>
        highestNumber*: <input type="text" name="highestNumber" id="highestNumber"/> </br>
        numberOfExpressions*: <input type="text" name="numberOfExpressions" id="numberOfExpressions"/> </br>
        resultType*:
        <input type="radio" id="Integer1" name="Integer1" value="Integer1">
        <label for="Integer1">int</label>
        <input type="radio" id="Double1" name="Double1" value="Double1">
        <label for="Double1">double</label>
        <br>
        <br>
        <input type="hidden" id="quizType" name="quizType" value="randomQuiz">
        <input type="submit" value="Submit"/>
    </form>



    <%=Adaptor.errorsList.toString()%>

    <br>
    <br><b>Back to menu </b>
    <br>
    <button onclick="document.location='http://localhost:8080/MathQuiz_war_exploded/Navigation/CreateQuiz.jsp'"> Back </button>
    <br>
</head>
<body>

</body>
</html>
