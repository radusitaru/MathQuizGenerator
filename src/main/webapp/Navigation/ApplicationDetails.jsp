<%--
  Created by IntelliJ IDEA.
  User: radus
  Date: 25.04.2022
  Time: 16:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<html style="background-color:CornSilk;color:Black;text-align:left" lang="">
<html  lang="">
<head>
    <title>Application details</title>
  <h1 style="text-align: center">Application details</h1>
  <h2>*********************************************************************************************************************************************************************</h2>

  <h3>1. Application scope: </h3>

  - Create mathematical quizzes based on user input

    <h3>2. Application functions: </h3>

      <h4> 2.1 Allow user to create 3 types of mathematical quizzes: </h4>

        - randomQuiz : allow user to create random quiz based on highest number, operators, numbersInExpression, numberOfExpressions, resultType (int or double)<br>
          - resultRangeQuiz: allow user to create a quiz that generates expressions that have a result included in a user input range.<br>
           - Moreover, the expressions themselves are generated based on highest number, operators, numbersInExpression, numberOfExpressions, resultType (int or double)<br>
              - fixedResultQuiz: allow user to create a quiz that generates expressions that have a fixed result, based on user input.
                 Moreover, the expressions themselves are generated based on highest number, operators, numbersInExpression and numberOfExpressions. <br>

  <h4> 2.2 Allow user to save quiz details, expressions and results in database </h4>

    <h4> 2.3 Allow user to load a quiz saved in database. </h4>

      <h3> 3. Application code structure </h3>

        <h4> 3.1 The backend of this application is made of 4 java classes, which are servlets. Each servlet is responsible for a different function of the application: </h4>

                 - Generator: This servlet is responsible for generating the mathematical expressions and results. <br>
            - Quiz: This servlet is responsible for creating a quiz object, which stores all the information regarding the quiz which is being created.
            This object is used throughout the whole application as input for other methods.<br>
                - Database: This servlet is responsible for saving and loading data from and to the database.<br>
                  - Driver: This servlet is responsible for running commands between frontend and backend. To be more specific, it is responsible for reading user input from the frontend,<br>
                     checking for input mistakes, converting input format to desired format that is used in other backend servlets, creating the quiz objects based on user input.<br>

          <h4> 3.2 The frontend of this application is made of 3 folders: Navigation, QuizTypes and Results: </h4>

  <h4> 3.2.1 Navigation: Contains 2 JSP and 1 HTML files. These are the pages used for navigation:</h4>

    - Menu: HTML file responsible for directing user to create a quiz or to search an already existing quiz from database.<br>
     - CreateQuiz: JSP file responsible for the user interface in creating quizzes.<br>
        - ExistingQuiz: JSP file responsible for user interface in loading quiz from database.<br>

    <h4> 3.2.2 QuizTypes:</h4>

      - randomQuiz: JSP file responsible for user interface to creating a randomQuiz. <br>
        - resultRangeQuiz: JSP file responsible for user interface to creating a resultRangeQuiz. <br>
          - fixedResultQuiz: JSP file responsible for user interface to creating a fixedResultQuiz. <br>

      <h4> 3.2.3 Results</h4>

        - Results: JSP file responsible for displaying the newly create quiz details.<br>
          - ExistingQuizResults: JSP file responsible for displaying the quiz details loaded from database.<br>

            <h2>*********************************************************************************************************************************************************************</h2>
  <div style="text-align:center;">
  <form name="submit" action="http://localhost:8080/MathQuizGenerator_war_exploded/Navigation/Menu.html" method="POST">
    <input type="hidden" id="command" name="command" value="backToMenu">
    <input type="submit" value="Back"/>
  </form>
  </div>
  <br>

</head>
<body>

</body>
</html>