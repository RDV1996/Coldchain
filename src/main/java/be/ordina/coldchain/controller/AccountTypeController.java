/**
 * Managing account types
 * Author: Jorne Lambrechts
 */
package be.ordina.coldchain.controller;

import be.ordina.coldchain.model.AccountType;
import be.ordina.coldchain.repository.AccountTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping(value = "/accounttypes")
public class AccountTypeController {

    @Autowired
    private AccountTypeRepository accountTypeRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<AccountType> getTypes(){
        List<AccountType> types = new ArrayList<>();
        accountTypeRepository.findAll().forEach(types::add);

        return types;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void addType(@RequestParam(value = "naam") String naam){
        AccountType type = new AccountType();
        type.setNaam(naam);

        accountTypeRepository.save(type);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public void deleteType(@RequestParam(value = "id") long id){
        accountTypeRepository.deleteById(id);
    }

    @RequestMapping(value = "/patch", method = RequestMethod.PATCH)
    public void patchTpe(@RequestParam(value = "id") long id,
                         @RequestParam(value = "version") int version,
                         @RequestParam(value = "naam") String naam) {
        AccountType type = new AccountType();
        type.setId(id);
        type.setVersion(version);
        type.setNaam(naam);

        accountTypeRepository.save(type);
    }

        public AccountType getAccountTypeById(long id) {
        AccountType accountType = new AccountType();
        accountType = accountTypeRepository.findById(id).get();
        return accountType;
    }

}
