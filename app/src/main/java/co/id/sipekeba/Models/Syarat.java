package co.id.sipekeba.Models;

public class Syarat {
    String id;
    String id_layanan;
    String syarat;
    String created;
    String modified;
    String status;

    public Syarat(
            String id,
            String id_layanan,
            String syarat,
            String created,
            String modified,
            String status
    ){
        this.id         = id;
        this.id_layanan = id_layanan;
        this.syarat     = syarat;
        this.created    = created;
        this.modified   = modified;
        this.status     = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_layanan() {
        return id_layanan;
    }

    public void setId_layanan(String id_layanan) {
        this.id_layanan = id_layanan;
    }

    public String getSyarat() {
        return syarat;
    }

    public void setSyarat(String syarat) {
        this.syarat = syarat;
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
