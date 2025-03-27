/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author X1
 */
public class SanPham {
    private Integer maSP;
    private String tenSP;
    private Integer maDM;
    private String trangThai;
    private Double giaNhap, giaBan;
    private Integer maSz;
    private Integer maMau;
    private Integer soLuong;

    public SanPham() {
    }

    public SanPham(Integer maSP, Integer maDM, String trangThai, Double giaNhap, Double giaBan, Integer soLuong) {
        this.maSP = maSP;
        this.maDM = maDM;
        this.trangThai = trangThai;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
    }

    public SanPham( Integer maSP, String tenSP, Integer maDM, String trangThai, Double giaNhap, Double giaBan, Integer maSz, Integer maMau, Integer soLuong) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.maDM = maDM;
        this.trangThai = trangThai;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.maSz = maSz;
        this.maMau = maMau;
        this.soLuong = soLuong;
    }

    public SanPham(Integer maSP, String tenSP) {
        this.maSP = maSP;
        this.tenSP = tenSP;
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

    public Integer getMaDM() {
        return maDM;
    }

    public void setMaDM(Integer maDM) {
        this.maDM = maDM;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public Double getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(Double giaNhap) {
        this.giaNhap = giaNhap;
    }

    public Double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(Double giaBan) {
        this.giaBan = giaBan;
    }

    public Integer getMaSz() {
        return maSz;
    }

    public void setMaSz(Integer maSz) {
        this.maSz = maSz;
    }

    public Integer getMaMau() {
        return maMau;
    }

    public void setMaMau(Integer maMau) {
        this.maMau = maMau;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }
    
    
}
