package Stream.project.stream.models.DTOs;

import Stream.project.stream.models.Product;
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
public class ImageDataDto {

  private String name;


  private byte[] imageData;
}
