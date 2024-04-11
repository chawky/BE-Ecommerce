package Stream.project.stream.services;

import Stream.project.stream.common.ImageUtil;
import Stream.project.stream.models.Product;
import Stream.project.stream.repositories.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepo;
    @Transactional
    public Product uploadProduct(Product product, MultipartFile file) throws IOException {
      return  productRepo.save(Product.builder()
                .productName(product.getProductName())
                .productDesc(product.getProductDesc())
                .productPrice(product.getProductPrice())
                .productCategory(product.getCategory())
                .productImageName(file.getOriginalFilename())
                .imageData(ImageUtil.compressImage(file.getBytes())).build());

    }

//    @Transactional
//    public ImageData getInfoByImageByName(String name) {
//        Optional<ImageData> dbImage = productRepo.findByName(name);
//
//        return ImageData.builder()
//                .name(dbImage.get().getName())
//                .type(dbImage.get().getType())
//                .imageData(ImageUtil.decompressImage(dbImage.get().getImageData())).build();
//
//    }
//
//    @Transactional
//    public byte[] getImage(String name) {
//        Optional<ImageData> dbImage = productRepo.findByName(name);
//        byte[] image = ImageUtil.decompressImage(dbImage.get().getImageData());
//        return image;
//    }

}
