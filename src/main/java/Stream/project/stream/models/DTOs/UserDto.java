package Stream.project.stream.models.DTOs;

import java.io.Serializable;
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
public class UserDto implements Serializable {
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private List<AddressDto> addresses;
    private String role;

}
