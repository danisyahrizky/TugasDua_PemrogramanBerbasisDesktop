import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    static menu[] menuRestoran = new menu[50];
    static menu[] pesanan = new menu[50];
    static int[] jumlahPesanan = new int[50];

    static int jumlahMenuSaatIni = 8;
    static int jumlahItemDipesan = 0;

    public static void main(String[] args) {
        inisialisasiMenu();
        loopMenuUtama();
        scanner.close(); 
    }

    /**
     * Method baru untuk menu utama
     */
    public static void loopMenuUtama() {
        boolean berjalan = true;
        while (berjalan) {
            System.out.println("\n--- Aplikasi Restoran Sederhana ---");
            System.out.println("Pilih mode:");
            System.out.println("1. Menu Pemesanan (Pelanggan)");
            System.out.println("2. Menu Pengelolaan (Pemilik Restoran)");
            System.out.println("0. Keluar Aplikasi");
            System.out.print("Masukkan pilihan Anda: ");

            int pilihan = bacaInteger();
            scanner.nextLine(); // konsumsi newline

            switch (pilihan) {
                case 1:
                    menuPemesananPelanggan();
                    break;
                case 2:
                    menuPengelolaanPemilik();
                    break;
                case 0:
                    berjalan = false;
                    System.out.println("Terima kasih!");
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    /**
     * Method baru untuk alur pemesanan
     */
    public static void menuPemesananPelanggan() {
        System.out.println("\n== Mode Pemesanan Pelanggan ==");
        // Reset pesanan sebelumnya
        pesanan = new menu[50]; // [KOREKSI]
        jumlahPesanan = new int[50];
        jumlahItemDipesan = 0;

        tampilkanMenu();
        ambilPesanan();

        if (jumlahItemDipesan > 0) {
            hitungDanCetakStruk();
        } else {
            System.out.println("Pemesanan dibatalkan.");
        }
    }

    /**
     * Method baru untuk menu pengelolaan (Pemilik)
     */
    public static void menuPengelolaanPemilik() {
        boolean berjalan = true;
        while (berjalan) {
            System.out.println("\n== Mode Pengelolaan Menu (Pemilik) ==");
            System.out.println("1. Tambah Menu Baru");
            System.out.println("2. Ubah Harga Menu");
            System.out.println("3. Hapus Menu");
            System.out.println("0. Kembali ke Menu Utama");
            System.out.print("Masukkan pilihan Anda: ");

            int pilihan = bacaInteger();
            scanner.nextLine(); // konsumsi newline

            switch (pilihan) {
                case 1:
                    tambahMenu();
                    break;
                case 2:
                    ubahMenu();
                    break;
                case 3:
                    hapusMenu();
                    break;
                case 0:
                    berjalan = false; 
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }


    public static void inisialisasiMenu() {
        // [KOREKSI] Menggunakan constructor 'menu' (huruf kecil)
        // Menghapus sintaks "nama:", "harga:" yang tidak valid di Java
        menuRestoran[0] = new menu("Nasi Padang", 25000, "Makanan");
        menuRestoran[1] = new menu("Sate Ayam", 30000, "Makanan");
        menuRestoran[2] = new menu("Gado-Gado", 20000, "Makanan");
        menuRestoran[3] = new menu("Rawon", 35000, "Makanan");
        menuRestoran[4] = new menu("Es Teh", 5000, "Minuman");
        menuRestoran[5] = new menu("Es Jeruk", 8000, "Minuman");
        menuRestoran[6] = new menu("Jus Alpukat", 15000, "Minuman");
        menuRestoran[7] = new menu("Kopi Susu", 12000, "Minuman");
        jumlahMenuSaatIni = 8; 
    }

    public static void tampilkanMenu() {
        System.out.println("\n=== Menu Restoran ===");
        
        System.out.println("--- Makanan ---");
        int nomor = 1;
        for (int i = 0; i < jumlahMenuSaatIni; i++) {
            if (menuRestoran[i] != null && menuRestoran[i].kategori.equals("Makanan")) {
                System.out.println(nomor + ". " + menuRestoran[i].nama + " - Rp " + menuRestoran[i].harga);
                nomor++;
            }
        }

        System.out.println("\n--- Minuman ---");
        for (int i = 0; i < jumlahMenuSaatIni; i++) {
            if (menuRestoran[i] != null && menuRestoran[i].kategori.equals("Minuman")) {
                System.out.println(nomor + ". " + menuRestoran[i].nama + " - Rp " + menuRestoran[i].harga);
                nomor++;
            }
        }
        System.out.println("=====================");
    }

    public static void ambilPesanan() {
        System.out.println("\n--- Masukkan Pesanan ---");
        System.out.println("(Masukkan nomor menu, atau 'selesai' untuk mengakhiri)");

        while (true) {
            if (jumlahItemDipesan >= pesanan.length) {
                System.out.println("Keranjang pesanan penuh. Lanjut ke pembayaran.");
                break;
            }

            System.out.print("Pesanan (Nomor Menu): ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("selesai")) {
                if (jumlahItemDipesan == 0) {
                    System.out.println("Anda belum memesan apapun.");
                }
                break; 
            }

            try {
                int nomorMenu = Integer.parseInt(input);

                // [KOREKSI] Menggunakan tipe 'menu'
                menu menuDipesan = cariMenuBerdasarkanNomor(nomorMenu);

                if (menuDipesan != null) {
                    System.out.print("Jumlah: ");
                    int qty = bacaInteger();
                    scanner.nextLine(); 

                    if (qty > 0) {
                        pesanan[jumlahItemDipesan] = menuDipesan;
                        jumlahPesanan[jumlahItemDipesan] = qty;
                        jumlahItemDipesan++; 
                        System.out.println(">> Berhasil ditambahkan: " + menuDipesan.nama + " x" + qty);
                    } else {
                        System.out.println("Jumlah harus lebih dari 0.");
                    }
                } else {
                    System.out.println("Nomor menu tidak valid. Silakan coba lagi.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Masukkan nomor menu atau 'selesai'.");
            }
        }
    }

    /**
     * Helper untuk 'ambilPesanan'
     * [KOREKSI] Mengembalikan tipe 'menu'
     */
    private static menu cariMenuBerdasarkanNomor(int nomor) {
        int nomorSaatIni = 1;
        // Cari di Makanan
        for (int i = 0; i < jumlahMenuSaatIni; i++) {
            if (menuRestoran[i] != null && menuRestoran[i].kategori.equals("Makanan")) {
                if (nomorSaatIni == nomor) {
                    return menuRestoran[i];
                }
                nomorSaatIni++;
            }
        }
        // Cari di Minuman
        for (int i = 0; i < jumlahMenuSaatIni; i++) {
            if (menuRestoran[i] != null && menuRestoran[i].kategori.equals("Minuman")) {
                if (nomorSaatIni == nomor) {
                    return menuRestoran[i];
                }
                nomorSaatIni++;
            }
        }
        return null; // Tidak ketemu
    }

    public static void hitungDanCetakStruk() {
        System.out.println("\n=== STRUK PEMBAYARAN ===");

        int subtotal = 0;

        // [MODIFIKASI] Struk dibuat dinamis menggunakan loop
        for (int i = 0; i < jumlahItemDipesan; i++) {
            if (pesanan[i] != null) {
                // [FIX] Memperbaiki typo 'jumalhPesanan' dari kode asli Anda
                int totalItem = pesanan[i].harga * jumlahPesanan[i]; 
                subtotal += totalItem;
                System.out.println(pesanan[i].nama + "\t" + jumlahPesanan[i] + "x" + pesanan[i].harga + "\t= Rp " + totalItem);
            }
        }

        System.out.println("-------------------------");
        System.out.println("Subtotal:\t\t= Rp " + subtotal);

        double diskonPersen = 0;
        double diskonBeliSatu = 0;
        String namaMenuBeliSatu = "";
        int biayaPelayanan = 20000;

        // Skenario 1: Diskon 10%
        if (subtotal > 100000) {
            diskonPersen = subtotal * 0.10;
            System.out.println("Diskon 10%:\t\t= Rp -" + (int) diskonPersen);
        }

        // Skenario 2: Beli 1 Gratis 1 (Minuman termurah)
        if (subtotal > 50000) {
            int hargaMinumanTermurah = Integer.MAX_VALUE;
            boolean adaMinuman = false;

            for (int i = 0; i < jumlahItemDipesan; i++) {
                if (pesanan[i].kategori.equals("Minuman")) {
                    adaMinuman = true;
                    if (pesanan[i].harga < hargaMinumanTermurah) {
                        hargaMinumanTermurah = pesanan[i].harga;
                        namaMenuBeliSatu = pesanan[i].nama;
                    }
                }
            }
            if (adaMinuman) {
                diskonBeliSatu = hargaMinumanTermurah;
            }
        }

        if (diskonBeliSatu > 0) {
            System.out.println("Promo BOGO (" + namaMenuBeliSatu + "):\t= Rp -" + (int) diskonBeliSatu);
        }

        double totalSetelahDiskon = subtotal - diskonPersen - diskonBeliSatu;
        double pajak = totalSetelahDiskon * 0.10; // Pajak dari total setelah diskon
        double totalAkhir = totalSetelahDiskon + pajak + biayaPelayanan;

        System.out.println("Pajak 10% \t\t\t= Rp " + (int) pajak);
        System.out.println("Biaya Pelayanan \t= Rp " + biayaPelayanan);
        System.out.println("-------------------------");
        System.out.println("Total Akhir:\t\t= Rp " + (int) totalAkhir);
        System.out.println("=========================");
    }

    // --- METHOD BARU UNTUK PENGELOLAAN ---

    private static void tambahMenu() {
        System.out.println("\n-- Tambah Menu Baru --");
        if (jumlahMenuSaatIni >= menuRestoran.length) {
            System.out.println("Gagal: Daftar menu sudah penuh.");
            return;
        }

        System.out.print("Masukkan Nama Menu: ");
        String nama = scanner.nextLine();

        System.out.print("Masukkan Harga Menu: ");
        int harga = bacaInteger();
        scanner.nextLine(); 

        String kategori = "";
        while (true) {
            System.out.print("Masukkan Kategori (Makanan/Minuman): ");
            kategori = scanner.nextLine();
            if (kategori.equalsIgnoreCase("Makanan") || kategori.equalsIgnoreCase("Minuman")) {
                // Konsistensi format
                kategori = kategori.substring(0, 1).toUpperCase() + kategori.substring(1).toLowerCase();
                break;
            } else {
                System.out.println("Kategori tidak valid.");
            }
        }

        System.out.print("Anda akan menambahkan '" + nama + "'. Yakin? (Ya/Tidak): ");
        if (bacaKonfirmasi()) {
            // [KOREKSI] Menggunakan constructor 'menu'
            menuRestoran[jumlahMenuSaatIni] = new menu(nama, harga, kategori);
            jumlahMenuSaatIni++;
            System.out.println(">> Menu baru berhasil ditambahkan!");
        } else {
            System.out.println("Penambahan menu dibatalkan.");
        }
    }

    private static void ubahMenu() {
        System.out.println("\n-- Ubah Harga Menu --");
        tampilkanMenu();
        System.out.print("Masukkan nomor menu yang akan diubah harganya: ");
        
        int nomorMenu = bacaInteger();
        scanner.nextLine(); 

        // [KOREKSI] Menggunakan tipe 'menu'
        menu menuDiubah = cariMenuBerdasarkanNomor(nomorMenu);

        if (menuDiubah != null) {
            System.out.println("Menu yang dipilih: " + menuDiubah.nama + " (Harga lama: Rp " + menuDiubah.harga + ")");
            System.out.print("Yakin ingin mengubah harga menu ini? (Ya/Tidak): ");

            if (bacaKonfirmasi()) {
                System.out.print("Masukkan harga baru: ");
                int hargaBaru = bacaInteger();
                scanner.nextLine(); 

                menuDiubah.harga = hargaBaru;
                System.out.println(">> Harga menu berhasil diubah!");
            } else {
                System.out.println("Perubahan harga dibatalkan.");
            }
        } else {
            System.out.println("Nomor menu tidak valid.");
        }
    }

    private static void hapusMenu() {
        System.out.println("\n-- Hapus Menu --");
        tampilkanMenu();
        System.out.print("Masukkan nomor menu yang akan dihapus: ");

        int nomorMenu = bacaInteger();
        scanner.nextLine(); 

        int indexDihapus = -1;
        int nomorSaatIni = 1;
        
        // Cari index di Makanan
        for (int i = 0; i < jumlahMenuSaatIni; i++) {
            if (menuRestoran[i] != null && menuRestoran[i].kategori.equals("Makanan")) {
                if (nomorSaatIni == nomorMenu) {
                    indexDihapus = i;
                    break;
                }
                nomorSaatIni++;
            }
        }
        // Cari index di Minuman
        if (indexDihapus == -1) {
            for (int i = 0; i < jumlahMenuSaatIni; i++) {
                if (menuRestoran[i] != null && menuRestoran[i].kategori.equals("Minuman")) {
                    if (nomorSaatIni == nomorMenu) {
                        indexDihapus = i;
                        break;
                    }
                    nomorSaatIni++;
                }
            }
        }

        if (indexDihapus != -1) {
            // [KOREKSI] Menggunakan tipe 'menu'
            menu menuDihapus = menuRestoran[indexDihapus];
            System.out.println("Menu yang dipilih: " + menuDihapus.nama);
            System.out.print("YAKIN ingin menghapus menu ini? (Ya/Tidak): ");

            if (bacaKonfirmasi()) {
                // Geser semua elemen setelahnya ke kiri
                for (int i = indexDihapus; i < jumlahMenuSaatIni - 1; i++) {
                    menuRestoran[i] = menuRestoran[i + 1];
                }
                menuRestoran[jumlahMenuSaatIni - 1] = null; 
                jumlahMenuSaatIni--; 

                System.out.println(">> Menu berhasil dihapus!");
            } else {
                System.out.println("Penghapusan menu dibatalkan.");
            }
        } else {
            System.out.println("Nomor menu tidak valid.");
        }
    }


    // --- METHOD HELPER UNTUK INPUT ---

    private static int bacaInteger() {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Input tidak valid. Harap masukkan angka.");
                scanner.next(); // Bersihkan buffer
                System.out.print("Masukkan lagi: ");
            }
        }
    }

    private static boolean bacaKonfirmasi() {
        String input = scanner.nextLine();
        return input.equalsIgnoreCase("Ya");
    }
}