package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Account;
import sec.project.repository.AccountRepository;
import sec.project.service.SessionService;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Controller
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private SessionService sessionService;

    @PersistenceContext
    private EntityManager entityManager;

    @PostConstruct
    public void init() {
        // Initial data for testing
        Account account = new Account( "eero", "asdasd");
        account.setBalance(100);
        accountRepository.save(account);

        Account account2 = new Account( "pekka", "password");
        account2.setBalance(500);
        accountRepository.save(account2);

        Account account3 = new Account( "maija", "president");
        account3.setBalance(50);
        accountRepository.save(account3);
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main(@RequestParam("id") String idstr, @RequestParam(value = "message", required=false) String message, Model model) {
        if (idstr != null && !idstr.isEmpty()) {
            try {
                Long id = Long.parseLong(idstr);
                Account account = accountRepository.findById(id);
                if (account != null) {
                    if (message != null) {
                        model.addAttribute("message", message);
                    }
                    model.addAttribute("account", account);
                    return "main";
                }
            } catch (Exception e) {
                return "redirect:/";
            }
        }
        model.addAttribute("message", "You must login first!");
        return "form";
    }

    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    public String transfer(@RequestParam("sender") String sender, @RequestParam("receiver") String reciver, @RequestParam("amount") Integer amount, Model model) {
        Account senderAccount = accountRepository.findByUsername(sender);
        Account receiverAccount = accountRepository.findByUsername(reciver);
        senderAccount.setBalance(senderAccount.getBalance() - amount);
        receiverAccount.setBalance(receiverAccount.getBalance() + amount);
        accountRepository.save(senderAccount);
        accountRepository.save(receiverAccount);
        return "redirect:/main?id=" + senderAccount.getId() + "&message=Send successfully " + amount + " to " + receiverAccount.getUsername();
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(@RequestParam("keyword") String keyword, Model model) {
        String login = sessionService.getLoggedUser();
        if (login != null && !login.isEmpty()) {
            List<Account> results = accountRepository.findByUsernameIgnoreCaseContaining(keyword);

            model.addAttribute("account", accountRepository.findByUsername(login));
            model.addAttribute("results", results);
            return "main";
        }
        return "form";
    }
}
