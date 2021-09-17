package dao;

import vo.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {

    List<User> queryUsers(User user, int pageNum, int lineNum) throws SQLException;

    User queryUserById(java.lang.String userId) throws SQLException;

    void addUser(User user) throws SQLException;

    int queryUserCount(User u) throws SQLException;

    void editUser(User user) throws SQLException;

    void deleteUser(String userId) throws SQLException;
}
