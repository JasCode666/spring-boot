package com.jas.api;

import com.jas.domain.Account;
import com.jas.dto.AccountDTO;
import com.jas.exception.AccountNotFoundExecption;
import com.jas.exception.InvalidRequestException;
import com.jas.service.AccountService;
import com.jas.util.CustomBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

/**
 * 簡易RESTful API
 */
@RestController
@RequestMapping("/api/v2")
public class AccountApi {

    @Autowired
    private AccountService accountService;

    /**
     * 搜尋所有紀錄
     * @return
     */
    @GetMapping("/accounts")
    public ResponseEntity<?>  listAllAccount() {
        List<Account> accounts = accountService.findAll();
        if (accounts.isEmpty())
            throw new AccountNotFoundExecption("Account Not Found");
        return new ResponseEntity<Object>(accounts, HttpStatus.OK);
    }

    /**
     * 搜尋一條紀錄
     * @param id
     * @return
     */
    @GetMapping("/accounts/{id}")
    public ResponseEntity<?> getAccount(@PathVariable long id) {
        Account account = accountService.findOne(id);
        if (account == null)
            throw new AccountNotFoundExecption(String.format("Account by id %s Not Found", id));
        return new ResponseEntity<Object>(account, HttpStatus.OK);
    }

    /**
     * 新增一條紀錄
     * @param accountDTO
     * @return
     */
    @PostMapping("/accounts")
    public ResponseEntity<?> saveAccount(@Valid @RequestBody AccountDTO accountDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Invalid parameter", bindingResult);
        }
        Account account1 = accountService.save(accountDTO.convertToAccount());
        return new ResponseEntity<Object>(account1, HttpStatus.CREATED);
    }

    /**
     * 更新一條紀錄
     * @param id
     * @param accountDTO
     * @return
     */
    @PutMapping("/accounts/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable long id,@Valid @RequestBody AccountDTO accountDTO, BindingResult bindingResult) {

        Account currentAccount = accountService.findOne(id);
        if (currentAccount == null) {
            throw new AccountNotFoundExecption(String.format("Account by id %s not found", id));
        }
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Invalid parameter", bindingResult);
        }
        accountDTO.convertToAccount(currentAccount);                // 修正可以不用傳送全部資料
        Account account1 = accountService.save(currentAccount);
        return new ResponseEntity<Object>(account1, HttpStatus.OK);
    }

    /**
     * 刪除一個紀錄
     * @param id
     * @return
     */
    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable long id) {
        accountService.delete(id);
        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }

    /**
     * 刪除所有紀錄
     * @return
     */
    @DeleteMapping("/accounts")
    public ResponseEntity<?> deleteAllAccount(){
        accountService.deleteAllAccount();
        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }
}
