package larisa.jsf.service.impl;

import larisa.entity.Maker;
import larisa.jsf.service.MakerService;
import org.springframework.stereotype.Service;

/**
 * Created by home on 25.02.17.
 */
@Service("makerService")
public class MakerServiceImpl extends DefaultServiceImpl<Maker, Integer> implements MakerService {

    public MakerServiceImpl() {
        super("id", "name");
    }


}
