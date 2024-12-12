package com.goormthon.halmang.domain.emoji.responseDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class SendEmojiRes {

    @JsonProperty("send_seq")
    private Long sendSeq;

    @JsonProperty("e_id")
    private String eId;

    @JsonProperty("sender_id")
    private String senderId;

    @JsonProperty("receiver_id")
    private String receiverId;

    @JsonProperty("reg_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime regTime;

    @QueryProjection
    public SendEmojiRes(String eId, String senderId, String receiverId, LocalDateTime regTime) {
        this.eId = eId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.regTime = regTime;
    }

    @QueryProjection
    public SendEmojiRes(Long SendSeq, String eId, String senderId, String receiverId, LocalDateTime regTime) {
        this.sendSeq = SendSeq;
        this.eId = eId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.regTime = regTime;
    }

}
