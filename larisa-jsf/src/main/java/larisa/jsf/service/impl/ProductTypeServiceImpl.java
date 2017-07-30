package larisa.jsf.service.impl;

import larisa.entity.ProductType;
import larisa.jsf.service.ProductTypeService;
import larisa.repository.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by home on 25.02.17.
 */
@Service("productTypeService")
public class ProductTypeServiceImpl extends DefaultServiceImpl<ProductType, Integer> implements ProductTypeService {
    @Autowired
    ProductTypeRepository repository;

    public ProductTypeServiceImpl() {
        super("id", "name");
    }

    @Override
    public ProductTypeRepository getRepository() {
        return repository;
    }
}
