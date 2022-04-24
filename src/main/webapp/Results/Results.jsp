<%@ page import="Servlets.Generator" %>
<%@ page import="static Servlets.Generator.generateAllExpressions" %>
<%@ page import="Servlets.Driver" %>
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
    <b><%=Driver.quizzes.get(0).getQuizName()%>
    </b> <br>
    <b>Quiz type:</b>
    <b><%=Driver.quizzes.get(0).getQuizType()%>
    </b>
    <br>
    <br>

    <%=allExpressionsAndResults.toString()%>

    <br>
    <div></div>
    <br>
    <form name="submit" action="http://localhost:8080/MathQuizGenerator_war_exploded/Driver" method="POST">
        <input type="hidden" id="command" name="command" value="generateAgain">
        <input type="submit" value="Generate again"/>
    </button>
    </form>
    <div></div>
    <br>
    <form name="submit" action="http://localhost:8080/MathQuizGenerator_war_exploded/Driver" method="POST">
        <input type="hidden" id="command" name="command" value="saveQuizInDB">
        <input type="submit" value="SaveQuizInDB"/>
        </button>
    </form>
    <br>
    <button onclick="document.location='http://localhost:8080/MathQuizGenerator_war_exploded/Navigation/Menu.html'"> Back to menu</button>
    <br>


</head>
<body>

</body>
</html>
