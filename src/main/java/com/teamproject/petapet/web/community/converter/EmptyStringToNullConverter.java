package com.teamproject.petapet.web.community.converter;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class EmptyStringToNullConverter implements AttributeConverter<String, String> {
    @Override
    public String convertToDatabaseColumn(String string) {
        return StringUtils.trimToNull(string);
    }
    @Override
    public String convertToEntityAttribute(String dbData) {
        return dbData;
    }
}
