package com.apap.tugas1.service;

import java.util.Date;
import java.util.List;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.model.PegawaiModel;

public interface PegawaiService {
	PegawaiModel getPegawaiDetailByNip(String nip);
	double hitungGaji(PegawaiModel pegawai, List<JabatanModel> jabatan);
	List<JabatanModel> getJabatan(List<JabatanPegawaiModel> jabatanPegawai);
	List<PegawaiModel> getPegawaiByInstansi(InstansiModel instansi);
	String generateNip(PegawaiModel pegawai);
	List<PegawaiModel> getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(InstansiModel instansi, Date tanggalLahir, String tahunMasuk);
	void addPegawaiBaru(PegawaiModel pegawai);
	void updatePegawai(PegawaiModel pegawai);
	List<PegawaiModel> findPegawaiByInstansiAndJabatan(InstansiModel instansi, JabatanModel jabatan);
	List<PegawaiModel> findPegawaiByProvinsiAndJabatan(List<PegawaiModel> pegawaiProvinsi, JabatanModel jabatan);
}
