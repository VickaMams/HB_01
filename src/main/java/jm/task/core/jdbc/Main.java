package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.sql.SQLException;
import java.sql.Statement;

public class Main  {
    public static void main(String[] args) throws Exception  {
        Session session = null;

        try {
//            UserDaoJDBCImpl userDao = new UserDaoJDBCImpl();
            UserServiceImpl us = new UserServiceImpl();
            us.createUsersTable();

            us.saveUser("Donald", "Trump", (byte) 76);

            us.saveUser("Liz", "Truss", (byte) 45);

            us.saveUser("Joseph Robinette", "Biden Jr.", (byte) 81);

            us.saveUser("Boris", "Johnson)", (byte) 58);


            us.removeUserById(1);
            us.getAllUsers();
            us.cleanUsersTable();
            us.dropUsersTable();
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
