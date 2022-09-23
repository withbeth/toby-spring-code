package springbook.user.dao;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import springbook.user.domain.User;


class UserDaoTest {

    UserDao eastRegionUserDao;
    UserDao westRegionUserDao;

    @BeforeEach
    void setUp() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
            DaoFactory.class);
        eastRegionUserDao = ctx.getBean("eastRegionUserDao", UserDao.class);
        westRegionUserDao = ctx.getBean("westRegionUserDao", UserDao.class);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getUserWhoDoesNotExist() throws SQLException, ClassNotFoundException {

        eastRegionUserDao.deleteAll();
        westRegionUserDao.deleteAll();

        assertThatThrownBy(() -> eastRegionUserDao.get("spring-vol1"))
            .isInstanceOf(EmptyResultDataAccessException.class);

        assertThatThrownBy(() -> westRegionUserDao.get("spring-vol1"))
            .isInstanceOf(EmptyResultDataAccessException.class);
    }


    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void getCount(int num) throws SQLException, ClassNotFoundException {

        eastRegionUserDao.deleteAll();
        westRegionUserDao.deleteAll();

        for (int i = 0; i < num; i++) {
            User user = new User(String.valueOf(i), "user", "pwd");
            eastRegionUserDao.add(user);
            westRegionUserDao.add(user);
        }

        assertEquals(num, eastRegionUserDao.getCount());
        assertEquals(num, westRegionUserDao.getCount());

    }

    @Test
    void addAndGet() throws SQLException, ClassNotFoundException {

        User user = new User();
        user.setId("10");
        user.setName("withbeth");
        user.setPassword("0000");

        eastRegionUserDao.deleteAll();
        westRegionUserDao.deleteAll();

        assertEquals(0, eastRegionUserDao.getCount());
        assertEquals(0, westRegionUserDao.getCount());

        eastRegionUserDao.add(user);
        westRegionUserDao.add(user);

        assertEquals(1, eastRegionUserDao.getCount());
        assertEquals(1, westRegionUserDao.getCount());

        User userFromEast = eastRegionUserDao.get(user.getId());
        User userFromWest = westRegionUserDao.get(user.getId());

        assertEquals(user.getId(), userFromEast.getId());
        assertEquals(user.getName(), userFromEast.getName());
        assertEquals(user.getPassword(), userFromEast.getPassword());

        assertEquals(user.getId(), userFromWest.getId());
        assertEquals(user.getName(), userFromWest.getName());
        assertEquals(user.getPassword(), userFromWest.getPassword());
    }

}