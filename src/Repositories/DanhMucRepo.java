/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories;

import Models.DanhMuc;
import java.util.ArrayList;
import java.sql.*;

/**
 *
 * @author X1
 */
public class DanhMucRepo {
    DbConnection dbConnection;
    
    //CHECK ID
    public boolean checkIdCat(Integer id) {
        String sql = "SELECT COUNT(*) FROM DanhMuc WHERE Deleted!=1 AND MaDanhMuc = ?";
        
        try (Connection conn = dbConnection.getConnection(); 
                PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setInt(1, id);
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
    
    //GETLIST BY ID
    public DanhMuc getListById(Integer id){
        String sql = "SELECT * FROM DanhMuc WHERE Deleted!=1 AND MaDanhMuc = ?";
        DanhMuc dm = new DanhMuc();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, id);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer maDM = rs.getInt("MaDanhMuc");
                String tenDM = rs.getString("TenDanhMuc");
                String trangThai = rs.getString("TrangThai");
                
                dm = new DanhMuc(maDM, tenDM, trangThai);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dm;
    }
    
    //SEARCH
    public ArrayList<DanhMuc> getListDMByName(String name){
        String sql = "SELECT * FROM DanhMuc WHERE Deleted!=1 AND TenDanhMuc LIKE '%"+name+"%'";
        ArrayList<DanhMuc> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer maDM = rs.getInt("MaDanhMuc");
                String tenDM = rs.getString("TenDanhMuc");
                String trangThai = rs.getString("TrangThai");
                
                DanhMuc dm = new DanhMuc(maDM, tenDM, trangThai);
                ls.add(dm); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    //GETLIST
    public ArrayList<DanhMuc> getList(){
        String sql = "SELECT * FROM DanhMuc WHERE Deleted !=1";
        ArrayList<DanhMuc> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer maDM = rs.getInt("MaDanhMuc");
                String tenDM = rs.getString("TenDanhMuc");
                String trangThai = rs.getString("TrangThai");
                
                DanhMuc dm = new DanhMuc(maDM, tenDM, trangThai);
                ls.add(dm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    //GET_ID
    public DanhMuc getIdByName(String name){
        String sql = "SELECT MaDanhMuc, TenDanhMuc FROM DanhMuc WHERE TenDanhMuc = ?";
        DanhMuc dm = null;
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, name);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer maDM = rs.getInt("MaDanhMuc");
                String tenDM = rs.getString("TenDanhMuc");
                
                dm = new DanhMuc(maDM, tenDM);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dm;
    }
    
    //CRUD
    public Boolean addDanhMuc(DanhMuc dm){
        String sql = "INSERT INTO DanhMuc(TenDanhMuc, TrangThai,Deleted) VALUES\n" +
"					 (?, ?,0)";
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, dm.getTenDM());
            ps.setObject(2, dm.getTrangThai());
            
            int check = ps.executeUpdate();
            if (check>0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public Boolean updateDanhMuc(DanhMuc dm){
        String sql = "UPDATE DanhMuc\n" +
                     "SET TenDanhMuc = ?, TrangThai = ? WHERE MaDanhMuc = ?";
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, dm.getTenDM());
            ps.setObject(2, dm.getTrangThai());
            ps.setObject(3, dm.getMaDM());
            
            int check = ps.executeUpdate();
            if (check>0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public Boolean anDanhMuc(DanhMuc dm){
        String sql = "UPDATE DanhMuc\n" +
                    "SET Deleted = 1 WHERE MaDanhMuc = ?";
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, dm.getMaDM());
            
            int check = ps.executeUpdate();
            if (check>0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
