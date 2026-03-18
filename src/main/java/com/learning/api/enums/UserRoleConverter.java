package com.learning.api.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class UserRoleConverter implements AttributeConverter<UserRole, String> {

    @Override
    public String convertToDatabaseColumn(UserRole role) {
        if (role == null) return null;
        return role.name().toLowerCase();
    }

    @Override
    public UserRole convertToEntityAttribute(String value) {
        if (value == null) return null;
        return UserRole.valueOf(value.toUpperCase());
    }
}
