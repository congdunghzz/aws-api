package ueh.congdunghzz.aws.service.image;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ueh.congdunghzz.aws.common.Validator;
import ueh.congdunghzz.aws.common.exception.ApplicationException;
import ueh.congdunghzz.aws.common.util.UniqueID;
import ueh.congdunghzz.aws.config.AwsConfig;
import ueh.congdunghzz.aws.enitity.Image;
import ueh.congdunghzz.aws.model.request.ImageRequest;
import ueh.congdunghzz.aws.model.response.PageResponse;
import ueh.congdunghzz.aws.repository.ImageRepository;
import ueh.congdunghzz.aws.security.AuthUser;
import ueh.congdunghzz.aws.service.S3Service.AwsS3Service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService{
    private final ImageRepository imageRepository;
    private final AwsS3Service awsS3Service;
    private final AwsConfig awsProperties;
    private final Set<String> EXECUTABLE_CONTENT_TYPE = Set.of("image/jpeg", "image/png", "image/jpg");

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Image uploadImage(AuthUser authUser, ImageRequest request) throws IOException {
        MultipartFile multipartFile = request.getFile();
        if (!EXECUTABLE_CONTENT_TYPE.contains(multipartFile.getContentType())){
            throw new ApplicationException(HttpStatus.BAD_REQUEST, multipartFile.getContentType() + " NOT support");
        }
        String fileName = multipartFile.getOriginalFilename();
        String key = generateKey(authUser.getId(), UniqueID.getUUID() ,fileName);
        awsS3Service.upload(key, multipartFile.getContentType(), multipartFile.getInputStream());
        Image image = Image.builder()
                .createDate(System.currentTimeMillis())
                .id(UniqueID.getUUID())
                .title(request.getTitle())
                .key(key)
                .ownedBy(authUser.getId())
                .ownerName(authUser.getName())
                .name(fileName)
                .url(awsProperties.getEndpoint()+"/"+awsProperties.getBucket()+"/"+ key)
                .contentType(multipartFile.getContentType())
                .build();
        return imageRepository.insert(image);
    }

    @Override
    public PageResponse getAllImages(int page, int size) {
        Pageable pageable = PageRequest.of(page -1, size);
        return new PageResponse(imageRepository.findAllByOrderByCreateDateDesc(pageable));
    }

    @Override
    public Image getById(String id) {
        Image image = imageRepository.findById(id).orElse(null);
        Validator.notNull(image, HttpStatus.NOT_FOUND, "Image not found");
        return image;
    }

    @Override
    public Image getByKey(String key) {
        Image image = imageRepository.findByKey(key).orElse(null);
        Validator.notNull(image, HttpStatus.NOT_FOUND, "Image not found");
        return image;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        Image image = getById(id);
        awsS3Service.delete(image.getKey());
        imageRepository.delete(image);
    }

    @Override
    public void downloadById(String id, HttpServletResponse response) throws IOException {
        Image image = getById(id);
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", image.getName());
        BufferedInputStream inputStream = new BufferedInputStream(awsS3Service.getInputStreamByKey(image.getKey()));
        response.setHeader(headerKey, headerValue);
        response.setContentType(image.getContentType());
        BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        outputStream.write(inputStream.readAllBytes());
        outputStream.flush();
        outputStream.close();
    }
    private String generateKey(String userid, String id, String fileName){
        return userid + "$" + id + "$" + fileName;
    }
}
