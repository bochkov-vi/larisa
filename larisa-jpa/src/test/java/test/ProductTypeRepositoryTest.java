package test;

import com.google.common.collect.ImmutableList;
import larisa.entity.ProductType;
import larisa.repository.ProductTypeRepository;
import larisa.service.ProductTypeService;
import org.entity3.repository.CustomRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by home on 23.02.17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/META-INF/spring-config.xml")
public class ProductTypeRepositoryTest implements ApplicationContextAware {
    @Autowired
    ProductTypeRepository productTypeRepository;

    ApplicationContext applicationContext;

    @Autowired
    ProductTypeService productTypeService;

    @Test
    public void testSaveChild() {
        ProductType parentProductType = null;
        parentProductType = productTypeRepository.findByName("Parent");
        if (parentProductType != null) {
            productTypeService.delete(parentProductType);
        }
        parentProductType = new ProductType();
        parentProductType.setName("Parent");

        parentProductType = productTypeService.save(parentProductType);

        ProductType childProductType = productTypeRepository.findByName("child");

        if (childProductType != null) {
            productTypeService.delete(childProductType);
        }
        childProductType = new ProductType();

        childProductType.setName("child");
        childProductType.setParents(ImmutableList.of(parentProductType));
        childProductType.setVolumeNote("1 kg");
        childProductType = productTypeService.save(childProductType);
        parentProductType = productTypeService.findOne(parentProductType.getId());
        System.err.println(childProductType);
        System.err.println(parentProductType);

    }

    @Test
    public void testDelete() {
        ProductType parentProductType = null;
        parentProductType = productTypeRepository.findByName("Parent");
        if (parentProductType != null) {
            productTypeService.delete(parentProductType);
        }
        ProductType childProductType = productTypeRepository.findByName("child");
        if (childProductType != null) {
            productTypeService.delete(childProductType);
        }
    }

    @Test
    public void mainTest() {

        for (String name : applicationContext.getBeanNamesForType(CustomRepository.class)) {
            CustomRepository customRepository = (CustomRepository) applicationContext.getBean(name);
            customRepository.findAll();
        }
    }

    @Test
    public void testService() {
        productTypeService.findAll();
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}