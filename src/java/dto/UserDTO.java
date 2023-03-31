/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author MY LAPTOP
 */
public class UserDTO {

    private String email;
    private String userID;
    private String password;
    private String fullName;
    private String roleID;
    private String address;
    private String phone;
    private String avatar;

    public UserDTO() {
    }

    public UserDTO(String email, String userID, String password, String fullName, String roleID, String address, String phone, String avatar) {
        this.email = email;
        this.userID = userID;
        this.password = password;
        this.fullName = fullName;
        this.roleID = roleID;
        this.address = address;
        this.phone = phone;
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "UserDTO{" + "email=" + email + ", userID=" + userID + ", password=" + password + ", fullName=" + fullName + ", roleID=" + roleID + ", address=" + address + ", phone=" + phone + ", avatar=" + avatar + '}';
    }

    
}
