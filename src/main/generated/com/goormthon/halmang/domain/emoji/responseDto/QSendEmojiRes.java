package com.goormthon.halmang.domain.emoji.responseDto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.goormthon.halmang.domain.emoji.responseDto.QSendEmojiRes is a Querydsl Projection type for SendEmojiRes
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QSendEmojiRes extends ConstructorExpression<SendEmojiRes> {

    private static final long serialVersionUID = -1800278256L;

    public QSendEmojiRes(com.querydsl.core.types.Expression<String> eId, com.querydsl.core.types.Expression<String> senderId, com.querydsl.core.types.Expression<String> receiverId, com.querydsl.core.types.Expression<java.time.LocalDateTime> regTime) {
        super(SendEmojiRes.class, new Class<?>[]{String.class, String.class, String.class, java.time.LocalDateTime.class}, eId, senderId, receiverId, regTime);
    }

    public QSendEmojiRes(com.querydsl.core.types.Expression<Long> SendSeq, com.querydsl.core.types.Expression<String> eId, com.querydsl.core.types.Expression<String> senderId, com.querydsl.core.types.Expression<String> receiverId, com.querydsl.core.types.Expression<java.time.LocalDateTime> regTime) {
        super(SendEmojiRes.class, new Class<?>[]{long.class, String.class, String.class, String.class, java.time.LocalDateTime.class}, SendSeq, eId, senderId, receiverId, regTime);
    }

}

