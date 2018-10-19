package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;

public interface InstansiService {
	InstansiModel getDetailInstansiById(long id);
	InstansiModel getDetailInstansiByProvinsi(ProvinsiModel provinsi);
	List<InstansiModel> getAllInstansi();
	List<InstansiModel> getAllInstansiByProvinsi(ProvinsiModel provinsi);
	List<PegawaiModel> getAllPegawaiInstansiById();
}
