package com.epam.esm;

import com.epam.esm.dto.OrderClientModel;

import java.util.List;
import java.util.Map;

public interface OrderService {
    List<OrderClientModel> search (Map<String, String> parameters);

    List<OrderClientModel> readAll (Map<String, String> parameters);

    List<OrderClientModel> readByUserId (Map<String, String> parameters);

    OrderClientModel readById (long id);

    OrderClientModel create (OrderClientModel order, long userId);
}
