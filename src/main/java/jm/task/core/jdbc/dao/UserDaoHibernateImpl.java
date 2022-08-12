package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private  SessionFactory sessionFactory = Util.getSessionFactory();
    private  Transaction transaction = null;

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE  IF NOT EXISTS User (id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(20), " +
                    "lastName VARCHAR(50), " +
                    "age TINYINT)").executeUpdate();
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS User").executeUpdate();
            session.getTransaction().commit();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()){
            transaction= session.beginTransaction();
            session.save(new User(name, lastName, age));
            System.out.println("User с именем "+name+" добавлен в базу данных");
            transaction.commit();

        } catch (Exception e) {
            if(transaction!=null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null){
                session.delete(user);
            }
            transaction.commit();
        }catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

    }

    @Override
    public List<User> getAllUsers() {
        List <User> allUsers = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            allUsers = session.createQuery( "FROM User").list();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return allUsers;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE User").executeUpdate();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
