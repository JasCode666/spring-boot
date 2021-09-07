package com.jas.form;

import com.jas.domain.User;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 註冊頁面驗證
 */
public class UserForm {

    public static final String PHONE_REG = "^09[0-9]{8}$";

    @Length(min = 3, max = 16)
    private String name;

    @Length(min = 6, max = 16)
    private String password;

    @Length(min = 6, max = 16)
    private String comfirmPassword;

    @Pattern(regexp = PHONE_REG)
    private String phone;

    @Email
    @NotBlank
    private String email;

    public UserForm() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getComfirmPassword() {
        return comfirmPassword;
    }

    public void setComfirmPassword(String comfirmPassword) {
        this.comfirmPassword = comfirmPassword;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User convertToUser() {
        User user = new UserFormConvert().convert(this);
        return user;
    }

    private class UserFormConvert implements FormConvert<UserForm, User>{

        @Override
        public User convert(UserForm userForm) {
            User user = new User();
            BeanUtils.copyProperties(userForm, user);
            return user;
        }

        @Override
        public User convert(UserForm userForm, User user) {
            return null;
        }
    }

    public boolean comfirmPassword() {
        if (this.password.equals(this.comfirmPassword))
            return true;
        return false;
    }
}
