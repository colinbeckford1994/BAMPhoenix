package com.bam.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bam.beans.Batch;
import com.bam.beans.Users;
import com.bam.service.BatchService;
import com.bam.service.UsersService;


@RestController
@RequestMapping(value="/api/v1/Users/")
public class UserController {
	
	private final String USER_ID = "userId";
	private final String BATCH_ID = "batchId";
	
	@Autowired
	UsersService userService;
	
	@Autowired
	BatchService batchService;
	
	@RequestMapping(value="All", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Users> getAllUsers(){
		return userService.findAllUsers();
	}
	
	@RequestMapping(value="AllTrainers", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Users> getAllTrainers(){
		return userService.findByRole(2);
	}
	
	@RequestMapping(value="AllAssociates", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Users> getAllAssociates(){
		return userService.findByRole(1);
	}
	
	@RequestMapping(value="InBatch", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Users> getUsersInBatch(HttpServletRequest request) {
		//Get the batch id from the request
		int batchId = Integer.parseInt( request.getParameter(BATCH_ID) );
		
		//Retrieve and return users in a batch from the database
		return userService.findUsersInBatch(batchId);
	}
	
	@RequestMapping(value="Drop", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public List<Users> dropUserFromBatch(HttpServletRequest request) {
		//Get the user id from the request
		int userId = Integer.parseInt( request.getParameter(USER_ID) );
		Users user = userService.findUserById( userId );
		int batchId = user.getBatch().getId();
		
		//Drop user from the batch
		user.setBatch(null);
		user.setRole(0);
		userService.addOrUpdateUser(user);
		
		//Return users from batch without the user
		return userService.findUsersInBatch(batchId);
	}
	
	@RequestMapping(value="Update", method=RequestMethod.POST, produces="application/json")
	public void updateUser(@RequestBody Users currentUser) {
		Users user = userService.findUserByEmail(currentUser.getEmail());
		currentUser.setPwd(user.getPwd());
		userService.addOrUpdateUser(currentUser);
	}
	
	@RequestMapping(value="Register", method=RequestMethod.POST, produces="application/json")
	public void addUser(@RequestBody Users currentUser) throws Exception {
		if(userService.findUserByEmail(currentUser.getEmail())==null){
			currentUser.setRole(1);
			userService.addOrUpdateUser(currentUser);
		} else {
			throw new IllegalArgumentException("Email exists in database");
		}	
	}

	/**
	 * @author Tom Scheffer
	 * @param jsonObject - object being passed in
	 * @throws Exception - for when previous password is wrong
	 * 
	 * 	Updates the user's password from the update view. Updates password to pwd2 when pwd equals their old pwd
	 */
	@RequestMapping(value="Reset", method=RequestMethod.POST, produces="application/java")
	public void resetPassword(@RequestBody Users userNewPass) throws Exception{
		Users currentUser = userService.findUserByEmail(userNewPass.getEmail());
		if(currentUser.getPwd().equals(userNewPass.getPwd())){
			currentUser.setPwd(userNewPass.getPwd2());
			userService.addOrUpdateUser(currentUser);
		}else{
			throw new IllegalArgumentException("Wrong password, password not changed");
		}
	}
	
	@RequestMapping(value="Remove", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public List<Users> removeUser(HttpServletRequest request) {
		//Get the user id from the request
		int userId = Integer.parseInt( request.getParameter(USER_ID) );
		Users user = userService.findUserById( userId );
		int batchId = user.getBatch().getId();
		
		//Set the user as inactive
		Batch b = null;
		user.setBatch(b);
		userService.addOrUpdateUser(user);
		
		//Return users from batch without the user
		return userService.findUsersInBatch(batchId);
	}
	
	@RequestMapping(value="Add", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public List<Users> addUserToBatch(HttpServletRequest request) {
		//Get the user id from the request
		int userId = Integer.parseInt( request.getParameter(USER_ID) );
		//Get the batch to add the user to from the request
		int batchId = Integer.parseInt( request.getParameter(BATCH_ID) );
		
		Users user = userService.findUserById( userId );
		
		user.setBatch(batchService.getBatchById(batchId));
		
		userService.addOrUpdateUser(user);
		
		return userService.findUsersNotInBatch();
	}
	
	@RequestMapping(value="NotInABatch", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Users> getUsersNotInBatch(HttpServletRequest request) {
		return userService.findUsersNotInBatch();
	}
	
}
