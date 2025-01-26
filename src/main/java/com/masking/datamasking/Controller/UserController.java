package com.masking.datamasking.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.masking.datamasking.Custom.Loggable;
import com.masking.datamasking.Dto.AuthRequest;
import com.masking.datamasking.Dto.TransferRequestDto;
import com.masking.datamasking.Entity.User;
import com.masking.datamasking.Service.JwtService;
import com.masking.datamasking.Service.TransferService;
import com.masking.datamasking.Service.UserService;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

    @Autowired
    private TransferService transferService;
  

    @Loggable   
    @GetMapping("/hello")
    public String getMethodName() {
        return "Its working!!";
    }
    
    
    @PostMapping("/add")
    public ResponseEntity<String> addNewUser(@RequestBody User user) {
        
        userService.saveUser(user);
        
        return new ResponseEntity<>("user added", HttpStatus.CREATED );
    }

    @GetMapping("/name")
    public ResponseEntity<User> getUserByUserName(@RequestParam String userName) {
        User  user = userService.getUserByUsername(userName).get();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/getAllUsers")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<List<User>> getAllUsersADMIN() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public User getUserById(@PathVariable int id) {
		return userService.getUserById(id).get();
	}

	@PostMapping("/getToken")
	public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

		if (authentication.isAuthenticated()) {
			return jwtService.generateToken(authRequest.getUsername());
		}
		
		throw new UsernameNotFoundException("invalid user details.");
	}

    @GetMapping("/transfer")
    public String transfer(@RequestBody TransferRequestDto transferRequestDto) {
        return transferService.transferMoney(transferRequestDto);
    }
    
    
}
