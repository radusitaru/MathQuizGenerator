<%@ page import="BackEnd.Database" %>
<%@ page import="static BackEnd.Database.scoresFromDB" %>
<%@ page import="BackEnd.BackendRanking" %>
<%@ page import="static BackEnd.BackendMain.score" %>
<%@ page import="static BackEnd.BackendMain.nrOfLevels" %>
<%@ page import="static BackEnd.BackendMain.*" %>
<%@ page import="static BackEnd.Database.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Results</title>

    <b>Your final score is:</b>
    <%=score(score, nrOfLevels)%>
    <div>
        <b>You finished the quiz in:</b>

    </div>
    <div>
        <b>Statistics:</b>

    </div>

    <b></b>
</head>
<body>

</body>
</html>
