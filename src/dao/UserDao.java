package dao;

import bean.User;

import java.util.List;
import java.util.Map;

/**
 * 用户操作的dao
 * @author gyy
 * @date 2020/2/8 - 20:41
 */
public interface UserDao {
    public List<User> findAll();

    public User findOne(String username, String password);

    public void addUser(User user);

    public void delUser(int id);

    public User findUserById(int id);

    public void updateUser(User user);

    /**
     * 从start位置开始，查询rows_int条记录，并返回List<User>集合
     * @param start
     * @param rows_int
     * @param condition_map
     */
    List<User> findByPage(int start, int rows_int, Map<String, String[]> condition_map);

    int findTotalCount(Map<String, String[]> condition_map);
}
