/*
 Author:Roby de Visser
*/

package be.ordina.coldchain.controller;

import be.ordina.coldchain.model.Account;
import be.ordina.coldchain.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(value = "/accounts/add", method = RequestMethod.POST)
    public void addAccount(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "passport") String passport,
            @RequestParam(value = "name") String name) {
        Account account = new Account();
        account.setEmail(email);
        account.setPassport(passport);
        account.setName(name);

        accountRepository.save(account);
    }

    @RequestMapping(value = "/accounts", method=RequestMethod.GET)
    public List<Account> getAccounts(){
        List<Account> accounts = new ArrayList<>();
        accountRepository.findAll().forEach(accounts::add);
        return accounts;
    }
}