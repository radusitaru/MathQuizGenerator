<%@ page import="FrontEnd.Adaptor" %>
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
        <div> INFO: If 0 is inserted = restart intake</div>
        <br>
    </div>
    <form name="submit" action="http://localhost:8080/MathQuiz_war_exploded/Adaptor" method="POST">
        quizName: <input type="text" name="quizName" id="quizName"/> (default=restart intake) </br>
        highestNumber: <input type="text" name="highestNumber" id="highestNumber"/> (default=10) </br>
        numberOfExpressions: <input type="text" name="numberOfExpressions" id="numberOfExpressions"/> (default=5) </br>
        resultType: <input type="checkbox" id="int" name="int" value="int">
        <label for="int"> int</label>
        <input type="checkbox" id="double" name="double" value="double">
        <label for="double"> double</label> (if multiple or no choice selected, default = int) <br>
        <input type="hidden" id="quizType" name="quizType" value="randomQuiz">
        <br>
        <input type="submit" value="Submit"/>
    </form>

    <%if(Adaptor.repeatIntake) {
        for (int i = 0; i < Adaptor.errorsList.size(); i++) {
            System.out.println(Adaptor.errorsList.get(i));
        }
    }
    %>

</head>
<body>

</body>
</html>
