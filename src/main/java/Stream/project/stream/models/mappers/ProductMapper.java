package Stream.project.stream.models.mappers;

import Stream.project.stream.models.DTOs.ProductDto;
import Stream.project.stream.models.Product;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

public class ProductMapper extends UtilMapper {

  public static ModelMapper productEntity2DTO() {
    ModelMapper mapper = new ModelMapper();
    mapper.getConfiguration().setPreferNestedProperties(false);
    TypeMap<Product, ProductDto> propertyMapper = mapper.createTypeMap(Product.class,
        ProductDto.class);
    propertyMapper.addMappings(m -> m.map(
        Product::getProductImages, ProductDto::setProductImages));
    return  mapper;
  }
}
