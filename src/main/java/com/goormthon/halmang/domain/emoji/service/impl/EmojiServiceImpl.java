package com.goormthon.halmang.domain.emoji.service.impl;

import com.goormthon.halmang.domain.emoji.responseDto.SendEmojiRes;
import com.goormthon.halmang.domain.emoji.service.EmojiService;
import com.goormthon.halmang.entity.SendEmoji;
import com.goormthon.halmang.entity.User;
import com.goormthon.halmang.repository.SendEmojiRepository;
import com.goormthon.halmang.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class EmojiServiceImpl implements EmojiService {

    private final SendEmojiRepository sendEmojiRepository;
    private final UserRepository userRepository;

    @Override
    public List<SendEmojiRes> getUnreadEmojis(String userId) {
        return sendEmojiRepository.findAllUnread(userId);
    }

    @Override
    public void sendEmoji(String eId, String senderId, String receiverId) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(EntityNotFoundException::new);
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(EntityNotFoundException::new);
        SendEmoji sendEmoji = SendEmoji.builder()
                .eId(eId)
                .sender(sender)
                .receiver(receiver)
                .readFlag(false)
                .build();
        sendEmojiRepository.save(sendEmoji);
    }

    @Override
    public void readDetail(Long sendSeq, String userId) throws RuntimeException {
        SendEmoji sendEmoji = sendEmojiRepository.findById(sendSeq).orElseThrow(EntityNotFoundException::new);
        if(sendEmoji.getReceiver().getId().equals(userId)) {
            SendEmoji updatedEmoji = sendEmoji.updateReadFlag(true);
            log.info(updatedEmoji.toString());
        }else {
            throw new RuntimeException("its not yours");
        }
    }

    @Override
    public List<SendEmojiRes> getEmojiHistByDate(LocalDate date, String userId) {
        return sendEmojiRepository.findAllByDateWithUserId(date, userId);
    }

    @Override
    public Long getCountAtToday(String userId) {
        return sendEmojiRepository.getCountAtToday(userId);
    }
}
