package ueh.congdunghzz.aws.service.image;

import org.springframework.web.multipart.MultipartFile;
import ueh.congdunghzz.aws.enitity.Image;

import java.util.List;

public interface ImageService {
    Image uploadImage(MultipartFile multipartFile);
    List<Image> getAllImages();
    Image getById(String id);
    Image getByKey(String key);
    void delete(String id);
    void downloadById(String id);
}
