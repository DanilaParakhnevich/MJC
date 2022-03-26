package com.epam.esm.dao.extractor;

import com.epam.esm.entity.CertificateEntity;
import com.epam.esm.entity.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderExtractor implements ResultSetExtractor<List<OrderEntity>> {
    private CertificateExtractor certificateExtractor;

    @Override
    public List<OrderEntity> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<OrderEntity> orders = new ArrayList<>();
        while (rs.next()) {
            OrderEntity order = new OrderEntity();
            CertificateEntity certificate = new CertificateEntity();
            order.setId(rs.getLong("o_id"));
            order.setPrice(rs.getBigDecimal("o_price"));
            order.setPurchaseDate(rs.getTimestamp("o_purchase_date").toLocalDateTime());
            certificateExtractor.fillCertificateFields(certificate, rs);
            order.setCertificate(certificate);
            orders.add(order);
        }
        return orders;
    }

    @Autowired
    public void setCertificateExtractor(CertificateExtractor certificateExtractor) {
        this.certificateExtractor = certificateExtractor;
    }
}
