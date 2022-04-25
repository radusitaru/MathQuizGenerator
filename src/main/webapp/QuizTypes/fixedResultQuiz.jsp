<%@ page import="Servlets.Driver" %>
<%@ page import="Servlets.ErrorHandling" %>
<html>
<html lang="en" xmlns:color="http://www.w3.org/1999/xhtml">
<html style="background-color:CornSilk;color:Black;text-align:center;" lang="">
<html lang="">
<head>

    <meta charset="UTF-8">

    <title>Fixed result quiz</title>
    <h1 style="text-align: center">Fixed result quiz</h1>
    <h2>
        *********************************************************************************************************************************************************************</h2>
    <form name="submit" action="http://localhost:8080/MathQuizGenerator_war_exploded/Driver" method="POST">
        <b>Quiz name:</b> <input type="text" name="quizName" id="quizName"/> </br>
        <b>Highest number:</b> <input type="text" name="highestNumber" id="highestNumber"/>  </br>
        <b>Numbers in expression:</b> <input type="text" name="numbersInExpression" id="numbersInExpression"/>  </br>
        <b>Number of expressions:</b> <input type="text" name="numberOfExpressions" id="numberOfExpressions"/>  </br>
        <b>Operators:</b><br>
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
        <b>Fixed result: <input type="text" name="fixedResult" id="fixedResult"/></b>
        <br>
        <br>
        <input type="hidden" id="command" name="command" value="generateQuiz">
        <input type="hidden" id="quizType" name="quizType" value="fixedResultQuiz">
        <input type="submit" value="Generate"/>
    </form>


    <b>Errors:</b> <%=ErrorHandling.errorsList.toString()%>


    <div></div>
    <br>
    <form name="submit" action="http://localhost:8080/MathQuizGenerator_war_exploded/Navigation/CreateQuiz.jsp"
          method="POST">
        <input type="hidden" id="command" name="command" value="backToMenu">
        <input type="submit" value="Back"/>
    </form>

</head>
<body>
<h2>
    *********************************************************************************************************************************************************************</h2>
<br>
</body>
</html>