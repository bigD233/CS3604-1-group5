package com.example.whitecommunity.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.example.whitecommunity.pojo.User;
import com.example.whitecommunity.pojo.UserMessage;
import com.example.whitecommunity.pojo.UserMessageSummary;
import com.example.whitecommunity.pojo.UserPunishment;
import com.example.whitecommunity.result.LoginResult;
import com.example.whitecommunity.result.PageResult;
import com.example.whitecommunity.result.Result;
import com.example.whitecommunity.service.ClassificationService;
import com.example.whitecommunity.service.UserFollowingService;
import com.example.whitecommunity.service.UserMessageService;
import com.example.whitecommunity.service.UserMessageSummaryService;
import com.example.whitecommunity.service.UserPunishmentService;
import com.example.whitecommunity.service.UserService;
import com.example.whitecommunity.utils.JwtUtils;
import org.springframework.web.client.RestTemplate;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    UserFollowingService userFollowingService;

    @Autowired
    UserMessageSummaryService userMessageSummaryService;

    @Autowired
    UserMessageService userMessageService;

    @Autowired
    UserPunishmentService userPunishmentService;

    @Autowired
    ClassificationService classificationService;

    @Autowired
    RestTemplate restTemplate;

    @CrossOrigin
    @PostMapping("/api/user/login")
    public Result<LoginResult> login(@RequestParam String username, @RequestParam String password) {
        User user = userService.login(username, password);
        if (user == null) {
            return Result.error("用户名或密码错误");
        }
        String token = JwtUtils.sign(user.getId(), user.getPassword());
        return Result.success(new LoginResult(user.getId(), token));
    }

    @CrossOrigin
    @PostMapping("/api/user/add")
    public Result<LoginResult> addUser(@RequestParam String username, @RequestParam String password) {
        username = classificationService.classifyText(username);
        if (username.contains(" ") || username.contains("*")) {
            return Result.error("用户名含有不当内容");
        }
        User user = userService.add(username, password);
        if (user == null) {
            return Result.error("用户名已被使用");
        }
        String token = JwtUtils.sign(user.getId(), user.getPassword());
        return Result.success(new LoginResult(user.getId(), token));
    }

    @CrossOrigin
    @GetMapping("/api/user/get")
    public Result<User> getUser(@RequestParam int id) {
        User user = userService.get(id);
        if (user == null) {
            return Result.error("用户不存在");
        }
        return Result.success(user);
    }

    @CrossOrigin
    @PostMapping("/api/user/update/setting")
    public Result<String> updateUserSetting(@RequestParam int id, @RequestParam(required = false) String sign,
            @RequestParam(required = false) String avatar) {
        Subject subject = SecurityUtils.getSubject();
        String jwt = (String) subject.getPrincipal();

        if (JwtUtils.getId(jwt) != id) {
            return Result.forbidden();
        }

        sign = classificationService.classifyText(sign);

        User user = userService.updateSetting(id, sign, avatar);
        if (user == null) {
            return Result.error("更新失败");
        }
        return Result.success(null);
    }

    @CrossOrigin
    @PostMapping("/api/user/update/password")
    public Result<LoginResult> updateUserPassword(@RequestParam int id, @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        Subject subject = SecurityUtils.getSubject();
        String jwt = (String) subject.getPrincipal();

        if (JwtUtils.getId(jwt) != id) {
            return Result.forbidden();
        }

        User user = userService.updatePassword(id, oldPassword, newPassword);
        if (user == null) {
            return Result.error("密码错误");
        }
        String token = JwtUtils.sign(user.getId(), user.getPassword());
        return Result.success(new LoginResult(user.getId(), token));
    }

    @CrossOrigin
    @PostMapping("/api/user/update/info")
    public Result<String> updateUserInfo(@RequestParam int id, @RequestParam Long birthday,
            @RequestParam String sex, @RequestParam Integer height,
            @RequestParam Integer weight) {
        Subject subject = SecurityUtils.getSubject();
        String jwt = (String) subject.getPrincipal();

        if (JwtUtils.getId(jwt) != id) {
            return Result.forbidden();
        }

        User user = userService.updateInfo(id, birthday > 0 ? new Date(birthday) : null, sex, height, weight);
        if (user == null) {
            return Result.error("更新失败");
        }
        return Result.success(null);
    }

    @CrossOrigin
    @PostMapping("/api/user/add/following")
    public Result<String> addFollowing(@RequestParam int followee, @RequestParam int follower) {
        Subject subject = SecurityUtils.getSubject();
        String jwt = (String) subject.getPrincipal();

        if (JwtUtils.getId(jwt) != follower) {
            return Result.forbidden();
        }

        if (followee == follower) {
            return Result.error("参数错误");
        }

        userFollowingService.add(followee, follower);

        return Result.success(null);
    }

    @CrossOrigin
    @PostMapping("/api/user/remove/following")
    public Result<String> removeFollowing(@RequestParam int followee, @RequestParam int follower) {
        Subject subject = SecurityUtils.getSubject();
        String jwt = (String) subject.getPrincipal();

        if (JwtUtils.getId(jwt) != follower) {
            return Result.forbidden();
        }

        userFollowingService.remove(followee, follower);

        return Result.success(null);
    }

    @CrossOrigin
    @GetMapping("/api/user/get/following")
    public Result<Boolean> getFollowing(@RequestParam int followee, @RequestParam int follower) {
        return Result.success(userFollowingService.exists(followee, follower));
    }

    @CrossOrigin
    @GetMapping("/api/user/get/follower")
    public Result<PageResult<User>> getFollowers(@RequestParam int followee,
            @RequestParam int pageSize,
            @RequestParam int pageNum) {
        List<User> list = userService.getFollowers(followee, pageSize, pageNum - 1);
        if (list == null) {
            return Result.error("用户不存在");
        }
        int total = userFollowingService.countFollowers(followee);
        return Result.success(new PageResult<>(list, total));
    }

    @CrossOrigin
    @GetMapping("/api/user/get/followee")
    public Result<PageResult<User>> getFollowees(@RequestParam int follower,
            @RequestParam int pageSize,
            @RequestParam int pageNum) {
        List<User> list = userService.getFollowees(follower, pageSize, pageNum - 1);
        if (list == null) {
            return Result.error("用户不存在");
        }
        int total = userFollowingService.countFollowees(follower);
        return Result.success(new PageResult<>(list, total));
    }

    @CrossOrigin
    @PostMapping("/api/message/add")
    public Result<String> newMessage(@RequestParam int from, @RequestParam int to, String message) {
        Subject subject = SecurityUtils.getSubject();
        String jwt = (String) subject.getPrincipal();

        if (JwtUtils.getId(jwt) != from) {
            return Result.forbidden();
        }

        if (from == to) {
            return Result.error("参数错误");
        }
        userMessageService.add(from, to, message);
        userMessageSummaryService.increaseUnread(from, to, message);
        return Result.success(null);
    }

    @CrossOrigin
    @PostMapping("/api/message/read")
    public Result<String> readMessage(@RequestParam int from, @RequestParam int to) {
        Subject subject = SecurityUtils.getSubject();
        String jwt = (String) subject.getPrincipal();

        if (JwtUtils.getId(jwt) != to) {
            return Result.forbidden();
        }

        userMessageSummaryService.clearUnread(from, to);
        return Result.success(null);
    }

    @CrossOrigin
    @GetMapping("/api/message/summary")
    public Result<Page<UserMessageSummary>> getMessageSummary(@RequestParam int id, @RequestParam int pageSize,
            @RequestParam int pageNum) {
        Subject subject = SecurityUtils.getSubject();
        String jwt = (String) subject.getPrincipal();

        if (JwtUtils.getId(jwt) != id) {
            return Result.forbidden();
        }

        Page<UserMessageSummary> page = userMessageSummaryService.findByTo(id, pageSize, pageNum);
        return Result.success(page);
    }

    @CrossOrigin
    @GetMapping("/api/message/get")
    public Result<Page<UserMessage>> getMessage(@RequestParam int id1, @RequestParam int id2,
            @RequestParam int pageSize,
            @RequestParam int pageNum) {
        Subject subject = SecurityUtils.getSubject();
        String jwt = (String) subject.getPrincipal();
        int jwtId = JwtUtils.getId(jwt);

        if (jwtId != id1 && jwtId != id2) {
            return Result.forbidden();
        }

        Page<UserMessage> page = userMessageService.get(id1, id2, pageSize, pageNum);
        return Result.success(page);
    }

    @CrossOrigin
    @GetMapping("/api/user/role")
    public Result<List<String>> getRole() {
        Subject subject = SecurityUtils.getSubject();
        List<String> roles = new ArrayList<>();

        if (subject.hasRole("normal")) {
            roles.add("normal");
        }

        if (subject.hasRole("admin")) {
            roles.add("admin");
        }

        return Result.success(roles);
    }

    @CrossOrigin
    @GetMapping("/api/user/punishment/get")
    public Result<UserPunishment> getPunishment(@RequestParam int userId) {
        return Result.success(userPunishmentService.get(userId));
    }

    @CrossOrigin
    @GetMapping("/api/user/punishment/all")
    public Result<List<UserPunishment>> getAllPunishment() {
        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole("admin")) {
            return Result.forbidden();
        }

        return Result.success(userPunishmentService.getAll());
    }

    @CrossOrigin
    @PostMapping("/api/user/punishment/set")
    public Result<String> setPunishment(@RequestParam int userId, @RequestParam int days) {
        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole("admin")) {
            return Result.forbidden();
        }

        userPunishmentService.punish(userId, days);
        return Result.success(null);
    }

    @CrossOrigin
    @PostMapping("/api/user/punishment/stop")
    public Result<String> stopPunishment(@RequestParam int userId) {
        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole("admin")) {
            return Result.forbidden();
        }

        userPunishmentService.stop(userId);
        return Result.success(null);
    }

    @GetMapping("/api/user/recommend")
    @CrossOrigin
    public List<Integer> getRecommendAnswer(@RequestParam int userId) {
        List<Integer> res = restTemplate.getForObject("http://localhost:4333/user_to_user?user_id=" + userId,
                List.class);
        return res;
    }

}
