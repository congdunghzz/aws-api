package ueh.congdunghzz.aws.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ueh.congdunghzz.aws.enitity.Image;

public interface ImageRepository extends MongoRepository<Image, String> {
}
