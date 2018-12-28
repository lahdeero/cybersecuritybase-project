package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Account;
import sec.project.domain.Login;
import sec.project.repository.LoginRepository;
import sec.project.repository.AccountRepository;
import sec.project.service.SessionService;

import java.util.List;

@Controller
public class LoginController {
    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private SessionService sessionService;

    @RequestMapping("*")
    public String defaultMapping() {
        return "redirect:/form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String loadForm() {
        return "form";
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        boolean checkPassword = accountRepository.checkPassword(password);
        if (checkPassword) {
            Account account = accountRepository.findByUsername(username);
            if (account != null) {
                sessionService.sedLoggedUser(username);
                return "redirect:/main?id=" + account.getId();
            }
        }
        model.addAttribute("message", "Bad username or password");
        return "form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createForm() {
        return "create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createAccount(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        Account account = new Account(username, password);
        accountRepository.save(account);
        model.addAttribute("account", account);
        model.addAttribute("message", "login successful");
        return "main";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String loggedOut() {
        sessionService.sedLoggedUser("");
        return "form";
    }

}
