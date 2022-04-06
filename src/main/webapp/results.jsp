<%@ page import="BackEnd.Database" %>
<%@ page import="static BackEnd.Database.scoresFromDB" %>
<%@ page import="BackEnd.BackendRanking" %>
<%@ page import="static BackEnd.BackendMain.score" %>
<%@ page import="static BackEnd.BackendMain.nrOfLevels" %>
<%@ page import="static BackEnd.BackendMain.*" %>
<%@ page import="static BackEnd.Database.*" %>
<%@ page import="FrontEnd.QuizCalculation" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Results</title>

    <b>Your final score is:</b>
    <%=score(score, nrOfLevels)%>
    <div>
        <b>You finished the quiz in:</b>
        <%=quizTime(startTime,endTime)%> seconds.


    </div>
    <div>
        <b>Statistics:</b>
        <%=myRanking%>

    </div>

    <div>
        <form action="http://localhost:8080/MathQuizFrontEnd_war_exploded/FrontEndRanking" method="POST">

            <button type="submit" value="Statistics">See full statistics!</button>
        </form>

    </div>
    <b></b>
</head>
<body>

</body>
</html>
