/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

/**
 *
 * @author Dhany
 */
import java.util.ArrayList;
import java.sql.*;

public class Anggota {

    private int idanggota;
    private String nama;
    private String alamat;
    private String telepon;

    @Override
    public String toString() {
        return getNama();  // atau bisa juga: return getIdanggota() + " - " + getNama();
    }

    // Getter dan Setter
    public int getIdanggota() {
        return idanggota;
    }

    public void setIdanggota(int idanggota) {
        this.idanggota = idanggota;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    // Constructor
    public Anggota() {
    }

    public Anggota(String nama, String alamat, String telepon) {
        this.nama = nama;
        this.alamat = alamat;
        this.telepon = telepon;
    }

    // Get data by ID
    public Anggota getById(int id) {
        Anggota ang = new Anggota();
        ResultSet rs = DBHelper.selectQuery("SELECT * FROM anggota WHERE idanggota = '" + id + "'");

        try {
            while (rs.next()) {
                ang = new Anggota();
                ang.setIdanggota(rs.getInt("idanggota"));
                ang.setNama(rs.getString("nama"));
                ang.setAlamat(rs.getString("alamat"));
                ang.setTelepon(rs.getString("telepon"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ang;
    }

    // Get all data
    public ArrayList<Anggota> getAll() {
        ArrayList<Anggota> ListAnggota = new ArrayList<>();
        ResultSet rs = DBHelper.selectQuery("SELECT * FROM anggota");

        try {
            while (rs.next()) {
                Anggota ang = new Anggota();
                ang.setIdanggota(rs.getInt("idanggota"));
                ang.setNama(rs.getString("nama"));
                ang.setAlamat(rs.getString("alamat"));
                ang.setTelepon(rs.getString("telepon"));
                ListAnggota.add(ang);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ListAnggota;
    }

    // Search
    public ArrayList<Anggota> search(String keyword) {
        ArrayList<Anggota> ListAnggota = new ArrayList<>();
        String sql = "SELECT * FROM anggota WHERE "
                + "nama LIKE '%" + keyword + "%' OR "
                + "alamat LIKE '%" + keyword + "%' OR "
                + "telepon LIKE '%" + keyword + "%'";
        ResultSet rs = DBHelper.selectQuery(sql);

        try {
            while (rs.next()) {
                Anggota ang = new Anggota();
                ang.setIdanggota(rs.getInt("idanggota"));
                ang.setNama(rs.getString("nama"));
                ang.setAlamat(rs.getString("alamat"));
                ang.setTelepon(rs.getString("telepon"));
                ListAnggota.add(ang);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ListAnggota;
    }

    // Save (Insert or Update)
    public void save() {
        if (getById(idanggota).getIdanggota() == 0) {
            String SQL = "INSERT INTO anggota (nama, alamat, telepon) VALUES ("
                    + "'" + this.nama + "', "
                    + "'" + this.alamat + "', "
                    + "'" + this.telepon + "')";
            this.idanggota = DBHelper.insertQueryGetId(SQL);
        } else {
            String SQL = "UPDATE anggota SET "
                    + "nama = '" + this.nama + "', "
                    + "alamat = '" + this.alamat + "', "
                    + "telepon = '" + this.telepon + "' "
                    + "WHERE idanggota = " + this.idanggota;
            DBHelper.executeQuery(SQL);
        }
    }

    // Delete
    public void delete() {
        String SQL = "DELETE FROM anggota WHERE idanggota = '" + this.idanggota + "'";
        DBHelper.executeQuery(SQL);
    }
}
