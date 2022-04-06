<%--
  Created by IntelliJ IDEA.
  User: radus
  Date: 06.04.2022
  Time: 13:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Multiply&Divide only</title>
</head>
<body>
<form action="http://localhost:8080/MathQuizFrontEnd_war_exploded/quizGeneration" method="POST">

    <button type="submit" value="Start quiz!">Start quiz!</button>
</form>

</body>

<div>
    <form action="http://localhost:8080/MathQuizFrontEnd_war_exploded/FrontEndMath/Arithmetics.jsp" method="POST">

        <button type="submit" value="Back">Back</button>
    </form>


</div>
</html>
