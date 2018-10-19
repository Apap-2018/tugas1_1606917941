package com.apap.tugas1.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanPegawaiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;

@Controller
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private InstansiService instansiService;
	
	@Autowired
	private ProvinsiService provinsiService;
	
	@Autowired
	private JabatanPegawaiService jabatanPegawaiService;
	
	@RequestMapping("/")
	private String home(Model model) {
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		model.addAttribute("listJabatan", listJabatan);
		List<InstansiModel> listInstansi = instansiService.getAllInstansi();
		model.addAttribute("listInstansi", listInstansi);
		return "home";
	}
	
	@RequestMapping(value="/pegawai/view", method = RequestMethod.GET)
	private String view(String nip,Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);
		model.addAttribute("pegawai", pegawai);
		InstansiModel instansi = pegawai.getInstansi();
		model.addAttribute("instansi", instansi.getNama()+"-"+instansi.getProvinsi().getNama());
		List<JabatanPegawaiModel> jabatan = pegawai.getJabatanPegawai();
		List<JabatanModel> posisi = pegawaiService.getJabatan(jabatan);
		model.addAttribute("listJabatan", posisi);
		int gaji = (int) pegawaiService.hitungGaji(pegawai, posisi);
		model.addAttribute("gaji", gaji);
		return "view-pegawai";
	}
	
	@RequestMapping(path="/pegawai/cari", method = RequestMethod.GET)
	private String cariPegawai(Optional<String> idProvinsi, Optional<String> idInstansi, Optional<String> idJabatan, Model model) {
		List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		model.addAttribute("listProvinsi",listProvinsi);
		model.addAttribute("listJabatan", listJabatan);
		
		if(idProvinsi.isPresent()) {
			ProvinsiModel provinsi = provinsiService.getDetailProvinsiById(Long.parseLong(idProvinsi.get()));
			if(idInstansi.isPresent()) {
				InstansiModel instansi = instansiService.getDetailInstansiById(Long.parseLong(idInstansi.get()));
				List<PegawaiModel> pegawaiInstansi = instansi.getPegawaiInstansi();
				if(idJabatan.isPresent()) {
					JabatanModel jabatan = jabatanService.getJabatanDetailById(Long.parseLong(idJabatan.get()));
				}
				else {
					
				}
			}
			
		}
		return "search-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/instansi", method = RequestMethod.GET)
	public @ResponseBody
	List<InstansiModel> findAllInstansi(
	        @RequestParam(value = "idProvinsi", required = true) Long idProvinsi) {
		ProvinsiModel provinsi = provinsiService.getDetailProvinsiById(idProvinsi);
		List<InstansiModel> instansi = instansiService.getAllInstansiByProvinsi(provinsi);
	    return instansi;
	}
	
	@RequestMapping(value = "/pegawai/carifilter", method = RequestMethod.GET)
	public @ResponseBody
	List<PegawaiModel> findPegawaiByFilter(
			 @RequestParam(value = "idProvinsi", required = true) Long idProvinsi, @RequestParam(value = "idInstansi", required = true) Long idInstansi) {
		ProvinsiModel provinsi = provinsiService.getDetailProvinsiById(idProvinsi);
		System.out.println(provinsi.getNama());
		InstansiModel instansi = instansiService.getDetailInstansiById(idInstansi);
		System.out.println(instansi.getNama());
	    return instansi.getPegawaiInstansi();
	}
	
	@RequestMapping(path="/pegawai/termuda-tertua", method = RequestMethod.GET)
	private String tuamudaPegawai(String idInstansi, Model model) {
		InstansiModel instansi = instansiService.getDetailInstansiById(Long.parseLong(idInstansi));
		List<PegawaiModel> listPegawaiInstansi = pegawaiService.getPegawaiByInstansi(instansi);
		
		PegawaiModel termuda = listPegawaiInstansi.get(listPegawaiInstansi.size()-1);
		model.addAttribute("termuda", termuda);
		List<JabatanPegawaiModel> jabatanTermuda = termuda.getJabatanPegawai();
		List<JabatanModel> posisi1 = pegawaiService.getJabatan(jabatanTermuda);
		model.addAttribute("jabatanMuda", posisi1);
		
		PegawaiModel tertua = listPegawaiInstansi.get(0);
		List<JabatanPegawaiModel> jabatanTertua = tertua.getJabatanPegawai();
		List<JabatanModel> posisi2 = pegawaiService.getJabatan(jabatanTertua);
		model.addAttribute("tertua", tertua);
		model.addAttribute("jabatanTua", posisi2);
		return "tertua-termuda";
	}
	
	@RequestMapping(path="/pegawai/tambah", method = RequestMethod.GET)
	private String addPegawai(Model model) {
		PegawaiModel pegawaiBaru = new PegawaiModel();
		pegawaiBaru.setJabatanPegawai(new ArrayList<JabatanPegawaiModel>());
		System.out.println(pegawaiBaru.getJabatanPegawai());
		JabatanPegawaiModel jabatanPegawai = new JabatanPegawaiModel();
		pegawaiBaru.getJabatanPegawai().add(jabatanPegawai);
		model.addAttribute("pegawaiBaru", pegawaiBaru);
		
		
		List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
		model.addAttribute("listProvinsi", listProvinsi);
		
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		model.addAttribute("listJabatan", listJabatan);
		
		return "add-pegawai";
	}
	
	@RequestMapping(value="/pegawai/tambah", method=RequestMethod.POST, params={"addRow"})
	public String addRowJabatan(@ModelAttribute PegawaiModel pegawai, Model model) {
		System.out.println(pegawai.getJabatanPegawai());
		pegawai.getJabatanPegawai().add(new JabatanPegawaiModel());
		model.addAttribute("pegawaiBaru", pegawai);
		
		List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
		model.addAttribute("listProvinsi", listProvinsi);
		
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		model.addAttribute("listJabatan", listJabatan);
	    return "add-pegawai";
	}
	
	@RequestMapping(value="/pegawai/tambah", method=RequestMethod.POST,params={"removeRow"})
	public String removeRow(@ModelAttribute PegawaiModel pegawai, Model model, 
	        final HttpServletRequest req) {
	    Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
	    pegawai.getJabatanPegawai().remove(rowId.intValue());
	    model.addAttribute("pegawaiBaru", pegawai);
	    
	    List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
		model.addAttribute("listProvinsi", listProvinsi);
		
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		model.addAttribute("listJabatan", listJabatan);
	    return "add-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST, params= {"save"})
	private String addPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		//set nip
		String nip = pegawaiService.generateNip(pegawai);
		System.out.println(nip);
		pegawai.setNip(nip);
		
		//List jabatan
		List<JabatanPegawaiModel> listJabPegawai = pegawai.getJabatanPegawai();
		
		pegawai.setJabatanPegawai(new ArrayList<JabatanPegawaiModel>());
		pegawaiService.addPegawaiBaru(pegawai);
		
		//menambahkan jabatan pegawai ke jabatanpegawaimodel
		for(JabatanPegawaiModel jabatan : listJabPegawai) {
			jabatan.setPegawai(pegawai);
			jabatanPegawaiService.addJabatanPegawai(jabatan);
			
		}
		model.addAttribute("penambahan", "Pegawai");
		return "add";
	}

}
