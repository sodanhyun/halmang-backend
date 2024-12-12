package com.goormthon.halmang.repository;

import com.goormthon.halmang.entity.SendEmoji;
import com.goormthon.halmang.repository.querydsl.SendEmojiRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SendEmojiRepository extends JpaRepository<SendEmoji, Long>, SendEmojiRepositoryCustom {

}
