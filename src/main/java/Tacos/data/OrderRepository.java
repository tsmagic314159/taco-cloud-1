package Tacos.data;

import org.springframework.data.repository.CrudRepository;

import Tacos.Order;

public interface OrderRepository extends CrudRepository<Order, Long>{
}
