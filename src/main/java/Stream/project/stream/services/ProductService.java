package Stream.project.stream.services;

import Stream.project.stream.common.ImageUtil;
import Stream.project.stream.models.ImageData;
import Stream.project.stream.models.Product;
import Stream.project.stream.repositories.ProductRepo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductService {

  @Autowired
  private ProductRepo productRepo;

  @Transactional
  public Product uploadProduct(Product product, List<MultipartFile> file) throws IOException {
    Product p = Product.builder()
        .productName(product.getProductName())
        .productDesc(product.getProductDesc())
        .productPrice(product.getProductPrice())
        .productCategory(product.getProductCategory()).build();
    List<ImageData> productImages = new ArrayList<>();
    for (MultipartFile f : file) {
      ImageData producted = ImageData.builder()
          .imageData(ImageUtil.compressImage(f.getBytes()))
          .name(f.getOriginalFilename())
          .product(p).build();
      productImages.add(producted);
    }
    p.setProductImages(productImages);
    return productRepo.save(p);

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
