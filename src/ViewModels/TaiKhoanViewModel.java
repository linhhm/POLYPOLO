/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ViewModels;

/**
 *
 * @author Admin
 */
public class TaiKhoanViewModel {
    
    private Integer maNV;
    private String tenDN;
    private String mK;
    private String hoTen;
    private String gioiTinh;
    private String vaiTro;
    private String sDT;
    private String diaChi;

    public TaiKhoanViewModel() {
    }

    public TaiKhoanViewModel(Integer maNV) {
        this.maNV = maNV;
    }

    public TaiKhoanViewModel(Integer maNV, String tenDN, String mK, String hoTen, String gioiTinh, String sDT, String diaChi) {
        this.maNV = maNV;
        this.tenDN = tenDN;
        this.mK = mK;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.sDT = sDT;
        this.diaChi = diaChi;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }
    

    public Integer getMaNV() {
        return maNV;
    }

    public void setMaNV(Integer maNV) {
        this.maNV = maNV;
    }

    public String getTenDN() {
        return tenDN;
    }

    public void setTenDN(String tenDN) {
        this.tenDN = tenDN;
    }

    public String getmK() {
        return mK;
    }

    public void setmK(String mK) {
        this.mK = mK;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getsDT() {
        return sDT;
    }

    public void setsDT(String sDT) {
        this.sDT = sDT;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    @Override
    public String toString() {
        return "TaiKhoanViewModel{" + "maNV=" + maNV + '}';
    }
    
    
    

    
}
