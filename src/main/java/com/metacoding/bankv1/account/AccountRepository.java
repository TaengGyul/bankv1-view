package com.metacoding.bankv1.account;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AccountRepository {
    private final EntityManager em;

    public void updateByNumber(int balance, String password, int number) {
        Query query = em.createNativeQuery("update account_tb set balance = ?, password = ? where number = ?");
        query.setParameter(1, balance);
        query.setParameter(2, password);
        query.setParameter(3, number);
        query.executeUpdate();
    }

    public List<Account> findAllByUserId(Integer userId) {
        Query query = em.createNativeQuery("select * from account_tb where user_id = ? order by created_at desc", Account.class);
        query.setParameter(1, userId);
        return query.getResultList();
    }

    public void save(Integer number, String password, Integer balance, int userId) {
        Query query = em.createNativeQuery("insert into account_tb(number, password, balance, user_id, created_at) values (?, ?, ?, ?, now())");
        query.setParameter(1, number);
        query.setParameter(2, password);
        query.setParameter(3, balance);
        query.setParameter(4, userId);
        query.executeUpdate();
    }

    public Account findByNumber(Integer number) {
        Query query = em.createNativeQuery("select * from account_tb where number = ?", Account.class);
        query.setParameter(1, number);

        try {
            return (Account) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }


//    업데이트 쿼리 방식 2 (이렇게 해도 된다)
//    public void updateWithdraw(int amount, int number) {
//        Query query = em.createNativeQuery("update account_tb set balance = balance - ? where number = ?");
//        query.setParameter(1, amount);
//        query.setParameter(2, number);
//        query.executeUpdate();
//    }
//
//    public void updateDeposit(int amount, int number) {
//        Query query = em.createNativeQuery("update account_tb set balance = balance + ? where number = ?");
//        query.setParameter(1, amount);
//        query.setParameter(2, number);
//        query.executeUpdate();
//    }
//
//    public void updatePassword(String password, int number) {
//        Query query = em.createNativeQuery("update account_tb set password = ? where number = ?");
//        query.setParameter(1, password);
//        query.setParameter(2, number);
//        query.executeUpdate();
//    }

}

