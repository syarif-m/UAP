import java.util.ArrayList;

public class GoDriveRentalSystem {
    private ArrayList<Kendaraan> daftarKendaraan;

    public GoDriveRentalSystem() {
        daftarKendaraan = new ArrayList<>();
    }

    public void tambahKendaraan(Kendaraan kendaraan) {
        for (Kendaraan k : daftarKendaraan) {
            if (k.getKodeKendaraan().equalsIgnoreCase(kendaraan.getKodeKendaraan())) {
                System.out.println("Kendaraan dengan kode tersebut sudah ada.");
                return;
            }
        }
        daftarKendaraan.add(kendaraan);
        System.out.println("[INFO] Kendaraan berhasil ditambahkan: " + kendaraan.getNamaKendaraan() + " (" + kendaraan.getKodeKendaraan() + ")");
    }

    public void tampilkanDaftarKendaraan() {
        if (daftarKendaraan.isEmpty()) {
            System.out.println("Tidak ada kendaraan dalam daftar.");
            return;
        }
        System.out.println("=== DAFTAR ARMADA GODRIVE ===");
        int nomor = 1;
        for (Kendaraan kendaraan : daftarKendaraan) {
            System.out.print(nomor + ". ");
            kendaraan.tampilInfo(); 
            nomor++;
        }
    }

    public void sewaKendaraan(String kodeKendaraan, int lamaSewa, boolean isVIP) 
            throws KendaraanTidakTersediaException {
        for (Kendaraan kendaraan : daftarKendaraan) {
            if (kendaraan.getKodeKendaraan().equalsIgnoreCase(kodeKendaraan)) {
                if (kendaraan.isTersedia()) {
                    
                    double hargaHarian = kendaraan.getHargaSewaPerHari();
                    double totalBiayaDasarHarian = hargaHarian * lamaSewa;
                    
                    double biayaTambahan = 0;
                    String teksTambahan = "";
                    
                    if (kendaraan instanceof Mobil) {
                        int kursi = ((Mobil) kendaraan).getJumlahKursi();
                        if (kursi > 5) {
                            biayaTambahan = 50000; 
                            teksTambahan = "Tambahan Kursi (" + kursi + ")";
                        }
                    } else if (kendaraan instanceof Motor) {
                        String transmisi = ((Motor) kendaraan).getJenisTransmisi();
                        if (transmisi != null && transmisi.equalsIgnoreCase("Matik")) {
                            biayaTambahan = 10000 * lamaSewa; 
                            teksTambahan = "Tambahan Asuransi (Matik)";
                        }
                    }

                    double totalSebelumDiskon = totalBiayaDasarHarian + biayaTambahan;
                    
                    double totalDiskon = 0;
                    double nominalDiskonVIP = 0;
                    double nominalDiskonDurasi = 0;

                    if (isVIP) {
                        nominalDiskonVIP = totalSebelumDiskon * 0.15; 
                        totalDiskon += nominalDiskonVIP;
                    }
                    if (lamaSewa > 7) {
                        nominalDiskonDurasi = totalSebelumDiskon * 0.10; 
                        totalDiskon += nominalDiskonDurasi;
                    }

                    double totalBiayaAkhir = totalSebelumDiskon - totalDiskon;

                    System.out.println("\n=== TRANSAKSI SEWA GODRIVE ===");
                    System.out.println("Kendaraan Berhasil Disewa!");
                    System.out.println("Unit               : " + kendaraan.getNamaKendaraan() + " (" + kendaraan.getKodeKendaraan() + ")");
                    System.out.println("Lama Sewa          : " + lamaSewa + " hari");
                    System.out.printf("Biaya Dasar Harian : Rp %,.0f%n", totalBiayaDasarHarian);
                    
                    if (biayaTambahan > 0) {
                        System.out.printf("%-18s : Rp %,.0f%n", teksTambahan, biayaTambahan);
                    }
                    if (nominalDiskonVIP > 0) {
                        System.out.printf("Diskon Member VIP (15%%) : -Rp %,.0f%n", nominalDiskonVIP);
                    }
                    if (nominalDiskonDurasi > 0) {
                        System.out.printf("Diskon Durasi >7 Hari (10%%) : -Rp %,.0f%n", nominalDiskonDurasi);
                    }
                    
                    System.out.println("----------------------------------------");
                    System.out.printf("TOTAL BIAYA AKHIR  : Rp %,.0f%n", totalBiayaAkhir);

                    kendaraan.setTersedia(false);
                } else {
                    throw new KendaraanTidakTersediaException("Kendaraan dengan kode " + kodeKendaraan + " gagal disewa. Alasan: Kendaraan sedang disewa atau tidak ditemukan");
                }
                return;
            }
        }
        throw new KendaraanTidakTersediaException("Kendaraan dengan kode " + kodeKendaraan + " gagal disewa. Alasan: Kendaraan sedang disewa atau tidak ditemukan");
    }

    public void kembalikanKendaraan(String kodeKendaraan) throws KendaraanTidakTersediaException {
        for (Kendaraan kendaraan : daftarKendaraan) {
            if (kendaraan.getKodeKendaraan().equalsIgnoreCase(kodeKendaraan)) {
                if (!kendaraan.isTersedia()) {
                    kendaraan.setTersedia(true);
                    System.out.println("[INFO] Kendaraan " + kendaraan.getNamaKendaraan() + " (" + kendaraan.getKodeKendaraan() + ") berhasil dikembalikan. Status: Tersedia.");
                } else {
                    System.out.println("Kendaraan ini sedang tidak dalam status disewa.");
                }
                return;
            }
        }
        throw new KendaraanTidakTersediaException("Kendaraan dengan kode " + kodeKendaraan + " tidak ditemukan.");
    }
}