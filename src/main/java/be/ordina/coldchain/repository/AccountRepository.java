/*
 Author:Roby de Visser
*/

package be.ordina.coldchain.repository;

import be.ordina.coldchain.model.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {

}
