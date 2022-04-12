<%@ page import="Servlets.Adaptor" %>
<html>
<head>
    <meta charset="UTF-8">

    <title>Fixed result quiz</title>
    <div>

        <b>Fixed result quiz</b><br>
        <br>
    </div>
    <form name="submit" action="http://localhost:8080/MathQuiz_war_exploded/Adaptor" method="POST">
        quizName*: <input type="text" name="quizName" id="quizName"/> </br>
        highestNumber*: <input type="text" name="highestNumber" id="highestNumber"/>  </br>
        numberOfExpressions*: <input type="text" name="numberOfExpressions" id="numberOfExpressions"/>  </br>
        fixedResult*: <input type="text" name="fixedResult" id="fixedResult"/>
        <br>
        <br>
        <input type="hidden" id="quizType" name="quizType" value="fixedResultQuiz">
        <input type="submit" value="Submit"/>
    </form>



    <%=Adaptor.errorsList.toString()%>


    <br>
    <br><b>Back </b>
    <br>
    <button onclick="document.location='http://localhost:8080/MathQuiz_war_exploded/Navigation/CreateQuiz.jsp'"> Back </button>
    <br>

</head>
<body>

</body>
</html>