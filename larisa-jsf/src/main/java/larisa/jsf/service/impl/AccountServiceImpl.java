package larisa.jsf.service.impl;

import larisa.entity.Account;
import org.springframework.stereotype.Service;

/**
 * Created by home on 25.02.17.
 */
@Service("accountService")
public class AccountServiceImpl extends DefaultServiceImpl<Account, String> {

    public AccountServiceImpl() {
        super("id");
    }
}
