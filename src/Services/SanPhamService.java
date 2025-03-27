/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import Models.DanhMuc;
import Models.KichCo;
import Models.MauSac;
import Models.SanPham;
import Models.SanPhamChiTiet;
import Repositories.DanhMucRepo;
import Repositories.KichCoRepo;
import Repositories.MauSacRepo;
import Repositories.SanPhamRepository;
import ViewModels.SanPhamViewModel;
import java.util.ArrayList;

/**
 *
 * @author X1
 */
public class SanPhamService {
    SanPhamRepository spRepo = new SanPhamRepository();
    DanhMucRepo dmRepo = new DanhMucRepo();
    MauSacRepo msRepo = new MauSacRepo();
    KichCoRepo szRepo = new KichCoRepo();
    
    //CHECK ID
    public Boolean checkId(Integer id) {
        return spRepo.checkId(id);
    }
    public Boolean checkName(String tenSP){
        return spRepo.checkName(tenSP);
    }
    public boolean checkIdCat(Integer id) {
        return dmRepo.checkIdCat(id);
    }
    public boolean checkIdColor(Integer id) {
        return msRepo.checkIdColor(id);
    }
    public boolean checkIdSz(Integer id) {
        return szRepo.checkIdSz(id);
    }
    
    //IMPORT
    public String addImport(SanPham sp){
       Boolean check = spRepo.addSP(sp);
        if (check) {
            return "Import data thành công!";
        }else{
            return "Import data thất bại!";
        }
    }
    
    //GETLIST
    public ArrayList<SanPham> getListSP(){
        return spRepo.getListSP();
    }
    public SanPhamViewModel getListById(Integer id){
        return spRepo.getListByID(id);
    }
    public ArrayList<SanPhamViewModel> getListSanPham(){
        return spRepo.getListSanPham();
    }
    public ArrayList<SanPhamViewModel> getListByDanhMuc(String danhMuc){
        return spRepo.getListByDanhMuc(danhMuc);
    }
    //UNHIDE
    public ArrayList<SanPhamViewModel> getListHide(){
        return spRepo.getListHideSP();
    }
    //SEARCH
    public ArrayList<SanPhamViewModel> getListBySearch(String name){
        return spRepo.getListBySearch(name);
    }
    
    //GET_CBO 
    public ArrayList<DanhMuc> getCboDM(){
        return dmRepo.getList();
    }
    public ArrayList<MauSac> getCboMau(){
        return msRepo.getList();
    }
    public ArrayList<KichCo> getCboSz(){
        return szRepo.getList();
    }
    
    //GETLIST TT
    public ArrayList<MauSac> getListHideTTMS(){
        return msRepo.getListHide();
    }
    public ArrayList<KichCo> getListHideTTSz(){
        return szRepo.getList();
    }
    public void hideSP(SanPham sp){
        spRepo.hideSP(sp);
    }
    public Boolean unhideSP(SanPham sp){
        return spRepo.unhideSP(sp);
    }
    
    //ACTION HIDE
    public void hidetTTMS(MauSac ms){
        msRepo.hideTTMS(ms);
    }
    public void hideTTSz(KichCo sz){
        szRepo.hideTTSz(sz);
    }
//    public Boolean unhideSP(MauSac ms){
//        return msRepo.unhideTTMS(ms);
//    }

    //GET_ID 
    public SanPham getId(String tenSP){
        return spRepo.getId(tenSP);
    }
    public MauSac getIdByName(String name){
        return msRepo.getIdByName(name);
    }
    public KichCo getIdByNamee(String name){
        return szRepo.getIdByName(name);
    }
    public DanhMuc getIdByNameee(String name){
        return dmRepo.getIdByName(name);
    }
    public SanPhamChiTiet getName(String name){
        return spRepo.getName(name);
    }

    //CRUD
    public String addSP(SanPham sp){
        Boolean check = spRepo.addSP(sp);
        if (check) {
            return "Thêm mới sản phẩm thành công!";
        }else{
            return "Thêm mới sản phẩm thất bại :(";
        }
    }
    public String updateSP(SanPham sp){
        Boolean check = spRepo.updateSP(sp);
        if (check) {
            return "Update thành công gòii~";
        }else{
            return "Update thất bại!!";
        }
    }
    
    //DANHMUC
    public ArrayList<DanhMuc> getList(){
        return dmRepo.getList();
    }
    public DanhMuc getListDMById(Integer id){
        return dmRepo.getListById(id);
    }
    public ArrayList<DanhMuc> getListDMByName(String name){
        return dmRepo.getListDMByName(name);
    }
    public String addDanhMuc(DanhMuc dm){
        Boolean check = dmRepo.addDanhMuc(dm);
        if (check) {
            return "Thêm danh mục mới thành công!";
        }else{
            return "Thêm danh mục mới thất bại :(";
        }
    }
    public String updateDanhMuc(DanhMuc dm){
        Boolean check = dmRepo.updateDanhMuc(dm);
        if (check) {
            return "Cập nhật danh mục thành công!";
        }else{
            return "Cập nhật danh mục thất bại :(";
        }
    }
    public String anDanhMuc(DanhMuc dm){
        Boolean check = dmRepo.anDanhMuc(dm);
        if (check) {
            return "Xóa danh mục thành công!";
        }else{
            return "Xóa danh mục thất bại :(";
        }
    }
    
    //THUOCTINH
    public ArrayList<MauSac> loadDataColor() {
        return msRepo.getList();
    }
    public ArrayList<KichCo> loadDataSz() {
        return szRepo.getList();
    }
    public ArrayList<MauSac> getListMS(){
        return msRepo.getList();
    }
    public ArrayList<KichCo> getListSz(){
        return szRepo.getList();
    }
    public String addColor(MauSac ms) {
        Boolean check = msRepo.addColor(ms);
        if (check) {
            return "Thêm thuộc tính màu sắc mới thành công!";
        } else {
            return "Thêm thuộc tính màu sắc mới thất bại :(";
        }
    }
    public String addSz(KichCo sz){
        Boolean check = szRepo.addSz(sz);
        if (check) {
            return "Thêm thuộc tính kích cỡ mới thành công!";
        }else{
            return "Thêm thuộc tính kích cỡ mới thất bại :(";
        }
    }
    public String updateCl(MauSac ms){
        Boolean check = msRepo.updateColor(ms);
        if (check) {
            return "Cập nhật thuộc tính màu sắc mới thành công!";
        }else{
            return "Cập nhật thuộc tính màu sắc mới thất bại :(";
        }
    }
    public String updateSz(KichCo sz){
        Boolean check = szRepo.updateSz(sz);
        if (check) {
            return "Cập nhật thuộc tính size mới thành công!";
        }else{
            return "Cập nhật thuộc tính size mới thất bại :(";
        }
    }
    
}
