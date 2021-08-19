package com.hhr.accountbook.dao.impl;

import com.hhr.accountbook.model.QAccount;
import com.hhr.accountbook.model.QBill;
import com.hhr.accountbook.model.QPaymentMethod;
import com.hhr.accountbook.model.QSpendingType;
import com.hhr.accountbook.vo.BillVo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Author: Harry
 * @Date: 2021/8/18 19:39
 * @Version 1.0
 */
@Repository
@Slf4j
public class QueryDslBillDaoImpl {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    public List<BillVo> findAllBillVoByChoose( Long accountId,
                                               Timestamp startTime,
                                               Timestamp endTime,
                                               String paymentMethod,
                                               String spendingType){

        QBill qBill = QBill.bill;
        QPaymentMethod qPaymentMethod = QPaymentMethod.paymentMethod;
        QSpendingType qSpendingType = QSpendingType.spendingType;
        QAccount qAccount = QAccount.account;


        JPAQuery<BillVo> jpaQuery = jpaQueryFactory.select(
                Projections.bean(
                        BillVo.class,
                        qBill.id.as("id"),
                        qAccount.accountName.as("accountName"),
                        qBill.details.as("details"),
                        qPaymentMethod.method.as("payMethod"),
                        qSpendingType.type.as("spendingType"),
                        qBill.payTime.as("payTime"),
                        qBill.money.as("money")
                )
        ).from(qBill)
         .join(qPaymentMethod).on(qBill.paymentMethod.eq(qPaymentMethod.id))
         .join(qSpendingType).on(qBill.spendingType.eq(qSpendingType.id))
         .join(qAccount).on(qBill.accountId.eq(qAccount.id))
         .where(
                 qBill.payTime.between(startTime.toString(),endTime.toString())
                 .and(qAccount.id.eq(accountId))
         );


        if(!"全部".equals(paymentMethod)){
            jpaQuery = jpaQuery.where(qPaymentMethod.method.eq(paymentMethod));
        }

        if(!"全部".equals(spendingType)){
            jpaQuery = jpaQuery.where(qSpendingType.type.eq(spendingType));
        }

        List<BillVo> billVoList = jpaQuery.orderBy(qBill.payTime.asc()).fetch();

        return billVoList;
    }

    public List<BillVo> findBillVoByTime(Long accountId,Timestamp startTime,Timestamp endTime){
        QBill qBill = QBill.bill;
        QPaymentMethod qPaymentMethod = QPaymentMethod.paymentMethod;
        QSpendingType qSpendingType = QSpendingType.spendingType;
        QAccount qAccount = QAccount.account;

        JPAQuery<BillVo> jpaQuery = jpaQueryFactory.select(
                Projections.bean(
                        BillVo.class,
                        qBill.id.as("id"),
                        qAccount.accountName.as("accountName"),
                        qBill.details.as("details"),
                        qPaymentMethod.method.as("payMethod"),
                        qSpendingType.type.as("spendingType"),
                        qBill.payTime.as("payTime"),
                        qBill.money.as("money")
                )
        ).from(qBill)
        .join(qPaymentMethod).on(qBill.paymentMethod.eq(qPaymentMethod.id))
        .join(qSpendingType).on(qBill.spendingType.eq(qSpendingType.id))
        .join(qAccount).on(qBill.accountId.eq(qAccount.id))
        .where(
                qBill.payTime.between(startTime.toString(),endTime.toString())
                .and(qAccount.id.eq(accountId)))
        .orderBy(qBill.payTime.asc());

        List<BillVo> billVoList = jpaQuery.fetch();

        return billVoList;
    }
}
