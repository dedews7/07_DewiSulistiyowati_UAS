import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class TransaksiRental {
    static int kodeTransaksiCounter = 1000;

    int kodeTransaksi;
    String namaPeminjam;
    int lamaPinjam;
    double totalBiaya;
    BarangRental br;

    public TransaksiRental(String namaPeminjam, int lamaPinjam, BarangRental br) {
        this.kodeTransaksi = kodeTransaksiCounter++;
        this.namaPeminjam = namaPeminjam;
        this.lamaPinjam = lamaPinjam;
        this.br = br;
        hitungTotalBiaya();
    }

    private void hitungTotalBiaya() {
        double biayaPerJam = br.biayaSewa;
        if (namaPeminjam.toLowerCase().contains("member")) {
            biayaPerJam -= 25000;
        }
        double totalBiaya = biayaPerJam * lamaPinjam;
        if (lamaPinjam >= 48 && lamaPinjam <= 78) {
            totalBiaya *= 0.9; // potongan 10%
        } else if (lamaPinjam > 78) {
            totalBiaya *= 0.8; // potongan 20%
        }
        this.totalBiaya = totalBiaya;
    }

    public String toString() {
        return "Kode Transaksi: " + kodeTransaksi + "\nNama Peminjam: " + namaPeminjam + "\nLama Pinjam: " + lamaPinjam
                + " jam\nTotal Biaya: Rp " + totalBiaya + "\nBarang Rental: " + br.namaKendaraan;
    }
}

public class RentalProgram {
    static ArrayList<BarangRental> daftarKendaraan = new ArrayList<>();
    static ArrayList<TransaksiRental> daftarTransaksi = new ArrayList<>();

    public static void main(String[] args) {
        // Data kendaraan awal
        daftarKendaraan.add(new BarangRental("B 1234 MZ ", "Honda Beat", "Motor", 2018, 25000));
        daftarKendaraan.add(new BarangRental("N 3456 KF ", "Yamaha Fazzio", "Motor", 2022, 25000));
        daftarKendaraan.add(new BarangRental("L 5678 YX", "Toyota Avanza", "Mobil", 2019, 40000));
        daftarKendaraan.add(new BarangRental("S 8901 FG", "Honda HR-V", "Mobil", 2016, 40000));

        Scanner scanner = new Scanner(System.in);
        int pilihan;
        do {
            System.out.println("\nMenu:");
            System.out.println("1. Daftar Kendaraan");
            System.out.println("2. Peminjaman");
            System.out.println("3. Tampilkan seluruh transaksi");
            System.out.println("4. Urutkan transaksi urut No TNKB");
            System.out.println("5. Keluar");
            System.out.print("Pilih (1-5): ");
            pilihan = scanner.nextInt();
            switch (pilihan) {
                case 1:
                    tampilkanDaftarKendaraan();
                    break;
                case 2:
                    lakukanPeminjaman();
                    break;
                case 3:
                    tampilkanSeluruhTransaksi();
                    break;
                case 4:
                    urutkanTransaksi();
                    break;
            }
        } while (pilihan != 5);
        scanner.close();
    }

    static void tampilkanDaftarKendaraan() {
        System.out.println("\nDaftar Kendaraan Rental 99    :");
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.printf("| %-10s | %-15s | %-20s | %-10s | %-20s |\n", "Kode", "No TNKB", "Nama Kendaraan", "Tahun", "Biaya Sewa/jam");
        System.out.println("----------------------------------------------------------------------------------------------------");
        for (BarangRental br : daftarKendaraan) {
            System.out.printf("| %-10s | %-15s | %-20s | %-10s | %-20s |\n", br.kodeKendaraan, br.noTNKB, br.namaKendaraan, br.tahun, br.biayaSewa);
        }
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    static void lakukanPeminjaman() {
        Scanner scanner = new Scanner(System.in);
        tampilkanDaftarKendaraan();
        System.out.print("Masukkan kode kendaraan yang akan dipinjam: ");
        int kodeKendaraan = scanner.nextInt();
        scanner.nextLine(); // Membersihkan buffer
        System.out.print("Masukkan nama peminjam: ");
        String namaPeminjam = scanner.nextLine();
        System.out.print("Apakah Anda member? (ya/tidak): ");
        String isMember = scanner.nextLine();
        System.out.print("Masukkan lama pinjam (jam): ");
        int lamaPinjam = scanner.nextInt();
        BarangRental barangDipinjam = null;
    
        // Periksa apakah kendaraan sudah dipinjam
        boolean kendaraanTersedia = true;
        for (TransaksiRental tr : daftarTransaksi) {
            if (tr.br.kodeKendaraan == kodeKendaraan) {
                kendaraanTersedia = false;
                break;
            }
        }
    
        if (kendaraanTersedia) {
            for (BarangRental br : daftarKendaraan) {
                if (br.kodeKendaraan == kodeKendaraan) {
                    barangDipinjam = br;
                    break;
                }
            }
            if (barangDipinjam != null) {
                double biayaPerJam = barangDipinjam.biayaSewa;
                if (isMember.equalsIgnoreCase("ya")) {
                    biayaPerJam -= 25000;
                }
                double totalBiaya = biayaPerJam * lamaPinjam;
                if (lamaPinjam >= 48 && lamaPinjam <= 78) {
                    totalBiaya *= 0.9; // potongan 10%
                } else if (lamaPinjam > 78) {
                    totalBiaya *= 0.8; // potongan 20%
                }
                TransaksiRental transaksi = new TransaksiRental(namaPeminjam, lamaPinjam, barangDipinjam);
                transaksi.totalBiaya = totalBiaya;
                daftarTransaksi.add(transaksi);
                System.out.println("\nTransaksi berhasil! Berikut rincian transaksi:");
                System.out.println(transaksi);
            } else {
                System.out.println("Kode kendaraan tidak valid!");
            }
        } else {
            System.out.println("Maaf, kendaraan tersebut sudah disewa. Mohon pilih kendaraan yang tersedia!");
        }
    }
    
    

    static void tampilkanSeluruhTransaksi() {
        if (daftarTransaksi.isEmpty()) {
            System.out.println("\nBelum ada transaksi.");
        } else {
            System.out.println("\nSeluruh Transaksi:");
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++=+++++++++++++++++");
            System.out.printf("| %-5s | %-10s | %-20s | %-20s | %-15s | %-15s |\n", "Kode", "No TNKB", "Kendaraan", "Nama Peminjam", "Lama Meminjam", "Biaya Total");
            System.out.println("------------------------------------------------------------------------------------------------------------");
            for (TransaksiRental tr : daftarTransaksi) {
                System.out.printf("| %-5d | %-10s | %-20s | %-20s | %-15d | %-15.2f |\n", tr.kodeTransaksi, tr.br.noTNKB, tr.br.namaKendaraan, tr.namaPeminjam, tr.lamaPinjam, tr.totalBiaya);
            }
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        }
    }

    static void urutkanTransaksi() {
        Collections.sort(daftarTransaksi, (tr1, tr2) -> {
            String tnkb1 = tr1.br.noTNKB.toUpperCase();
            String tnkb2 = tr2.br.noTNKB.toUpperCase();
            return tnkb1.compareTo(tnkb2);
        });
        System.out.println("\nTransaksi berhasil diurutkan berdasarkan No TNKB.");
        tampilkanSeluruhTransaksi();
    }
}