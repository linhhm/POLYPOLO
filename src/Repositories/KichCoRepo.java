/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories;

import Models.KichCo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author X1
 */
public class KichCoRepo {
    DbConnection dbConnection;
    
    //CHECK ID
    public boolean checkIdSz(Integer id) {
        String sql = "SELECT COUNT(*) FROM Size WHERE Deleted!=1 AND MaSize = ?";
        
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
    
    //HIDE
    public Boolean hideTTSz(KichCo sz){
        String sql = "UPDATE Size SET Deleted = 1 WHERE MaSize = ?";
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, sz.getMaSize());
            
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
    public ArrayList<KichCo> getList(){
        String sql = "SELECT * FROM Size WHERE Deleted !=1";
        ArrayList<KichCo> ls = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Integer maSz = rs.getInt("MaSize");
                String tenSz = rs.getString("TenSize");
                
                KichCo size = new KichCo(maSz, tenSz);
                ls.add(size);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    //GET_ID BY NAME SZ
    public KichCo getIdByName(String name){
        String sql = "SELECT MaSize, TenSize FROM Size WHERE TenSize = ?" ;
        KichCo sz = null;
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setString(1, name);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer maSz = rs.getInt("MaSize");
                String tenSz = rs.getString("TenSize");
                
                sz = new KichCo(maSz, tenSz);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sz;
    }
    
    //ADD
    public Boolean addSz(KichCo sz){
        String sql = "INSERT INTO Size (TenSize,Deleted) VALUES (?,0)";
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareCall(sql)){
            ps.setObject(1, sz.getTenSize());
            
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
    public Boolean updateSz(KichCo sz){
        String sql = "UPDATE Size SET TenSize = ? WHERE MaSize =?"; 
        
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement ps =conn.prepareCall(sql)){
            ps.setObject(1, sz.getTenSize());
            ps.setObject(2, sz.getMaSize());
            
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
