package com.deloitte.fiddler.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	
	public String saveFile(MultipartFile file);
	
	public String deleteFile(String id);
	
	public Resource getFile(String id);

}
