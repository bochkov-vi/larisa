package larisa.jsf.service.impl;

import larisa.entity.Outgo;
import larisa.jsf.service.OutgoService;
import org.springframework.stereotype.Service;

/**
 * Created by home on 25.02.17.
 */
@Service("outgoService")
public class OutgoServiceImpl extends DefaultServiceImpl<Outgo, Integer> implements OutgoService {

    public OutgoServiceImpl() {
        super();
    }


}
