package com.deloitte.fiddler.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.deloitte.fiddler.service.FileService;


@RestController
@RequestMapping("/api/file")
public class FileController {

	@Autowired
	FileService fs;

	@PostMapping
	public ResponseEntity<String> createFile(@RequestParam("file") MultipartFile file) {
		return new ResponseEntity<String>(this.fs.saveFile(file), HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Resource> getFilebyID(@PathVariable String id) {
		Resource file = this.fs.getFile(id);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);

	}
	
	@GetMapping("/{id}/name")
	public ResponseEntity<String> getFileNameById(@PathVariable String id) {
		return new ResponseEntity<String>(this.fs.getFileName(id), HttpStatus.OK);
	}


	@PostMapping("/{id}/delete")
	public ResponseEntity<Boolean> deleteFile(@PathVariable String id) {
		return new ResponseEntity<Boolean>(Boolean.valueOf(this.fs.deleteFile(id)), HttpStatus.NO_CONTENT);
		
	}
}
