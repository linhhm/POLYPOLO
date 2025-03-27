/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ViewModels;

/**
 *
 * @author X1
 */
public class HD_GioHangViewModel {
    private Integer maHDCT;
    private Integer maSP;
    private String tenSP;
    private String mauSac;
    private String kichCo;
    private Integer soLuong;
    private Double donGia;
    private Double thanhTien;

    public HD_GioHangViewModel() {
    }
    //
    public HD_GioHangViewModel(Integer soLuong) {
        this.soLuong = soLuong;
    }
    public Integer getSoLuong() {
        return soLuong;
    }
    //
    public Integer getMaHDCT() {
        return maHDCT;
    }

    public void setMaHDCT(Integer maHDCT) {
        this.maHDCT = maHDCT;
    }

    public HD_GioHangViewModel(Integer maHDCT, Integer maSP, String tenSP, String mauSac, String kichCo, Integer soLuong, Double donGia, Double thanhTien) {
        this.maHDCT = maHDCT;
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.mauSac = mauSac;
        this.kichCo = kichCo;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.thanhTien = thanhTien;
    }

    public HD_GioHangViewModel(Integer maSP, String tenSP, String mauSac, String kichCo, Integer soLuong, Double donGia, Double thanhTien) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.mauSac = mauSac;
        this.kichCo = kichCo;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.thanhTien = thanhTien;
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

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    public String getKichCo() {
        return kichCo;
    }

    public void setKichCo(String kichCo) {
        this.kichCo = kichCo;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public Double getDonGia() {
        return donGia;
    }

    public void setDonGia(Double donGia) {
        this.donGia = donGia;
    }

    public Double getThanhTien() {
        return donGia*soLuong;
    }

    public void setThanhTien(Double thanhTien) {
        this.thanhTien = thanhTien;
    }

    
    
    
}
