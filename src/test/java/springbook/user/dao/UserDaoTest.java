package springbook.user.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import springbook.user.domain.User;


class UserDaoTest {

    DaoFactory daoFactory = new DaoFactory();

    @BeforeEach
    void setUp() {
        // TODO : Clear DB Data
    }

    @AfterEach
    void tearDown() {
        // TODO : Clear DB Data
    }

    @Test
    void allDBReadResultShouldBeSameAfterWrite() throws SQLException, ClassNotFoundException {
        // Prepare
        User withbeth = new User();
        withbeth.setId("8");
        withbeth.setName("withbeth");
        withbeth.setPassword("0000");

        UserDao devUserDao = daoFactory.devDBUserDao();
        devUserDao.add(withbeth);

        UserDao prodUserDao = daoFactory.prodDBUserDao();
        prodUserDao.add(withbeth);

        // Execute
        User userFromDevDB = devUserDao.get(withbeth.getId());
        User userFromProdDB = prodUserDao.get(withbeth.getId());

        // Verify
        assertEquals(withbeth.getId(), userFromDevDB.getId());
        assertEquals(withbeth.getName(), userFromDevDB.getName());
        assertEquals(withbeth.getPassword(), userFromDevDB.getPassword());

        // Verify
        assertEquals(withbeth.getId(), userFromProdDB.getId());
        assertEquals(withbeth.getName(), userFromProdDB.getName());
        assertEquals(withbeth.getPassword(), userFromProdDB.getPassword());
    }

}