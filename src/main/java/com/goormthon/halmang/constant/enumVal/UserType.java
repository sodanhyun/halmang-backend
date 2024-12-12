package com.goormthon.halmang.constant.enumVal;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UserType implements EntityEnumerable{
    PARENT("0", "parent"),
    CHILD("1", "child");

    private final String type;
    private final String name;

    @jakarta.persistence.Converter
    public static class Converter extends EntityEnumerableConverter<UserType> {
        public Converter() {
            super(UserType.class);
        }
    }
}
