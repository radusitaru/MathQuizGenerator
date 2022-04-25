package Servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static Servlets.Database.getFromDB;


@WebServlet("/Driver")
public class Driver extends HttpServlet {

    /**
     * --------------------------------------------------------------------------------
     * 1. Variables
     * --------------------------------------------------------------------------------
     */

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {

        /**
         * --------------------------------------------------------------------------------
         * 2. Driver (managing commands from user input)
         * --------------------------------------------------------------------------------
         */

        //2.1 Get error position and reset lists of errors
        ErrorHandling.getErrorPosition();
        ErrorHandling.resetErrorList();

        //2.2 Receiving command parameter
        switch (req.getParameter("command")) {


            //2.3 Generating new quiz
            case "generateQuiz":

                //2.3.1 Fetching quiz type
                ErrorHandling.quizType = req.getParameter("quizType");

                //2.3.2 Determining quiz type
                switch (ErrorHandling.quizType) {


                    //2.3.2.1 Verifying user input and generating random quiz
                    case "randomQuiz":

                        //2.3.2.1.1 Resetting values for each iteration
                        ErrorHandling.repeatIntake = false;
                        ErrorHandling.resetErrorList();
                        ErrorHandling.resetQuizzesList();
                        Generator.clearAllGeneratorLists();

                        //2.3.2.1.2 Checking user input and generating quiz
                        ErrorHandling.checkInputAndGenerateRandomQuiz(req.getParameter("quizName"),
                                req.getParameter("highestNumber"),
                                req.getParameter("numbersInExpression"),
                                req.getParameter("numberOfExpressions"),
                                req.getParameter("Integer1"),
                                req.getParameter("Double1"),
                                req.getParameter("add"),
                                req.getParameter("subtract"),
                                req.getParameter("multiply"),
                                req.getParameter("divide"));

                        //2.3.2.1.3 If user input is incorrect, repeat input intake
                        if (ErrorHandling.repeatIntake) {
                            RequestDispatcher rd = req.getRequestDispatcher("QuizTypes/randomQuiz.jsp");
                            try {
                                rd.forward(req, resp);
                            } catch (ServletException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        //2.3.2.1.4 Redirect to results page once command is completed
                        else {
                            RequestDispatcher rd5 = req.getRequestDispatcher("/Results/Results.jsp");
                            try {
                                rd5.forward(req, resp);
                            } catch (ServletException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;

                    //2.3.2.2 Verifying user input and generating ResultRangeQuiz
                    case "resultRangeQuiz":

                        //2.3.2.2.1 Resetting values
                        ErrorHandling.repeatIntake = false;
                        ErrorHandling.resetErrorList();
                        ErrorHandling.resetQuizzesList();
                        Generator.clearAllGeneratorLists();

                        //2.3.2.2.2 Checking user input and generating quiz
                        ErrorHandling.checkInputAndGenerateResultRangeQuiz(
                                req.getParameter("quizName"),
                                req.getParameter("highestNumber"),
                                req.getParameter("numbersInExpression"),
                                req.getParameter("numberOfExpressions"),
                                req.getParameter("Integer1"),
                                req.getParameter("Double1"),
                                req.getParameter("resultMin"),
                                req.getParameter("resultMax"),
                                req.getParameter("add"),
                                req.getParameter("subtract"),
                                req.getParameter("multiply"),
                                req.getParameter("divide"));

                        //2.3.2.2.3 If user input is incorrect, repeat input intake
                        if (ErrorHandling.repeatIntake) {
                            RequestDispatcher rd = req.getRequestDispatcher("QuizTypes/resultRangeQuiz.jsp");
                            try {
                                rd.forward(req, resp);
                            } catch (ServletException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        //2.3.2.2.4 Redirect to results page once command is completed
                        else {
                            RequestDispatcher rd5 = req.getRequestDispatcher("/Results/Results.jsp");
                            try {
                                rd5.forward(req, resp);
                            } catch (ServletException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        }

                        //2.3.2.3 Verifying fixedResultQuiz
                    case "fixedResultQuiz":

                        //3.2.2.3.1 Resetting values for each iteration
                        ErrorHandling.repeatIntake = false;
                        ErrorHandling.resetErrorList();
                        ErrorHandling.resetQuizzesList();
                        Generator.clearAllGeneratorLists();

                        //2.3.2.3.2 Checking user input and generating quiz
                        ErrorHandling.checkInputAndGenerateFixedResultQuiz(req.getParameter("quizName"),
                                req.getParameter("highestNumber"),
                                req.getParameter("numbersInExpression"),
                                req.getParameter("numberOfExpressions"),
                                req.getParameter("fixedResult"),
                                req.getParameter("add"),
                                req.getParameter("subtract"),
                                req.getParameter("multiply"),
                                req.getParameter("divide"));


                        //2.3.2.3.3 If user input is incorrect, repeat input intake
                        if (ErrorHandling.repeatIntake) {
                            RequestDispatcher rd = req.getRequestDispatcher("QuizTypes/fixedResultQuiz.jsp");
                            try {
                                rd.forward(req, resp);
                            } catch (ServletException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        //2.3.2.3.4 Redirect to results page once command is completed
                        else {
                            RequestDispatcher rd6 = req.getRequestDispatcher("/Results/Results.jsp");
                            try {
                                rd6.forward(req, resp);
                            } catch (ServletException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;

                    default:
                        break;
                }
                break;

            //2.4 Generating new quiz based on same input
            case "generateAgain":

                //2.4.1 Get quizType from previous input
                ErrorHandling.quizType = ErrorHandling.quizzes.get(0).getQuizType();

                //2.4.2 Clearing lists so that details from previous generation are deleted
                ErrorHandling.resetQuizzesList();
                Generator.clearAllGeneratorLists();

                //2.4.3 Select quizType based on previous quizType
                switch (ErrorHandling.quizType) {

                    //2.4.3.1 Generate randomQuiz based on previous quizType
                    case "randomQuiz":

                        //2.4.3.1.1 Reset values and lists
                        ErrorHandling.resetErrorList();
                        ErrorHandling.resetQuizzesList();
                        Generator.clearAllGeneratorLists();

                        //2.4.3.1.2 Generate new quiz based on previous quizType
                        ErrorHandling.checkInputAndGenerateRandomQuiz(ErrorHandling.quizInput.get(0),
                                ErrorHandling.quizInput.get(1),
                                ErrorHandling.quizInput.get(2),
                                ErrorHandling.quizInput.get(3),
                                ErrorHandling.quizInput.get(4),
                                ErrorHandling.quizInput.get(5),
                                ErrorHandling.quizInput.get(6),
                                ErrorHandling.quizInput.get(7),
                                ErrorHandling.quizInput.get(8),
                                ErrorHandling.quizInput.get(9));


                        //2.4.3.1.3 Redirect back to randomQuiz if input is invalid
                        if (ErrorHandling.repeatIntake) {
                            RequestDispatcher rd = req.getRequestDispatcher("QuizTypes/randomQuiz.jsp");
                            try {
                                rd.forward(req, resp);
                            } catch (ServletException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        //2.4.3.1.4 Redirect to results page once command is completed
                        else {
                            RequestDispatcher rd6 = req.getRequestDispatcher("/Results/Results.jsp");
                            try {
                                rd6.forward(req, resp);
                            } catch (ServletException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;

                    //2.4.3.2 Verifying resultRangeQuiz
                    case "resultRangeQuiz":

                        //2.4.3.2.1 Reset values and lists
                        ErrorHandling.repeatIntake = false;
                        ErrorHandling.resetErrorList();
                        ErrorHandling.resetQuizzesList();
                        Generator.clearAllGeneratorLists();

                        //2.4.3.2.2 Generate new quiz based on previous quizType
                        ErrorHandling.checkInputAndGenerateResultRangeQuiz(ErrorHandling.quizInput.get(0),
                                ErrorHandling.quizInput.get(1),
                                ErrorHandling.quizInput.get(2),
                                ErrorHandling.quizInput.get(3),
                                ErrorHandling.quizInput.get(4),
                                ErrorHandling.quizInput.get(5),
                                ErrorHandling.quizInput.get(6),
                                ErrorHandling.quizInput.get(7),
                                ErrorHandling.quizInput.get(8),
                                ErrorHandling.quizInput.get(9),
                                ErrorHandling.quizInput.get(10),
                                ErrorHandling.quizInput.get(11));
                        ErrorHandling.errorsList.clear();

                        //2.4.3.2.3 Redirect back to randomQuiz if input is invalid
                        if (ErrorHandling.repeatIntake) {
                            RequestDispatcher rd = req.getRequestDispatcher("QuizTypes/resultRangeQuiz.jsp");
                            try {
                                rd.forward(req, resp);
                            } catch (ServletException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        //2.4.3.2.4 Redirect to results page once command is completed
                        else {
                            RequestDispatcher rd6 = req.getRequestDispatcher("/Results/Results.jsp");
                            try {
                                rd6.forward(req, resp);
                            } catch (ServletException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;


                    //2.4.3.3 Verifying fixedResultQuiz
                    case "fixedResultQuiz":

                        //2.4.3.3.1 Reset values and lists
                        ErrorHandling.repeatIntake = false;
                        ErrorHandling.resetErrorList();
                        ErrorHandling.resetQuizzesList();
                        Generator.clearAllGeneratorLists();

                        //2.4.3.3.2 Generate new quiz based on previous quizType
                        ErrorHandling.checkInputAndGenerateFixedResultQuiz(ErrorHandling.quizInput.get(0),
                                ErrorHandling.quizInput.get(1),
                                ErrorHandling.quizInput.get(2),
                                ErrorHandling.quizInput.get(3),
                                ErrorHandling.quizInput.get(4),
                                ErrorHandling.quizInput.get(5),
                                ErrorHandling.quizInput.get(6),
                                ErrorHandling.quizInput.get(7),
                                ErrorHandling.quizInput.get(8));
                        ErrorHandling.errorsList.clear();

                        //2.4.3.3.3 Redirect back to randomQuiz if input is invalid
                        if (ErrorHandling.repeatIntake) {
                            RequestDispatcher rd = req.getRequestDispatcher("QuizTypes/fixedResultQuiz.jsp");
                            try {
                                rd.forward(req, resp);
                            } catch (ServletException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        //2.4.3.3.4 Redirect to results page once command is completed
                        else {
                            RequestDispatcher rd6 = req.getRequestDispatcher("/Results/Results.jsp");
                            try {
                                rd6.forward(req, resp);
                            } catch (ServletException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;

                    default:
                        break;
                }
                break;

            //2.5 Save quiz in database
            case "saveQuizInDB":

                Database.saveInDB(ErrorHandling.quizzes.get(0));
                //2.5.1
                RequestDispatcher rd = req.getRequestDispatcher("/Results/SavedQuiz.jsp");
                try {
                    rd.forward(req, resp);
                } catch (ServletException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            //2.6 Back to menu
            case "backToMenu":

                //2.6.1 Reset quiz & expression lists
                ErrorHandling.resetQuizzesList();
                Generator.clearAllGeneratorLists();

                //2.7.2 Redirect back to Menu
                RequestDispatcher rd7 = req.getRequestDispatcher("/Navigation/Menu.html");
                try {
                    rd7.forward(req, resp);
                } catch (ServletException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            //2.8 Get results quiz from database:
            case "dbQuizName":

                //2.8.1 Pulling quiz fromDB
                if (getFromDB(req.getParameter("quizName")) == true) {
                    //2.8.2 Redirect to results page once commands are completed and
                    RequestDispatcher rd9 = req.getRequestDispatcher("/Results/ExistingQuizResults.jsp");
                    try {
                        rd9.forward(req, resp);
                    } catch (ServletException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else ErrorHandling.errorsList.add("<b>Error #1: No such quiz in database, try again");
                RequestDispatcher rd4 = req.getRequestDispatcher("/Navigation/ExistingQuiz.jsp");
                try {
                    rd4.forward(req, resp);
                } catch (ServletException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }
}