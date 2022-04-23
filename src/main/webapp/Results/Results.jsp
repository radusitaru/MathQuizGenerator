<%@ page import="Servlets.Generator" %>
<%@ page import="static Servlets.Generator.generateAllExpressions" %>
<%@ page import="Servlets.Adaptor" %>
<%@ page import="static Servlets.Generator.allExpressionsAndResults" %><%--
  Created by IntelliJ IDEA.
  User: radus
  Date: 15.04.2022
  Time: 22:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Results</title>

    <b>Quiz name:</b>
    <b><%=Adaptor.quizzes.get(0).getQuizName()%>
    </b> <br>
    <b>Quiz type:</b>
    <b><%=Adaptor.quizzes.get(0).getQuizType()%>
    </b>
    <br>
    <br>

    <%=allExpressionsAndResults.toString()%>


    <br>
    <br>
    <div></div>
    Generate again
    <br>
    <form name="submit" action="http://localhost:8080/MathQuizGenerator_war_exploded/Adaptor" method="POST">
        <input type="hidden" id="command" name="command" value="generateAgain">
        <input type="submit" value="Generate again"/>
    </button>
    </form>
    <br>
    <br>

    <br>
    <br>
    <div></div>
    Generate again
    <br>
    <button onclick=document.location="http://localhost:8080/MathQuizGenerator_war_exploded/Adaptor"> Save quiz in DB
        <input type="hidden" id="command2" name="command" value="saveQuizInDB">
    </button>
    <br>
    <br>

</head>
<body>

</body>
</html>
