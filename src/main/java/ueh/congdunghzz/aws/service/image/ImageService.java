package ueh.congdunghzz.aws.service.image;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import ueh.congdunghzz.aws.enitity.Image;
import ueh.congdunghzz.aws.model.request.ImageRequest;
import ueh.congdunghzz.aws.model.response.PageResponse;
import ueh.congdunghzz.aws.security.AuthUser;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    Image uploadImage(AuthUser authUser, ImageRequest request) throws IOException;
    List<Image> getAllImages();
    Image getById(String id);
    Image getByKey(String key);
    void delete(String id);
    void downloadById(String id, HttpServletResponse response) throws IOException;
}
