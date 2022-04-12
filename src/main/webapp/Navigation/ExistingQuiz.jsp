<%--
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

<form name="submit" action="http://localhost:8080/MathQuiz_war_exploded/Adaptor" method="POST">
    quizName*: <input type="text" name="quizName" id="quizName"/>  </br>
    highestNumber*: <input type="text" name="highestNumber" id="highestNumber"/>  </br>
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
<body>

</body>
</html>
