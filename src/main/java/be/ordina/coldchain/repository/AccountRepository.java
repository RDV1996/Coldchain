/*
 Author:Roby de Visser
*/

package be.ordina.coldchain.repository;

import be.ordina.coldchain.model.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends CrudRepository<Account, Long> {
    Account getAccountByEmail(String email);
}
