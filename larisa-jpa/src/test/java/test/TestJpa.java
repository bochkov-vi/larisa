package test;

import larisa.repository.AccountRepository;
import larisa.repository.MakerRepository;
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


    @Test
    public void testFindAll(){
        accountRepository.findAll();
    }


}
