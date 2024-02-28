package com.sp04440.sqlhost;

import jakarta.annotation.PostConstruct;
import org.h2.tools.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.Arrays;

@SpringBootApplication
public class SqlhostApplication {

	private final JdbcTemplate jdbcTemplate;

    public SqlhostApplication(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static void main(String[] args) {
		SpringApplication.run(SqlhostApplication.class, args);
	}

	@Bean(initMethod = "start", destroyMethod = "stop")
	public Server inMemoryH2DatabaseaServer() throws SQLException {
		return Server.createTcpServer(
				"-tcp", "-tcpAllowOthers", "-tcpPort", "9090");
	}


	@PostConstruct
	private void initDb() {
		String[] sqlStatements = {
				"drop table student if exists",
				"create table student(id serial,first_name varchar(255),last_name varchar(255))"
		};

		Arrays.asList(sqlStatements).forEach(jdbcTemplate::execute);

	}
}
