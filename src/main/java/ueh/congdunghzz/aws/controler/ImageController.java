package ueh.congdunghzz.aws.controler;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ueh.congdunghzz.aws.common.util.ResponseUtil;
import ueh.congdunghzz.aws.model.request.ImageRequest;
import ueh.congdunghzz.aws.security.AuthUser;
import ueh.congdunghzz.aws.service.image.ImageService;

import java.io.IOException;

@RequestMapping("/api/image")
@RestController
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @GetMapping
    public ResponseEntity<?> getPage(@RequestParam(value = "page", defaultValue = "1") int page,
                                     @RequestParam(value = "size", defaultValue = "10") int size){
        return ResponseUtil.successResponse(imageService.getAllImages(page, size));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        return ResponseUtil.successResponse(imageService.getById(id));
    }
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> upload(@ModelAttribute @Valid ImageRequest request,
                                    AuthUser authUser) throws IOException {
        return ResponseUtil.successResponse(imageService.uploadImage(authUser, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        imageService.delete(id);
        return ResponseUtil.successResponse("OK");
    }
    @GetMapping("/download/{id}")
    public ResponseEntity<?> download(@PathVariable String id,
                                      HttpServletResponse response) throws IOException {
        imageService.downloadById(id, response);
        return ResponseEntity.ok().body("OK");
    }
}
