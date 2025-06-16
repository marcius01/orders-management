package tech.skullprogrammer.auth.utility;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import tech.skullprogrammer.auth.model.ERole;
import tech.skullprogrammer.auth.model.User;
import tech.skullprogrammer.auth.model.dto.UserDTO;

import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface MapperUtils {

    UserDTO.Out toUserDTO(User user);
    @Mapping(target = "roles", ignore = true)
    UserDTO.Out toUserDTO(UserDTO.In user);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "password", target = "password")
    @Mapping(source = "roles", target = "roles")
    User toUser(UserDTO.In user, String password, Set<ERole> roles);
}
