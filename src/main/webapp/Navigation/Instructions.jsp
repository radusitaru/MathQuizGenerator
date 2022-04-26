<%--
  Created by IntelliJ IDEA.
  User: radus
  Date: 25.04.2022
  Time: 16:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<html style="background-color:CornSilk;color:Black;text-align:left;" lang="">
<html lang="">
<head>
    <title>Instructions</title>


    <h1 style="text-align: center">Instructions</h1>
    <h2>
        *******************************************************************************************************************************************************************</h2>

    <h2>1. Create quiz:</h2>
    <h3> 1.1 Click on create quiz and select your quiz type:</h3>
    - Random quiz, <br><br>
    - Result range quiz, <br><br>
    - Fixed result quiz. <br> <br>
    <h3> 1.2 Insert quiz parameters<br></h3>
    - Quiz name, => quiz name (cannot be empty, error will show up)<br><br>
    - Highest number, => highest number that will be generated in any of the quiz expressions (cannot be
    negative,non-digit, double, dot in front,dot at end or empty, otherwise error will show up) <br><br>
    - Numbers in expression, => count of numbers in each expression (cannot be negative,non-digit, double, dot in
    front,dot at end or empty, otherwise error will show up) <br><br>
    - Number of expressions, => count of expressions in the quiz (cannot be negative,non-digit, double, dot in front,dot
    at end or empty, otherwise error will show up) <br><br>
    - Operators, => operators used in quiz (cannot leave all empty, error will show up) <br><br>
    - Result type, (only in random and result range quiz) => whether result will be integer or double (cannot select
    multiple answers or leave all empty, error will show up) <br><br>
    - Result Min, (only in result range quiz) => minimum result of each expression (cannot be negative,non-digit, dot in
    front,dot at end or empty, otherwise error will show up) <br><br>
    - Result Max, (only in result range quiz) => maximum result of each expression (cannot be negative,non-digit, dot in
    front,dot at end or empty, otherwise error will show up) <br><br>
    - Fixed result, (only in fixed result quiz)=> fixed result of each expression (cannot be negative,non-digit, dot in
    front,dot at end or empty, otherwise error will show up) <br><br>

    <h2>2. Search quiz in database:</h2>
    <h3> 2.1 Click on search quiz in database button from Menu.</h3>
    <h3> 2.2 Insert your quiz name and click search.</h3>
    <h2>
        *******************************************************************************************************************************************************************</h2>
    <br>
    <div style="text-align:center;">
        <form name="submit" action="http://localhost:8080/MathQuizGenerator_war_exploded/Navigation/Menu.html"
              method="POST">
            <input type="hidden" id="command" name="command" value="backToMenu">
            <input type="submit" value="Back"/>
        </form>
    </div>
    <br>
</head>
<body>

</body>
</html>
