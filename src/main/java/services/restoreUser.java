package services;

import model.User;
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

@WebServlet(name = "services.restoreUser")
public class restoreUser extends HttpServlet {

    private static final String PARAMETER_EMAIL = "Email";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        RequestDispatcher rd;
        DBDriver driver = DBDriver.getInstance();
        HttpSession session = request.getSession(true);
        session.setMaxInactiveInterval(600);

        String email = request.getParameter(PARAMETER_EMAIL);

        User user = new DBHelper(driver.getConnection()).restoreUser(email);

        String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
        boolean verify = VerifyRecaptcha.verify(gRecaptchaResponse);


        if(user != null && verify){

            new Thread(() ->
                    new Emailer().createEmail(user.getEmail(), "Odzyskanie danych użytkownika", "<h3>Dane użytkownika</h3>" +
                            "<div>Login : "+user.getLogin()+"</div>" +
                            "<div>Haslo : "+user.getPassword()+"</div>")
            ).start();

            rd = request.getRequestDispatcher("/index.jsp");
            rd.forward(request, response);

        } else {
            System.out.println("Problem z wysłaniem email'a przy odzyskaniu użytkownika");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        RequestDispatcher rd;
        rd = request.getRequestDispatcher("/WEB-INF/jsp/restoreUser.jsp");
        rd.forward(request, response);

        // doPost(request, response);
    }
}