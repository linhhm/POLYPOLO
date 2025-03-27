/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories;

import Models.KhachHang;
import Models.Linh_KhachHangViewBang2;
import ViewModels.KhachHangViewModel;
import java.util.ArrayList;
import java.sql.*;

/**
 *
 * @author X1
 */
public class KhachHangRepository {

    DbConnection dbConnection;

    //GETLIST
    public ArrayList<KhachHangViewModel> getList() {
        String sql = "select MaKhachHang, MaHoaDon, TenKhachHang, GioiTinh, SoDienThoai, DiaChi from KhachHang WHERE Deleted != 1;";
        ArrayList<KhachHangViewModel> ls = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maKH = rs.getInt("MaKhachHang");
                String tenKh = rs.getString("TenKhachHang");
                Integer maHD = rs.getInt("MaHoaDon");
                String gioiTinh = rs.getString("GioiTinh");
                String soDT = rs.getString("SoDienThoai");
                String diaChi = rs.getString("DiaChi");

                KhachHangViewModel kh = new KhachHangViewModel(maKH, tenKh, gioiTinh, soDT, diaChi, maHD);
                ls.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }

    ///TABLE HOADON
    public ArrayList<Linh_KhachHangViewBang2> getList_Bang2(Integer MaHD) {
        String sql = " SELECT HoaDon.MaHoaDon, SanPhamChiTiet.TenSanPhamChiTiet, KhachHang.TenKhachHang, SoLuong, DonGia, HoaDon.NgayLap\n"
                + "FROM HoaDonChiTiet\n"
                + "JOIN SanPhamChiTiet ON HoaDonChiTiet.MaSanPhamChiTiet = SanPhamChiTiet.MaSanPhamChiTiet\n"
                + "JOIN HoaDon ON HoaDon.MaHoaDon = HoaDonChiTiet.MaHoaDon \n"
                + "join KhachHang on HoaDon.MaHoaDon = KhachHang.MaHoaDon\n" +
        "WHERE HoaDon.MaHoaDon = ?";
        ArrayList<Linh_KhachHangViewBang2> list = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, MaHD);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maHD = rs.getInt("MaHoaDon");
                String tenSP = rs.getString("TenSanPhamChiTiet");
                String tenKh = rs.getString("TenKhachHang");
                Integer soLuong = rs.getInt("SoLuong");
                Double DonGia = rs.getDouble("DonGia");
                Date ngay = rs.getDate("NgayLap");
                Linh_KhachHangViewBang2 kh = new Linh_KhachHangViewBang2(maHD, tenSP, tenKh, soLuong, DonGia, DonGia, ngay);
                list.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    ////Tìm theo tên khách hàng
    public ArrayList<KhachHangViewModel> getListSearch(String id) {
        String sql = "SELECT KhachHang.MaKhachHang, KhachHang.TenKhachHang, HoaDon.MaHoaDon, KhachHang.GioiTinh,KhachHang.SoDienThoai,KhachHang.DiaChi FROM KhachHang \n"
                + "left JOIN HoaDon ON HoaDon.MaHoaDon = KhachHang.MaHoaDon WHERE KhachHang.TenKhachHang LIKE '%" + id + "%'";
        ArrayList<KhachHangViewModel> ls = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maKH = rs.getInt("MaKhachHang");
                String tenKh = rs.getString("TenKhachHang");
                Integer maHD = rs.getInt("MaHoaDon");
                String gioiTinh = rs.getString("GioiTinh");
                String soDT = rs.getString("SoDienThoai");
                String diaChi = rs.getString("DiaChi");

                KhachHangViewModel kh = new KhachHangViewModel(maKH, tenKh, gioiTinh, soDT, diaChi, maHD);
                ls.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }

    /////Tìm theo SĐT
    public ArrayList<KhachHangViewModel> getListSearchSDT(String sdt) {
        String sql = "SELECT KhachHang.MaKhachHang, KhachHang.TenKhachHang, HoaDon.MaHoaDon, KhachHang.GioiTinh,KhachHang.SoDienThoai,KhachHang.DiaChi FROM KhachHang "
                + "LEFT JOIN HoaDon ON HoaDon.MaHoaDon = KhachHang.MaHoaDon WHERE KhachHang.SoDienThoai = ?";
        ArrayList<KhachHangViewModel> ls = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sdt);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maKH = rs.getInt("MaKhachHang");
                String tenKh = rs.getString("TenKhachHang");
                Integer maHD = rs.getInt("MaHoaDon");
                String gioiTinh = rs.getString("GioiTinh");
                String soDT = rs.getString("SoDienThoai");
                String diaChi = rs.getString("DiaChi");

