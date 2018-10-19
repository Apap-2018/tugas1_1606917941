package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.service.JabatanService;

@RestController
public class JabatanRestController {
	@Autowired
	private JabatanService jabatanService;
	
	@RequestMapping(path="/alljabatan", method=RequestMethod.GET)
	public List<JabatanModel> getAllJabatan(){
		return jabatanService.getAllJabatan();
	}
}
