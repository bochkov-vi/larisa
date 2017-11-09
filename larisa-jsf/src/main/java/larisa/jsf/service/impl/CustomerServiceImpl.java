package larisa.jsf.service.impl;

import larisa.entity.Customer;

/**
 * Created by home on 25.02.17.
 */
public class CustomerServiceImpl extends DefaultServiceImpl<Customer, Integer> {
    public CustomerServiceImpl() {
        super("id");
    }
}
