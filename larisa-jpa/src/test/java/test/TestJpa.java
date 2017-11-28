package test;

import larisa.entity.ProductType;
import larisa.repository.AccountRepository;
import larisa.repository.MakerRepository;
import larisa.repository.ProductTypeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:META-INF/spring-config.xml")
public class TestJpa {
    @Autowired
    MakerRepository makerRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ProductTypeRepository productTypeRepository;


    @Test
    public void testFindAll(){
        for (ProductType p : productTypeRepository.findAll()) {
            System.out.println(p);
        }
    }


}
