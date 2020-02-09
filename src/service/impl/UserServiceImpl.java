package service.impl;

import bean.PageBean;
import bean.User;
import dao.UserDao;
import dao.impl.UserDaoImpl;
import service.UserService;

import java.util.List;
import java.util.Map;

/**
 * @author gyy
 * @date 2020/2/8 - 20:39
 */
public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();
    @Override
    public List<User> findAll() {
        //调用dao完成查询
        return userDao.findAll();
    }

    @Override
    public User findOne(User user) {
        return userDao.findOne(user.getUsername(), user.getPassword());
    }

    @Override
    public void addUser(User user) {
        userDao.addUser(user);
    }

    @Override
    public void delUSer(String id) {
        userDao.delUser(Integer.parseInt(id));
    }

    @Override
    public User findUserById(String id) {
        return userDao.findUserById(Integer.parseInt(id));
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public void delSelectedUsers(String[] ids) {
        for (int i = 0; i < ids.length; i++) {
            delUSer(ids[i]);
        }
    }

    @Override
    public PageBean<User> findUserByPage(String currentPage, String rows, Map<String, String[]> condition_map) {
        //创建空的pb对象
        PageBean<User> pb = new PageBean<User>();

        //转化类型
        int currentPage_int = Integer.parseInt(currentPage);
        int rows_int = Integer.parseInt(rows);

        //设置当前页码和每页行数
        pb.setCurrentPage(currentPage_int);
        pb.setRows(rows_int);

        //计算开始查询的位置
        int start = (currentPage_int - 1) * rows_int;

        //UserDao对象进行数据库查询user集合
        List<User> users = userDao.findByPage(start, rows_int, condition_map);
        //存储该页的List<User>
        pb.setList(users);

        //设置总记录数
        int totalCount = userDao.findTotalCount(condition_map);
        pb.setTotalCount(totalCount);

        //设置总页码
        double totalPage = Math.ceil((double) totalCount / rows_int);
        int totalPage_int = (int) totalPage;
        pb.setTotalPage(totalPage_int);

        return pb;
    }
}
