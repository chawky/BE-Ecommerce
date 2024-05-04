package Stream.project.stream.controllers;

import Stream.project.stream.models.DTOs.ProductDto;
import Stream.project.stream.models.Product;
import Stream.project.stream.models.mappers.ProductMapper;
import Stream.project.stream.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*")
@RestController
public class ProductsController {

  @Autowired
  private ProductService productService;

  @PostMapping("/productUpload")
  public ResponseEntity<?> uploadImage(@RequestParam("image") List<MultipartFile> productImage,
      @RequestParam("product") String product) throws IOException {

    Product p = new ObjectMapper().readValue(product, Product.class);
    ProductDto response = ProductMapper.productEntity2DTO()
        .map(productService.uploadProduct(p, productImage), ProductDto.class);

    return ResponseEntity.status(HttpStatus.OK)
        .body(response);
  }
}
