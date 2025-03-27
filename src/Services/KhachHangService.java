/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import Models.KhachHang;
import Models.Linh_KhachHangViewBang2;
import Repositories.KhachHangRepository;
import ViewModels.KhachHangViewModel;
import java.util.ArrayList;

/**
 *
 * @author X1
 */
public class KhachHangService {
    KhachHangRepository kHRepo = new KhachHangRepository();
    
    public ArrayList<KhachHangViewModel> getList(){
        return kHRepo.getList();
    }
    public ArrayList<Linh_KhachHangViewBang2> getList_Bang2(Integer MaHD){
        return kHRepo.getList_Bang2(MaHD);
    }
    public ArrayList<KhachHangViewModel> getListSearch(String id){
        return kHRepo.getListSearch(id);
    }
    public ArrayList<KhachHangViewModel> getListSearchSDT(String sdt){
        return kHRepo.getListSearchSDT(sdt);
    }
    public KhachHangViewModel getLisstt(Integer id){
        return kHRepo.getListt(id);
    }
    public ArrayList<KhachHangViewModel> danhSchAn(){
        return kHRepo.DanhSachAn();
    }
    public boolean boAN(KhachHang kh){
        return kHRepo.boAn(kh);
    }
    public String addNew(KhachHang kh){
        boolean check = kHRepo.addNew(kh);
        if (check) {
            return "Thêm khách hàng thành công!";
        }else{
            return "Thêm khách hàng thất bại :(";
        }
    }
    public String update(KhachHang kh){
        boolean check = kHRepo.update(kh);
        if (check) {
            return "Cập nhật khách hàng thành công!";
        }else{
            return "Cập nhật khách hàng thất bại :(";
        }
    }
    public String XoaKH(Integer maKH){
        boolean check = kHRepo.XoaKH(maKH);
        if (check) {
            return "Xóa khách hàng thành công!";
        }else{
            return "Xóa khách hàng thất bại :(";
        }
    }
    
}
