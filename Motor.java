public class Motor extends Kendaraan {
    private String jenisTransmisi;

    public Motor(String kodeKendaraan, String namaKendaraan, double hargaSewaPerHari, String jenisTransmisi) {
        super(kodeKendaraan, namaKendaraan, hargaSewaPerHari);
        this.jenisTransmisi = jenisTransmisi;
    }

    public String getJenisTransmisi() { return jenisTransmisi; }
    public void setJenisTransmisi(String jenisTransmisi) { this.jenisTransmisi = jenisTransmisi; }

    @Override
    public void tampilInfo() {
        System.out.print("[MOTOR] Kode: " + getKodeKendaraan() + " | Nama: " + getNamaKendaraan());
        System.out.println(" | Transmisi: " + jenisTransmisi + " | Tarif: Rp" + (int)getHargaSewaPerHari() + "/hari | Status: " + (isTersedia() ? "Tersedia" : "Disewa"));
    }

    @Override
    public double hitungBiayaDasar(int lamaSewa) {
        double biayaDasar = getHargaSewaPerHari() * lamaSewa;
        if (jenisTransmisi != null && jenisTransmisi.equalsIgnoreCase("Matik")) {
            biayaDasar += 10000 * lamaSewa; 
        }
        return biayaDasar;
    }
}