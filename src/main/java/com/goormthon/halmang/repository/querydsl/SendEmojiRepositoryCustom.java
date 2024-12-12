package com.goormthon.halmang.repository.querydsl;

import com.goormthon.halmang.domain.emoji.responseDto.SendEmojiRes;

import java.time.LocalDate;
import java.util.List;

public interface SendEmojiRepositoryCustom {

    List<SendEmojiRes> findAllUnread(String userId);

    List<SendEmojiRes> findAllByDateWithUserId(LocalDate date, String userId);

    Long getCountAtToday(String userId);

}
