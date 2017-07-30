package larisa.repository;

import larisa.DefaultEntityRepository;
import larisa.entity.File;
import org.springframework.stereotype.Repository;

/**
 * Created by home on 23.02.17.
 */
@Repository
public interface FileRepository extends DefaultEntityRepository<File, Integer> {
}
