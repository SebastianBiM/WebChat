package services;

import database.DBDriver;
import database.DBHelper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Locale;

@WebServlet(name = "services.activeUser")
public class activeUser extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        RequestDispatcher rd;

        rd = request.getRequestDispatcher("/index.jsp");
        rd.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // basic initialization
        request.setCharacterEncoding("UTF-8");
        RequestDispatcher rd;
        HttpSession session = request.getSession(true);
        session.setMaxInactiveInterval(600);
        DBDriver driver = DBDriver.getInstance();

        // get token from activation link and encode
        String encoded = new String(Base64.getDecoder().decode(request.getParameter("token").substring(request.getParameter("token").indexOf(".") + 1)));

        // check if token is active or dead
        try {

            Calendar calendar = Calendar.getInstance();


            if (calendar.getTime().before(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.UK).parse(encoded))) {

                new DBHelper(driver.getConnection()).activeUser(request.getParameter("token"));

                rd = request.getRequestDispatcher("/WEB-INF/jsp/activeUser.jsp");
                rd.forward(request, response);

            } else {

                System.out.println("token wygasł");

                session.setAttribute("error", "Link aktywacyjny wygasł.");
                rd = request.getRequestDispatcher("/index.jsp");
                rd.forward(request, response);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}