<%@ page import="Servlets.Driver" %>
<html>
<head>
    <meta charset="UTF-8">

    <title>Result range quiz</title>
    <div>

        <b>Result range quiz</b><br>
        <br>
    </div>
    <form name="submit" action="http://localhost:8080/MathQuizGenerator_war_exploded/Driver" method="POST">
        quizName*: <input type="text" name="quizName" id="quizName"/> </br>
        highestNumber*: <input type="text" name="highestNumber" id="highestNumber"/> </br>
        numbersInExpression*: <input type="text" name="numbersInExpression" id="numbersInExpression"/>  </br>
        numberOfExpressions*: <input type="text" name="numberOfExpressions" id="numberOfExpressions"/> </br>
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
        resultType*:
        <input type="radio" id="Integer1" name="Integer1" value="Integer1">
        <label for="Integer1">int</label>
        <input type="radio" id="Double1" name="Double1" value="Double1">
        <label for="Double1">double</label>
        <br>
        resultMin: <input type="text" name="resultMin" id="resultMin"/> </br>
        resultMax: <input type="text" name="resultMax" id="resultMax"/> </br>
        <br>
        <br>
        <input type="hidden" id="command" name="command" value="generateQuiz">
        <input type="hidden" id="quizType" name="quizType" value="resultRangeQuiz">
        <input type="submit" value="Submit"/>
    </form>



    <b>Errors:</b>  <%=Driver.errorsList.toString()%>

    <br>
    <br>
    <button onclick="document.location='http://localhost:8080/MathQuizGenerator_war_exploded/Navigation/CreateQuiz.jsp'"> Back </button>
    <br>

</head>
<body>

</body>
</html>