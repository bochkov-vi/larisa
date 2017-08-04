package test;

import larisa.entity.Maker;
import larisa.jsf.service.MakerService;
import larisa.repository.MakerRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:/META-INF/test-spring.xml")
public class TestMakerService {
    @Autowired
    MakerService makerService;

    @Autowired
    MakerRepository makerRepository;

    @Test
    public void testFindAll() {
        makerService.findAll();
    }

    @Test
    public void findByMask() {
        String makerName = "Крымская Роза";
        Maker maker = makerService.findByName(makerName);
        if (maker == null) {
            maker = new Maker(makerName);
            makerRepository.save(maker);
        }
        Assert.assertFalse(makerService.findByMask("крымская").isEmpty());
    }
}