                KhachHangViewModel kh = new KhachHangViewModel(maKH, tenKh, gioiTinh, soDT, diaChi, maHD);
                ls.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }

    public boolean addNew(KhachHang kh) {

        String sql = "INSERT INTO KhachHang ( TenKhachHang, GioiTinh, SoDienThoai, DiaChi,LoaiKhachHang,Deleted) VALUES (?, ?, ?, ?,N'Thành Viên', 0)";

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, kh.getTenKH());
            ps.setString(2, kh.getGioiTinh());
            ps.setString(3, kh.getSoDT());
            ps.setString(4, kh.getDiaChi());

            int result = ps.executeUpdate();
            if (result > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(KhachHang kh) {

        String sql = "update KhachHang set TenKhachHang = ? , GioiTinh = ? , SoDienThoai = ?, "
                + "DiaChi = ? where MaKhachHang = ?";

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, kh.getTenKH());
            ps.setObject(2, kh.getGioiTinh());
            ps.setObject(3, kh.getSoDT());
            ps.setObject(4, kh.getDiaChi());
            ps.setInt(5, kh.getMaKH());
            int result = ps.executeUpdate();

            if (result > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoa(Integer ID) {

        String sql = "SELECT * FROM KhachHang where MaKhachHang =" + ID + "";

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ID);
            int result = ps.executeUpdate();

            if (result > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    ///
    public Boolean XoaKH(KhachHang kh) {
        String sql = "UPDATE KhachHang SET Deleted = 1 WHERE MaKhachHang = ?";
        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, kh.getMaKH());

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /////Danh sach da an
    public ArrayList<KhachHangViewModel> DanhSachAn() {
        String sql = "Select MaKhachHang, TenKhachHang, MaHoaDon, GioiTinh, SoDienThoai, DiaChi from KhachHang where Deleted = 1";
        ArrayList<KhachHangViewModel> ls = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maKH = rs.getInt("MaKhachHang");
                String tenKh = rs.getString("TenKhachHang");
                Integer maHD = rs.getInt("MaHoaDon");
                String gioiTinh = rs.getString("GioiTinh");
                String soDT = rs.getString("SoDienThoai");
                String diaChi = rs.getString("DiaChi");

                KhachHangViewModel kh = new KhachHangViewModel(maKH, tenKh, gioiTinh, soDT, diaChi, maHD);
                ls.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }

    ////Bo an
    public Boolean boAn(KhachHang kh) {
        String sql = "UPDATE KhachHang SET Deleted = 0 WHERE MaKhachHang = ?";
        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, kh.getMaKH());

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    ///
    public KhachHangViewModel getListt(Integer id) {
        String sql = "SELECT KhachHang.MaKhachHang, KhachHang.TenKhachHang, HoaDon.MaHoaDon, KhachHang.GioiTinh,KhachHang.SoDienThoai,KhachHang.DiaChi FROM KhachHang \n"
                + "left JOIN HoaDon ON HoaDon.MaHoaDon = KhachHang.MaHoaDon WHERE KhachHang.MaKhachHang = ?";
        KhachHangViewModel kh = new KhachHangViewModel();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maKH = rs.getInt("MaKhachHang");
                String tenKh = rs.getString("TenKhachHang");
                Integer maHD = rs.getInt("MaHoaDon");
                String gioiTinh = rs.getString("GioiTinh");
                String soDT = rs.getString("SoDienThoai");
                String diaChi = rs.getString("DiaChi");

                kh = new KhachHangViewModel(maKH, tenKh, gioiTinh, soDT, diaChi, maHD);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kh;
    }

    public boolean XoaKH(Integer maKH) {
        String sql = "DELETE FROM KhachHang WHERE MaKhachHang = ?";
        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, maKH);

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
