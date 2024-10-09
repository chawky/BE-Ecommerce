package Stream.project.stream.controllers;

import Stream.project.stream.models.Product;
import Stream.project.stream.services.FileStore;
import Stream.project.stream.services.ProductService;
import java.io.IOException;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*")
@AllArgsConstructor
@RestController
public class ProductsController {

  @Autowired
  private ProductService service;
  @Autowired
  private FileStore fileStoreService;

  @GetMapping
  public ResponseEntity<List<Product>> getTodos() {
    return new ResponseEntity<>(service.getAllTodos(), HttpStatus.OK);
  }
  @PostMapping(
      path = "/saveProduct",
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  @RolesAllowed({ "SELLER_ROLE", "ADMIN_ROLE" })
  public ResponseEntity<Product> saveTodo(@RequestParam("title") String title,
      @RequestParam("description") String description,
      @RequestParam("price") double price,
      @RequestParam("file") MultipartFile file) throws IOException {
    return new ResponseEntity<>(service.saveTodo(title, description,price,file), HttpStatus.OK);
  }
  @GetMapping(value = "{id}/image/download")
  public Product getProduct(@PathVariable("id") Long id) {
    byte[] productImage= service.downloadTodoImage(id);
    Product product = service.getProductById(id);
    product.setImageBytes(productImage);
    return product;
  }
  @GetMapping(value = "/allProductData")
  public  List<Product> getProduct() {
    List<Product> products = service.getProducts();
    fileStoreService.downloadAllProducts();
    return products;
  }

//  @PostMapping("/productUpload")
//  public ResponseEntity<?> uploadImage(@RequestParam("image") List<MultipartFile> productImage,
//      @RequestParam("product") String product) throws IOException {
//
//    Product p = new ObjectMapper().readValue(product, Product.class);
//    ProductDto response = ProductMapper.productEntity2DTO()
//        .map(productService.uploadProduct(p, productImage), ProductDto.class);
//
//    return ResponseEntity.status(HttpStatus.OK)
//        .body(response);
//  }
//  @GetMapping("/allProductData")
//  public ResponseEntity<?> getAllProductData() throws IOException {
//
//    List<Product> products = productService.getProducts();
//
//    return ResponseEntity.status(HttpStatus.OK)
//        .body(products);
//  }
}
