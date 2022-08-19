package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection = Util.getConnection();
    private static final Logger logger = Logger.getLogger(UserDaoJDBCImpl.class.getName());
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS test_users (" +
                "id int NOT NULL AUTO_INCREMENT," +
                "name varchar(100) NOT NULL," +
                "lastName varchar(100) NOT NULL," +
                "age int DEFAULT NULL," +
                "PRIMARY KEY (id)" +
                ")";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            logger.log(Level.INFO, "Таблица создана");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS bdusk.test_users";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            logger.log(Level.INFO, "Таблица удалена");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO bdusk.test_users(name, lastname, age) VALUES(?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.printf("User с именем – %s добавлен в базу данных %n", name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM bdusk.test_users WHERE ID=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            logger.log(Level.INFO, "Пользователь с ID({0}) удален \n", id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> listAllUsers = new ArrayList<>();
        String sql = "SELECT id, name, lastName, age FROM bdusk.test_users";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery(sql);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                listAllUsers.add(user);
            }
            logger.log(Level.INFO, "Все пользователи получены и добавлены в список");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listAllUsers;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM bdusk.test_users";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            logger.log(Level.INFO, "Таблица очищена");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
