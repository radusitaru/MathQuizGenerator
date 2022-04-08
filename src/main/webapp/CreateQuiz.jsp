<%@ page import="static Backend.Engine.Expression" %><%--
  Created by IntelliJ IDEA.
  User: radus
  Date: 07.04.2022
  Time: 20:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create your own quiz</title>

    <b>Insert yes if you want to include an operator</b>


    <form name="result" action="FrontendNumbersGeneration" method="POST">

        <div>
            <h1 style="background-color:rgb(240, 240, 240);">
                <b>+</b>

        Include "+"? <input type="text" name="+" id="+">
        </h1>

        </div>


        <div>
        <h1 style="background-color:rgb(220, 220, 220);">

            <b>-</b>
        Include "-" ? <input type="text" name="-" id="-"/>
        </h1>

        </div>


        <div>
            <b>*</b>
            Include "*" ? <input type="text" name="*" id="*">
            </h1>
        </div>



        <div>
            <b>/</b>
            <h1 style="background-color:rgb(200, 200, 200);">
        Include "/" ? <input type="text" name="/" id="/"/>


        </h1>
        </div>

        <div>
            <b>Insert max number</b>
            <h1 style="background-color:rgb(200, 200, 200);">
                Insert max number <input type="text" name="max" id="max"/>


            </h1>
        </div>

        <div>
            <b>Insert total numbers</b>
            <h1 style="background-color:rgb(180, 180, 180);">
                Insert total numbers <input type="text" name="TotalNumbers" id="totalNumbers"/>


            </h1>
        </div>

        <div>

            <h1 style="background-color:rgb(160, 160, 160);">
            <input type="submit" value="Submit"/>
            </h1>
        </div>

    </form>
</head>
<body>

</body>
</html>
