package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;
import java.sql.Statement;

public class Main  {
    public static void main(String[] args) throws Exception  {

//        Util util = new Util();
//        util.getConnection();
        try {
            UserDaoJDBCImpl userDao = new UserDaoJDBCImpl();

            userDao.createUsersTable();

            userDao.saveUser("Donald", "Trump", (byte) 76);

            userDao.saveUser("Liz", "Truss", (byte) 45);

            userDao.saveUser("Joseph Robinette", "Biden Jr.", (byte) 81);

            userDao.saveUser("Boris", "Johnson)", (byte) 58);


            userDao.removeUserById(1);
            userDao.getAllUsers();
            userDao.cleanUsersTable();
            userDao.dropUsersTable();
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
