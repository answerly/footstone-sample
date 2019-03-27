package com.olasharing.footstone.sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@RefreshScope
@RestController
public class AppController {

    @Value("${pass.test}")
    private String passTest;

    @Autowired
    private DataSource dataSource;

    public void setPassTest(String passTest) {
        this.passTest = passTest;
    }

    @GetMapping("/props")
    public String props() {
        return passTest;
    }

    @GetMapping("/tables")
    public String tables() throws Exception {
        StringBuilder sb = new StringBuilder();
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SHOW TABLES");
            while (rs.next()) {
                if (sb.length() != 0) {
                    sb.append(",");
                }
                sb.append(rs.getString(1));
            }
        }
        return sb.toString();
    }
}
