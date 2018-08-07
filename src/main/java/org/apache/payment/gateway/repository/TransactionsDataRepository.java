package org.apache.payment.gateway.repository;

import org.apache.payment.gateway.config.hibernate.AbstractBaseRepository;
import org.apache.payment.gateway.domains.TransactionData;
import org.apache.payment.gateway.dto.request.TransactionRequest;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sanyam Goel created on 15/6/18
 */

@Repository
public class TransactionsDataRepository extends AbstractBaseRepository {

    /**
     * get list of transactions from database
     * @param nextTransactionId
     * @param limit
     * @param vendorIdList
     * @param clientPhoneNumber
     * @param clientAccountNumber
     * @return
     */
    public List<TransactionData> getTransactionsFromDb(long nextTransactionId, int limit, List<Long> vendorIdList, String clientPhoneNumber, String clientAccountNumber) {
        /*
          select COUNT(*) From Transaction_data
          Integer count = ((Number)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
          SELECT * FROM `transaction_data` LIMIT 5 OFFSET 0; starting from 0 to 5
          .setFirstResult(firstResult||offset).setMaxResults(limit);
        */
        Criteria criteria = this.getCurrentSession().createCriteria(TransactionData.class);
        criteria.add(Restrictions.gt("transactionId", nextTransactionId));

        if (vendorIdList != null && !vendorIdList.isEmpty()) {
            criteria.add(Restrictions.in("vendorId", vendorIdList));
        }

        if (clientPhoneNumber != null) {
            criteria.add(Restrictions.eq("clientPhoneNumber", clientPhoneNumber));
        }

        if (clientAccountNumber != null) {
            criteria.add(Restrictions.eq("clientAccountNumber", clientAccountNumber));
        }

        criteria.setMaxResults(limit);
        return criteria.list();
    }

    /**
     * Get total count of transactions
     *
     * @return
     */
    public Integer getTotalCount() {
        Criteria criteria = this.getCurrentSession().createCriteria(TransactionData.class);
        return ((Number) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
    }


    /**
     * list of transactions by a vendor reference ID
     *
     * @param vendorRefId
     * @return
     */
    // Get Transaction by Vendor Reference ID
    public List<TransactionData> getTransactionByVendorReferenceId(String vendorRefId) {
//        select * from transaction_data where vendor_reference_id = "?";
        Criteria criteria = this.getCurrentSession().createCriteria(TransactionData.class);
        criteria.add(Restrictions.eq("vendorReferenceId", vendorRefId));
        return criteria.list();
    }

    //to insert transaction recieved from application
    public void postTransactionDetailsByUser(TransactionRequest transactionRequest) {
        // criteria to insert
    }

}
