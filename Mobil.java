package UAP;

public class Mobil extends Kendaraan {
    private int jumlahKursi;
    public Mobil(String kodeKendaraan, String namaKendaraan, double hargaSewaPerHari, int jumlahKursi) {
        super(kodeKendaraan, namaKendaraan, hargaSewaPerHari);
        this.jumlahKursi = jumlahKursi;
    }
    public int getJumlahKursi() { return jumlahKursi; }
    @Override
    public double hitungTotalBiaya(int lamaSewa) {
        double biayaDasar = lamaSewa * getHargaSewaPerHari();
        if (jumlahKursi > 5) {
            biayaDasar += 50000;
        }
        return biayaDasar;
    }
    @Override
    public void tampilkanInfo() {
        String status = isStatusTersedia() ? "Tersedia" : "Tidak Tersedia";
        System.out.printf("| %-10s | %-15s | Rp %,-14.2f | %-15s | Kursi: %-3d |\n", 
                getKodeKendaraan(), getNamaKendaraan(), getHargaSewaPerHari(), status, jumlahKursi);
    }
}