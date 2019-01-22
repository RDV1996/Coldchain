/*
 Author:Roby de Visser
*/

package be.ordina.coldchain.controller;

import be.ordina.coldchain.Config.PasswordEncodingConfig;
import be.ordina.coldchain.model.Account;
import be.ordina.coldchain.model.AccountType;
import be.ordina.coldchain.repository.AccountRepository;
import be.ordina.coldchain.repository.AccountTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AccountController {

    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountTypeController accountTypeController;

    @RequestMapping(value = "/accounts/add", method = RequestMethod.POST)
    public void addAccount(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "accountType") AccountType accountType) {
        Account account = new Account();
        account.setEmail(email);
        account.setPassport(passwordEncoder.encode(password));
        account.setName(name);
        account.setAccountType(accountType);

        accountRepository.save(account);
    }

    @RequestMapping(value = "/accounts/patch", method = RequestMethod.PATCH)
    public String patchAccount(
            @RequestParam(value = "id") long id,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "accountTypeId") long accountTypeId,
            @RequestParam(value = "confirmPassword") String confirmPassword,
            @RequestParam(value = "version") int version) {
        if(passwordEncoder.matches(confirmPassword, getAccountById(id).getPassport())) {
            Account account = new Account();
            account.setVersion(version);
            account.setId(id);
            account.setEmail(email);
            account.setPassport(passwordEncoder.encode(password));
            account.setName(name);
            account.setAccountType(accountTypeController.getAccountTypeById(accountTypeId));

            accountRepository.save(account);

            return "Succesful";
        }
        else {
            return "wrong password";
        }
    }

    @RequestMapping(value = "/accounts", method=RequestMethod.GET)
    public List<Account> getAccounts(){
        List<Account> accounts = new ArrayList<>();
        accountRepository.findAll().forEach(accounts::add);
        return accounts;
    }

    private Account getAccountById(long id){
        Account account = new Account();
        account = accountRepository.findById(id).get();
        return account;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public long login(@RequestParam(value = "email") String email,
                        @RequestParam(value = "password") String password){
        Account account = accountRepository.getAccountByEmail(email);

        if (passwordEncoder.matches(password, account.getPassport())){
            return account.getId();
        }else{
            return 0;
        }


    }
}