package com.apap.tugas1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.InstansiDB;

@Service
@Transactional
public class InstansiServiceImpl implements InstansiService {
	@Autowired
	private InstansiDB instansiDb;
	
	@Override
	public InstansiModel getDetailInstansiById(long id) {
		// TODO Auto-generated method stub
		return instansiDb.findInstansiById(id);
	}

	@Override
	public List<InstansiModel> getAllInstansi() {
		// TODO Auto-generated method stub
		return instansiDb.findAll();
	}

	@Override
	public List<InstansiModel> getAllInstansiByProvinsi(ProvinsiModel provinsi) {
		// TODO Auto-generated method stub
		return instansiDb.findAllInstansiByProvinsi(provinsi);
	}

	@Override
	public InstansiModel getDetailInstansiByProvinsi(ProvinsiModel provinsi) {
		// TODO Auto-generated method stub
		return instansiDb.findInstansiByProvinsi(provinsi);
	}

	@Override
	public List<PegawaiModel> getAllPegawaiInstansiById() {
		// TODO Auto-generated method stub
		return null;
	}

}
