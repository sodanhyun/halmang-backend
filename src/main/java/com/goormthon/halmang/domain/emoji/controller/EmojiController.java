package com.goormthon.halmang.domain.emoji.controller;

import com.goormthon.halmang.domain.emoji.requestDto.SendEmojiReq;
import com.goormthon.halmang.domain.emoji.responseDto.SendEmojiRes;
import com.goormthon.halmang.domain.emoji.service.EmojiService;
import com.goormthon.halmang.domain.sse.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/emoji")
@RequiredArgsConstructor
@Slf4j
public class EmojiController {
    private final EventService eventService;
    private final EmojiService emojiService;

    /*
    api1.
    이모지 조회 페이지 접근 시,
    해당 REQ를 보내는 유저가
    안읽은 이모지 리스트 조회
     */
    @GetMapping("/read")
    public ResponseEntity<List<SendEmojiRes>> read(Authentication authentication) {
        List<SendEmojiRes> unreadEmojis = emojiService.getUnreadEmojis(authentication.getName());
        return new ResponseEntity<>(unreadEmojis, HttpStatus.OK);
    }

    /*
    api2.
    이모지 보내기 페이지에서
    특정 id의 이모지를
    특정 user에게 전송
     */
    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestBody SendEmojiReq sendEmojiReq, Authentication authentication) {
        if(sendEmojiReq.getReceiverId().equals(authentication.getName()))
            return new ResponseEntity<>("you cant send yourself", HttpStatus.BAD_REQUEST);
        emojiService.sendEmoji(sendEmojiReq.getEId(), authentication.getName(), sendEmojiReq.getReceiverId());
        try{
            eventService.publish(sendEmojiReq.getReceiverId());
        }catch(Throwable e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
        return new ResponseEntity<>("emoji send successfully done", HttpStatus.OK);
    }

    /*
    api3.
    이모지 읽음 페이지에서
    이모지 읽음 처리
     */
    @PostMapping("/read_detail/{sendSeq}")
    public ResponseEntity<String> readDetail(@PathVariable("sendSeq") Long sendSeq, Authentication authentication) {
        try{
            emojiService.readDetail(sendSeq, authentication.getName());
        }catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("emoji read detail successfully done", HttpStatus.OK);
    }

    /*
    api4.
    이모지 히스토리 페이지에서
    이모지 주고받은 이력 조회,
    년/월/일 조건으로 필터
     */
    @GetMapping("/history")
    public ResponseEntity<?> history(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                     Authentication authentication) {
        List<SendEmojiRes> emojiHist = emojiService.getEmojiHistByDate(date, authentication.getName());
        return new ResponseEntity<>(emojiHist, HttpStatus.OK);
    }

    /*
    api5.
    user가 하루동안 받은
    이모지의 카운트 수
     */
    @GetMapping("/count")
    public ResponseEntity<?> count(Authentication authentication) {
        Long todayEmojiCnt = emojiService.getCountAtToday(authentication.getName());
        HashMap<String, Long> res = new HashMap<>();
        res.put("count", todayEmojiCnt);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /*
    api5.
    user가 하루동안 주고 받은
    이모지의 카운트 수
     */
    @GetMapping("/total_count")
    public ResponseEntity<?> totalCount(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                        Authentication authentication) {
        Long todayTotalEmojiCnt = emojiService.getTotalCountAtToday(authentication.getName(), date);
        HashMap<String, Long> res = new HashMap<>();
        res.put("total_count", todayTotalEmojiCnt);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
