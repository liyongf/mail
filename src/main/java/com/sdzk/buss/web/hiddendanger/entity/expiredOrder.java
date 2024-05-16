package com.sdzk.buss.web.hiddendanger.entity;

import com.sdzk.buss.web.common.Constants;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Order;

public class expiredOrder extends Order{
    private static final long serialVersionUID = 1L;
    private String propertyName;
    private boolean ascending;
    protected expiredOrder(String propertyName, boolean ascending) {

        super(propertyName, ascending);
        this.propertyName = propertyName;
        this.ascending = ascending;

    }


    public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery)
            throws HibernateException {
        String[] columns = criteriaQuery.getColumnsUsingProjection(criteria,
                propertyName);
        //在这里自定义你的字段
//        return " case "+columns[0]+" when '42' then 1 "
//                + "when '138' then 2 "
//                + "when '136' then 3 "
//                + "when '137' then 4 end ";
        //return  "( handlel_status in ( '" + Constants.HANDELSTATUS_REPORT + "','" + Constants.HANDELSTATUS_ROLLBACK_CHECK + "') and " + columns[0] + ") desc";
        String orderStr =  "handlel_status in ( '" + Constants.HANDELSTATUS_REPORT + "','" + Constants.HANDELSTATUS_ROLLBACK_CHECK + "') and Date(NOW()) > limit_date desc,handlel_status in ( '" + Constants.HANDELSTATUS_REPORT + "')  desc," +
                " exam_date desc";
        return orderStr;
    }

    public static expiredOrder getOrder(String propertyName ) {
        return new expiredOrder(propertyName, true);
    }
}
