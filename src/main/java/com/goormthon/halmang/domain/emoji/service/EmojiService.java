package com.goormthon.halmang.domain.emoji.service;

import com.goormthon.halmang.domain.emoji.responseDto.SendEmojiRes;

import java.time.LocalDate;
import java.util.List;

public interface EmojiService {

    public List<SendEmojiRes> getUnreadEmojis(String userId);

    public void sendEmoji(String eId, String senderId, String receiverId);

    public void readDetail(Long sendSeq, String userId);

    public List<SendEmojiRes> getEmojiHistByDate(LocalDate date, String userId);

    public Long getCountAtToday(String userId);

}
