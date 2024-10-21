package Stream.project.stream.models.DTOs;

import javax.persistence.Column;
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
public class AddressDto {
  private String street;
  private String strNumber;
  private String postalCode;
  private String city;
}
