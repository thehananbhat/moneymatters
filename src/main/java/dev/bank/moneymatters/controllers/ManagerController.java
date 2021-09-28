package dev.bank.moneymatters.controllers;

import java.util.HashMap;
import java.util.regex.Pattern;

import dev.bank.moneymatters.entities.Account;
import dev.bank.moneymatters.entities.Customer;
import dev.bank.moneymatters.entities.User;
import dev.bank.moneymatters.services.AccCreationEmailService;
import dev.bank.moneymatters.services.ManagerService;

import dev.bank.moneymatters.services.UserLoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;



@RestController
//@RequestMapping("api/v1/manager")
public class ManagerController {
    @Autowired
    ManagerService managerService;

    @Autowired
    UserLoginServiceImpl userLoginService;

    @Autowired
    AccCreationEmailService accCreationEmailService;

//    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("manager/create-customer")
    public ResponseEntity<Object> createCustomer(@RequestParam(value="aadhar_number") Long aadharNumber,
                                                 @RequestParam(value="dob") String dob,
                                                 @RequestParam(value="email") String email,
                                                 @RequestParam(value="name") String name,
                                                 @RequestParam(value="pan_card") String panCard,
                                                 @RequestParam(value="postal_address") String postalAddress
                                                 ){

        Customer newCust = new Customer();
        HashMap<String,String> resultSet = new HashMap<String,String>();
        newCust.setName(name);
        if(!Pattern.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}", dob)) {
            resultSet.put("message", "Invalid DOB! Format should me YYYY-MM-DD");
            return new ResponseEntity<>(resultSet, HttpStatus.BAD_REQUEST);
        }
        newCust.setDobFromString(dob);

        if(!Pattern.matches("[A-Z]{5}[0-9]{4}[A-Z]{1}", panCard)) {
            resultSet.put("message", "Invalid PAN number");
            return new ResponseEntity<>(resultSet, HttpStatus.BAD_REQUEST);
        }
        newCust.setPanCard(panCard);

        if(!Pattern.matches("[0-9]{12}",Long.toString(aadharNumber))) {
            resultSet.put("message", "Invalid Aadhar number");
            return new ResponseEntity<>(resultSet, HttpStatus.BAD_REQUEST);
        }
        newCust.setAadharNumber(aadharNumber);
        newCust.setPostalAddress(postalAddress);
        if(!Pattern.matches("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$", email)) {
            resultSet.put("message", "Invalid email");
            return new ResponseEntity<>(resultSet, HttpStatus.BAD_REQUEST);
        }
        newCust.setEmail(email);
        User newUser = userLoginService.createNewUserCredentials();
        newCust.setUser(newUser);
//        try {
//            newCust.setAadharImg(aadharImg.getBytes());
//            newCust.setPanImg(panImg.getBytes());
//        } catch (IOException e) {
//            e.printStackTrace();
//            resultSet.put("message", e.getMessage());
//            return new ResponseEntity<>(resultSet, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
        try {
            newCust = managerService.createNewCustomer(newCust);
        }catch(Exception e) {
            e.printStackTrace();
            resultSet.put("message", e.getMessage());
            return new ResponseEntity<>(resultSet, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        resultSet.put("customer_id", Long.toString(newCust.getCustomerId()));
        String emailUserName = newUser.getCustomerId();
        String emailPassword = newUser.getPassword();
        String customerEmail = newCust.getEmail();
        accCreationEmailService.sendEmail(emailUserName,emailPassword,customerEmail);
        return new ResponseEntity<>(resultSet, HttpStatus.OK);
    }

    @PutMapping("createaccount")
    public ResponseEntity<Object> createAccount(@RequestParam(value="customer_id") int customerId){
        HashMap<String,String> resultSet = new HashMap<String,String>();
        try {
            Account account = managerService.createNewAccount(customerId);
            resultSet.put("customer_id", Integer.toString(customerId));
            resultSet.put("account_number",Long.toString(account.getAccountNumber()));
        }catch(Exception e) {
            e.printStackTrace();
            resultSet.put("message", e.getMessage());
            return new ResponseEntity<>(resultSet, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(resultSet, HttpStatus.OK);
    }

    @GetMapping("verifypancard")
    public ResponseEntity<Object> verifyIfPanCardExists(@RequestParam(required = true) String panCardNumber) {
        return managerService.verifyPanCard(panCardNumber);
    }

}