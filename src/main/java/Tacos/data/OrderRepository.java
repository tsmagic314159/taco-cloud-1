package Tacos.data;

import Tacos.Order;

public interface OrderRepository {
	Order save(Order order);
}
