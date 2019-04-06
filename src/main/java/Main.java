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


@WebServlet(name = "Main")
public class Main extends HttpServlet {

    private static final String PARAMETER_USERNAME = "Username";
    private static final String PARAMETER_PASSWORD = "Password";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Signing in
        request.setCharacterEncoding("UTF-8");
        RequestDispatcher rd;
        DBDriver driver = DBDriver.getInstance();
        HttpSession session = request.getSession(true);
        session.setMaxInactiveInterval(600);

        String name = request.getParameter(PARAMETER_USERNAME);
        String pass = request.getParameter(PARAMETER_PASSWORD);

        User user = driver.LogIn(name, pass); // shoooooooooooot to datebase
        boolean isActive = user != null ? new DBHelper(driver.getConnection()).isActiveUser(user.getId()) : false;

     //   User user = new DBHelper(driver.getConnection()).getOneUser(String.valueOf(driver.getUserID()));

        if(user != null && isActive != false){

            session.setAttribute("userName", user.getLogin());
            rd = request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
            rd.forward(request, response);

        } else {

            System.out.println("Konto nie istnieje lub nie zostało aktywowane.");

            session.setAttribute("error", "Konto nie istnieje lub nie zostało aktywowane.");
            rd = request.getRequestDispatcher("/index.jsp");
            rd.forward(request, response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

}
