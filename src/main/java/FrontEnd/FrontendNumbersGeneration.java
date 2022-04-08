package FrontEnd;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static Backend.Engine.*;

@WebServlet("/FrontendNumbersGeneration")
public class FrontendNumbersGeneration extends HttpServlet {


    static public List<Integer> numbersList = new ArrayList<>();
    static public List<String> chosenOperators = new ArrayList<>();
    static public List<String> expressionsList = new ArrayList<>();
    public static Random randomGenerator = new Random();


    static public void generateNumberList(int maxNumber, int listSize) {
        //make sure no operators are at the end of the expression
        if((chosenOperators.size()+listSize)%2!=0)listSize--;
        for (int i = 0; i != listSize-1; i++) {
            numbersList.add(randomGenerator.nextInt(maxNumber) + 1);
        }
    }

    public static void generateOperatorList(String...operators) {
        for (String operator : operators) {
            if (operator != null) chosenOperators.add(operator);
        }

    }
    static void generateExpressionList() {
        int nrCounter = 0;

        for (int currentNr = 0; currentNr < numbersList.size() + chosenOperators.size(); currentNr++) {

            if (currentNr % 2 == 0) {
                expressionsList.add(String.valueOf(numbersList.get(nrCounter)));
                nrCounter++;
            } else
                expressionsList.add(chosenOperators.get(randomGenerator.nextInt(chosenOperators.size())));
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {


        String plus = req.getParameter("+");
        String minus = req.getParameter("-");
        String multiply = req.getParameter("*");
        String divide = req.getParameter("/");
        int maxNumber = Integer.parseInt(req.getParameter("max"));
        int totalNumbers = Integer.parseInt(req.getParameter("totalNumbers"));

        generateOperatorList(plus,minus,multiply,divide);
        generateNumberList(maxNumber, totalNumbers);
        generateExpressionList();

        for (int i = 0; i < numbersList.size()+chosenOperators.size(); i++) {
            System.out.print(expressionsList.get(i));
        }



        startTime = getTime();

        RequestDispatcher rd = req.getRequestDispatcher("inQuiz.jsp");

        try {
            rd.forward(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}