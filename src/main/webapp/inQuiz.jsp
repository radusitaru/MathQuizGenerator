<%@ page import="java.util.Locale" %>
<%@ page import="static BackEnd.Main.generateTwoOpLevel" %>
<%@ page import="static BackEnd.Main.*" %><%--
  Created by IntelliJ IDEA.
  User: radus
  Date: 04.04.2022
  Time: 21:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>MATH QUIZ</title>


    <form name="result" action="results.jsp" method="POST">
        <div>
            <h1 style="background-color:rgb(240, 240, 240);">
            <b>Level 1:</b>
            <%=expressions[0]%>
        </div>
        Answer: <input type="text" name="result" id="result1">
        </h1>
        <div>


            <h1 style="background-color:rgb(220, 220, 220);">
            <b>Level 2:</b>
            <%=expressions[1]%>
        </div>
        Answer: <input type="text" name="result" id="result2"/>
        </h1>
        <div>


            <h1 style="background-color:rgb(200, 200, 200);">
            <b>Level 3:</b>
            <%=expressions[2]%>
        </div>
        Answer: <input type="text" name="result" id="result3"/>
        </h1>
        <div>


            <h1 style="background-color:rgb(180, 180, 180);">
            <b>Level 4:</b>
            <%=expressions[3]%>
        </div>
        Answer: <input type="text" name="result" id="result4"/>
        </h1>
        <div>


            <h1 style="background-color:rgb(160, 160, 160);">
            <b>Level 5:</b>
            <%=expressions[4]%>
        </div>
        Answer: <input type="text" name="result" id="result5"/>
        </h1>

        <div>
        <input type="submit" value="Submit"/>
            <input type="hidden" id="action" name="action" value="NEW">
        </div>
    </form>

</head>
<body>

</body>
</html>
