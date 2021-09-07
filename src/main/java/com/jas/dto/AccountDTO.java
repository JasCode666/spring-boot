package com.jas.dto;

import java.beans.PropertyDescriptor;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.jas.domain.Account;
import com.jas.form.FormConvert;
import com.jas.util.CustomBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.constraints.NotBlank;

public class AccountDTO {

    @NotBlank
    private String name;

    private String status;

    private String user;

    private String level;

    private String job;

    @NotBlank
    private String beanAccount;

    @NotBlank
    private String beanPassword;

    private String notice;

    private Date createdOn;

    public AccountDTO() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getBeanAccount() {
        return beanAccount;
    }

    public void setBeanAccount(String beanAccount) {
        this.beanAccount = beanAccount;
    }

    public String getBeanPassword() {
        return beanPassword;
    }

    public void setBeanPassword(String beanPassword) {
        this.beanPassword = beanPassword;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public void convertToAccount(Account account) {
        new AccountConvert().convert(this, account);
    }

    public Account convertToAccount() {
        return new AccountConvert().convert(this);
    }

    /**
     * 使用泛型實作轉換
     */
    private class AccountConvert implements FormConvert<AccountDTO, Account> {

        @Override
        public Account convert(AccountDTO accountDTO) {
            Account account = new Account();
            BeanUtils.copyProperties(accountDTO, account);
            return account;
        }

        @Override
        public Account convert(AccountDTO accountDTO, Account account) {
            String[] nullPropertyNames = CustomBeanUtils.getNullPropertyNames(accountDTO);
            BeanUtils.copyProperties(accountDTO, account, nullPropertyNames);
            return null;
        }
    }
}
