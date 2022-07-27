package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Util util = new Util();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() throws SQLException {
        Connection connection= util.getConnection();
        connection.setAutoCommit(false);
        try (Statement statement = util.getConnection().createStatement()) {
             statement.executeUpdate("CREATE TABLE  IF NOT EXISTS users (id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(20), " +
                    "lastName VARCHAR(50), " +
                    "age TINYINT)");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
            if (connection!= null) {
                connection.close();
            }
        }

    }

    public void dropUsersTable() throws SQLException {
        Connection connection= util.getConnection();
        connection.setAutoCommit(false);
        try (Statement statement = util.getConnection().createStatement()){
            statement.executeUpdate("DROP TABLE IF EXISTS users");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            connection.setAutoCommit(true);
            if (connection!= null) {
                connection.close();
            }
        }

    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        Connection connection = util.getConnection();
        connection.setAutoCommit(false);
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (name, " +
                "lastName, age) VALUES (?, ?, ?)")) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем "+name+" добавлен в базу данных");
            connection.commit();


        } catch (SQLException e) {
            if (connection!= null) connection.rollback();
           e.printStackTrace();
        }   finally {
            connection.setAutoCommit(true);
            if (connection!= null) {
                connection.close();
            }
        }

    }

    public void removeUserById(long id) throws SQLException {
        Connection connection = util.getConnection();
        connection.setAutoCommit(false);
        try(PreparedStatement preparedStatement = util.getConnection().prepareStatement("DELETE FROM users " +
                "WHERE ID = ?")){

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) connection.rollback();
            e.printStackTrace();
        }finally {
            connection.setAutoCommit(true);
            if (connection!= null) {
                connection.close();
            }
        }

    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        Connection connection= util.getConnection();
        try (PreparedStatement preparedStatement = util.getConnection().prepareStatement("SELECT  * FROM users")) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");
                users.add(new User ( name, lastName, age));
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }finally {

            if (connection!= null) {
                connection.close();
            }
        }
        return users;
    }

    public void cleanUsersTable() throws SQLException {
        Connection connection = util.getConnection();
        connection.setAutoCommit(false);
        try (Statement statement = util.getConnection().createStatement()){

            statement.executeUpdate("TRUNCATE TABLE users");
            connection.commit();
        } catch (SQLException e) {
           e.printStackTrace();
        }finally {
            connection.setAutoCommit(true);
            if (connection!= null) {
                connection.close();
            }
        }
    }
}
