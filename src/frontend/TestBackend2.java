package frontend;

import backend.*;

public class TestBackend2 {

    public static void main(String[] args) {
        // Membuat objek anggota
        Anggota ang1 = new Anggota("Budi", "Jl. Merdeka No.1", "081234567890");
        Anggota ang2 = new Anggota("Sari", "Jl. Sudirman No.2", "089876543210");

        // Test insert
        ang1.save();
        ang2.save();

        // Test update
        ang2.setAlamat("Jl. Sudirman No.99");
        ang2.setTelepon("088888888888");
        ang2.save();

        // Test delete (opsional, jika ingin menghapus salah satu data)
        // ang1.delete();

        // Test select all
        System.out.println("=== Daftar Semua Anggota ===");
        for (Anggota a : new Anggota().getAll()) {
             System.out.println("Nama: " + a.getNama() + ", Alamat: " + a.getAlamat() + ", Telepon: " + a.getTelepon());
        }

        // Test search
        System.out.println("=== Pencarian Anggota dengan keyword 'Sari' ===");
        for (Anggota a : new Anggota().search("Sari")) {
            System.out.println("Nama: " + a.getNama() + ", Alamat: " + a.getAlamat() + ", Telepon: " + a.getTelepon());
        }
    }
}
