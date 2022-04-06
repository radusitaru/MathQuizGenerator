<%--
  Created by IntelliJ IDEA.
  User: radus
  Date: 06.04.2022
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Math category</title>

    <b>Select math category:</b>

</head>

<div>
    <form action="http://localhost:8080/MathQuizFrontEnd_war_exploded/FrontEndMath/Arithmetics/AddSubstract.jsp" method="POST">

        <button type="submit" value="Add&Substract only">Add&Substract only</button>
    </form>



</div>

<div>
    <form action="http://localhost:8080/MathQuizFrontEnd_war_exploded/FrontEndMath/Arithmetics/MultiplyDivide.jsp" method="POST">

        <button type="submit" value="Multiply&Divide only">Multiply&Divide only</button>
    </form>



</div>

</div>

<div>
    <form action="http://localhost:8080/MathQuizFrontEnd_war_exploded/FrontEndMath/Arithmetics/AllOperations.jsp" method="POST">

        <button type="submit" value="All Operations">All Operations</button>
    </form>



</div>

<div>
    <form action="http://localhost:8080/MathQuizFrontEnd_war_exploded/Menu.html" method="POST">

        <button type="submit" value="Back to menu">Back to menu</button>
    </form>



</div>
<body>

</body>
</html>
