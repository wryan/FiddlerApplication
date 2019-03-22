package com.deloitte.fiddler.service.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.deloitte.fiddler.service.FileService;

@Component
public class FileServiceImpl implements FileService {

	RestTemplate restTemplate;

	Environment env;
	
	DiscoveryClient discoveryClient;

	@Autowired
	public FileServiceImpl(Environment envL,  DiscoveryClient discoveryClientL) {
		this.restTemplate = new RestTemplate();
		this.env = envL;
		this.discoveryClient = discoveryClientL;
//		System.out.println(this.discoveryClient.getServices()); 
	}

	@Override
	public String saveFile(MultipartFile file) {
	    LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

		FileOutputStream fo;
		Path tempDir;
		try {
			tempDir = Files.createTempDirectory("tempFiles");

			fo = new FileOutputStream(tempDir + "/" + file.getOriginalFilename());
			System.out.println(file.getOriginalFilename());
			fo.write(file.getBytes());
			fo.close();
			HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            map.add("file", new FileSystemResource(tempDir + "/" + file.getOriginalFilename()));

	        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);

			return this.restTemplate.postForEntity(this.env.getProperty("fiddler.services.file.host") + 
					this.env.getProperty("fiddler.services.file.endpoints.create"),
					requestEntity,
							String.class) 
					.getBody();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}


	@Override
	public String deleteFile(String id) {
		return this.restTemplate.postForEntity(this.env.getProperty("fiddler.services.file.host") + 
				this.env.getProperty("fiddler.services.file.endpoints.delete") + id + "/delete",
						null,
						String.class)
				.getBody();
	}


	@Override
	public Resource getFile(String id) {
		return this.restTemplate.getForObject(
				this.env.getProperty("fiddler.services.file.host")
						+ this.env.getProperty("fiddler.services.file.endpoints.get") + id,
						Resource.class);
	}
	
	@Override
	public String getFileName(String id) {
		return this.restTemplate.getForObject(
				this.env.getProperty("fiddler.services.file.host")
						+ this.env.getProperty("fiddler.services.file.endpoints.get") + id + "/name",
						String.class);
	}


}
