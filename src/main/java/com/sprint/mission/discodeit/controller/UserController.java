package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.data.UserDto;
import com.sprint.mission.discodeit.dto.request.UserCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserStatusUpdateRequest;
import com.sprint.mission.discodeit.dto.request.UserUpdateRequest;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.UserStatusService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/mission4/users")
@AllArgsConstructor
public class UserController {
    private UserService userService;
    private UserStatusService userStatusService;

    // 사용자 등록
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createUser(@ModelAttribute UserCreateRequest request) {
        System.out.println("######### postUser");
        System.out.println("# request = " + request);

        return ResponseEntity.ok(userService.create(request, Optional.empty()));
    }

    // 사용자 수정
    @RequestMapping(value = "/{userId}", method = RequestMethod.PATCH)
    public ResponseEntity updateUser(@PathVariable UUID userId,
                                     @RequestBody UserUpdateRequest request) {
        System.out.println("######### patchUser");
        System.out.println("# userId = " + userId);
        System.out.println("# request = " + request);

        return ResponseEntity.ok(userService.update(userId, request, Optional.empty()));
    }

    // 사용자 삭제
    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@PathVariable UUID userId) {
        System.out.println("######### deleteUser");
        System.out.println("# userId = " + userId);

        userService.delete(userId);

        return ResponseEntity.noContent().build();
    }

    // 모든 사용자 조회
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity findAllUsers() {
        System.out.println("######### findAllUsers");

        return ResponseEntity.ok(userService.findAll());
    }

    // 사용자의 온라인 상태 업데이트
    @RequestMapping(value = "/{userId}/status", method = RequestMethod.PATCH)
    public ResponseEntity updateUserStatus(@PathVariable UUID userId,
                                           @RequestBody UserStatusUpdateRequest request) {
        System.out.println("######### patchUserStatus");
        System.out.println("# request = " + request);

        return ResponseEntity.ok(userStatusService.updateByUserId(userId, request));
    }
}
