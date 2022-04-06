package FrontEnd;

import BackEnd.BackendRanking;
import BackEnd.Database;

import javax.script.ScriptException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import static BackEnd.BackendMain.*;


@WebServlet("/StatisticsLvl1")
public class StatisticsLvl1 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        Database.getFromDB();

        try {
            PrintWriter pw = resp.getWriter();
            pw.println(Database.myRanking);
            for(int i =0;i<Database.scoresFromDB.size();i++){
               pw.println(Database.scoresFromDB.get(i));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}