package com.harpreet.blog.blogappapis;

import com.harpreet.blog.blogappapis.config.AppConstants;
import com.harpreet.blog.blogappapis.entities.Role;
import com.harpreet.blog.blogappapis.repositories.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {

		SpringApplication.run(BlogAppApisApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.passwordEncoder.encode("xyz"));

		try {
// If roles are not created then the table roles will be created
//			If it is already created then it will not create roles table again because we are using the same id 501, 502
			Role role = new Role();
			role.setId(AppConstants.ADMIN_USER);
			role.setName("ADMIN_USER");


			Role role1 = new Role();
			role1.setId(AppConstants.NORMAL_USER);
			role1.setName("NORMAL_USER");

			List<Role> roles = List.of(role,role1);

			List<Role> result =  this.roleRepo.saveAll(roles);

			result.forEach((r)->{
				System.out.println(r.getName());
			});
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}
}
