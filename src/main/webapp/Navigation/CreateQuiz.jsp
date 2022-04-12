<%@ page import="Servlets.Adaptor" %><%--
  Created by IntelliJ IDEA.
  User: radus
  Date: 12.04.2022
  Time: 16:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <%Adaptor.resetErrorList();%>
  <meta charset="UTF-8">
  <title>Create your own quiz</title>
</head>
<b>Select one of the below quiz models:</b>
<div>
</div>
<br>
<b>Model 1: Random quiz </b>
<div></div>
Only select highest number and number of expressions
<br>
<button onclick=document.location="http://localhost:8080/MathQuiz_war_exploded/QuizTypes/randomQuiz.jsp"> Random quiz
</button>
<br>
<div>
</div>
<br>

<b>Model 2: Fixed result quiz </b>
<div></div>
Select the fixed result for the expressions in the quiz, the highest number and number of expressions
<br>
<button onclick=document.location="http://localhost:8080/MathQuiz_war_exploded/QuizTypes/fixedResultQuiz.jsp"> Fixed
  result quiz
</button>
<br>
<div>
</div>

<br>
<b>Model 3: Result range quiz </b>
<div></div>
Select the result range for the quiz, the highest number and number of expressions
<br>
<button onclick=document.location="http://localhost:8080/MathQuiz_war_exploded/QuizTypes/resultRangeQuiz.jsp"> Result
  range quiz
</button>
<br>

<br><b>Back to menu </b>
<br>
<button onclick="document.location='Menu.html'"> Back to menu</button>
<br>

<body>

</body>
</html>