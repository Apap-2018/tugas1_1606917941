package com.apap.tugas1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.service.JabatanService;


@Controller
public class JabatanController {
	@Autowired
	private JabatanService jabatanService;
	
	@RequestMapping(path="/jabatan/viewall", method=RequestMethod.GET)
	public String viewAllJabatan(){
		return "viewall-jabatan";
	}
	
	@RequestMapping(path="/jabatan/view", method=RequestMethod.GET)
	public String viewJabatan(String idJabatan, Model model){
		JabatanModel jabatan = jabatanService.getJabatanDetailById(Long.parseLong(idJabatan));
		model.addAttribute("jabatan", jabatan);
		return "view-jabatan";
	}
	
	@RequestMapping(path="/jabatan/tambah", method=RequestMethod.GET)
	public String addJabatan(Model model){
		model.addAttribute("jabatan", new JabatanModel());
		return "add-jabatan";
	}
	
	@RequestMapping(value="/jabatan/tambah", method = RequestMethod.POST)
	private String addJabatanSubmit(@ModelAttribute JabatanModel jabatan, Model model) {
		model.addAttribute("penambahan", "Jabatan");
		jabatanService.addJabatan(jabatan);
		return "add";
	}
	
	@RequestMapping(value="/jabatan/update", method = RequestMethod.GET)
	private String updateJabatan(@RequestParam(value = "idJabatan") String idJabatan, Model model) {
		JabatanModel archiveJabatan = jabatanService.getJabatanDetailById(Long.parseLong(idJabatan));
		model.addAttribute("jabatan", archiveJabatan);
		return "update-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/update", method = RequestMethod.POST)
    private String updateJabatanSubmit(@ModelAttribute JabatanModel jabatan, Model model) {
        jabatanService.updateJabatan(jabatan);
        return "update";
    }
	
	@RequestMapping(value="/jabatan/hapus", method = RequestMethod.POST)
	private String deleteJabatan(String idJabatan, Model model) {
		JabatanModel targetJabatan = jabatanService.getJabatanDetailById(Long.parseLong(idJabatan));
		if(targetJabatan.getJabatanPegawai().isEmpty()) {
			jabatanService.deleteJabatan(targetJabatan);
			model.addAttribute("hasil", "berhasil");
		}else {
			model.addAttribute("hasil", "tidak berhasil");
		}
		return "delete";
	}
}
