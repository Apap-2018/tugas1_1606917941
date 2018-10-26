package com.apap.tugas1.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.PegawaiDB;

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService {
	@Autowired
	private PegawaiDB pegawaiDb;
	
	@Autowired
	private JabatanPegawaiService jabatanPegawaiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	@Override
	public PegawaiModel getPegawaiDetailByNip(String nip) {
		// TODO Auto-generated method stub
		return pegawaiDb.findByNip(nip);
	}
	
	/**
	 * Method untuk menghitung gaji karyawan
	 */
	@Override
	public double hitungGaji(PegawaiModel pegawai, List<JabatanModel> jabatan) {
		// TODO Auto-generated method stub
		double gaji = 0;
		double presentase = pegawai.getInstansi().getProvinsi().getPresentaseTunjangan()/100;
		System.out.println(presentase);
		ArrayList<Double> listgajipokok = new ArrayList<>();
		for (JabatanModel posisi: jabatan) {
			listgajipokok.add(posisi.getGajiPokok());
		}
		double gajipokok = Collections.max(listgajipokok);
		gaji = gajipokok + presentase * gajipokok;
		return gaji;
	}

	/**
	 * Method untuk mendapatkan list nama jabatan
	 */
	@Override
	public List<JabatanModel> getJabatan(List<JabatanPegawaiModel> jabatanPegawai) {
		ArrayList<JabatanModel> posisi = new ArrayList<>();
		for(JabatanPegawaiModel position: jabatanPegawai) {
			posisi.add(jabatanService.getJabatanDetailById(position.getJabatan().getId()));
		}
		return posisi;
	}
	
	/**
	 * Method untuk mengurutan pegawai dari instansi berdasarkan tanggal lahir
	 */
	@Override
	public List<PegawaiModel> getPegawaiByInstansi(InstansiModel instansi) {
		// TODO Auto-generated method stub
		return pegawaiDb.findByInstansiOrderByTanggalLahirAsc(instansi);
	}

	/**
	 * Method untuk membuat nip
	 */
	@Override
	public String generateNip(PegawaiModel pegawai) {
		// TODO Auto-generated method stub
		String nip ="";
		//4 Digit pertama dari id instansi
		nip += pegawai.getInstansi().getId(); 
		//6 Digit dari tanggal lahir
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String lahir = sdf.format(pegawai.getTanggalLahir());
		String[] tgl = lahir.split("/");
		String tglLahir = tgl[2] + tgl[1] + tgl[0].substring(2, 4);
		nip+= tglLahir;
		//4 Digit dari tahun masuk
		nip+= pegawai.getTahunMasuk();
		List<PegawaiModel> tahunsama= this.getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(pegawai.getInstansi(), pegawai.getTanggalLahir(), pegawai.getTahunMasuk());
		int jumlah = tahunsama.size();
		//2 digit dari tahun dan tanggal lahir yang sama
		if (tahunsama.size() >= 10) {
			nip += jumlah;
		}
		else {
			nip += "0" + (jumlah+1);
		}
		
		return nip;
	}

	/**
	 * Method untuk mencari pegawai dengan tanggal lahir dan tahun masuk yang sama dari sebuah instansi
	 */
	@Override
	public List<PegawaiModel> getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(InstansiModel instansi,
			Date tanggalLahir, String tahunMasuk) {
		// TODO Auto-generated method
		return pegawaiDb.findByInstansiAndTanggalLahirAndTahunMasuk(instansi, tanggalLahir, tahunMasuk);
	}
	
	/**
	 * Method untuk menyimpan pegawai baru
	 */
	@Override
	public void addPegawaiBaru(PegawaiModel pegawai) {
		// TODO Auto-generated method stub
		pegawaiDb.save(pegawai);
	}
	
	/**
	 * Method untuk fitur pencarian
	 */
	@Override
	public List<PegawaiModel> findPegawaiByInstansiAndJabatan(InstansiModel instansi, JabatanModel jabatan) {
		// TODO Auto-generated method stub
		List<PegawaiModel> pegawaiInstansi = instansi.getPegawaiInstansi();
		List<PegawaiModel> pencarian = new ArrayList<>();
		long idJabatan = jabatan.getId();
		
		for(PegawaiModel peg : pegawaiInstansi) {
			for(JabatanPegawaiModel jab: peg.getJabatanPegawai()) {
				if(jab.getJabatan().getId() == idJabatan) {
					pencarian.add(peg);
				}
			}
		}
		return pencarian;
	}

	@Override
	public List<PegawaiModel> findPegawaiByProvinsiAndJabatan(List<PegawaiModel> pegawaiProvinsi,
			JabatanModel jabatan) {
		
		List<PegawaiModel> pencarian = new ArrayList<>();
		
		for(PegawaiModel peg: pegawaiProvinsi) {
			for(JabatanPegawaiModel jab: peg.getJabatanPegawai()) {
				if(jab.getJabatan().getId() == jabatan.getId()) {
					pencarian.add(peg);
				}
			}
		}
		return pencarian;
	}
	
	/**
	 * Method untuk mengubah pegawai
	 */

	@Override
	public void updatePegawai(PegawaiModel pegawai) {
		// TODO Auto-generated method stub
		PegawaiModel archivePegawai = this.getPegawaiDetailByNip(pegawai.getNip());
		
		//ubah nip pegawai
		String nipBaru = this.generateNip(pegawai);
		System.out.println(nipBaru);
		archivePegawai.setNip(nipBaru);
		
		//ubah nama, tempatlahir, tanggallahir, tahunmasuk, instansi
		archivePegawai.setNama(pegawai.getNama());
		archivePegawai.setTempatLahir(pegawai.getTempatLahir());
		archivePegawai.setTanggalLahir(pegawai.getTanggalLahir());
		archivePegawai.setTahunMasuk(pegawai.getTahunMasuk());
		archivePegawai.setInstansi(pegawai.getInstansi());
		
		//ubah jabatan dari pegawai
		int jumlahJabatanBaru = pegawai.getJabatanPegawai().size();
		int jumlahJabatanLama = archivePegawai.getJabatanPegawai().size();
		
		if(jumlahJabatanBaru < jumlahJabatanLama) {
			for(int i = 0; i < jumlahJabatanBaru;i++) {
				archivePegawai.getJabatanPegawai().get(i).setJabatan(pegawai.getJabatanPegawai().get(i).getJabatan());
			}
			int diff = jumlahJabatanLama - jumlahJabatanBaru;
			for(int j = 0; j < diff;j++) {
				//jabatanPegawaiService.removeJabatanPegawai(archivePegawai.getJabatanPegawai().get(archivePegawai.getJabatanPegawai().size()-1));
				archivePegawai.getJabatanPegawai().remove(archivePegawai.getJabatanPegawai().size()-1);
			}
			
		} else {
			for(int x = 0; x < jumlahJabatanLama;x++) {
				archivePegawai.getJabatanPegawai().get(x).setJabatan(pegawai.getJabatanPegawai().get(x).getJabatan());
			}
			if(jumlahJabatanBaru > jumlahJabatanLama) {
				for(int y= jumlahJabatanLama;y < jumlahJabatanBaru;y++) {
					JabatanPegawaiModel baru = pegawai.getJabatanPegawai().get(y);
					baru.setPegawai(archivePegawai);
					jabatanPegawaiService.addJabatanPegawai(baru);
				}
			}
		}
		
	}
}
