package larisa.jsf.service.impl;

import larisa.entity.Outgo;
import larisa.jsf.service.OutgoService;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

/**
 * Created by home on 25.02.17.
 */
@Service("outgoService")
public class OutgoServiceImpl extends DefaultServiceImpl<Outgo, Integer> implements OutgoService {

    public OutgoServiceImpl() {
        super();
    }

    @Override
    public Outgo createNewInstance() {
        Outgo outgo = super.createNewInstance();
        outgo.setDate(new LocalDate(DateTimeZone.forTimeZone(LocaleContextHolder.getTimeZone())));
        return outgo;
    }
}
