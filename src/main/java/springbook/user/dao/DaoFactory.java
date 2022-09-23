package springbook.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoFactory {

    public UserDao userDao(ConnectionMaker connectionMaker) {
        return new UserDao(connectionMaker);
    }

    @Bean
    public UserDao eastRegionUserDao() {
        return userDao(eastRegionDBConnectionMaker());
    }

    @Bean
    public UserDao westRegionUserDao() {
        return userDao(westRegionDBConnectionMaker());
    }

    @Bean
    public EastRegionDBConnectionMaker eastRegionDBConnectionMaker() {
        return new EastRegionDBConnectionMaker();
    }

    @Bean
    public WestRegionDBConnectionMaker westRegionDBConnectionMaker() {
        return new WestRegionDBConnectionMaker();
    }

}
