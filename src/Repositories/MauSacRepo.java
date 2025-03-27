/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories;

import Models.MauSac;
import java.util.ArrayList;
import java.sql.*;

/**
 *
 * @author X1
 */
public class MauSacRepo {
    DbConnection dbConnection;
    
    //CHECK ID
    public boolean checkIdColor(Integer id) {
        String sql = "SELECT COUNT(*) FROM MauSac WHERE Deleted!=1 AND MaMau = ?";
        
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
    
    //HIDE_UNHIDE
    public Boolean hideTTMS(MauSac ms){
        String sql = "UPDATE MauSac SET Deleted = 1 WHERE MaMau = ?";
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, ms.getMaMau());
            
            int check = ps.executeUpdate();
            if (check>0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public Boolean unhideTTMS(MauSac ms){
        String sql = "UPDATE MauSac SET Deleted = 0 WHERE MaMau = ?";
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, ms.getMaMau());
            
            int check = ps.executeUpdate();
            if (check>0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    //GETLIST
    public ArrayList<MauSac> getList(){
        String sql = "SELECT * FROM MauSac WHERE Deleted !=1";
        ArrayList<MauSac> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Integer maMau = rs.getInt("MaMau");
                String tenMau = rs.getString("TenMau");
                
                MauSac ms = new MauSac(maMau, tenMau);
                ls.add(ms);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    public ArrayList<MauSac> getListHide(){
        String sql = "SELECT * FROM MauSac WHERE Deleted !=0";
        ArrayList<MauSac> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Integer maMau = rs.getInt("MaMau");
                String tenMau = rs.getString("TenMau");
                
                MauSac ms = new MauSac(maMau, tenMau);
                ls.add(ms);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    //GET_ID BY NAME COLOR
    public MauSac getIdByName(String name){
        String sql = "SELECT MaMau, TenMau FROM MauSac WHERE TenMau = ?";
        MauSac ms = null;
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, name);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer maMau = rs.getInt("MaMau");
                String tenMau = rs.getString("TenMau");
                
                ms = new MauSac(maMau, tenMau);
            }  
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ms;
    }
    
    //ADD
    public Boolean addColor(MauSac ms){
        String sql = "INSERT INTO MauSac (TenMau,Deleted) VALUES (?,0);";
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps =conn.prepareCall(sql)){
            ps.setNString(1, ms.getTenMau());
            
            int check = ps.executeUpdate();
            if (check>0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    //UPDATE
    public Boolean updateColor(MauSac ms){
        String sql = "UPDATE MauSac SET TenMau = ? WHERE MaMau = ?";
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, ms.getTenMau());
            ps.setObject(2, ms.getMaMau());
            
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
