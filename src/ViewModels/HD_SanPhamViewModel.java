/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ViewModels;

/**
 *
 * @author X1
 */
public class HD_SanPhamViewModel {
    private Integer maSP;
    private String tenSP;
    private String tenDM;
    private String maSac;
    private String kichCo;    
    private Double donGia;
    private Integer soLuong;
    private String trangThai;

    public HD_SanPhamViewModel() {
    }

    public HD_SanPhamViewModel(Integer maSP, String tenSP, String tenDM, String maSac, String kichCo, Double donGia, Integer soLuong, String trangThai) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.tenDM = tenDM;
        this.maSac = maSac;
        this.kichCo = kichCo;
        this.donGia = donGia;
        this.soLuong = soLuong;
        this.trangThai = trangThai;
    }

    public Integer getMaSP() {
        return maSP;
    }

    public void setMaSP(Integer maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getTenDM() {
        return tenDM;
    }

    public void setTenDM(String tenDM) {
        this.tenDM = tenDM;
    }

    public String getMaSac() {
        return maSac;
    }

    public void setMaSac(String maSac) {
        this.maSac = maSac;
    }

    public String getKichCo() {
        return kichCo;
    }

    public void setKichCo(String kichCo) {
        this.kichCo = kichCo;
    }

    public Double getDonGia() {
        return donGia;
    }

    public void setDonGia(Double donGia) {
        this.donGia = donGia;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
    
    
    
    
    
    
}
