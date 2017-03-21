package larisa.service.impl;

import larisa.entity.ProductType;
import larisa.repository.ProductTypeRepository;
import larisa.service.ProductTypeService;
import org.entity3.service.impl.HierarchicalEntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by home on 25.02.17.
 */
@Service("productTypeService")
public class ProductTypeServiceImpl extends HierarchicalEntityServiceImpl<ProductType, Integer> implements ProductTypeService {
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
