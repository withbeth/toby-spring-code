package springbook.user.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springbook.user.domain.User;


class UserDaoTest {

    @BeforeEach
    void setUp() {
        // TODO : Clear DB Data
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addAndGet() throws SQLException, ClassNotFoundException {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
            DaoFactory.class);
        UserDao eastRegionUserDao = ctx.getBean("eastRegionUserDao", UserDao.class);
        UserDao westRegionUserDao = ctx.getBean("westRegionUserDao", UserDao.class);

        User user = new User();
        user.setId("10");
        user.setName("withbeth");
        user.setPassword("0000");

        assertTrue(eastRegionUserDao.getCount() > 0);
        assertTrue(westRegionUserDao.getCount() > 0);

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