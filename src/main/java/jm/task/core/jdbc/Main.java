package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserDao userDao = new UserDaoJDBCImpl();

        userDao.createUsersTable();
        userDao.saveUser("Иван", "Иванов", (byte) 55);
        userDao.saveUser("Елена", "Петрова", (byte) 22);
        userDao.saveUser("Александр", "Дрозд", (byte) 25);
        userDao.saveUser("Ненси", "Дрю", (byte) 29);

        List<User> listAllUsers = userDao.getAllUsers();
        System.out.println(listAllUsers.toString());

        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}
