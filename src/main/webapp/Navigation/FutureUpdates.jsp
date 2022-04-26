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
    <title>Future updates</title>


    <h1 style="text-align: center">Future updates</h1>
    <h2>
        *******************************************************************************************************************************************************************</h2>

    <h2>1. Introduce quiz playing feature</h2>

    <h3> 1.1 Allow user to run quizzes and be ranked based on</h3>
    - Quiz type, <br><br>
    - Quiz score, <br><br>
    - Quiz completion time. <br> <br>
    <h3> 1.2 Allow user to see current ranking based on quiz types<br></h3>
    <br>

    <h2>2. Fix current bugs:</h2>

    <h3>2.1 Prompt error on user input which is not realistic - example: highestNumber = 10, numbers in expression = 50,
        operators = + & *, fixed result = 0.</h3>
    <h3>2.2 Improve quiz generation speed by improving the quiz generation formula.</h3>
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
