/*
 Author:Roby de Visser
*/

package be.ordina.coldchain.controller;

import be.ordina.coldchain.model.Account;
import be.ordina.coldchain.model.AccountType;
import be.ordina.coldchain.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/accounts")
public class AccountController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountTypeController accountTypeController;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void addAccount(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "accountTypeId") long accountTypeId,
            @RequestParam(value = "photoURL") String photoURL) {
        Account account = new Account();
        account.setEmail(email);
        account.setPassword(passwordEncoder.encode(password));
        account.setName(name);
        account.setAccountType(accountTypeController.getAccountTypeById(accountTypeId));
        account.setPhotoURL(photoURL);

        accountRepository.save(account);
    }

    @RequestMapping(value = "/patch", method = RequestMethod.PATCH)
    public void patchAccount(
            @RequestParam(value = "id") long id,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "accountTypeId") long accountTypeId,
            @RequestParam(value = "confirmPassword") String confirmPassword,
            @RequestParam(value = "version") int version,
            @RequestParam(value = "photoURL") String photoURL) {
        String pwCheck = getPassword(id);
        if (passwordEncoder.matches(confirmPassword, pwCheck)) {
            Account account = new Account();
            account.setVersion(version);
            account.setId(id);
            account.setEmail(email);
            if (password.equals("")) {
                account.setPassword(pwCheck);
            } else {
                account.setPassword(passwordEncoder.encode(password));
            }
            account.setName(name);
            account.setAccountType(accountTypeController.getAccountTypeById(accountTypeId));
            account.setPhotoURL(photoURL);


            accountRepository.save(account);
        }
    }

    private List<Account> getAccounts() {
        List<Account> accounts = new ArrayList<>();
        accountRepository.findAll().forEach(accounts::add);
        return accounts;
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public Account getAccountById(@PathVariable("id") long id) {
        Account account = new Account();
        account = accountRepository.findById(id).get();
        account.setPassword("");
        return account;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public long login(@RequestParam(value = "email") String email,
                      @RequestParam(value = "password") String password) {
        Account account = accountRepository.getAccountByEmail(email);

        if (passwordEncoder.matches(password, account.getPassword())) {
            return account.getId();
        } else {
            return 0;
        }


    }

    @RequestMapping(value = "/email/{email}", method = RequestMethod.GET)
    public List<Account> getAccountsByEmail(
            @PathVariable(value = "email", required = false) String email) {

        List<Account> accounts = new ArrayList<>();
        accountRepository.findByEmail(email).forEach(accounts::add);
        for (Account account : accounts) {
            account.setPassword("");
        }
        return accounts;
    }

    private String getPassword(long id) {
        Account account = new Account();
        account = accountRepository.findById(id).get();
        return account.getPassword();
    }


}