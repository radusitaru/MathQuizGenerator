<%@ page import="java.util.Locale" %>
<%@ page import="static Backend.Engine.TwoOperatorExpression" %>
<%@ page import="static Backend.Engine.*" %><%--
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


    <form name="result" action="Arithmetics" method="POST">

        <div>
            <h1 style="background-color:rgb(240, 200, 180);">
                <b>Enter your name!</b>


                <input type="text" name="resultName" id="resultName">
            </h1>
        </div>


        <div>
            <h1 style="background-color:rgb(240, 240, 240);">
                <b>Level 1:</b>
                    <%=Expression[0]%>
        </div>
        Answer: <input type="text" name="result1" id="result1">
        </h1>
        <div>


            <h1 style="background-color:rgb(220, 220, 220);">
                <b>Level 2:</b>
                    <%=Expression[1]%>
        </div>
        Answer: <input type="text" name="result2" id="result2"/>
        </h1>
        <div>


            <h1 style="background-color:rgb(200, 200, 200);">
                <b>Level 3:</b>
                    <%=Expression[2]%>
        </div>
        Answer: <input type="text" name="result3" id="result3"/>
        </h1>
        <div>


            <h1 style="background-color:rgb(180, 180, 180);">
                <b>Level 4:</b>
                    <%=Expression[3]%>
        </div>
        Answer: <input type="text" name="result4" id="result4"/>
        </h1>
        <div>


            <h1 style="background-color:rgb(160, 160, 160);">
                <b>Level 5:</b>
                    <%=Expression[4]%>
        </div>
        Answer: <input type="text" name="result5" id="result5"/>
        </h1>

        <div>
            <input type="submit" value="Submit"/>
        </div>
    </form>

</head>
<body>

</body>
</html>
