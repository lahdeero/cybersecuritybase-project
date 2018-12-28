package sec.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class SessionService {

    @Autowired
    private HttpSession session;

    public void sedLoggedUser(String username) {
        session.setAttribute("loggedUser", username);
    }

    public String getLoggedUser() {
        if (session.getAttribute("loggedUser") != null) {
            return (String) session.getAttribute("loggedUser");
        }
        return "";
    }
}
