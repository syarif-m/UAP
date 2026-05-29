import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        GoDriveRentalSystem gdrs = new GoDriveRentalSystem();
        
        gdrs.tambahKendaraan(new Mobil("MBL01", "Toyota Avanza", 350000, 7));
        gdrs.tambahKendaraan(new Motor("MTR01", "Honda Vario", 88000, "Matik"));
        
        boolean exit = false;
        while (!exit) {
            System.out.println("=== MENU GO DRIVE RENTAL SYSTEM ===");
            System.out.println("1. Tambah Kendaraan");
            System.out.println("2. Tampilkan Daftar Armada");
            System.out.println("3. Sewa Kendaraan");
            System.out.println("4. Kembalikan Kendaraan");
            System.out.println("5. Keluar");
            System.out.print("Pilih menu: ");
            
            try {
                int choice = sc.nextInt();
                sc.nextLine(); 
                
                if (choice == 1) {
                    System.out.print("Masukkan jenis kendaraan (mobil/motor): ");
                    String jenis = sc.nextLine(); 
                    System.out.print("Masukkan kode kendaraan: ");
                    String kode = sc.nextLine();
                    System.out.print("Masukkan nama kendaraan: ");
                    String nama = sc.nextLine(); 
                    System.out.print("Masukkan harga sewa per hari: ");
                    double harga = sc.nextDouble();
                    sc.nextLine(); 
                    
                    if (jenis.equalsIgnoreCase("mobil")) {
                        System.out.print("Masukkan kapasitas kursi: ");
                        int kapasitas = sc.nextInt();
                        sc.nextLine(); 
                        gdrs.tambahKendaraan(new Mobil(kode, nama, harga, kapasitas));
                    } else if (jenis.equalsIgnoreCase("motor")) {
                        System.out.print("Masukkan tipe motor: ");
                        String tipe = sc.nextLine();
                        gdrs.tambahKendaraan(new Motor(kode, nama, harga, tipe));
                    } else {
                        System.out.println("Jenis kendaraan tidak valid.");
                    }
                    
                } else if (choice == 2) {
                    gdrs.tampilkanDaftarKendaraan();
                    
                } else if (choice == 3) {
                    System.out.print("Masukkan kode kendaraan yang ingin disewa: ");
                    String kodeSewa = sc.nextLine();
                    System.out.print("Masukkan durasi sewa (dalam hari): ");
                    int lamaSewa = sc.nextInt();
                    sc.nextLine(); 
                    System.out.print("Apakah Anda Member VIP? (y/n): ");
                    boolean vipStatus = sc.nextLine().equalsIgnoreCase("y");
                    
                    gdrs.sewaKendaraan(kodeSewa, lamaSewa, vipStatus); 
                    
                } else if (choice == 4) {
                    System.out.print("Masukkan kode kendaraan yang ingin dikembalikan: ");
                    String kodeKembali = sc.nextLine();
                    gdrs.kembalikanKendaraan(kodeKembali); 
                    
                } else if (choice == 5) {
                    exit = true;
                } else {
                    System.out.println("Pilihan tidak valid.");
                }
                
            } catch (InputMismatchException e) {
                System.out.println("Input tidak valid. Silakan masukkan angka.");
                sc.nextLine(); 
            }
            System.out.println(); 
        }
        sc.close();
    }
}