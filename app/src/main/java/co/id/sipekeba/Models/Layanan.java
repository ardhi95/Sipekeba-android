package co.id.sipekeba.Models;

public class Layanan {

    String id;
    String id_admin;
    String jenisBarang;
    String keterangan;
    String created;
    String modified;
    String status;

    public Layanan(
            String id,
            String id_admin,
            String jenisBarang,
            String keterangan,
            String created,
            String modified,
            String status) {
        this.id             = id;
        this.id_admin       = id_admin;
        this.jenisBarang    = jenisBarang;
        this.keterangan     = keterangan;
        this.created        = created;
        this.modified       = modified;
        this.status         = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_admin() {
        return id_admin;
    }

    public void setId_admin(String id_admin) {
        this.id_admin = id_admin;
    }

    public String getJenisBarang() {
        return jenisBarang;
    }

    public void setJenisBarang(String jenisBarang) {
        this.jenisBarang = jenisBarang;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
