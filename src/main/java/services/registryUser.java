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
import java.util.*;

@WebServlet(name = "services.registryUser")
public class registryUser extends HttpServlet {

    private static final String PARAMETER_USERNAME = "Username";
    private static final String PARAMETER_PASSWORD = "Password";
    private static final String PARAMETER_NAME = "Name";
    private static final String PARAMETER_SURNAME = "Surname";
    private static final String PARAMETER_EMAIL = "Email";


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // basic initialization
        request.setCharacterEncoding("UTF-8");
        RequestDispatcher rd;
        DBDriver driver = DBDriver.getInstance();
        HttpSession session = request.getSession(true);
        session.setMaxInactiveInterval(600);

        // get values from form
        String log = request.getParameter(PARAMETER_USERNAME);
        String pass = request.getParameter(PARAMETER_PASSWORD);
        String name = request.getParameter(PARAMETER_NAME);
        String surname= request.getParameter(PARAMETER_SURNAME);
        String email = request.getParameter(PARAMETER_EMAIL);

        // add new user to db
        String isRegistry = new DBHelper(driver.getConnection()).addOneUser(log, pass, name, surname, email);
        session.setAttribute("isRegistry", isRegistry);

        // map with possible errors from form side
        Map<String, String> error = new HashMap<>();
        error.put("login", "loginem");
        error.put("password", "hasłem");
        error.put("name", "imieniem");
        error.put("surname", "nazwiskiem");
        error.put("email", "e-mailem");


        // check if is any error from db while adding record to table
        if(isRegistry.length() == 0){

            // get current time and add 15 minutes for token
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MINUTE, 15);

            // create uniqueID for token
            String uniqueID = UUID.randomUUID().toString().replace("-", "") + "." + Base64.getEncoder().encodeToString(calendar.getTime().toString().getBytes());

            // add user's token to db
            long newUserID = new DBHelper(driver.getConnection()).getOneUserID(log, pass);
            new DBHelper(driver.getConnection()).activeUser(newUserID , uniqueID);

            String content =    "<div>Cześć,</div><br>" +
                                "<div>Dzięki, że zdecydowałeś się zarejestrować!</div>" +
                                "<div>Oto Twój <a href=\""+request.getRequestURL().toString().replace("registryUser", "activeUser?token=" + uniqueID)+"\">link aktywacyjny.</a></div><br>" +
                                "<div>Życzę dobrej zabawy!</div>";

            // send email to new user with activation link
            new Thread(() ->
                new Emailer().createEmail(email,
                        "Aktywacja konta",content)
            ).start();

            rd = request.getRequestDispatcher("/index.jsp");
            rd.forward(request, response);

        } else {

            session.setAttribute("error", error.get(isRegistry));
            rd = request.getRequestDispatcher("/WEB-INF/jsp/registerUser.jsp");
            rd.forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        RequestDispatcher rd;
        rd = request.getRequestDispatcher("/WEB-INF/jsp/registerUser.jsp");
        rd.forward(request, response);

        //doPost(request,response);
    }
}
