package service;

import bean.PageBean;
import bean.User;

import java.util.List;
import java.util.Map;

/**
 * 用户管理的功能接口
 * @author gyy
 * @date 2020/2/8 - 20:37
 */
public interface UserService {
    /**
     * 查询所有用户信息
     * @return
     */
    public List<User> findAll();

    public User findOne(User user);

    public void addUser(User user);

    public void delUSer(String id);

    public User findUserById(String id);

    public void updateUser(User user);

    public void delSelectedUsers(String[] ids);

    /**
     * 分页条件查询
     * @param currentPage
     * @param rows
     * @param condition_map
     * @return Pagebean<User>
     */
    PageBean<User> findUserByPage(String currentPage, String rows, Map<String, String[]> condition_map);
}
