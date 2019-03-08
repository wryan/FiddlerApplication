package com.deloitte.fiddler.service;

import java.io.File;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


public interface FileService {

	
	public String saveFile(MultipartFile file);
	
	public boolean deleteFile(String id);
	
	public String getFileName(String id);
	
	public Resource getFile(String id);
	
}
