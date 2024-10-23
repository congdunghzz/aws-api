package ueh.congdunghzz.aws.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import ueh.congdunghzz.aws.enitity.Image;

import java.util.List;
import java.util.Optional;

public interface ImageRepository {
    List<Image> findAllByOrderByCreateDateDesc();

    Optional<Image> findByKey(String key);
    Optional<Image> findById(String id);
    Image insert(Image image);
    void delete(Image image);
}
