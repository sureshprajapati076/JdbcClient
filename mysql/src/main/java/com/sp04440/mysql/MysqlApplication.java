package com.sp04440.mysql;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@SpringBootApplication
@RestController
public class MysqlApplication {

    public MysqlApplication(JdbcTemplate jdbcTemplate, JdbcClient jdbcClient) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcClient = jdbcClient;
    }

    public static void main(String[] args) {
		SpringApplication.run(MysqlApplication.class, args);
	}

	private final JdbcTemplate jdbcTemplate;

	private final JdbcClient jdbcClient;


	@GetMapping("/")
	public String insertByGetMethodForDemo(@RequestParam("firstName") String fName, @RequestParam("lastName") String lName){

		jdbcTemplate.update("insert into student(first_name, last_name) values(?,?)",fName,lName);

		return "Insert Success";
	}


	@GetMapping("/all")
	public List<Student> findAll(){

		return jdbcClient.sql("select * from student").query(Student.class).list();
	}

	@GetMapping("/byId")
	public Student findById(@RequestParam("id") Integer studentId){
		return jdbcClient.sql("select id,first_Name,last_Name from student s where s.id=:studentId")
				.param("studentId",studentId)
				.query(Student.class)
				.optional().orElse(null);
	}


	@GetMapping("/byId2")
	public StudentV2 findById2(@RequestParam("id") Integer studentId){
		return jdbcClient.sql("select first_Name,last_Name from student s where s.id=:studentId")
				.param("studentId",studentId)
				.query(StudentV2.class)
				.optional().orElse(null);
	}

	@GetMapping("/all2")
	public List<StudentV2> findAll2(){

		return jdbcClient.sql("select * from student").query(StudentV2.class).list();
	}




}
