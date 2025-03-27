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
public class TaiKhoan {
    private Integer maND, maNV;
    private String tenDN, matK, tenNV, soDT, vaiT, diaC, gioiT;
    private Date ngayS;

    public TaiKhoan() {
    }

    public TaiKhoan(Integer maND, Integer maNV, String tenDN, String matK, String tenNV, String soDT, String vaiT, String diaC, String gioiT, Date ngayS) {
        this.maND = maND;
        this.maNV = maNV;
        this.tenDN = tenDN;
        this.matK = matK;
        this.tenNV = tenNV;
        this.soDT = soDT;
        this.vaiT = vaiT;
        this.diaC = diaC;
        this.gioiT = gioiT;
        this.ngayS = ngayS;
    }

    public TaiKhoan(Integer maND, String tenDN, String matK, String tenNV, String soDT, String vaiT, String diaC, String gioiT, Date ngayS) {
        this.maND = maND;
        this.tenDN = tenDN;
        this.matK = matK;
        this.tenNV = tenNV;
        this.soDT = soDT;
        this.vaiT = vaiT;
        this.diaC = diaC;
        this.gioiT = gioiT;
        this.ngayS = ngayS;
    }

    public Integer getMaNV() {
        return maNV;
    }

    public void setMaNV(Integer maNV) {
        this.maNV = maNV;
    }

    public Date getNgayS() {
        return ngayS;
    }

    public void setNgayS(Date ngayS) {
        this.ngayS = ngayS;
    }

    

    public Integer getMaND() {
        return maND;
    }

    public void setMaND(Integer maND) {
        this.maND = maND;
    }

    public String getTenDN() {
        return tenDN;
    }

    public void setTenDN(String tenDN) {
        this.tenDN = tenDN;
    }

    public String getMatK() {
        return matK;
    }

    public void setMatK(String matK) {
        this.matK = matK;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public String getVaiT() {
        return vaiT;
    }

    public void setVaiT(String vaiT) {
        this.vaiT = vaiT;
    }

    public String getDiaC() {
        return diaC;
    }

    public void setDiaC(String diaC) {
        this.diaC = diaC;
    }

    public String getGioiT() {
        return gioiT;
    }

    public void setGioiT(String gioiT) {
        this.gioiT = gioiT;
    }

   
    
}
