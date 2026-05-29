package UAP;

public class Motor extends Kendaraan {
    private String jenisTransmisi;
    public Motor(String kodeKendaraan, String namaKendaraan, double hargaSewaPerHari, String jenisTransmisi) {
        super(kodeKendaraan, namaKendaraan, hargaSewaPerHari);
        this.jenisTransmisi = jenisTransmisi;
    }
    public String getJenisTransmisi() { return jenisTransmisi; }
    @Override
    public double hitungTotalBiaya(int lamaSewa) {
        double biayaDasar = lamaSewa * getHargaSewaPerHari();
        if (jenisTransmisi.equalsIgnoreCase("Matik")) {
            biayaDasar += (10000 * lamaSewa);
        }
        return biayaDasar;
    }
    @Override
    public void tampilkanInfo() {
        String status = isStatusTersedia() ? "Tersedia" : "Tidak Tersedia";

        System.out.printf("| %-10s | %-15s | Rp %,-14.2f | %-15s | Transmisi: %-6s |\n", 
                getKodeKendaraan(), getNamaKendaraan(), getHargaSewaPerHari(), status, jenisTransmisi);
    }
}