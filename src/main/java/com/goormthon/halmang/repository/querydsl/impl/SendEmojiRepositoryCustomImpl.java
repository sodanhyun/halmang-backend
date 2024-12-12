package com.goormthon.halmang.repository.querydsl.impl;

import com.goormthon.halmang.domain.emoji.responseDto.QSendEmojiRes;
import com.goormthon.halmang.domain.emoji.responseDto.SendEmojiRes;
import com.goormthon.halmang.repository.querydsl.Querydsl4RepositorySupport;
import com.goormthon.halmang.repository.querydsl.SendEmojiRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.goormthon.halmang.entity.QSendEmoji.sendEmoji;

public class SendEmojiRepositoryCustomImpl extends Querydsl4RepositorySupport implements SendEmojiRepositoryCustom {

    protected SendEmojiRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        super(queryFactory);
    }

    @Override
    public List<SendEmojiRes> findAllUnread(String userId) {
        return select(new QSendEmojiRes(
                sendEmoji.sendSeq,
                sendEmoji.eId,
                sendEmoji.sender.id,
                sendEmoji.receiver.id,
                sendEmoji.regTime
                ))
                .from(sendEmoji)
                .where(sendEmoji.receiver.id.eq(userId)
                        .and(sendEmoji.readFlag.eq(Boolean.FALSE)))
                .fetch();
    }

    @Override
    public List<SendEmojiRes> findAllByDateWithUserId(LocalDate date, String userId) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        return select(new QSendEmojiRes(
                sendEmoji.sendSeq,
                sendEmoji.eId,
                sendEmoji.sender.id,
                sendEmoji.receiver.id,
                sendEmoji.regTime
                )).from(sendEmoji)
                .where((sendEmoji.sender.id.eq(userId)
                        .or(sendEmoji.receiver.id.eq(userId)))
                        .and(sendEmoji.regTime.between(startOfDay, endOfDay)))
                .orderBy(sendEmoji.sendSeq.asc())
                .fetch();
    }

    @Override
    public Long getCountAtToday(String userId) {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);
        return select(sendEmoji.count())
                .from(sendEmoji)
                .where(sendEmoji.receiver.id.eq(userId)
                        .and(sendEmoji.regTime.between(startOfDay, endOfDay)))
                .fetchOne();
    }
}
