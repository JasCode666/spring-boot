package com.jas.service;

import com.jas.domain.Account;
import com.jas.domain.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public List<Account> findAll() {

        return accountRepository.findAll();
    }

    public Page<Account> findAllByPage(Pageable pageable) {
//        Pageable pageable = PageRequest.of(1, 5, Sort.Direction.ASC, "id");
        return accountRepository.findAll(pageable);
    }

    public Account save(Account account) {

        return accountRepository.save(account);
    }

    public Account findOne(long id) {

        return accountRepository.findById(id);
    }

    public void delete(long id) {
        accountRepository.deleteById(id);
    }

    public List<Account> findByStatus(String status) {

        return accountRepository.findByStatus(status);
    }

    public List<Account> findByStatusAndUser(String status, String user) {
        return accountRepository.findByStatusAndUser(status, user);
    }

    public List<Account> findByNoticeContains(String notice) {
        return accountRepository.findByNoticeContains(notice);
    }

    @Transactional
    public int updateByJPQL(String status, long id) {
        return accountRepository.updateByJPQL(status, id);
    }

    public Page<Account> findStatusByPage(String status, Pageable pageable) {

        return accountRepository.findByStatus(status, pageable);
    }

    public void deleteAllAccount() {
        accountRepository.deleteAll();
    }

}
