/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ViewModels;
/**
 *
 * @author hmail
 */
public class ThongKeViewDoanhThu {
    private Integer thang;
    private Integer nam;
    private Integer soLuong;
    private Integer tongTien;
    private Integer ngay;
    private String tennv;
    private Integer loiNhuan;

    public ThongKeViewDoanhThu() {
    }

//    public ThongKeViewDoanhThu(Integer soLuong, Integer tongTien, Integer ngay) {
//        this.soLuong = soLuong;
//        this.tongTien = tongTien;
//        this.ngay = ngay;
//    }

    public ThongKeViewDoanhThu(Integer thang, Integer soLuong, Integer tongTien) {
        this.thang = thang;
        this.soLuong = soLuong;
        this.tongTien = tongTien;
    }

    public ThongKeViewDoanhThu(Integer thang, Integer nam, Integer soLuong, Integer tongTien, Integer ngay, String tennv) {
        this.thang = thang;
        this.nam = nam;
        this.soLuong = soLuong;
        this.tongTien = tongTien;
        this.ngay = ngay;
        this.tennv = tennv;
    }

    public ThongKeViewDoanhThu(Integer thang, Integer nam, Integer soLuong, Integer tongTien, Integer ngay, String tennv, Integer loiNhuan) {
        this.thang = thang;
        this.nam = nam;
        this.soLuong = soLuong;
        this.tongTien = tongTien;
        this.ngay = ngay;
        this.tennv = tennv;
        this.loiNhuan = loiNhuan;
    }

    public Integer getLoiNhuan() {
        return loiNhuan;
    }

    public void setLoiNhuan(Integer loiNhuan) {
        this.loiNhuan = loiNhuan;
    }

    
    
    public Integer getThang() {
        return thang;
    }

    public void setThang(Integer thang) {
        this.thang = thang;
    }

    public Integer getNam() {
        return nam;
    }

    public void setNam(Integer nam) {
        this.nam = nam;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public Integer getTongTien() {
        return tongTien;
    }

    public void setTongTien(Integer tongTien) {
        this.tongTien = tongTien;
    }

    public Integer getNgay() {
        return ngay;
    }

    public void setNgay(Integer ngay) {
        this.ngay = ngay;
    }

    public String getTennv() {
        return tennv;
    }

    public void setTennv(String tennv) {
        this.tennv = tennv;
    }

}
