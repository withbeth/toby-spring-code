package springbook.user.dao;

public class DaoFactory {

    public UserDao userDao(ConnectionMaker connectionMaker) {
        return new UserDao(connectionMaker);
    }

    public UserDao devDBUserDao() {
        return userDao(devDBConnectionMaker());
    }

    public UserDao prodDBUserDao() {
        return userDao(prodDBConnectionMaker());
    }

    public DevDBConnectionMaker devDBConnectionMaker() {
        return new DevDBConnectionMaker();
    }

    public ProdDBConnectionMaker prodDBConnectionMaker() {
        return new ProdDBConnectionMaker();
    }

}
