$(document).ready( function () {
	console.log($(location).attr("href"))
	var url = new URL($(location).attr("href"));
	var idProvinsi = url.searchParams.get("idProvinsi")
	console.log(idProvinsi)
	var idInstansi = url.searchParams.get("idInstansi")
	console.log(idInstansi)
	var idJabatan = url.searchParams.get("idJabatan")
	if(idProvinsi !== null & idInstansi !== null){
		var table = $('#pegawaiTable').DataTable({
			"sAjaxSource": "/pegawai/carifilter?idProvinsi="+ idProvinsi + "&idInstansi="+ idInstansi,
			"sAjaxDataProp": "",
			"order": [[ 0, "asc" ]],
			"aoColumns": [
			    { "mData": "nip"},
		      { "mData": "nama" },
				  { "mData": "tempatLahir" },
				  { "mData": "tanggalLahir" },
				  { "mData": "tahunMasuk" },
				  { "mData": "instansi.nama"}
				  
				  
			]
	 })
	}else if(idProvinsi !== null & idInstansi == null){
		var table = $('#pegawaiTable').DataTable({
			"sAjaxSource": "/pegawai/carifilter?idProvinsi="+ idProvinsi,
			"sAjaxDataProp": "",
			"order": [[ 0, "asc" ]],
			"aoColumns": [
			    { "mData": "nip"},
		      { "mData": "nama" },
				  { "mData": "tempatLahir" },
				  { "mData": "tanggalLahir" },
				  { "mData": "tahunMasuk" },
				  { "mData": "instansi.nama"}
				  
				  
			]
	 })
	}else if(idProvinsi == null & idInstansi == null & idJabatan !== null){
		var table = $('#pegawaiTable').DataTable({
			"sAjaxSource": "/pegawai/carifilter?idJabatan="+ idJabatan,
			"sAjaxDataProp": "",
			"order": [[ 0, "asc" ]],
			"aoColumns": [
			    { "mData": "nip"},
		      { "mData": "nama" },
				  { "mData": "tempatLahir" },
				  { "mData": "tanggalLahir" },
				  { "mData": "tahunMasuk" },
				  { "mData": "instansi.nama"}
				  
				  
			]
	 })
	}
	else{
		var table = $('#pegawaiTable').DataTable({})
	}
	
});