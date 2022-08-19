package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь

        /*
         * Test JDBC
         */
        UserDao userDao = new UserDaoJDBCImpl();
        Util.getConnection();

        userDao.createUsersTable();
        userDao.saveUser("Иван", "Иванов", (byte) 35);
        userDao.saveUser("Елена", "Петрова", (byte) 29);
        userDao.saveUser("Александр", "Дрозд", (byte) 35);
        userDao.saveUser("Ненси", "Дрю", (byte) 59);

        List<User> listAllUsers = userDao.getAllUsers();
        System.out.println(listAllUsers.toString());

        userDao.cleanUsersTable();
        userDao.dropUsersTable();

        /*
        * Test Hibernate
        */
        UserDao userDaoHibernate = new UserDaoHibernateImpl();
//        Util.getSessionFactory();

        userDaoHibernate.createUsersTable();
        userDaoHibernate.saveUser("Федор", "Иванов", (byte) 55);
        userDaoHibernate.saveUser("Петр", "Петров", (byte) 22);
        userDaoHibernate.saveUser("Катерина", "Элиз", (byte) 25);
        userDaoHibernate.saveUser("Алиса", "Найн", (byte) 29);

        List<User> listAllUsersHibernate = userDaoHibernate.getAllUsers();
        System.out.println(listAllUsersHibernate.toString());

        userDaoHibernate.cleanUsersTable();
        userDaoHibernate.dropUsersTable();
    }
}
