/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ViewModels;

import java.util.Date;


/**
 *
 * @author X1
 */
public class HD_HoaDonViewModel {
    private Integer maHD;
    private String tenKH;
    private String soDT;
    private String phuongThuc, trangThai;
    private Double tongTien;
    private Date ngayLap;
    private String tenNV;

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public HD_HoaDonViewModel() {
    }
//
    public HD_HoaDonViewModel(Double tongTien) {
        this.tongTien = tongTien;
    }
    public HD_HoaDonViewModel(Integer maHD) {
        this.maHD = maHD;
    }
//    

//    public HD_HoaDonViewModel(Integer maHD, String tenKH, String soDT, String phuongThuc, String trangThai, Double tongTien, String ngayLap, String tenNV) {
//        this.maHD = maHD;
//        this.tenKH = tenKH;
//        this.soDT = soDT;
//        this.phuongThuc = phuongThuc;
//        this.trangThai = trangThai;
//        this.tongTien = tongTien;
//        this.ngayLap = ngayLap;
//        this.tenNV = tenNV;
//    }
    public HD_HoaDonViewModel(Integer maHD, String tenKH, String soDT, String phuongThuc, Date ngayLap, String tenNV) {
        this.maHD = maHD;
        this.tenKH = tenKH;
        this.soDT = soDT;
        this.phuongThuc = phuongThuc;
        this.ngayLap = ngayLap;
        this.tenNV = tenNV;
    }

    public HD_HoaDonViewModel(Integer maHD, String tenKH, String soDT, String phuongThuc, String trangThai, Double tongTien, Date ngayLap, String tenNV) {
        this.maHD = maHD;
        this.tenKH = tenKH;
        this.soDT = soDT;
        this.phuongThuc = phuongThuc;
        this.trangThai = trangThai;
        this.tongTien = tongTien;
        this.ngayLap = ngayLap;
        this.tenNV = tenNV;
    }

    public HD_HoaDonViewModel(Integer maHD, String tenKH, String soDT, String phuongThuc, Double tongTien, Date ngayLap, String tenNV) {
        this.maHD = maHD;
        this.tenKH = tenKH;
        this.soDT = soDT;
        this.phuongThuc = phuongThuc;
        this.tongTien = tongTien;
        this.ngayLap = ngayLap;
        this.tenNV = tenNV;
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

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public String getPhuongThuc() {
        return phuongThuc;
    }

    public void setPhuongThuc(String phuongThuc) {
        this.phuongThuc = phuongThuc;
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
