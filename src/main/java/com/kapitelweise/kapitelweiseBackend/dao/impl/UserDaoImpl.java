package com.kapitelweise.kapitelweiseBackend.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.kapitelweise.kapitelweiseBackend.dao.UserDao;
import com.kapitelweise.kapitelweiseBackend.domain.AppUser;

//die konkrete Implementierung, hier führen wir unten die Datenbankabfrage durch.
//@Repository ist ein Spring Annotation, die sagt, dass diese Klasse ein Repository ist, also eine Datenzugriffsschicht.
// d.h. UserDao --> UserDaoImpl und --> AppUser arbeiten quasi zusammen.


@Repository
public class UserDaoImpl implements UserDao {


    private final JdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(UserDaoImpl.class);

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean userExists(String username) {
        // Implement logic to check if the user exists in the database
        return false; // Placeholder implementation
    }

    @Override
    public void saveUser(AppUser user) {
        // Implement logic to save the user to the database
    }

    @Override
   public Optional<AppUser> findByUsername(String username){
    List <AppUser> users = jdbcTemplate.query("Select username, password from users where username = ? Limit 1",
    new UserRowMapper(), username);

    return users.stream().findFirst();
   }


   public static class UserRowMapper implements RowMapper<AppUser> {
        @Override
        public AppUser mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new AppUser(rs.getString("username"), rs.getString("password"));
        }
    }
}