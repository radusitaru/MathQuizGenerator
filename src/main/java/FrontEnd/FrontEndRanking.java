package FrontEnd;

import Backend.Database;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/FrontEndRanking")
public class FrontEndRanking extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {

        try {
            PrintWriter pw = resp.getWriter();
            pw.println(Database.myRanking);
            for(int i = 0; i< Database.scoresFromDB.size(); i++){
                pw.println(Database.scoresFromDB.get(i));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
