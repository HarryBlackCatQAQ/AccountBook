package com.hhr.accountbook.dao;

import com.hhr.accountbook.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Author: Harry
 * @Date: 2021/8/17 0:07
 * @Version 1.0
 */
public interface BillDao extends JpaRepository<Bill,String> , QuerydslPredicateExecutor<Bill> {


    @Query(value = "SELECT bill.id as id,\n" +
            "            account.account_name as accounName,\n" +
            "            bill.details as details,\n" +
            "            payment_method.method as payMethod,\n" +
            "            spending_type.type as spendingType,\n" +
            "            bill.paytime as payTime\n" +
            "        FROM bill\n" +
            "        join account on :accountId == bill.accountId\n" +
            "        join spending_type on spending_type.id == bill.spending_type\n" +
            "        join payment_method on payment_method.id == bill.payment_method\n" +
            "        WHERE bill.paytime BETWEEN :startTime AND :endTime",
    nativeQuery = true)
    public List<Object[]> findAllBillVoByAccountId(@Param("accountId") Long accountId,
                                                   @Param("startTime") String startTime,
                                                   @Param("endTime") String endTime);

    @Query(value = "SELECT bi.id as id,\n" +
            "            a.account_name as accountName,\n" +
            "            bi.details as details,\n" +
            "            pm.method as payMethod,\n" +
            "            st.type as spendingType,\n" +
            "            bi.paytime as payTime,\n" +
            "            bi.money as money\n" +
            "        FROM bill AS bi\n" +
            "        JOIN payment_method AS pm ON bi.payment_method == pm.id\n" +
            "        JOIN spending_type AS st ON bi.spending_type == st.id\n" +
            "        JOIN account as a ON a.id == bi.accountId\n" +
            "        WHERE (bi.paytime BETWEEN :startTime AND :endTime)\n" +
            "        AND a.id == :accountId\n" +
            "        AND pm.method == :paymentMethod\n" +
            "        AND st.type == :spendingType\n" +
            "        ORDER BY bi.paytime",
    nativeQuery = true)
    public List<Object[]> findAllBillVoByChoose(@Param("accountId") Long accountId,
                                                @Param("startTime") String startTime,
                                                @Param("endTime") String endTime,
                                                @Param("paymentMethod") String paymentMethod,
                                                @Param("spendingType") String spendingType);

    @Query(value = "SELECT spending_type.type as type,COUNT(spending_type.type) as typeCount\n" +
            "        FROM bill\n" +
            "        join spending_type ON bill.spending_type == spending_type.id\n" +
            "        WHERE bill.account_id == :accountId\n" +
            "        AND (bill.paytime BETWEEN :startTime AND :endTime)\n" +
            "        GROUP BY spending_type",
    nativeQuery = true)
    public List<Object[]> findAllBillSpendingTypeVoByAccountId(@Param("accountId")Long accountId,
                                                               @Param("startTime") String startTime,
                                                               @Param("endTime") String endTime);

    @Query(value = "SELECT SUM(bill.money) as sumMoney\n" +
            "            FROM bill\n" +
            "            WHERE bill.account_id == :accountId\n" +
            "            AND (bill.paytime BETWEEN :startTime AND :endTime)",
    nativeQuery = true)
    public Double findSumMoneyByAccountIdAndDate(@Param("accountId") Long accountId,
                                                 @Param("startTime") String startTime,
                                                 @Param("endTime") String endTime);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM bill where bill.account_id = :accountId",nativeQuery = true)
    public void deleteBillByAccountId2(@Param("accountId") Long accountId);
}

