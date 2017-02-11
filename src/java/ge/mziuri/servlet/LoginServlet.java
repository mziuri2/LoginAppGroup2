
package ge.mziuri.servlet;

import ge.mziuri.dao.UserDAO;
import ge.mziuri.dao.UserDAOImpl;
import ge.mziuri.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {
    
    private final String COUNT_VISITS = "countVisits";
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
         
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserDAO userDAO = new UserDAOImpl();
        User user = userDAO.getUser(username, Integer.toString(password.hashCode()));
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = response.getWriter();
        if (user == null) {
            pw.append("ასეთი მომხმარებელი არ არისებობს!");
        } else {
            Cookie[] cockies = request.getCookies();
            int count = 0;
            if (cockies != null) {
                for (Cookie cookie : cockies) {
                    if (cookie.getName().equals(COUNT_VISITS)) {
                        count = Integer.parseInt(cookie.getValue());
                    }
                }
            }
            count++;
            pw.append("გამარჯობა " + user.getFirstName() + " ეს არის შენი " + count + " შემოსვლა");
            Cookie cookie = new Cookie(COUNT_VISITS, String.valueOf(count));
            response.addCookie(cookie);
        }
    }
}
