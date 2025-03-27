/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories;

import ViewModels.HD_GioHangViewModel;
import Models.HoaDon;
import Models.HoaDonChiTiet;
import Models.MyReceipts;
import ViewModels.HD_HoaDonViewModel;
import ViewModels.HD_InvoiceViewModel;
import ViewModels.HD_SanPhamViewModel;
import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author X1
 */
public class HoaDonRepository {
    DbConnection dbConnection;
    
    //IMPORT EXCEL
    public ArrayList<MyReceipts> getMyReceipts(){
        String sql = "SELECT hd.MaHoaDon, hd.TenKhachHang, hd.LoaiKhachHang, SUM(hdct.SoLuong) AS SoL, hd.TongTien, hd.PhuongThucThanhToan, hd.TenNhanVien, hd.TrangThai, hd.NgayLap\n" +
                        "FROM HoaDon hd INNER JOIN HoaDonChiTiet hdct ON hd.MaHoaDon = hdct.MaHoaDon \n" +
                        "GROUP BY hd.MaHoaDon, hd.TenKhachHang, hd.LoaiKhachHang, hd.TongTien, hd.PhuongThucThanhToan, hd.TenNhanVien, hd.TrangThai, hd.NgayLap \n" +
                        "ORDER BY hd.NgayLap";
        ArrayList<MyReceipts> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer maHD = rs.getInt("MaHoaDon");
                String tenK = rs.getString("TenKhachHang");
                String loaiK = rs.getString("LoaiKhachHang");
                Integer soL = rs.getInt("SoL");
                Double tongT = rs.getDouble("TongTien");
                String phuongT = rs.getString("PhuongThucThanhToan");
                String tenN = rs.getString("TenNhanVien");
                String trangT = rs.getString("TrangThai");
                Date ngayL = rs.getDate("NgayLap");
                
                MyReceipts mS = new MyReceipts(maHD, soL, tenK, phuongT, trangT, loaiK, tongT, ngayL, tenN);
                ls.add(mS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;  
    }
    //INVOICE
    public ArrayList<HD_InvoiceViewModel> getListKHById(Integer id){
        String sql = "SELECT hd.MaHoaDon, hd.TenNhanVien, hd.TenKhachHang, hd.PhuongThucThanhToan, kh.SoDienThoai, kh.DiaChi, hd.LoaiKhachHang FROM HoaDon hd INNER JOIN KhachHang kh ON Hd.MaHoaDon = kh.MaHoaDon \n" +
                        "WHERE kh.Deleted!=1 AND hd.Deleted!=1 AND kh.MaHoaDon = ?";
        ArrayList<HD_InvoiceViewModel> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, id);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer maHD = rs.getInt("MaHoaDon");
                String tenNV = rs.getString("TenNhanVien");
                String tenKH = rs.getString("TenKhachHang");
                String phuongThuc = rs.getString("PhuongThucThanhToan");
                String soDT = rs.getString("SoDienThoai");
                String diaC = rs.getString("DiaChi");
                String loaiK = rs.getString("LoaiKhachHang");
                
                HD_InvoiceViewModel listKH = new HD_InvoiceViewModel(maHD, tenNV, tenKH, soDT, diaC, phuongThuc,loaiK);
                ls.add(listKH);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    public ArrayList<HD_GioHangViewModel> printInvoiceById(Integer id) {
        String sql = "SELECT spct.MaSanPham, spct.TenSanPhamChiTiet\n" +
"                , ms.TenMau, s.TenSize, hdct.SoLuong, hdct.DonGia, SUM(hdct.DonGia * hdct.SoLuong) AS 'Total' FROM DanhMuc dm\n" +
"                INNER JOIN SanPham sp ON dm.MaDanhMuc = sp.MaDanhMuc\n" +
"                INNER JOIN SanPhamChiTiet spct ON spct.MaSanPham = sp.MaSanPham\n" +
"                INNER JOIN MauSac ms ON ms.MaMau = spct.MaMau\n" +
"                INNER JOIN Size s ON s.MaSize = spct.MaSize\n" +
"                INNER JOIN HoaDonChiTiet hdct ON hdct.MaSanPhamChiTiet = spct.MaSanPhamChiTiet\n" +
"                INNER JOIN HoaDon hd ON hd.MaHoaDon = hdct.MaHoaDon\n"
                + "WHERE hd.MaHoaDon = ?"
                + "				GROUP BY spct.MaSanPham, spct.TenSanPhamChiTiet\n"
                + "                , ms.TenMau, s.TenSize, hdct.SoLuong, hdct.DonGia\n"
                + "                ";
        ArrayList<HD_GioHangViewModel> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, id);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer maSP = rs.getInt("MaSanPham");
                String tenSP = rs.getString("TenSanPhamChiTiet");
                String tenMau = rs.getString("TenMau");
                String tenSz = rs.getString("TenSize");
                Integer soL = rs.getInt("SoLuong");
                Double donG = rs.getDouble("DonGia");
                Double total = rs.getDouble("Total");
                
                HD_GioHangViewModel invoice = new HD_GioHangViewModel(maSP, tenSP, tenMau, tenSz, soL, donG, total);
                ls.add(invoice);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    public HD_GioHangViewModel updateSoLuongSP(Integer maHD){
        String sql = "UPDATE HoaDonChiTiet \n" +
                    "SET SoLuong = 1 + SoLuong\n" +
                    "WHERE MaHoaDon = ?";
        HD_GioHangViewModel gh = new HD_GioHangViewModel();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, maHD);
            
            gh = new HD_GioHangViewModel(maHD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gh;
    }
    //EMPTY BASKET
    public Boolean deleteProduct(int maHD) {
        String sql = "delete HoaDonChiTiet where MaHoaDonChiTiet = ?";

        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, maHD);
        

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    //SEARCH CUSTOMER
    public ArrayList<HD_HoaDonViewModel> searchCustomer(String tel){
        String sql = "SELECT hd.MaHoaDon, hd.TenNhanVien, hd.TenKhachHang, kh.SoDienThoai, hd.PhuongThucThanhToan, hd.NgayLap, hd.TrangThai, hd.TongTien FROM HoaDon hd\n" +
                        "LEFT JOIN KhachHang kh ON kh.MaHoaDon = hd.MaHoaDon\n" +
                        "LEFT JOIN NhanVien nv ON nv.MaNhanVien = hd.MaNhanVien\n" +
                        "WHERE hd.Deleted !=1 AND kh.SoDienThoai = ?";
        ArrayList<HD_HoaDonViewModel> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, tel);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer maHD = rs.getInt("MaHoaDon");
                String tenNV = rs.getString("TenNhanVien");
                String tenKH = rs.getString("TenKhachHang");
                String soDT = rs.getString("SoDienThoai");
                String pTTT = rs.getString("PhuongThucThanhToan");
                Date ngayLap = rs.getDate("NgayLap");
                String trangThai = rs.getString("TrangThai");
                Double tongT = rs.getDouble("TongTien");
                
                HD_HoaDonViewModel hd = new HD_HoaDonViewModel(maHD, tenKH, soDT, pTTT, trangThai, tongT, ngayLap, tenNV);
                ls.add(hd); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    //GET ALL
    public ArrayList<HoaDon> getList() {
        String sql = "SELECT * FROM HoaDon WHERE Deleted !=1";
        ArrayList<HoaDon> lsTrangThai = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maHD = rs.getInt("MaHoaDon");
                String tenNV = rs.getString("TenNhanVien");
                String tenKH = rs.getString("TenKhachHang");
                String phuongThuc = rs.getString("PhuongThucThanhToan");
                Date ngayLap = rs.getDate("NgayLap");
                Double tongTien = rs.getDouble("TongTien");
                String trangThai = rs.getString("TrangThai");
                HoaDon hoaDon = new HoaDon(maHD, null, tenNV, tenKH, phuongThuc, tongTien, ngayLap, trangThai);
                lsTrangThai.add(hoaDon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lsTrangThai;
    }
    
    //GET LIST HOADON
    public ArrayList<HD_HoaDonViewModel> getListHoaDon(){
        String sql = "select hd.MaHoaDon, hd.TenNhanVien, hd.TenKhachHang, kh.SoDienThoai, hd.PhuongThucThanhToan, hd.TongTien, hd.NgayLap, hd.TrangThai from HoaDon hd\n" +
"                    left JOIN KhachHang kh ON kh.MaHoaDon = hd.MaHoaDon\n" +
"                    left JOIN NhanVien nv ON nv.MaNhanVien = hd.MaNhanVien\n";
        ArrayList<HD_HoaDonViewModel> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maHD = rs.getInt("MaHoaDon");
                String tenNV = rs.getString("TenNhanVien");
                String tenKH = rs.getString("TenKhachHang");
                String soDT = rs.getString("SoDienThoai");
                String phuongThuc = rs.getString("PhuongThucThanhToan");
                Double tongTien = rs.getDouble("TongTien");
                Date ngayLap = rs.getDate("NgayLap");
                String trangT = rs.getString("TrangThai");
                
                HD_HoaDonViewModel hd = new HD_HoaDonViewModel(maHD, tenKH, soDT, phuongThuc, trangT, tongTien, ngayLap, tenNV);
                ls.add(hd); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }  
    ////GETLIST BY TRANGTHAI
    public ArrayList<HD_HoaDonViewModel> getListByTrangThai(String trangThai) {
        String sql = "select hd.MaHoaDon, hd.TenNhanVien, hd.TenKhachHang, kh.SoDienThoai, hd.PhuongThucThanhToan, hd.TongTien, hd.NgayLap, hd.TrangThai from HoaDon hd\n" +
                    "left JOIN KhachHang kh ON kh.MaHoaDon = hd.MaHoaDon\n" +
                    "left JOIN NhanVien nv ON nv.MaNhanVien = hd.MaNhanVien\n" +
                    "where TrangThai = ?";
        ArrayList<HD_HoaDonViewModel> lsHoaDon = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setString(1, trangThai);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maHD = rs.getInt("MaHoaDon");
                String tenNV = rs.getString("TenNhanVien");
                String tenKH = rs.getString("TenKhachHang");
                String soDT = rs.getString("SoDienThoai");
                String phuongThuc = rs.getString("PhuongThucThanhToan");
                Double tongTien = rs.getDouble("TongTien");
                Date ngayLap = rs.getDate("NgayLap");
                String trangT = rs.getString("TrangThai");
                
                HD_HoaDonViewModel hd = new HD_HoaDonViewModel(maHD, tenKH, soDT, phuongThuc, trangT, tongTien, ngayLap, tenNV);
                lsHoaDon.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lsHoaDon;
    }

    //LOAD DATA TO FORM ẠAAAAAAA
    public HD_HoaDonViewModel getListHDById(Integer id) {
        String sql = "SELECT hd.MaHoaDon, hd.TenNhanVien, hd.TenKhachHang, kh.SoDienThoai, hd.PhuongThucThanhToan, hd.NgayLap FROM HoaDon hd\n"
                + "LEFT JOIN KhachHang kh ON kh.MaHoaDon = hd.MaHoaDon\n"
                + "LEFT JOIN NhanVien nv ON nv.MaNhanVien = hd.MaNhanVien\n"
                + "WHERE hd.MaHoaDon = ?";
        //    ArrayList<HoaDonViewModel> lsHoaDon = new ArrayList<>();
        HD_HoaDonViewModel hd = new HD_HoaDonViewModel();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maHD = rs.getInt("MaHoaDon");
                String tenNV = rs.getString("TenNhanVien");
                String tenKH = rs.getString("TenKhachHang");
                String soDT = rs.getString("SoDienThoai");
                String phuongThuc = rs.getString("PhuongThucThanhToan");
                Date ngayLap = rs.getDate("NgayLap");

                hd = new HD_HoaDonViewModel(maHD, tenKH, soDT, phuongThuc, ngayLap, tenNV);
                //            lsHoaDon.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //    return lsHoaDon;
        return hd;
    }
    
    //UPDATE LBL 
    public HoaDon getTotal(Integer id){
        String sql = "SELECT SUM(hdct.DonGia*hdct.SoLuong) as 'Total' FROM HoaDonChiTiet hdct\n" +
                    "LEFT JOIN HoaDon hd\n" +
                    "ON hd.MaHoaDon = hdct.MaHoaDon\n" +
                    "WHERE hd.Deleted !=1 AND hd.MaHoaDon = ?";
        HoaDon hd = null;
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, id);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                hd = new HoaDon(rs.getDouble(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hd;
    }
    //////////////////SANPHAM
    public ArrayList<HD_SanPhamViewModel> getListByDanhMuc(String dm) {
        String sql = "SELECT spct.MaSanPhamChiTiet, spct.TenSanPhamChiTiet, dm.TenDanhMuc\n" +
"                , ms.TenMau, s.TenSize\n" +
"                , sp.GiaBan, spct.SoLuongTon, spct.TrangThai FROM SanPham sp\n" +
"                INNER JOIN DanhMuc dm ON dm.MaDanhMuc = sp.MaDanhMuc\n" +
"                INNER JOIN SanPhamChiTiet spct ON spct.MaSanPham = sp.MaSanPham\n" +
"                INNER JOIN MauSac ms ON ms.MaMau = spct.MaMau\n" +
"                INNER JOIN Size s ON s.MaSize = spct.MaSize\n" +
"		 WHERE sp.Deleted!=1 AND dm.TenDanhMuc = ?";
        ArrayList<HD_SanPhamViewModel> lsSanPham = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, dm);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maSPCT = rs.getInt("MaSanPhamChiTiet");
                String tenSP = rs.getString("TenSanPhamChiTiet");
                String tenDM = rs.getString("TenDanhMuc");
                String tenMau = rs.getString("TenMau");
                String kichCo = rs.getString("TenSize");
                Double donGia = rs.getDouble("GiaBan");
                Integer soLuong = rs.getInt("SoLuongTon");
                String trangThai = rs.getString("TrangThai");

                HD_SanPhamViewModel sanPhamView = new HD_SanPhamViewModel(maSPCT, tenSP, tenDM, tenMau, kichCo, donGia, soLuong, trangThai);
                lsSanPham.add(sanPhamView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lsSanPham;
    }
    public ArrayList<HD_SanPhamViewModel> getListSanPham() {
        String sql = "SELECT spct.MaSanPhamChiTiet, spct.TenSanPhamChiTiet, dm.TenDanhMuc\n" +
"                , ms.TenMau, s.TenSize\n" +
"                , sp.GiaBan, spct.SoLuongTon, spct.TrangThai FROM SanPham sp\n" +
"                INNER JOIN DanhMuc dm ON dm.MaDanhMuc = sp.MaDanhMuc\n" +
"                INNER JOIN SanPhamChiTiet spct ON spct.MaSanPham = sp.MaSanPham\n" +
"                INNER JOIN MauSac ms ON ms.MaMau = spct.MaMau\n" +
"                INNER JOIN Size s ON s.MaSize = spct.MaSize\n" +
"				WHERE sp.Deleted!=1";
        ArrayList<HD_SanPhamViewModel> lsSanPham = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maSPCT = rs.getInt("MaSanPhamChiTiet");
                String tenSP = rs.getString("TenSanPhamChiTiet");
                String tenDM = rs.getString("TenDanhMuc");
                String tenMau = rs.getString("TenMau");
                String kichCo = rs.getString("TenSize");
                Double donGia = rs.getDouble("GiaBan");
                Integer soLuong = rs.getInt("SoLuongTon");
                String trangThai = rs.getString("TrangThai");

                HD_SanPhamViewModel sanPhamView = new HD_SanPhamViewModel(maSPCT, tenSP, tenDM, tenMau, kichCo, donGia, soLuong, trangThai);
                lsSanPham.add(sanPhamView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lsSanPham;
    }
    public Boolean updateSP(int soL, int id) {
        String sql = " UPDATE SanPhamChiTiet SET SoLuongTon = ?"
                + "WHERE MaSanPhamChiTiet =?";

        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, soL);
            ps.setObject(2, id);

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    //FIX
    public Boolean mergeSP(int soL, int maHD, int maSPCT){
        String sql = "UPDATE HoaDonChiTiet\n" +
                        "SET SoLuong = SoLuong + ? \n" +
                        "WHERE MaHoaDon = ? AND MaSanPhamChiTiet = ?";
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, soL);
            ps.setObject(2, maHD);
            ps.setObject(3, maSPCT);
            
            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public Boolean checkSPExists(int maHD, int maSPCT){
        String sql = "SELECT COUNT(*) FROM HoaDonChiTiet\n" +
                    "WHERE MaHoaDon = ? AND MaSanPhamChiTiet = ?";
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, maHD);
            ps.setObject(2, maSPCT);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;     
    }
    
    //GETLIST SP BY ID
    public ArrayList<HD_SanPhamViewModel> getListById(Integer id){
        String sql ="SELECT spct.MaSanPham, spct.TenSanPhamChiTiet, dm.TenDanhMuc\n" +
"                , ms.TenMau, s.TenSize\n" +
"                , sp.GiaBan, spct.SoLuongTon, spct.TrangThai FROM SanPham sp\n" +
"                INNER JOIN DanhMuc dm ON dm.MaDanhMuc = sp.MaDanhMuc\n" +
"                INNER JOIN SanPhamChiTiet spct ON spct.MaSanPham = sp.MaSanPham\n" +
"                INNER JOIN MauSac ms ON ms.MaMau = spct.MaMau\n" +
"                INNER JOIN Size s ON s.MaSize = spct.MaSize \n" +
"                WHERE spct.MaSanPhamChiTiet = ?";
        ArrayList<HD_SanPhamViewModel> lsSanPham = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, id);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maSPCT = rs.getInt("MaSanPham");
                String tenSP = rs.getString("TenSanPhamChiTiet");
                String tenDM = rs.getString("TenDanhMuc");
                String tenMau = rs.getString("TenMau");
                String kichCo = rs.getString("TenSize");
                Double donGia = rs.getDouble("GiaBan");
                Integer soLuong = rs.getInt("SoLuongTon");
                String trangThai = rs.getString("TrangThai");

                HD_SanPhamViewModel sanPhamView = new HD_SanPhamViewModel(maSPCT, tenSP, tenDM, tenMau, kichCo, donGia, soLuong, trangThai);
                lsSanPham.add(sanPhamView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lsSanPham;
    }
    //GETLIST SP BY SEARCH
    public ArrayList<HD_SanPhamViewModel> getListSearchByName(String name) {
        String sql = "SELECT spct.MaSanPham, spct.TenSanPhamChiTiet, dm.TenDanhMuc\n"
                + ", ms.TenMau, s.TenSize\n"
                + ", sp.GiaBan, spct.SoLuongTon, spct.TrangThai FROM SanPham sp\n"
                + "INNER JOIN DanhMuc dm ON dm.MaDanhMuc = sp.MaDanhMuc\n"
                + "INNER JOIN SanPhamChiTiet spct ON spct.MaSanPham = sp.MaSanPham\n"
                + "INNER JOIN MauSac ms ON ms.MaMau = spct.MaMau\n"
                + "INNER JOIN Size s ON s.MaSize = spct.MaSize "
                + "WHERE spct.TenSanPhamChiTiet LIKE N'%"+name+"%'";
        ArrayList<HD_SanPhamViewModel> lsSanPham = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maSPCT = rs.getInt("MaSanPham");
                String tenSP = rs.getString("TenSanPhamChiTiet");
                String tenDM = rs.getString("TenDanhMuc");
                String tenMau = rs.getString("TenMau");
                String kichCo = rs.getString("TenSize");
                Double donGia = rs.getDouble("GiaBan");
                Integer soLuong = rs.getInt("SoLuongTon");
                String trangThai = rs.getString("TrangThai");

                HD_SanPhamViewModel sanPhamView = new HD_SanPhamViewModel(maSPCT, tenSP, tenDM, tenMau, kichCo, donGia, soLuong, trangThai);
                lsSanPham.add(sanPhamView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lsSanPham;
    }

    //ADD HDCT
    public Boolean addHDCT(HoaDonChiTiet hdct) {
        String sql = "INSERT INTO HoaDonChiTiet (MaHoaDon, MaSanPhamChiTiet, SoLuong, DonGia)\n"
                + "					VALUES (?, ?, ?, ?)";

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, hdct.getMaHD());
            ps.setObject(2, hdct.getMaSPCT());
            ps.setObject(3, hdct.getSoLuong());
            ps.setObject(4, hdct.getDonGia());

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean thanhToan(HoaDon hd) {
        String sql = "UPDATE HoaDon SET TrangThai = N'Đã thanh toán' WHERE MaHoaDon = ?";

        try (Connection conn = dbConnection.getConnection(); 
                PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, hd.getMaHD());
            
            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //UPDATE
    public Boolean updateHDCT(int soL, double gia, int id) {
        String sql = "UPDATE HoaDonChiTiet\n"
                + "SET SoLuong = ?, DonGia = ? WHERE MaHoaDon = ?";

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, soL);
            ps.setObject(2, gia);
            ps.setObject(3, id);

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    //GETLIST HDCT
    public ArrayList<HD_GioHangViewModel> getListGioHangById(Integer id) {
        String sql = "SELECT hdct.MaHoaDonChiTiet, spct.MaSanPham, spct.TenSanPhamChiTiet\n"
                + ", ms.TenMau, s.TenSize, hdct.SoLuong, hdct.DonGia, hd.TongTien FROM DanhMuc dm\n"
                + "INNER JOIN SanPham sp ON dm.MaDanhMuc = sp.MaDanhMuc\n"
                + "INNER JOIN SanPhamChiTiet spct ON spct.MaSanPham = sp.MaSanPham\n"
                + "INNER JOIN MauSac ms ON ms.MaMau = spct.MaMau\n"
                + "INNER JOIN Size s ON s.MaSize = spct.MaSize\n"
                + "INNER JOIN HoaDonChiTiet hdct ON hdct.MaSanPhamChiTiet = spct.MaSanPhamChiTiet\n"
                + "INNER JOIN HoaDon hd ON hd.MaHoaDon = hdct.MaHoaDon\n"
                + "WHERE hd.MaHoaDon = ?";
        ArrayList<HD_GioHangViewModel> ls = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maHDCT = rs.getInt("MaHoaDonChiTiet");
                Integer maSP = rs.getInt("MaSanPham");
                String tenSP = rs.getString("TenSanPhamChiTiet");
                String tenMau = rs.getString("TenMau");
                String tenSz = rs.getString("TenSize");
                Integer soL = rs.getInt("SoLuong");
                Double donG = rs.getDouble("DonGia");
                Double tengT = rs.getDouble("TongTien");

                HD_GioHangViewModel gh = new HD_GioHangViewModel(maHDCT, maSP, tenSP, tenMau, tenSz, soL, donG, tengT);
                ls.add(gh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }

    //GETLIST BY ID HOADON_ent
    public HoaDon getListByID(Integer id) {
        String sql = "SELECT * FROM HoaDon WHERE MaHoaDon = ?";
        HoaDon hoaDon = new HoaDon();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maHD = rs.getInt("MaHoaDon");
                Integer maNV = rs.getInt("MaNhanVien");
                String tenKH = rs.getString("TenKhachHang");
                String tenNV = rs.getString("TenNhanVien");
                Double tongTien = rs.getDouble("TongTien");
                Date ngayLap = rs.getDate("NgayLap");
                String trangThai = rs.getString("TrangThai");
                String phuongThuc = rs.getString("PhuongThucThanhToan");

                hoaDon = new HoaDon(maHD, maNV, tenNV, tenKH, phuongThuc, tongTien, ngayLap, trangThai);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hoaDon;
    }
    //ADD HOADON
    public Boolean addHoaDon(HoaDon hoaDon) {
        String sql = "INSERT INTO HoaDon (MaNhanVien, TenKhachHang, TenNhanVien, TongTien, NgayLap, PhuongThucThanhToan, TrangThai,LoaiKhachHang,Deleted)\n"
                + "VALUES (?, ?, ?, 0, ?, ?, N'Chưa thanh toán',N'Khách Lẻ',0);";

        try (Connection conn = dbConnection.getConnection(); 
                PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, hoaDon.getMaNV());
            ps.setObject(2, hoaDon.getTenKH());
            ps.setObject(3, hoaDon.getTenNV());
            ps.setObject(4, hoaDon.getNgayLap());
            ps.setObject(5, hoaDon.getPhuongThuc());

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
