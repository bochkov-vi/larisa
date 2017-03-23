package larisa.service.impl;

import larisa.entity.Maker;
import larisa.repository.MakerRepository;
import larisa.service.MakerService;
import org.entity3.service.impl.EntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by home on 25.02.17.
 */
@Service("makerService")
public class MakerServiceImpl extends EntityServiceImpl<Maker, Integer> implements MakerService {
    @Autowired
    MakerRepository repository;

    public MakerServiceImpl() {
        super("id", "name");
    }

    @Override
    public MakerRepository getRepository() {
        return repository;
    }
}
