<%@ page import="Backend.Database" %>
<%@ page import="static Backend.Database.scoresFromDB" %>
<%@ page import="Backend.Database" %>
<%@ page import="static Backend.Engine.score" %>
<%@ page import="static Backend.Engine.nrOfLevels" %>
<%@ page import="static Backend.Engine.*" %>
<%@ page import="static Backend.Database.*" %>
<%@ page import="FrontEnd.Arithmetics" %>
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
