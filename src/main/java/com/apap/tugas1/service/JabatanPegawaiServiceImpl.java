package com.apap.tugas1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.repository.JabatanPegawaiDB;

@Service
@Transactional
public class JabatanPegawaiServiceImpl implements JabatanPegawaiService {
	@Autowired
	private JabatanPegawaiDB jabatanPegawaiDb;
	
	@Override
	public void addJabatanPegawai(JabatanPegawaiModel jabatanPegawai) {
		// TODO Auto-generated method stub
		jabatanPegawaiDb.save(jabatanPegawai);
	}

	@Override
	public void removeJabatanPegawai(JabatanPegawaiModel jabatanPegawai) {
		// TODO Auto-generated method stub
		jabatanPegawaiDb.delete(jabatanPegawai);
	}

}
