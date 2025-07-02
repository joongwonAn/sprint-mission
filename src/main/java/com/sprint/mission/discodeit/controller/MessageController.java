package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.request.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.request.MessageUpdateRequest;
import com.sprint.mission.discodeit.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/messages")
@AllArgsConstructor
public class MessageController {
    private final MessageService messageService;

    // 메시지 보내기
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity sendMessage(@RequestBody MessageCreateRequest request) {
        System.out.println("######### sendMessage");
        System.out.println("# request = " + request);

        return ResponseEntity.ok(messageService.create(request, List.of()));
    }

    // 메시지 수정
    @RequestMapping(value = "/{message-id}", method = RequestMethod.PATCH)
    public ResponseEntity updateMessage(@PathVariable("message-id") UUID messageId,
                                        @RequestBody MessageUpdateRequest request) {
        System.out.println("######### updateMessage");
        System.out.println("# request = " + request);

        return ResponseEntity.ok(messageService.update(messageId, request));
    }

    // 메시지 삭제
    @RequestMapping(value = "/{message-id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteMessage(@PathVariable("message-id") UUID messageId) {
        System.out.println("######### deleteMessage");
        System.out.println("# messageId = " + messageId);

        messageService.delete(messageId);

        return ResponseEntity.noContent().build();
    }

    // 특정 채널의 메시지 목록 조회
    @RequestMapping(value = "channels/{channel-id}/message", method = RequestMethod.GET)
    public ResponseEntity findAllMessagesByChannelId(@PathVariable("channel-id") UUID channelId) {
        System.out.println("######### findAllMessagesByChannelId");
        System.out.println("# channelId = " + channelId);

        return ResponseEntity.ok(messageService.findAllByChannelId(channelId));
    }
}
