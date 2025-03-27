/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ViewModels;

/**
 *
 * @author X1
 */
public class SanPhamViewModel {
    private Integer maSP,maSPCT;
    private String tenSP;
    private String tenDM;
    private String mauSac;
    private String kichCo;
    private Double giaNhap;
    private Double giaBan;
    private String trangThai;
    private Integer soLuong;

    public SanPhamViewModel(String tenSP, String tenDM, String mauSac, String kichCo, Double giaNhap, Double giaBan, String trangThai, Integer soLuong) {
        this.tenSP = tenSP;
        this.tenDM = tenDM;
        this.mauSac = mauSac;
        this.kichCo = kichCo;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.trangThai = trangThai;
        this.soLuong = soLuong;
    }

    public Integer getMaSPCT() {
        return maSPCT;
    }

    public void setMaSPCT(Integer maSPCT) {
        this.maSPCT = maSPCT;
    }
    


    public SanPhamViewModel() {
    }

    public SanPhamViewModel(Integer maSP, Integer maSPCT, String tenSP, String tenDM, String mauSac, String kichCo, Double giaNhap, Double giaBan, String trangThai, Integer soLuong) {
        this.maSP = maSP;
        this.maSPCT = maSPCT;
        this.tenSP = tenSP;
        this.tenDM = tenDM;
        this.mauSac = mauSac;
        this.kichCo = kichCo;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.trangThai = trangThai;
        this.soLuong = soLuong;
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
