/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Admin
 */
public class NhanSu {
    private String tenNhanVien;
    private String gioiTinh;
    private String ngaySinh;
    private String soDienThoai;
    private String diaChi;
    private Integer maNguoiDung, maNhanVien;

    public NhanSu() {
    }

    public NhanSu(String tenNhanVien, Integer maNhanVien) {
        this.tenNhanVien = tenNhanVien;
        this.maNhanVien = maNhanVien;
    }

    public NhanSu(String tenNhanVien, String gioiTinh, String soDienThoai, String diaChi, Integer maNguoiDung, Integer maNhanVien) {
        this.tenNhanVien = tenNhanVien;
        this.gioiTinh = gioiTinh;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
        this.maNguoiDung = maNguoiDung;
        this.maNhanVien = maNhanVien;
    }
    
    public NhanSu(String tenNhanVien, String gioiTinh, String soDienThoai, String diaChi, Integer maNguoiDung) {
        this.tenNhanVien = tenNhanVien;
        this.gioiTinh = gioiTinh;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
        this.maNguoiDung = maNguoiDung;
    }
    
    public Integer getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(Integer maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public NhanSu( String tenNhanVien, String gioiTinh, String ngaySinh, String soDienThoai, String diaChi, Integer maNguoiDung) {
        this.tenNhanVien = tenNhanVien;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
        this.maNguoiDung = maNguoiDung;
    }


    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public Integer getMaNguoiDung() {
        return maNguoiDung;
    }

    public void setMaNguoiDung(Integer maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }

    
}