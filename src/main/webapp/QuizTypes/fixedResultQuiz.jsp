<%@ page import="Servlets.Driver" %>
<html>
<head>
    <meta charset="UTF-8">

    <title>Fixed result quiz</title>
    <div>

        <b>Fixed result quiz</b><br>
        <br>
    </div>
    <form name="submit" action="http://localhost:8080/MathQuizGenerator_war_exploded/Driver" method="POST">
        quizName*: <input type="text" name="quizName" id="quizName"/> </br>
        highestNumber*: <input type="text" name="highestNumber" id="highestNumber"/>  </br>
        numbersInExpression*: <input type="text" name="numbersInExpression" id="numbersInExpression"/>  </br>
        numberOfExpressions*: <input type="text" name="numberOfExpressions" id="numberOfExpressions"/>  </br>
        operators*:<br>
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
        fixedResult*: <input type="text" name="fixedResult" id="fixedResult"/>
        <br>
        <br>
        <input type="hidden" id="command" name="command" value="generateQuiz">
        <input type="hidden" id="quizType" name="quizType" value="fixedResultQuiz">
        <input type="submit" value="Submit"/>
    </form>



   <b>Errors:</b> <%=Driver.errorsList.toString()%>


    <br>
    <br>
    <button onclick="document.location='http://localhost:8080/MathQuizGenerator_war_exploded/Navigation/CreateQuiz.jsp'"> Back </button>
    <br>

</head>
<body>

</body>
</html>