/**
 * Author: Jorne Lambrechts
 */
package be.ordina.coldchain.repository;

import be.ordina.coldchain.model.AccountType;
import org.springframework.data.repository.CrudRepository;

public interface AccountTypeRepository extends CrudRepository<AccountType, Long> {

}
