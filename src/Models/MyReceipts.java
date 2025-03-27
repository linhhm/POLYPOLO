/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.util.Date;

/**
 *
 * @author X1
 */
public class MyReceipts {
    private Integer maHD, soL;
    private String tenKH;
    private String phuongThuc, trangThai, loaiKH;
    private Double tongTien;
    private Date ngayLap;
    private String tenNV;

    public MyReceipts() {
    }

    public MyReceipts(Integer maHD, Integer soL, String tenKH, String phuongThuc, String trangThai, String loaiKH, Double tongTien, Date ngayLap, String tenNV) {
        this.maHD = maHD;
        this.soL = soL;
        this.tenKH = tenKH;
        this.phuongThuc = phuongThuc;
        this.trangThai = trangThai;
        this.loaiKH = loaiKH;
        this.tongTien = tongTien;
        this.ngayLap = ngayLap;
        this.tenNV = tenNV;
    }

    public Integer getSoL() {
        return soL;
    }

    public void setSoL(Integer soL) {
        this.soL = soL;
    }

    

    public Integer getMaHD() {
        return maHD;
    }

    public void setMaHD(Integer maHD) {
        this.maHD = maHD;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getPhuongThuc() {
        return phuongThuc;
    }

    public void setPhuongThuc(String phuongThuc) {
        this.phuongThuc = phuongThuc;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getLoaiKH() {
        return loaiKH;
    }

    public void setLoaiKH(String loaiKH) {
        this.loaiKH = loaiKH;
    }

    public Double getTongTien() {
        return tongTien;
    }

    public void setTongTien(Double tongTien) {
        this.tongTien = tongTien;
    }

    public Date getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(Date ngayLap) {
        this.ngayLap = ngayLap;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }
    
    
}
