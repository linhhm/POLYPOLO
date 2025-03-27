/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories;

import Models.NhanSu;
import Models.TaiKhoan;
import Models.User;
import ViewModels.TaiKhoanViewModel;
import ViewModels.UserViewModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author X1
 */
public class UserRepository {
    
    DbConnection dbConnection;
    
    public ArrayList<UserViewModel> filterByRoleAndGender(String vaiT, String gioiT){
        String sql = "SELECT * FROM NhanVien nv INNER JOIN NguoiDung nd ON nv.MaNguoiDung = nd.MaNguoiDung\n" +
                    "WHERE nd.VaiTro = ? AND nv.GioiTinh = ? ;";
        ArrayList<UserViewModel> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, vaiT);
            ps.setObject(2, gioiT);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer maNV = rs.getInt("MaNhanVien");
                Integer maND = rs.getInt("MaNguoiDung");
                String hoTen = rs.getString("TenNhanVien");
                String gioiTinh = rs.getString("GioiTinh");
                String soDT = rs.getString("SoDienThoai");
                String diaChi = rs.getString("DiaChi");
                String tenDN = rs.getString("TenDangNhap");
                String mk = rs.getString("MatKhau");
                String vaiTro = rs.getString("VaiTro");
                
                UserViewModel u = new UserViewModel(maNV, maND, tenDN, hoTen, mk, soDT, vaiTro, diaChi, gioiTinh);
                ls.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
        
    }
    
    public Boolean delete(String maND) {
        String sql = "DELETE FROM [dbo].[NguoiDung]\n"
                + "      WHERE [MaNguoiDung] = '" + maND + "'";

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

    public ArrayList<User> getMaTK() {
        String sql = "select MaNguoiDung from NguoiDung";
        ArrayList<User> list = new ArrayList<>();
        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maTK = rs.getInt("MaNguoiDung");

                User u = new User(maTK);
                list.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Boolean update(User u) {
        String sql = "UPDATE [dbo].[NguoiDung]\n"
                + "   SET [TenDangNhap] = ?\n"
                + "      ,[MatKhau] = ?\n"
                + "      ,[VaiTro] = ?\n"
                + " WHERE MaNguoiDung = ?";

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, u.getUserName());
            //HUONG - Đổi cái này là getUSERNAME
            ps.setObject(2, u.getPassCode());
            ps.setObject(3, u.getRole());
            ps.setObject(4, u.getUserID());

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public Boolean updateMatKhau(User u) {
        String sql = "UPDATE [dbo].[NguoiDung]\n"
                + "   SET [MatKhau] = ?\n"
                + " WHERE TenDangNhap = ?";

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
           
            //HUONG - Đổi cái này là getUSERNAME
            ps.setObject(1, u.getPassCode());
            ps.setObject(2, u.getUserName());

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getTenDN(String tenDN) {
        String sql = "Select NguoiDung.TenDangNhap from NhanVien inner join NguoiDung on NhanVien.MaNguoiDung = NguoiDung.MaNguoiDung where TenDangNhap = '" + tenDN + "'";
        TaiKhoanViewModel tkvm = new TaiKhoanViewModel();
        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tkvm.setTenDN(rs.getString("TenDangNhap"));
                return tkvm.getTenDN();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Loi";
    }

    public User getListByUserId(String userID) {
        String sql = "SELECT * FROM NguoiDung nd WHERE nd.TenDangNhap = ? ";
        User u = new User();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, userID);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String username = rs.getString("TenDangNhap");
                String password = rs.getString("MatKhau");
                String role = rs.getString("VaiTro");

                u = new User(null, username, password, role);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }
    public ArrayList<User> getListByTenDN(String tenDN) {
        String sql = "SELECT * FROM NguoiDung nd WHERE nd.TenDangNhap like '%"+tenDN+"%' ";
        ArrayList<User> list = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer userID = rs.getInt("MaNguoiDung");
                String username = rs.getString("TenDangNhap");
                String password = rs.getString("MatKhau");
                String role = rs.getString("VaiTro");

                list.add(new User(userID, username, password, role));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public ArrayList<User> getListByVaiTro(String roles) {
        String sql = "SELECT * FROM NguoiDung nd WHERE nd.VaiTro = ?";
         ArrayList<User> list = new ArrayList<>();
        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, roles);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer userID = rs.getInt("MaNguoiDung");
                String username = rs.getString("TenDangNhap");
                String password = rs.getString("MatKhau");
                String role = rs.getString("VaiTro");

                list.add(new User(userID, username, password, role));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public ArrayList<UserViewModel> getListByVaiTroTTTK(String roles) {
        String sql = "select * from NhanVien inner join NguoiDung on NhanVien.MaNguoiDung = NguoiDung.MaNguoiDung WHERE NguoiDung.VaiTro = ?";
         ArrayList<UserViewModel> list = new ArrayList<>();
        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, roles);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maNV = rs.getInt("MaNhanVien");
                Integer maND = rs.getInt("MaNguoiDung");
                String hoTen = rs.getString("TenNhanVien");
                String gioiTinh = rs.getString("GioiTinh");
                String soDT = rs.getString("SoDienThoai");
                String diaChi = rs.getString("DiaChi");
                String tenDN = rs.getString("TenDangNhap");
                String mk = rs.getString("MatKhau");
                String vaiTro = rs.getString("VaiTro");
                
                list.add(new UserViewModel(maNV, maND, tenDN, hoTen, mk, soDT, vaiTro, diaChi, gioiTinh));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public ArrayList<UserViewModel> getListByGioiTinh(String gtinh) {
        String sql = "select * from NhanVien inner join NguoiDung on NhanVien.MaNguoiDung = NguoiDung.MaNguoiDung WHERE NhanVien.GioiTinh = ?";
         ArrayList<UserViewModel> list = new ArrayList<>();
        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, gtinh);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maNV = rs.getInt("MaNhanVien");
                Integer maND = rs.getInt("MaNguoiDung");
                String hoTen = rs.getString("TenNhanVien");
                String gioiTinh = rs.getString("GioiTinh");
                String soDT = rs.getString("SoDienThoai");
                String diaChi = rs.getString("DiaChi");
                String tenDN = rs.getString("TenDangNhap");
                String mk = rs.getString("MatKhau");
                String vaiTro = rs.getString("VaiTro");
                
                list.add(new UserViewModel(maNV, maND, tenDN, hoTen, mk, soDT, vaiTro, diaChi, gioiTinh));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //CHECKLOGIN
    public Boolean checkLogin(String userID, String passCode) {
        String sql = "SELECT * FROM NguoiDung WHERE TenDangNhap = ? AND MatKhau = ?";
        User u = null;

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, userID);
            ps.setObject(2, passCode);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String username = rs.getString("TenDangNhap");
                String password = rs.getString("MatKhau");

                u = new User(userID, passCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (u == null) {
            return false;
        } else {
            return true;
        }
    }

    //THÊM PUBLIC ARRAY NÀY
    public ArrayList<UserViewModel> getDataFromList() {
        //HUONG -SỬA SQL
        String sql = "SELECT NguoiDung.MaNguoiDung, NguoiDung.TenDangNhap, NguoiDung.MatKhau, NhanVien.TenNhanVien, NhanVien.SoDienThoai, NhanVien.GioiTinh, NhanVien.DiaChi, NhanVien.NgaySinh, NguoiDung.VaiTro\n"
                + "FROM NguoiDung INNER JOIN NhanVien\n"
                + "ON NguoiDung.MaNguoiDung = NhanVien.MaNguoiDung\n"
                + ""
                //HUONG -  BỎ DÒNG NÀY                
                //" +           WHERE nv.Deleted!=1 AND nd.Deleted!=1"
                + "";
        ArrayList<UserViewModel> ls = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                Integer maND = rs.getInt("MaNguoiDung");
                String tenDN = rs.getString("TenDangNhap");
                String mk = rs.getString("MatKhau");
                String hoTen = rs.getString("TenNhanVien");
                //THÊM
                String vaiTro = rs.getString("VaiTro");
                String soDT = rs.getString("SoDienThoai");
                String gioiTinh = rs.getString("GioiTinh");
                Date ngaySinh = rs.getDate("NgaySinh");
                String diaChi = rs.getString("DiaChi");

                //SỬA LẠI DÒNG, LẤY CONSTRUCTOR FULL
                UserViewModel u = new UserViewModel(maND, tenDN, tenDN, mk, soDT, vaiTro, diaChi, gioiTinh, ngaySinh);
                ls.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }

    //GETLIST
    public ArrayList<UserViewModel> getList() {
        String sql = "select * from NhanVien inner join NguoiDung on NhanVien.MaNguoiDung = NguoiDung.MaNguoiDung "
                //HUONG -  BỎ DÒNG NÀY                
                //" +           WHERE nv.Deleted!=1 AND nd.Deleted!=1"
                + "";
        ArrayList<UserViewModel> ls = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maNV = rs.getInt("MaNhanVien");
                Integer maND = rs.getInt("MaNguoiDung");
                String hoTen = rs.getString("TenNhanVien");
                String gioiTinh = rs.getString("GioiTinh");
                String soDT = rs.getString("SoDienThoai");
                String diaChi = rs.getString("DiaChi");
                String tenDN = rs.getString("TenDangNhap");
                String mk = rs.getString("MatKhau");
                String vaiTro = rs.getString("VaiTro");
                
                ls.add(new UserViewModel(maNV, maND, tenDN, hoTen, mk, soDT, vaiTro, diaChi, gioiTinh));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }

    public UserViewModel getListById(Integer id) {
        //HUONG - SUA SQL
        String sql = "SELECT NguoiDung.MaNguoiDung, NguoiDung.TenDangNhap, NguoiDung.MatKhau\n"
                + "                   , NhanVien.TenNhanVien, NhanVien.SoDienThoai, NhanVien.GioiTinh, NhanVien.DiaChi, NhanVien.NgaySinh, NguoiDung.VaiTro FROM NguoiDung INNER JOIN NhanVien ON NguoiDung.MaNguoiDung = NhanVien.MaNguoiDung\n"
                //HUONG - BỎ DÒNG NÀY
                //      + "                   WHERE nv.Deleted !=1 AND nd.Deleted!=1 AND "
                + "where NguoiDung.MaNguoiDung = ?";
        UserViewModel u = new UserViewModel();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maND = rs.getInt("MaNguoiDung");
                String tenDN = rs.getString("TenDangNhap");
                String mk = rs.getString("MatKhau");
                String hoTen = rs.getString("TenNhanVien");
                String vaiTro = rs.getString("VaiTro");
                String soDT = rs.getString("SoDienThoai");
                String diaC = rs.getString("DiaChi");
                Date ngayS = rs.getDate("NgaySinh");
                String gioiT = rs.getString("GioiTinh");

                u = new UserViewModel(maND, tenDN, hoTen, mk, soDT, vaiTro, diaC, gioiT, ngayS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }

    //GETLIST USER
    public ArrayList<User> getListUser() {
        String sql = "SELECT * FROM NguoiDung ";
        ArrayList<User> ls = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("MaNguoiDung");
                String userId = rs.getString("TenDangNhap");
                String passCode = rs.getString("MatKhau");
                String role = rs.getString("VaiTro");

                User u = new User(id, userId, passCode, role);
                ls.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }

    public ArrayList<NhanSu> getAll() {
        String sql = "SELECT * FROM NhanVien WHERE Deleted !=1";
        ArrayList<NhanSu> list = new ArrayList<>();
//        ArrayList<User> ls = new  ArrayList<>(); 

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
//                Integer id = rs.getInt("MaNguoiDung");
//                String userId = rs.getString("TenDangNhap");
//                String passCode = rs.getString("MatKhau");
//                String role = rs.getString("VaiTro");
//                
//                User u = new User(id, userId, passCode, role);
//ls.add(u);
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

    //SEARCH
    public ArrayList<UserViewModel> getListByTen(String name,String tendn) {
        String sql = "select * from NhanVien inner join NguoiDung on NhanVien.MaNguoiDung = NguoiDung.MaNguoiDung\n"
                + "WHERE NhanVien.TenNhanVien LIKE '%" + name + "%' and NguoiDung.TenDangNhap LIKE '%" + tendn + "%' ";
        ArrayList<UserViewModel> ls = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer maNV = rs.getInt("MaNhanVien");
                Integer maND = rs.getInt("MaNguoiDung");
                String hoTen = rs.getString("TenNhanVien");
                String gioiTinh = rs.getString("GioiTinh");
                String soDT = rs.getString("SoDienThoai");
                String diaChi = rs.getString("DiaChi");
                String tenDN = rs.getString("TenDangNhap");
                String mk = rs.getString("MatKhau");
                String vaiTro = rs.getString("VaiTro");
                
                ls.add(new UserViewModel(maNV, maND, tenDN, hoTen, mk, soDT, vaiTro, diaChi, gioiTinh));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }

    //CHECK ID
    public Boolean checkName(String name) {
        String sql = "SELECT COUNT(*) FROM NguoiDung WHERE TenDangNhap = ?";

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, name);

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

    //ADD ACCOUNT
    public Boolean addAccount(User u) {
        String sql = "INSERT INTO NguoiDung(TenDangNhap,MatKhau,VaiTro) \n"
                + "VALUES(?,?,?)\n";

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, u.getUserName());
            ps.setObject(2, u.getPassCode());
            ps.setObject(3, u.getRole());

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
    public Boolean updateAccount(TaiKhoan tk) {
        String sql = "UPDATE NhanVien\n"
                + "SET TenNhanVien = ?, SoDienThoai = ?,DiaChi = ?,GioiTinh = ?,NgaySinh = ?\n"
                + "WHERE Deleted!=1 AND  MaNguoiDung = ?; "
                + "UPDATE NguoiDung SET TenDangNhap = ? , MatKhau = ?, VaiTro = ? WHERE MaNguoiDung = ?;";

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, tk.getTenNV());
            ps.setObject(2, tk.getSoDT());
            ps.setObject(3, tk.getDiaC());
            ps.setObject(4, tk.getGioiT());
            ps.setObject(5, tk.getMaND());

            ps.setObject(6, tk.getTenDN());
            ps.setObject(7, tk.getMatK());
            ps.setObject(8, tk.getVaiT());
            ps.setObject(9, tk.getMaND());

            int check = ps.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //HIDE
    public Boolean hideAccount(User u) {
        String sql = "UPDATE NguoiDung SET Deleted = 1 WHERE MaNguoiDung = ?";

        try (Connection conn = dbConnection.getConnection(); PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setObject(1, u.getUserID());

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
