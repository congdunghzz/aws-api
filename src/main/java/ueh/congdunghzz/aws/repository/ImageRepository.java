package ueh.congdunghzz.aws.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import ueh.congdunghzz.aws.enitity.Image;

import java.util.Optional;

public interface ImageRepository extends MongoRepository<Image, String> {
    Page<Image> findAllByOrderByCreateDateDesc(Pageable pageable);

    Optional<Image> findByKey(String key);
}
