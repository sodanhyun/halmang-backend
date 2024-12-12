package com.goormthon.halmang.domain.emoji.requestDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SendEmojiReq {

    @JsonProperty("e_id")
    private String eId;

    @JsonProperty("receiver_id")
    private String receiverId;

}
