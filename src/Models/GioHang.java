/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author X1
 */
public class GioHang {
    private Integer maHD;
    private Integer mahDCT;
    private Integer maSP, soL;
    private Double donG;

    public GioHang() {
    }

    public GioHang(Integer maHD, Integer mahDCT, Integer maSP, Integer soL, Double donG) {
        this.maHD = maHD;
        this.mahDCT = mahDCT;
        this.maSP = maSP;
        this.soL = soL;
        this.donG = donG;
    }

    public Integer getMaHD() {
        return maHD;
    }

    public void setMaHD(Integer maHD) {
        this.maHD = maHD;
    }

    public Integer getMahDCT() {
        return mahDCT;
    }

    public void setMahDCT(Integer mahDCT) {
        this.mahDCT = mahDCT;
    }

    public Integer getMaSP() {
        return maSP;
    }

    public void setMaSP(Integer maSP) {
        this.maSP = maSP;
    }

    public Integer getSoL() {
        return soL;
    }

    public void setSoL(Integer soL) {
        this.soL = soL;
    }

    public Double getDonG() {
        return donG;
    }

    public void setDonG(Double donG) {
        this.donG = donG;
    }
    
    
    
}
