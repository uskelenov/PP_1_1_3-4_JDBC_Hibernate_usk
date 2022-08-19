package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;
import javax.persistence.criteria.CriteriaQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getSessionFactory();
    private static final Logger logger = Logger.getLogger(UserDaoJDBCImpl.class.getName());
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS test_users_hibernate (" +
                "id int NOT NULL AUTO_INCREMENT," +
                "name varchar(100) NOT NULL," +
                "lastName varchar(100) NOT NULL," +
                "age int DEFAULT NULL," +
                "PRIMARY KEY (id)" +
                ")";
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            transaction.commit();
            logger.log(Level.INFO, "Таблица создана");
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS bdusk.test_users_hibernate";
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            transaction.commit();
            logger.log(Level.INFO, "Таблица удалена");
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
            System.out.printf("User с именем – %s добавлен в базу данных %n", name);
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.remove(user);
                logger.log(Level.INFO, "Пользователь с ID({0}) удален \n", id);
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    @Deprecated
    public List<User> getAllUsers() {
        List<User> listAllUsers = new ArrayList<>();
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            CriteriaQuery<User> criteriaQuery = session
                    .getCriteriaBuilder()
                    .createQuery(User.class);
            criteriaQuery.from(User.class);
            listAllUsers = session.createQuery(criteriaQuery).getResultList();
            transaction.commit();
            logger.log(Level.INFO, "Все пользователи получены и добавлены в список");
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return listAllUsers;
    }

    @Override
    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE test_users_hibernate";
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            transaction.commit();
            logger.log(Level.INFO, "Таблица очищена");
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
