/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories;

import Models.NhanSu;
import ViewModels.NhanSuViewModel;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class Hoa_NhanSuRepo {
    DbConnection dbConnection;
    
    public NhanSu getIdByName(String name) {
        String sql = "SELECT MaNhanVien, TenNhanVien FROM NhanVien\n"
                + "WHERE Deleted!=1 AND TenNhanVien = ?";
        NhanSu ns = new NhanSu();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, name);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maNV = rs.getInt("MaNhanVien");
                String tenNv = rs.getString("TenNhanVien");

                ns = new NhanSu(tenNv, maNV);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ns;
    }

    public NhanSuViewModel getListById(Integer id) {
        String sql = "SELECT nv.MaNhanVien, nv.TenNhanVien\n"
                + ", nd.MaNguoiDung, nd.TenDangNhap, nv.GioiTinh, nd.VaiTro\n"
                + ", nv.SoDienThoai, nv.DiaChi FROM NhanVien nv\n"
                + "INNER JOIN NguoiDung nd ON nv.MaNguoiDung = nd.MaNguoiDung\n"
                + "WHERE nv.Deleted !=1 AND nv.MaNhanVien = " + id + "";
        NhanSuViewModel ns = new NhanSuViewModel();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maNV = rs.getInt("MaNhanVien");
                String tenNV = rs.getString("TenNhanVien");
                String tenND = rs.getString("TenDangNhap");
                String gioiTinh = rs.getString("GioiTinh");
                String vaiTro = rs.getString("VaiTro");
                String soDT = rs.getString("SoDienThoai");
                String diaChi = rs.getString("DiaChi");
                Integer maNguoiDung = rs.getInt("MaNguoiDung");

                ns = new NhanSuViewModel(maNV, tenNV, maNguoiDung, tenND, gioiTinh, diaChi, diaChi, vaiTro);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ns;
    }

    public ArrayList<NhanSuViewModel> getList() {
        String sql = "select MaNhanVien,TenNhanVien,TenDangNhap,GioiTinh,SoDienThoai,DiaChi,VaiTro,NhanVien.MaNguoiDung from NhanVien "
                + "inner join NguoiDung on NhanVien.MaNguoiDung = NguoiDung.MaNguoiDung";
        ArrayList<NhanSuViewModel> list = new ArrayList<>();
        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maNV = rs.getInt("MaNhanVien");
                String tenNV = rs.getString("TenNhanVien");
                String tenDN = rs.getString("TenDangNhap");
                String gioiTinh = rs.getString("GioiTinh");
                String sdt = rs.getString("SoDienThoai");
                String diaChi = rs.getString("DiaChi");
                String vaiTro = rs.getString("VaiTro");
                Integer maND = rs.getInt("MaNguoiDung");
                list.add(new NhanSuViewModel(maNV, tenNV, maND, tenDN, gioiTinh, sdt, diaChi, vaiTro));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<NhanSu> getListTenNV() {
        String sql = "SELECT * FROM NhanVien WHERE Deleted !=1;";
        ArrayList<NhanSu> list = new ArrayList<>();

        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String tenNV = rs.getString("TenNhanVien");
                String gioiTinh = rs.getString("GioiTinh");
                String soDT = rs.getString("SoDienThoai");
                String ngaySinh = rs.getString("NgaySinh");
                String diaChi = rs.getString("DiaChi");
                Integer maNguoiDung = rs.getInt("MaNguoiDung");

                NhanSu ns = new NhanSu(tenNV, gioiTinh, ngaySinh, soDT, diaChi, maNguoiDung);
                list.add(ns);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public NhanSu getAll() {
        String sql = "SELECT * FROM NhanVien";
        NhanSu ns = new NhanSu();

        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String tenNV = rs.getString("TenNhanVien");
                String gioiTinh = rs.getString("GioiTinh");
                String soDT = rs.getString("SoDienThoai");
                String ngaySinh = rs.getString("NgaySinh");
                String diaChi = rs.getString("DiaChi");
                Integer maNguoiDung = rs.getInt("MaNguoiDung");

                ns = new NhanSu(tenNV, gioiTinh, ngaySinh, soDT, diaChi, maNguoiDung);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ns;
    }

    public Boolean AddNew(NhanSu sp) {
        String sql = " INSERT INTO NhanVien(MaNguoiDung,TenNhanVien,GioiTinh,SoDienThoai,DiaChi) VALUES(?,?,?,?,?) ";

        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, sp.getMaNguoiDung());
            ps.setObject(2, sp.getTenNhanVien());
            ps.setObject(3, sp.getGioiTinh());
            ps.setObject(4, sp.getSoDienThoai());
            ps.setObject(5, sp.getDiaChi());
            int kq = ps.executeUpdate();
            if (kq > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean delete(String maNV) {
        String sql = "DELETE FROM [dbo].[NhanVien]\n"
                + "      WHERE [MaNhanVien] = '" + maNV + "'";

        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            int ketQua = ps.executeUpdate();
            if (ketQua > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

//    public static void main(String[] args) {
//        Integer maND = 8;
//        Integer maNV = 1007;
//        String tenNV = "Hoa";
//        String sdt = "001100";
//        String dc = "Vn";
//        //tk k lấy từ vai trò
//        String gtinh = "Nữ";
//
//        NhanSu ns = new NhanSu(tenNV, gtinh, sdt, dc, maND, maNV);
//        Boolean check = new Hoa_NhanSuRepo().updateNew(ns);
//        if(check){
//            System.out.println("Sửa thành công");
//            System.out.println(ns.toString());
//        }else{
//            System.out.println("Sửa thất bại") ;
//        }
//        ArrayList<NhanSuViewModel> list = new Hoa_NhanSuRepo().getList();
//        for (NhanSuViewModel nsvm : list) {
//            System.out.println(nsvm.toString());
//        }
//    }

    public Boolean updateNew(NhanSu ns) {
        String sql = //"alter table NhanVien nocheck constraint FK__NhanVien__MaNguo__49C3F6B7\n"+
                 "update NhanVien set TenNhanVien = ?, GioiTinh = ?,SoDienThoai = ?,DiaChi = ?"
                + ", NhanVien.MaNguoiDung  = ? "
                + "where MaNhanVien = ?";
        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, ns.getTenNhanVien());
            ps.setObject(2, ns.getGioiTinh());
            ps.setObject(3, ns.getSoDienThoai());
            ps.setObject(4, ns.getDiaChi());
            ps.setObject(5, ns.getMaNguoiDung());
            ps.setObject(6, ns.getMaNhanVien());

            int kq = ps.executeUpdate();
            return kq > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<NhanSuViewModel> searchByName(String name) {
        String sql = "SELECT nv.MaNhanVien, nv.TenNhanVien\n"
                + ", nd.TenDangNhap, nv.GioiTinh, nd.VaiTro\n"
                + ", nv.SoDienThoai, nv.DiaChi, nv.MaNguoiDung FROM NhanVien nv\n"
                + "INNER JOIN NguoiDung nd ON nv.MaNguoiDung = nd.MaNguoiDung\n"
                + "WHERE nv.TenNhanVien LIKE N'%" + name + "%'";
        ArrayList<NhanSuViewModel> list = new ArrayList<>();

        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhanSuViewModel spvm = new NhanSuViewModel(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(5));
                rs.getString(8);
                list.add(spvm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public Boolean checkName(Integer maND) {
        String sql = "SELECT COUNT(*) FROM NhanVien WHERE MaNguoiDung = ?";

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, maND);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }    
 

}
