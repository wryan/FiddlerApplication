package com.deloitte.fiddler.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.deloitte.fiddler.service.FileService;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

@Component
public class FileServiceImpl implements FileService {

	GridFS gridFS;

	@Autowired
	public FileServiceImpl(@Value("${spring.data.mongodb.host}") String mongoDBL) {
		MongoClient mc = new MongoClient(mongoDBL);

		this.gridFS = new GridFS(mc.getDB("fs"));

	}

	public String saveFile(MultipartFile file) {
		try {
			GridFSInputFile f = this.gridFS.createFile(file.getInputStream());
			f.setFilename(file.getOriginalFilename());
			f.setContentType(file.getContentType());
			f.setId(String.valueOf(this.gridFS.getFileList().getCollection().count() + 1));
			f.save();
			return (String) f.getId();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Error(e.getMessage());
		}

	}

	public boolean deleteFile(String id) {
		BasicDBObject query = new BasicDBObject("_id", id);
		GridFSDBFile file = this.gridFS.findOne(query);

		if(file != null){

			this.gridFS.remove(query);
			return true;
		} else {
			throw new NoSuchElementException("No document exists by id " + id);
		}
		
	}

	public Resource getFile(String id) {
		File f;
		try {
			BasicDBObject query = new BasicDBObject("_id", id);
			GridFSDBFile file = this.gridFS.findOne(query);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			file.writeTo(baos);
			f = new File(file.getFilename());
			FileUtils.writeByteArrayToFile(f, baos.toByteArray());
			return new UrlResource(f.toPath().toUri());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Error(e.getMessage());

		}

	}

}
