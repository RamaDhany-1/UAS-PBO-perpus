/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

import java.sql.*;
import java.util.ArrayList;

public class Peminjaman {

    private int idpeminjaman;
    private int idanggota;
    private int idbuku;
    private String tanggalpinjam;
    private String tanggalkembali;
    private int status; // <-- PERUBAHAN 1: Menambahkan properti status

    private Anggota anggota = new Anggota();
    private Buku buku = new Buku();

    // Getter dan Setter
    public int getIdpeminjaman() {
        return idpeminjaman;
    }

    public void setIdpeminjaman(int idpeminjaman) {
        this.idpeminjaman = idpeminjaman;
    }

    public int getIdanggota() {
        return idanggota;
    }

    public void setIdanggota(int idanggota) {
        this.idanggota = idanggota;
        this.anggota = new Anggota().getById(idanggota);
    }

    public int getIdbuku() {
        return idbuku;
    }

    public void setIdbuku(int idbuku) {
        this.idbuku = idbuku;
        this.buku = new Buku().getById(idbuku);
    }

    public String getTanggalpinjam() {
        return tanggalpinjam;
    }

    public void setTanggalpinjam(String tanggalpinjam) {
        this.tanggalpinjam = tanggalpinjam;
    }

    public String getTanggalkembali() {
        return tanggalkembali;
    }

    public void setTanggalkembali(String tanggalkembali) {
        this.tanggalkembali = tanggalkembali;
    }

    public Anggota getAnggota() {
        return anggota;
    }

    public Buku getBuku() {
        return buku;
    }

    // <-- PERUBAHAN 2: Menambahkan getter & setter untuk status
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    // Constructor
    public Peminjaman() {
    }

    public Peminjaman(Anggota anggota, Buku buku) {
        this.anggota = anggota;
        this.buku = buku;
        this.idanggota = anggota.getIdanggota();
        this.idbuku = buku.getIdbuku();
    }

    // Get by ID
    public Peminjaman getById(int id) {
        Peminjaman pinjam = new Peminjaman();
        ResultSet rs = DBHelper.selectQuery("SELECT * FROM peminjaman WHERE idpeminjaman = '" + id + "'");

        try {
            while (rs.next()) {
                pinjam = new Peminjaman();
                pinjam.setIdpeminjaman(rs.getInt("idpeminjaman"));
                pinjam.setIdanggota(rs.getInt("idanggota"));
                pinjam.setIdbuku(rs.getInt("idbuku"));
                pinjam.setTanggalpinjam(rs.getString("tanggalpinjam"));
                pinjam.setTanggalkembali(rs.getString("tanggalkembali"));
                pinjam.setStatus(rs.getInt("status")); // <-- PERUBAHAN 3: Ambil data status
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pinjam;
    }

    // Get all
    public ArrayList<Peminjaman> getAll() {
        ArrayList<Peminjaman> ListPeminjaman = new ArrayList<>();
        ResultSet rs = DBHelper.selectQuery("SELECT * FROM peminjaman");

        try {
            while (rs.next()) {
                Peminjaman pinjam = new Peminjaman();
                pinjam.setIdpeminjaman(rs.getInt("idpeminjaman"));
                pinjam.setIdanggota(rs.getInt("idanggota"));
                pinjam.setIdbuku(rs.getInt("idbuku"));
                pinjam.setTanggalpinjam(rs.getString("tanggalpinjam"));
                pinjam.setTanggalkembali(rs.getString("tanggalkembali"));
                pinjam.setStatus(rs.getInt("status")); // <-- PERUBAHAN 4: Ambil data status
                ListPeminjaman.add(pinjam);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ListPeminjaman;
    }

    // <-- PERUBAHAN 5: MENAMBAHKAN METHOD PENCARIAN BARU -->
    public ArrayList<Peminjaman> searchByIdAnggota(String keyword) {
        ArrayList<Peminjaman> ListPeminjaman = new ArrayList<>();

        // PENTING: Query ini masih rentan SQL Injection karena keterbatasan DBHelper.
        // Di aplikasi nyata, gunakan PreparedStatement.
        String sql = "SELECT * FROM peminjaman WHERE idanggota = '" + keyword + "'";
        ResultSet rs = DBHelper.selectQuery(sql);

        try {
            while (rs.next()) {
                Peminjaman pinjam = new Peminjaman();
                pinjam.setIdpeminjaman(rs.getInt("idpeminjaman"));
                pinjam.setIdanggota(rs.getInt("idanggota"));
                pinjam.setIdbuku(rs.getInt("idbuku"));
                pinjam.setTanggalpinjam(rs.getString("tanggalpinjam"));
                pinjam.setTanggalkembali(rs.getString("tanggalkembali"));
                pinjam.setStatus(rs.getInt("status")); // Ambil data status
                ListPeminjaman.add(pinjam);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ListPeminjaman;
    }

    // Save
    public void save() {
        // Variabel untuk menampung tanggal kembali yang akan dimasukkan ke SQL
        String tglKembaliForSql;

        // Jika tanggal kembali null, kosong, atau "0000-00-00", kita set nilainya menjadi NULL untuk SQL
        if (this.tanggalkembali == null || this.tanggalkembali.trim().isEmpty() || this.tanggalkembali.equals("0000-00-00")) {
            tglKembaliForSql = "NULL";
        } else {
            // Jika ada isi, kita pakai nilainya dengan menambahkan kutip
            tglKembaliForSql = "'" + this.tanggalkembali + "'";
        }

        if (getById(idpeminjaman).getIdpeminjaman() == 0) {
            // Blok untuk INSERT
            String SQL = "INSERT INTO peminjaman (idanggota, idbuku, tanggalpinjam, tanggalkembali, status) VALUES ("
                    + "'" + this.getIdanggota() + "', "
                    + "'" + this.getIdbuku() + "', "
                    + "'" + this.tanggalpinjam + "', "
                    + tglKembaliForSql + ", " // Menggunakan variabel yang sudah diproses
                    + "'0')";
            this.idpeminjaman = DBHelper.insertQueryGetId(SQL);
        } else {
            // Blok untuk UPDATE
            String SQL = "UPDATE peminjaman SET "
                    + "idanggota = '" + this.getIdanggota() + "', "
                    + "idbuku = '" + this.getIdbuku() + "', "
                    + "tanggalpinjam = '" + this.tanggalpinjam + "', "
                    + "tanggalkembali = " + tglKembaliForSql + ", " // Menggunakan variabel yang sudah diproses
                    + "status = '" + this.status + "' "
                    + "WHERE idpeminjaman = '" + this.idpeminjaman + "'";
            DBHelper.executeQuery(SQL);
        }
    }

    // Delete
    public void delete() {
        String SQL = "DELETE FROM peminjaman WHERE idpeminjaman = '" + this.idpeminjaman + "'";
        DBHelper.executeQuery(SQL);
    }
}
