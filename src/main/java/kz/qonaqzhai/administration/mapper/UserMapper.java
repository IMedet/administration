package kz.qonaqzhai.administration.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import kz.qonaqzhai.administration.entity.Role;
import kz.qonaqzhai.administration.entity.User;
import kz.qonaqzhai.shared.dto.UserDto;
import kz.qonaqzhai.shared.dto.enums.ERole;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "roles", source = "roles", qualifiedByName = "mapRoles")
    UserDto toUserTransmissionDto(User user);

    @Named("mapRoles")
    default List<ERole> mapRoles(Set<Role> roles) {
        return roles.stream()
                .map(Role::getRoleName)
                .collect(Collectors.toList());
    }
}
