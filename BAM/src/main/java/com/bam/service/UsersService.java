package com.bam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.bam.beans.Batch;
import com.bam.beans.Users;
import com.bam.dao.BatchRepository;
import com.bam.dao.UsersRepository;

@Transactional
public class UsersService {

	@Autowired
	UsersRepository dao;
	
	@Autowired
	BatchRepository bdao;
	
	public void addOrUpdateUser(Users user){
		dao.save(user);
	}
	
	public List<Users> findAllUsers(){
		return dao.findAll();
		
	}
	
	public Users findUserById(int userId){
		return dao.findByUserId(userId);
	}
	
	public Users findUserByEmail(String email){
		return dao.findByEmail(email);
	}
	
	public List<Users> findUsersInBatch(int batchId){
		//Get batch object by the id
		Batch batch = bdao.findById(batchId);
		//Return users in the batch
		return dao.findByBatch(batch);
	}
	
	public List<Users> findUsersNotInBatch(){
		//Return users in the batch with a null
		return dao.findByBatch(null);
	}
	
	
	
	
	
}
