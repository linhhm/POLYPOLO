/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author X1
 */
public class ThuocTinh {
    private Integer maThuocTinh;
    private String tenThuocTinh;
    private String tenMau;

    public String getTenMau() {
        return tenMau;
    }

    public void setTenMau(String tenMau) {
        this.tenMau = tenMau;
    }

    public ThuocTinh(Integer maThuocTinh, String tenMau, String tenThuocTinh) {
        this.maThuocTinh = maThuocTinh;
        this.tenMau = tenMau;
        this.tenThuocTinh = tenThuocTinh;
    }


    public ThuocTinh() {
    }

    public Integer getMaThuocTinh() {
        return maThuocTinh;
    }

    public void setMaThuocTinh(Integer maThuocTinh) {
        this.maThuocTinh = maThuocTinh;
    }

    public String getTenThuocTinh() {
        return tenThuocTinh;
    }

    public void setTenThuocTinh(String tenThuocTinh) {
        this.tenThuocTinh = tenThuocTinh;
    }
    
}
