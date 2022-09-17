package springbook.user.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import springbook.user.domain.User;


class UserDaoTest {

    @BeforeEach
    void setUp() {
        // TODO : Clear DB Data
    }

    @AfterEach
    void tearDown() {
        // TODO : Clear DB Data
    }

    @Test
    void userInfoShouldBeSameAfterGet() throws SQLException, ClassNotFoundException {
        // Prepare
        User withbeth = new User();
        withbeth.setId("4");
        withbeth.setName("withbeth");
        withbeth.setPassword("0000");

        UserDao userDao = new UserDao();
        userDao.add(withbeth);

        // Execute
        User withBethFromDB = userDao.get(withbeth.getId());

        // Verify
        assertEquals(withbeth.getId(), withBethFromDB.getId());
        assertEquals(withbeth.getName(), withBethFromDB.getName());
        assertEquals(withbeth.getPassword(), withBethFromDB.getPassword());
    }

}