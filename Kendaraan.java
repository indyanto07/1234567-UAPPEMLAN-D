package UAP;

public abstract class Kendaraan {
    private String kodeKendaraan;
    private String namaKendaraan;
    private double hargaSewaPerHari;
    private boolean statusTersedia;
    public Kendaraan(String kodeKendaraan, String namaKendaraan, double hargaSewaPerHari) {
        this.kodeKendaraan = kodeKendaraan;
        this.namaKendaraan = namaKendaraan;
        this.hargaSewaPerHari = hargaSewaPerHari;
        this.statusTersedia = true;
    }
    public String getKodeKendaraan() { 
        return kodeKendaraan; 
    }
    public String getNamaKendaraan() { 
        return namaKendaraan; 
    }
    public double getHargaSewaPerHari() {
         return hargaSewaPerHari; 
        }
    public boolean isStatusTersedia() {
         return statusTersedia; 
        }
    public void setStatusTersedia(boolean statusTersedia) {
         this.statusTersedia = statusTersedia; 
        }
    public abstract double hitungTotalBiaya(int lamaSewa);
    public void tampilkanInfo() {
        String status = statusTersedia ? "Tersedia" : "Tidak Tersedia"; 
        System.out.printf("| %-10s | %-15s | Rp %,-14.2f | %-15s |\n", 
                kodeKendaraan, namaKendaraan, hargaSewaPerHari, status);
    }
}