package dao.impl;

import bean.User;
import dao.UserDao;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author gyy
 * @date 2020/2/8 - 20:42
 */
public class UserDaoImpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<User> findAll() {
        //使用jdbc操作数据库
        //定义sql
        String sql = "select * from user";
        List<User> users = template.query(sql, new BeanPropertyRowMapper<User>(User.class));
        return users;
    }

    @Override
    public User findOne(String username, String password) {
        //使用jdbc操作数据库
        //定义sql
        try {
            String sql = "select * from user where username = ? and password = ?";
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void addUser(User user) {
        //操作数据库完成添加功能
        String sql = "insert into user values(null, ?, ?, ?, ?, ?, ?, null, null)";
        template.update(sql, user.getName(), user.getGender(), user.getAge(), user.getAddress(), user.getQq(), user.getEmail());
    }

    @Override
    public void delUser(int id) {
        String sql = "delete from user where id = ?";
        template.update(sql, id);
    }

    @Override
    public User findUserById(int id) {
        try {
            String sql = "select * from user where id = ?";
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), id);
            return user;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateUser(User user) {
        String sql = "update user set name = ?, gender = ?, age = ?, address = ?, qq = ?, email = ? where id = ?";
        template.update(sql, user.getName(), user.getGender(), user.getAge(), user.getAddress(), user.getQq(), user.getEmail(), user.getId());
    }

    @Override
    public List<User> findByPage(int start, int rows_int, Map<String, String[]> condition_map) {
        try {
            String sql = "select * from user where 1 = 1 ";
            StringBuilder sb = new StringBuilder(sql);

            //定义参数集合
            List<Object> params = new ArrayList<>();

            //遍历map
            Set<String> keySet = condition_map.keySet();
            for (String key : keySet) {
                //排除分页的参数
                if("currentPage".equals(key) || "rows".equals(key)){
                    continue;
                }
                //获取value
                String value = condition_map.get(key)[0];
                if(value != null && !"".equals(value)){
                    //拼接sql
                    sb.append(" and " + key + " like ? ");
                    //保存参数的值
                    params.add("%" + value + "%");
                }
            }
            sb.append(" limit ? , ? ");
            //添加分页参数
            params.add(start);
            params.add(rows_int);


            List<User> users = template.query(sb.toString(), new BeanPropertyRowMapper<>(User.class), params.toArray());
            return users;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int findTotalCount(Map<String, String[]> condition_map) {

        //定义模板sql
        String sql = "select count(*) from user where 1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);

        //定义参数集合
        List<Object> params = new ArrayList<>();

        //遍历map
        Set<String> keySet = condition_map.keySet();
        for (String key : keySet) {
            //排除分页的参数
            if("currentPage".equals(key) || "rows".equals(key)){
                continue;
            }

            //获取value
            String value = condition_map.get(key)[0];
            if(value != null && !"".equals(value)){
                //拼接sql
                sb.append(" and " + key + " like ? ");
                //保存参数的值
                params.add("%" + value + "%");
            }
        }



        int totalCount = template.queryForObject(sb.toString(), Integer.class, params.toArray());
        return totalCount;
    }
}
