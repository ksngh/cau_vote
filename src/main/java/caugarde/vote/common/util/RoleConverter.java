package caugarde.vote.common.util;

import caugarde.vote.model.enums.Role;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Set<Role>, String> {

    private static final String SEPARATOR = ",";

    @Override
    public String convertToDatabaseColumn(Set<Role> roles) {
        return roles.stream()
                .map(Role::getAuthority)
                .collect(Collectors.joining(SEPARATOR));
    }

    @Override
    public Set<Role> convertToEntityAttribute(String dbData) {
        return Arrays.stream(dbData.split(SEPARATOR))
                .map(role -> Role.valueOf(role.replace("ROLE_", "")))
                .collect(Collectors.toSet());
    }

}