/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import Models.NhanSu;
import Models.TaiKhoan;
import Models.User;
import Repositories.Huong_TaiKhoanRepository;
import Repositories.Hoa_NhanSuRepo;
import Repositories.UserRepository;
import ViewModels.UserViewModel;
import java.util.ArrayList;

/**
 *
 * @author X1
 */
public class UserService {
    UserRepository userRepo = new UserRepository();
    Huong_TaiKhoanRepository taiKhoanRepository = new Huong_TaiKhoanRepository();
    Hoa_NhanSuRepo nsRepo = new Hoa_NhanSuRepo();
    
    public String getName(String tenDN) {
        return taiKhoanRepository.getName(tenDN);
    }
    public String getTenDN(String tenDN) {
        return taiKhoanRepository.getTenDN(tenDN);
    }
    public ArrayList<UserViewModel> filterByRoleAndGender(String vaiT, String gioiT){
        return userRepo.filterByRoleAndGender(vaiT, gioiT);
    }
    
    public String delete(String maND){
        Boolean check = userRepo.delete(maND);
        if(check){
            return "Xóa thành công";
        }else{
            return "Xóa thất bại";
        }
    }
    
    public String update(User u){
        Boolean kq = userRepo.update(u);
        if ( kq == true) {
            return "Sửa thành công";
        } else {
            return "Sửa thất bai";
        }
    }
    public String updateMatKhau(User u){
        Boolean kq = userRepo.updateMatKhau(u);
        if ( kq == true) {
            return "Sửa thành công";
        } else {
            return "Sửa thất bai";
        }
    }
    
    public Boolean checkLogin(String userID, String passCode){
        return userRepo.checkLogin(userID, passCode);
    }
            
    //GETLIST
    public ArrayList<UserViewModel> getList(){
        return userRepo.getList();
    }
    
    public NhanSu getAlLNs() {
        return nsRepo.getAll();
    }
    
    public ArrayList<NhanSu> getAll(){
        return userRepo.getAll();
    }
    public UserViewModel getListById(Integer id){
        return userRepo.getListById(id);
    }
    //GETLIST BY SEARCH
    public ArrayList<UserViewModel> getListByTen(String name,String tendn){
        return userRepo.getListByTen(name,tendn);
    }
    //GETLIST USER
    public ArrayList<User> getListUser(){
        return userRepo.getListUser();
    }
    public User getListByUserId(String userId){
        return userRepo.getListByUserId(userId);
    }
    
    //HIDE
    public void hideAccount(User u){
        userRepo.hideAccount(u);
    }
    
    
    
    //CHECK ID
    public Boolean checkName(String name){
        return userRepo.checkName(name);
    }
    
    //ADD
    public String addAccount(User u){
        Boolean check = userRepo.addAccount(u);
        if (check) {
            return "Thêm tài khoản mới thành công ~uwu~";
        }else{
            return "Thêm tài khoản mới thất bại uhu";
        }
    }
    //UPDATE
    public String updateAccount(TaiKhoan tk){
        Boolean check = userRepo.updateAccount(tk);
        if (check) {
            return "Sửa thông tin tài khoản thành công ~uwu~";
        }else{
            return "Sửa thông tin tài khoản thất bại uhu";
        }
    }
}
