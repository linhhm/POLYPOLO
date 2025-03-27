/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author X1
 */
public class SanPhamChiTiet {

    private Integer maSP;
    private String tenSP;
    private Integer maSize;
    private Integer maMau;
    private String trangThai;
    private Integer soLuong;

    public SanPhamChiTiet(Integer maSP, String tenSP, Integer soLuong) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.soLuong = soLuong;
    }

    public SanPhamChiTiet(Integer maSP, String tenSP) {
        this.maSP = maSP;
        this.tenSP = tenSP;
    }

    
    private Integer maDM;

    public SanPhamChiTiet() {
    }

    public Integer getMaDM() {
        return maDM;
    }

    public void setMaDM(Integer maDM) {
        this.maDM = maDM;
    }

    public SanPhamChiTiet(Integer maSP, String tenSP, Integer maSize, Integer maMau, String trangThai, Integer soLuong, Integer maDM) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.maSize = maSize;
        this.maMau = maMau;
        this.trangThai = trangThai;
        this.soLuong = soLuong;
        this.maDM = maDM;
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

    public Integer getMaSize() {
        return maSize;
    }

    public void setMaSize(Integer maSize) {
        this.maSize = maSize;
    }

    public Integer getMaMau() {
        return maMau;
    }

    public void setMaMau(Integer maMau) {
        this.maMau = maMau;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    
    
    

    
    
    
}
