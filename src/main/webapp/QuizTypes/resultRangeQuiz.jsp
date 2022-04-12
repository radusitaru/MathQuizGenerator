<%@ page import="FrontEnd.Adaptor" %>
<html>
<head>
    <meta charset="UTF-8">

    <title>Result range quiz</title>
    <div>

        <b>Result range quiz</b><br>
        <br>
    </div>
    <form name="submit" action="http://localhost:8080/MathQuiz_war_exploded/Adaptor" method="POST">
        quizName*: <input type="text" name="quizName" id="quizName"/> (default=restart intake) </br>
        highestNumber*: <input type="text" name="highestNumber" id="highestNumber"/> (default=10) </br>
        numberOfExpressions*: <input type="text" name="numberOfExpressions" id="numberOfExpressions"/> (default=5) </br>
        resultType*:
        <input type="radio" id="Integer1" name="Integer1" value="Integer1">
        <label for="Integer1">int</label>
        <input type="radio" id="Double1" name="Double1" value="Double1">
        <label for="Double1">double</label>
        resultMin: <input type="text" name="resultMin" id="resultMin"/> (default=5) </br>
        resultMax: <input type="text" name="resultMax" id="resultMax"/> (default=10) </br>
        <br>
        <br>
        <input type="hidden" id="quizType" name="quizType" value="resultRangeQuiz">
        <input type="submit" value="Submit"/>
    </form>



    <%=Adaptor.errorsList.toString()%>

</head>
<body>

</body>
</html>