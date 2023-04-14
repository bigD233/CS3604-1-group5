package com.example.whitecommunity.service;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.whitecommunity.dao.UserDAO;
import com.example.whitecommunity.pojo.User;

@Service
public class UserService {
    @Autowired
    UserDAO userDAO;

    public String encodePassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean checkPassword(String password, String encodedPassword) {
        return BCrypt.checkpw(password, encodedPassword);
    }

    public User add(String username, String password) {
        if (userDAO.countByUsername(username) > 0) {
            return null;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(encodePassword(password));
        user = userDAO.save(user);

        return user;
    }

    public User login(String username, String password) {
        User user = userDAO.findByUsername(username);
        if (user == null || !checkPassword(password, user.getPassword())) {
            return null;
        }
        return user;
    }

    public User updatePassword(int id, String oldPassword, String newPassword) {
        User user = userDAO.findById(id);
        if (user == null || newPassword == null || !checkPassword(oldPassword, user.getPassword())) {
            return null;
        }

        user.setPassword(encodePassword(newPassword));
        user = userDAO.save(user);

        return user;
    }

    public User updateSetting(int id, String sign, String avatar) {
        User user = userDAO.findById(id);
        if (user == null) {
            return null;
        }

        if (sign != null) {
            user.setSign(sign);
        }

        if (avatar != null) {
            user.setAvatar(avatar);
        }

        user = userDAO.save(user);

        return user;
    }

    boolean checkBirthday(Date birthday) {
        return birthday.compareTo(new GregorianCalendar(1900, 0, 0).getTime()) > 0
                && birthday.compareTo(new Date()) < 0;
    }

    boolean checkSex(String sex) {
        return sex.equals("男") || sex.equals("女");
    }

    boolean checkHeight(int height) {
        return height > 0 && height < 300;
    }

    boolean checkWeight(int weight) {
        return weight > 0 && weight < 500;
    }

    public User updateInfo(int id, Date birthday, String sex, Integer height, Integer weight) {
        User user = userDAO.findById(id);
        if (user == null) {
            return null;
        }

        if (birthday != null && checkBirthday(birthday)) {
            user.setBirthday(new java.sql.Date(birthday.getTime()));
        }

        if (sex != null && checkSex(sex)) {
            user.setSex(sex);
        }

        if (height != null && checkHeight(height)) {
            user.setHeight(height);
        }

        if (weight != null && checkWeight(weight)) {
            user.setWeight(weight);
        }
        user = userDAO.save(user);

        return user;
    }

    public User get(int id) {
        User user = userDAO.findById(id);
        if (user == null) {
            return null;
        }
        return user;
    }

    public List<User> getFollowers(int followee, int pageSize, int pageNum) {
        if (pageSize < 0 || pageSize > 50 || pageNum < 0) {
            return null;
        }

        List<User> users = userDAO.getFollowers(followee, pageSize, pageNum);

        return users;
    }

    public List<User> getFollowees(int follower, int pageSize, int pageNum) {
        if (pageSize < 0 || pageSize > 50 || pageNum < 0) {
            return null;
        }

        List<User> users = userDAO.getFollowees(follower, pageSize, pageNum);

        return users;
    }

    /**
     * 查询所有关注的用户 (不分页)
     *
     * @param follower 关注着 id
     * @return 关注的用户的 id 的列表
     */
    public List<Integer> getFolloweesNoPage(int follower) {
        return userDAO.getFolloweesNoPage(follower);
    }
}
