package com.jas.domain;

import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PostUpdate;
import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Page<Account> findAll(Pageable pageable);

    @Query(value = "select * from account where status = ?1", nativeQuery = true)
    Page<Account> findByStatus(String status, Pageable pageable);

    Account findById(long id);

    List<Account> findByStatus(String status);

    List<Account> findByStatusAndUser(String status, String user);

    List<Account> findByNoticeContains(String notice);

//    @Query("select a from Account a where length(a.name) > ?1 ")
    @Query(value = "select * from account where length(name) > ?1 ", nativeQuery = true)
    List<Account> findByJPQL(int len);

    @Modifying
    @Query("update Account a set a.status = ?1 where a.id = ?2")
    int updateByJPQL(@Param("status") String status,@Param("id") long id);
}
