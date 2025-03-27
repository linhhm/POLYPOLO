/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author X1
 */
public class User {
    Integer userID;
    String userName, passCode, role;

    public User() {
    }

    public User(Integer userID) {
        this.userID = userID;
    }
    
    public User(Integer userID, String userName, String passCode, String role) {
        this.userID = userID;
        this.userName = userName;
        this.passCode = passCode;
        this.role = role;
    }  

    public User(String userName, String passCode, String role) {
        this.userName = userName;
        this.passCode = passCode;
        this.role = role;
    }
    
    public User(String userName, String passCode) {
        this.userName = userName;
        this.passCode = passCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getPassCode() {
        return passCode;
    }

    public void setPassCode(String passCode) {
        this.passCode = passCode;
    }

    @Override
    public String toString() {
        return "User{" + "userID=" + userID + ", userName=" + userName + ", passCode=" + passCode + ", role=" + role + '}';
    }
    
}
/*
dòng 18, 22,29,40
*/
