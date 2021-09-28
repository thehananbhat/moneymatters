package dev.bank.moneymatters.services;

import java.util.HashMap;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import dev.bank.moneymatters.entities.User;
import dev.bank.moneymatters.repositories.RoleRepo;
import dev.bank.moneymatters.repositories.UserLoginRepo;

@Service
public class UserLoginServiceImpl {

	@Autowired
	UserLoginRepo userLoginRepository;
	
//	CustomPasswordEncoder passwordEncoder = new CustomPasswordEncoder();
	
	@Autowired
	private RoleRepo roleRepository;
	
	public User createNewUserCredentials() {
		String customerId = RandomStringUtils.randomAlphabetic(7);
	    String password = RandomStringUtils.randomAlphabetic(8);
	    User newUser = new User(customerId,password,roleRepository.findById(1));
		return newUser;
	}
	
	public ResponseEntity<Object> verifyUserCredential(String userId, String password, int roleId) {

		User user = userLoginRepository.findUserByUserIdAndPassword(userId, password);
		User user1 = userLoginRepository.findUserByUserIdAndRoleRoleId(userId, roleId);
		HashMap<String,String> result = new HashMap<String, String>();
		if (user != null && user1 != null) {
				result.put("message","Valid User");
				return new ResponseEntity<>(result,HttpStatus.OK);
		} 
		else {
			result.put("message","Invalid User");
			return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
		}

	}
	public ResponseEntity<Object> updateUserCredential(String userId, String password) {
		User user1 = userLoginRepository.findUserByUserId(userId);
		HashMap<String,String> result = new HashMap<String, String>();
		if (user1 == null){
			result.put("message","User Not Found");
			return new ResponseEntity<>(result,HttpStatus.NOT_FOUND);
		}
		else if(user1.isFirstTime()==true){
			user1.setPassword(password);
			user1.setFirstTime(false);
			userLoginRepository.save(user1);
			result.put("message","Credentials Updated");
			return new ResponseEntity<>(result,HttpStatus.OK);
		}
		else {
			result.put("message","You have already updated your password");
			return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
		}
}
}
