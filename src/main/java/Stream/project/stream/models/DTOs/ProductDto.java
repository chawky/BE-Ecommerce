package Stream.project.stream.models.DTOs;

import Stream.project.stream.models.ImageData;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDto {

  private String productName;
  private String productCategory;
  private String productDesc;
  private double productPrice;
  private List<ImageDataDto> productImages;
}
