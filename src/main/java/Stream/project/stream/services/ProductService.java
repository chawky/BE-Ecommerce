package Stream.project.stream.services;

import static org.apache.http.entity.ContentType.IMAGE_BMP;
import static org.apache.http.entity.ContentType.IMAGE_GIF;
import static org.apache.http.entity.ContentType.IMAGE_JPEG;
import static org.apache.http.entity.ContentType.IMAGE_PNG;

import Stream.project.stream.models.BucketName;
import Stream.project.stream.models.Product;
import Stream.project.stream.repositories.ProductRepo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Service
public class ProductService {

  private final FileStore fileStore;
  private final ProductRepo repository;
  @Autowired
  private ProductRepo productRepo;

  public Product saveTodo(String title, String description, double price, MultipartFile file)
      throws IOException {
    //check if the file is empty
    if (file.isEmpty()) {
      throw new IllegalStateException("Cannot upload empty file");
    }
    //Check if the file is an image
    if (!Arrays.asList(IMAGE_PNG.getMimeType(),
        IMAGE_BMP.getMimeType(),
        IMAGE_GIF.getMimeType(),
        IMAGE_JPEG.getMimeType()).contains(file.getContentType())) {
      throw new IllegalStateException("FIle uploaded is not an image");
    }
    //get file metadata
    Map<String, String> metadata = new HashMap<>();
    metadata.put("Content-Type", file.getContentType());
    metadata.put("Content-Length", String.valueOf(file.getSize()));
    //Save Image in S3 and then save Todo in the database
    String path = String.format("%s/%s", BucketName.PRODUCT_IMAGE.getBucketName(),
        UUID.randomUUID());
    String fileName = String.format("%s", file.getOriginalFilename());
    try {
      fileStore.upload(path, fileName, Optional.of(metadata), file.getInputStream());
    } catch (IOException e) {
      throw new IllegalStateException("Failed to upload file", e);
    }
    Product todo = Product.builder()
        .price(price)
        .description(description)
        .name(title)
        .imagePath(path)
        .imageFileName(fileName)
        .imageBytes(file.getBytes())
        .build();
    repository.save(todo);
    return repository.findByName(todo.getName());
  }

  public byte[] downloadTodoImage(Long id) {
    Product todo = repository.findById(id).get();
    return fileStore.download(todo.getImagePath(), todo.getImageFileName());
  }

  public List<Product> getAllTodos() {
    List<Product> todos = new ArrayList<>();
    repository.findAll().forEach(todos::add);
    return todos;
  }
  @Transactional
  public List<Product> getProducts() {
    return productRepo.findAll();
  }

  public Product getProductById(Long id) {
    return  productRepo.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  //  //old work is below
//
//  @Transactional
//  public Product uploadProduct(Product product, List<MultipartFile> file) throws IOException {
//    Product p = Product.builder()
//        .productName(product.getProductName())
//        .productDesc(product.getProductDesc())
//        .productPrice(product.getProductPrice())
//        .productCategory(product.getProductCategory()).build();
//    List<ImageData> productImages = new ArrayList<>();
//    for (MultipartFile f : file) {
//      ImageData producted = ImageData.builder()
//          .imageData(ImageUtil.compressImage(f.getBytes()))
//          .name(f.getOriginalFilename())
//          .product(p).build();
//      productImages.add(producted);
//    }
//    p.setProductImages(productImages);
//    return productRepo.save(p);
//
//  }


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
