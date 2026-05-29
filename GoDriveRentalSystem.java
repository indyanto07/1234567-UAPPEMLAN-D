package UAP;
import java.util.ArrayList;
import java.util.Scanner;

public class GoDriveRentalSystem {
    private static ArrayList<Kendaraan> daftarKendaraan = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        daftarKendaraan.add(new Mobil("MBL01", "Avanza", 300000, 7));
        daftarKendaraan.add(new Mobil("MBL02", "Brio", 250000, 4));
        daftarKendaraan.add(new Mobil("MBL03", "Innova", 350000, 7));
        daftarKendaraan.add(new Mobil("MBL04", "Xenia", 280000, 7));
        daftarKendaraan.add(new Motor("MTR01", "NMax", 100000, "Matik"));
        daftarKendaraan.add(new Motor("MTR02", "Supra", 60000, "Manual"));
        daftarKendaraan.add(new Motor("MTR03", "Vario", 80000, "Matik"));
        daftarKendaraan.add(new Motor("MTR04", "Beat", 70000, "Matik"));
        int pilihan = 0;
        do {
            System.out.println("\n========= SISTEM RENTAL KENDARAAN 'GO DRIVE' =========");
            System.out.println("1. Tambah Data Kendaraan");
            System.out.println("2. Tampilkan Seluruh Kendaraan");
            System.out.println("3. Sewa Kendaraan (Transaksi)");
            System.out.println("4. Mengembalikan Kendaraan");
            System.out.println("5. Keluar");
            System.out.print("Pilih menu (1-5): ");
            
            try {
                pilihan = Integer.parseInt(scanner.nextLine());
                switch (pilihan) {
                    case 1: tambahKendaraan(); break;
                    case 2: tampilkanSemuaKendaraan(); break;
                    case 3: sewaKendaraan(); break; 
                    case 4: kembalikanKendaraan(); break;
                    case 5: System.out.println("Terima kasih telah menggunakan sistem Go Drive!"); break;
                    default: System.out.println("Pilihan menu tidak valid.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka!");
            }
        } while (pilihan != 5);
    }

    private static void tambahKendaraan() {
        System.out.println("\n--- Tambah Data Kendaraan ---");
        System.out.print("Jenis Kendaraan (1. Mobil / 2. Motor): ");
        int jenis = Integer.parseInt(scanner.nextLine());

        System.out.print("Masukkan Kode Kendaraan : ");
        String kode = scanner.nextLine();
        System.out.print("Masukkan Nama Kendaraan : ");
        String nama = scanner.nextLine();
        System.out.print("Masukkan Harga Sewa / Hari: ");
        double harga = Double.parseDouble(scanner.nextLine());

        if (jenis == 1) {
            System.out.print("Masukkan Jumlah Kursi    : ");
            int kursi = Integer.parseInt(scanner.nextLine());
            daftarKendaraan.add(new Mobil(kode, nama, harga, kursi));
            System.out.println("Data Mobil " + nama + " berhasil ditambahkan!");
        } else if (jenis == 2) {
            System.out.print("Masukkan Jenis Transmisi (Matik/Manual): ");
            String transmisi = scanner.nextLine();
            daftarKendaraan.add(new Motor(kode, nama, harga, transmisi));
            System.out.println("Data Motor " + nama + " berhasil ditambahkan!");
        } else {
            System.out.println("Jenis kendaraan tidak valid.");
        }
    }

    private static void tampilkanSemuaKendaraan() {
        System.out.println("\n-----------------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-15s | %-16s | %-15s | %-20s |\n", "KODE", "NAMA", "HARGA/HARI", "STATUS", "SPESIFIKASI TAMBAHAN");
        System.out.println("-----------------------------------------------------------------------------------------");
        for (Kendaraan k : daftarKendaraan) {
            k.tampilkanInfo();
        }
        System.out.println("-----------------------------------------------------------------------------------------");
    }

    private static void sewaKendaraan() throws KendaraanTidakTersediaException {
        System.out.println("\n--- Transaksi Penyewaan ---");
        System.out.print("Masukkan Kode Kendaraan yang ingin disewa: ");
        String kode = scanner.nextLine();

        Kendaraan kendaraanDipilih = cariKendaraan(kode);

        if (kendaraanDipilih == null || !kendaraanDipilih.isStatusTersedia()) {
            throw new KendaraanTidakTersediaException("Kendaraan dengan kode " + kode + " gagal disewa. Alasan: Kendaraan sedang disewa atau tidak ditemukan!");
        }

        System.out.print("Masukkan Lama Sewa (hari): ");
        int lamaSewa = Integer.parseInt(scanner.nextLine());
        
        System.out.print("Apakah penyewa merupakan Member VIP? (y/n): ");
        boolean isVIP = scanner.nextLine().equalsIgnoreCase("y");

        double hargaAwal = lamaSewa * kendaraanDipilih.getHargaSewaPerHari();
        double biayaDenganTambahan = kendaraanDipilih.hitungTotalBiaya(lamaSewa);
        double biayaTambahan = biayaDenganTambahan - hargaAwal; 
        double diskonVIP = isVIP ? (0.10 * biayaDenganTambahan) : 0;
        double diskonDurasi = (lamaSewa > 7) ? (0.05 * biayaDenganTambahan) : 0;
        double totalDiskon = diskonVIP + diskonDurasi;
        double totalAkhir = biayaDenganTambahan - totalDiskon;

        kendaraanDipilih.setStatusTersedia(false);

        System.out.println("\n======== NOTA TRANSAKSI PENYEWAAN ========");
        System.out.printf("NAMA KENDARAAN   : %s\n", kendaraanDipilih.getNamaKendaraan());
        System.out.printf("LAMA SEWA        : %d hari\n", lamaSewa);
        System.out.printf("HARGA AWAL       : Rp %,.2f\n", hargaAwal); 
        
        if (biayaTambahan > 0) {
            if (kendaraanDipilih instanceof Mobil) {
                System.out.printf("BIAYA JUMLAH KURSI >5  : Rp %,.2f\n", biayaTambahan);
            } else {
                System.out.printf("BIAYA TRANSMISI MATIK  : Rp %,.2f\n", biayaTambahan);
            }
        }
    
        if (diskonVIP > 0) {
            System.out.printf("DISKON VIP (10%%) : Rp %,.2f\n", diskonVIP);
        }
        if (diskonDurasi > 0) {
            System.out.printf("DISKON >7 HARI(5%%): Rp %,.2f\n", diskonDurasi);
        }
        
        System.out.println("------------------------------------------");
        System.out.printf("TOTAL BIAYA      : Rp %,.2f\n", totalAkhir);
        System.out.println("==========================================");
    }

    private static void kembalikanKendaraan() {
        System.out.println("\n--- Pengembalian Kendaraan ---");
        System.out.print("Masukkan Kode Kendaraan: ");
        String kode = scanner.nextLine();

        Kendaraan kendaraanDipilih = cariKendaraan(kode);

        if (kendaraanDipilih == null) {
            System.out.println("Kendaraan tidak ditemukan.");
            return;
        }

        if (kendaraanDipilih.isStatusTersedia()) {
            System.out.println("Kendaraan ini sudah tersedia di garasi.");
        } else {
            kendaraanDipilih.setStatusTersedia(true);
            System.out.println("Berhasil! Kendaraan '" + kendaraanDipilih.getNamaKendaraan() + "' telah dikembalikan.");
        }
    }

    private static Kendaraan cariKendaraan(String kode) {
        for (Kendaraan k : daftarKendaraan) {
            if (k.getKodeKendaraan().equalsIgnoreCase(kode)) return k;
        }
        return null;
    }
}