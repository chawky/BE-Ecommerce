package Stream.project.stream.models.mappers;

import static Stream.project.stream.models.ERole.fromCode;

import Stream.project.stream.models.Address;
import Stream.project.stream.models.DTOs.AddressDto;
import Stream.project.stream.models.DTOs.UserDto;
import Stream.project.stream.models.Role;
import Stream.project.stream.models.User;
import java.util.ArrayList;
import java.util.Collections;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Set;


public class UserMapper {

  public static final  ModelMapper configureMappings(ModelMapper modelMapper) {
    Converter<List<AddressDto>, List<Address>> addressDtoListConverter =
        ctx -> {

          if(ctx != null ){
            List <Address> addressList = new ArrayList<>();
            ctx.getSource().forEach(address -> {
              addressList.add(new  ModelMapper().map(address, Address.class));
            });

            return addressList;
          }
          return Collections.emptyList();
        };
    Converter<String, Set<Role>> stringListRoleConverter =
        ctx -> {

          if(ctx != null){

            return Collections.singleton(Role.builder()
                .roleName(fromCode(ctx.getSource())) // Assuming ERole is an enum for roles
                .build());
          }
          return Collections.emptySet();
        };
    modelMapper.typeMap(AddressDto.class, Address.class).addMappings(mapper -> {
      mapper.map(AddressDto::getStreet, Address::setStreet);
      mapper.map(AddressDto::getCity, Address::setCity);
      mapper.map(AddressDto::getPostalCode, Address::setPostalCode);
    });
    modelMapper.typeMap(UserDto.class, User.class).addMappings(mapper -> {
      mapper.using(stringListRoleConverter)
          .map(UserDto::getRole, User::setRole);
//      mapper.using(addressDtoListConverter)
//          .map(UserDto::getAddresses, User::setAddresses);

    });
    return modelMapper;
  }
}
