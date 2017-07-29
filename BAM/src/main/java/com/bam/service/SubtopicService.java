package com.bam.service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.bam.beans.Batch;
import com.bam.beans.Subtopic;
import com.bam.beans.SubtopicName;
import com.bam.beans.SubtopicStatus;
import com.bam.dao.BatchRepository;
import com.bam.dao.SubtopicNameRepository;
import com.bam.dao.SubtopicRepository;
import com.bam.dao.SubtopicStatusRepository;

@Transactional
public class SubtopicService {

	@Autowired
	SubtopicRepository subTopRep;
	
<<<<<<< HEAD
	@Autowired
	BatchRepository batchRep;
	
	@Autowired
	SubtopicNameRepository sNameRep;
	
	@Autowired
	SubtopicStatusRepository sStatRep;
	
	public void addSubtopic(int subtopic, int batch, int lessonDate){
		Subtopic s = new Subtopic();
		Batch b = new Batch();
		SubtopicName st = new SubtopicName();
		SubtopicStatus ss = new SubtopicStatus();
		Date date = null;
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			date = dateFormat.parse("23/09/2017");
		} catch (Exception e) {
			e.printStackTrace();
		}
		long time = date.getTime();
		Timestamp ts = new Timestamp(time);
		
		b = batchRep.findById(batch);
		st = sNameRep.findById(subtopic);
		ss = sStatRep.findById(1);
		
		s.setBatch(b);
		s.setSubtopicName(st);
		s.setStatus(ss);
		s.setSubtopicDate(ts);
		
		subTopRep.save(s);
	}
	
	

	public List<Subtopic> getSubtopicsByBatch(Batch batch) {

		return subTopRep.findByBatch(batch);
	}
	
}
