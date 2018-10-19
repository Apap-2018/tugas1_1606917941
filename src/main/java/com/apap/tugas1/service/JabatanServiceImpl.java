package com.apap.tugas1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.repository.JabatanDB;

@Service
@Transactional
public class JabatanServiceImpl implements JabatanService {
	@Autowired
	private JabatanDB jabatanDb;
	
	@Override
	public JabatanModel getJabatanDetailById(long id) {
		// TODO Auto-generated method stub
		return jabatanDb.findJabatanById(id);
	}

	@Override
	public List<JabatanModel> getAllJabatan() {
		// TODO Auto-generated method stub
		return jabatanDb.findAll();
	}

	@Override
	public void addJabatan(JabatanModel jabatan) {
		// TODO Auto-generated method stub
		jabatanDb.save(jabatan);
	}

	@Override
	public void updateJabatan(JabatanModel jabatanBaru) {
		// TODO Auto-generated method stub
		JabatanModel archiveJabatan = this.getJabatanDetailById(jabatanBaru.getId());
		archiveJabatan.setNama(jabatanBaru.getNama());
		archiveJabatan.setDeskripsi(jabatanBaru.getDeskripsi());
		archiveJabatan.setGajiPokok(jabatanBaru.getGajiPokok());
	}

	@Override
	public void deleteJabatan(JabatanModel jabatanHapus) {
		// TODO Auto-generated method stubs
		jabatanDb.delete(jabatanHapus);
	}
	
}
