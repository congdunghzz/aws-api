package ueh.congdunghzz.aws.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ueh.congdunghzz.aws.enitity.Image;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
public class ImageRepositoryImpl implements ImageRepository{
    private final static List<Image> images = new ArrayList<>();
    @Override
    public List<Image> findAllByOrderByCreateDateDesc() {
        return images
                .stream()
                .sorted(Comparator.comparing(Image::getCreateDate).reversed())
                .toList();
    }

    @Override
    public Optional<Image> findByKey(String key) {
        return images
                .stream()
                .filter(image -> image.getKey().equals(key))
                .findFirst();
    }

    @Override
    public Optional<Image> findById(String id) {
        return images
                .stream()
                .filter(image -> image.getId().equals(id))
                .findFirst();
    }

    @Override
    public Image insert(Image image) {
        images.add(image);
        return image;
    }

    @Override
    public void delete(Image image) {
        images.remove(image);
    }
}
