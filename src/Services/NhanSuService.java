/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import Models.User;
import Models.NhanSu;
import Repositories.Hoa_NhanSuRepo;
import Repositories.Huong_TaiKhoanRepository;
import ViewModels.NhanSuViewModel;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class NhanSuService {

    Hoa_NhanSuRepo spr = new Hoa_NhanSuRepo();
    Huong_TaiKhoanRepository kmr = new Huong_TaiKhoanRepository();

    //GETLIST BY ID
    public NhanSuViewModel getListById(Integer id) {
        return spr.getListById(id);
    }

    public ArrayList<NhanSuViewModel> getList() {
        return spr.getList();
    }

    public ArrayList<User> getListGV() {
        return kmr.getListGV();
    }

    public ArrayList<NhanSuViewModel> SearchByName(String name) {
        return spr.searchByName(name);
    }

    public User getByID(String tenGV) {
        return kmr.findID(tenGV);
    }

    public String AddNew(NhanSu sp) {
        Boolean check = spr.AddNew(sp);
        if (check == true) {
            return "Them Thanh Cong";
        } else {
            return "Them That Bai";
        }
    }

    public String delete(String maNV) {
        Boolean check = spr.delete(maNV);
        if (check) {
            return "Xóa thành công";
        } else {
            return "Xóa thất bại";
        }
    }

    public String updateNew(NhanSu ns) {
        Boolean check = spr.updateNew(ns);
        if (check) {
            return "Cập nhật thành công";
        } else {
            return "Cập nhật thất bại";
        }
    }

    public Boolean checkName(Integer maND) {
        return spr.checkName(maND);
    }

}
