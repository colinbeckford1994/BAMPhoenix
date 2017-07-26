package com.bam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bam.beans.Batch;
import com.bam.service.BatchService;

@RestController
@RequestMapping(value = "/AllBatches/")
public class BatchController 
{
	@Autowired
	BatchService batchService;
	
	@ResponseBody
	public List<Batch> getBatchAll()
	{
		return batchService.getBatchAll();
	}
}
