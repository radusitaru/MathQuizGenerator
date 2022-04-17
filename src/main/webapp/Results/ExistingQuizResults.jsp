<%@ page import="Servlets.Database" %><%--
  Created by IntelliJ IDEA.
  User: radus
  Date: 17.04.2022
  Time: 18:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <title>Existing quiz results</title>

</head>
Quiz name: <%=Database.quizFromDB.get(0).getQuizName()%><br>
Quiz type: <%=Database.quizFromDB.get(0).getQuizType()%><br>
Quiz date: <%=Database.quizFromDB.get(0).getQuizDate()%><br>
Quiz expressions and results: <%=Database.quizFromDB.get(0).getQuizResultsAndExpressions()%><br>
Quiz parameters: <%=Database.quizFromDB.get(0).getParameters()%><br>
<body>

</body>
</html>
