package dev.bank.moneymatters.controllers;
import dev.bank.moneymatters.services.UserLoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dev.bank.moneymatters.services.AccCreationEmailService;

@RestController
@RequestMapping("api/v1/user")
public class UserLoginController {
	@Autowired
	UserLoginServiceImpl userLoginService;
	@Autowired
	AccCreationEmailService accCreationEmailService;
	
	
	@PostMapping("user-login")
	public ResponseEntity<Object> verifyUserCredential(@RequestParam(required = true) String userId, @RequestParam(required = true) String password, @RequestParam (required = true)int roleId )
	{
		return userLoginService.verifyUserCredential(userId, password, roleId);
	}
	
	@PutMapping("update-user-credentials")
	public ResponseEntity<Object> updateUserCredential(@RequestParam(required = true) String userId,@RequestParam(required = true) String password)
	{
		return userLoginService.updateUserCredential(userId, password);
	}
	
	@PostMapping("email")
	public void sendMail(@RequestParam(required = true) String emailUserName,@RequestParam(required = true) String emailPassword, @RequestParam(required = true) String customerEmail)
	{
		accCreationEmailService.sendEmail(emailUserName,emailPassword,customerEmail);
	}
}
