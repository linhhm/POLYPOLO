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
public class UserViewModel {
    private Integer maNV;
    private Integer id;
    private String tenDN;
    private String tenNV;
    private String matKhau;
    private String soDT;
    private String vaiTro;
    private String diaC;
    private String gioiT;
    private Date ngayS;

    public UserViewModel() {
    }

    public UserViewModel(Integer maNV, Integer id, String tenDN, String tenNV, String matKhau, String soDT, String vaiTro, String diaC, String gioiT, Date ngayS) {
        this.maNV = maNV;
        this.id = id;
        this.tenDN = tenDN;
        this.tenNV = tenNV;
        this.matKhau = matKhau;
        this.soDT = soDT;
        this.vaiTro = vaiTro;
        this.diaC = diaC;
        this.gioiT = gioiT;
        this.ngayS = ngayS;
    }

    public UserViewModel(Integer maNV, Integer id, String tenDN, String tenNV, String matKhau, String soDT, String vaiTro, String diaC, String gioiT) {
        this.maNV = maNV;
        this.id = id;
        this.tenDN = tenDN;
        this.tenNV = tenNV;
        this.matKhau = matKhau;
        this.soDT = soDT;
        this.vaiTro = vaiTro;
        this.diaC = diaC;
        this.gioiT = gioiT;
    }
    
    public UserViewModel(Integer id, String tenDN, String tenNV, String matKhau, String soDT, String vaiTro) {
        this.id = id;
        this.tenDN = tenDN;
        this.tenNV = tenNV;
        this.matKhau = matKhau;
        this.soDT = soDT;
        this.vaiTro = vaiTro;
    }
    
    //THÊM CONSTRUCTOR  FULL
    public UserViewModel(Integer id, String tenDN, String tenNV, String matKhau, String soDT, String vaiTro, String diaC, String gioiT, Date ngayS) {
        this.id = id;
        this.tenDN = tenDN;
        this.tenNV = tenNV;
        this.matKhau = matKhau;
        this.soDT = soDT;
        this.vaiTro = vaiTro;
        this.diaC = diaC;
        this.gioiT = gioiT;
        this.ngayS = ngayS;
    }

    public Integer getMaNV() {
        return maNV;
    }

//    public UserViewModel(Integer id, String tenDN, String tenNV, String vaiTro, String matKhau, String soDT) {
//        this.id = id;
//        this.tenDN = tenDN;
//        this.tenNV = tenNV;
//        this.vaiTro = vaiTro;
//        this.matKhau = matKhau;
//        this.soDT = soDT;
//    }
    public void setMaNV(Integer maNV) {
        this.maNV = maNV;
    }

    public String getGioiT() {
        return gioiT;
    }

    public Date getNgayS() {
        return ngayS;
    }

    public void setNgayS(Date ngayS) {
        this.ngayS = ngayS;
    }

    public void setGioiT(String gioiT) {
        this.gioiT = gioiT;
    }

    public String getDiaC() {
        return diaC;
    }

    public void setDiaC(String diaC) {
        this.diaC = diaC;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTenDN() {
        return tenDN;
    }

    public void setTenDN(String tenDN) {
        this.tenDN = tenDN;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    @Override
    public String toString() {
        return "UserViewModel{" + "maNV=" + maNV + ", id=" + id + ", tenDN=" + tenDN + ", tenNV=" + tenNV + ", matKhau=" + matKhau + ", soDT=" + soDT + ", vaiTro=" + vaiTro + ", diaC=" + diaC + ", gioiT=" + gioiT + '}';
    }



}
/*
Thêm dòng 14 + get/setter; dòng 28,41
*/
