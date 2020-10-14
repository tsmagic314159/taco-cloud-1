package Tacos.data;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import Tacos.Order;
import Tacos.Taco;

//这个Repository采用SimpleJdbcInsert来进行对数据库的insert操作
@Repository
public class JdbcOrderRepository implements OrderRepository{
	
	//首先创建一个SimpleJdbcInsert类
	private SimpleJdbcInsert orderInserter;
	private SimpleJdbcInsert orderTacoInserter;
	// 通过Jackson中的objectMapper来帮助我们保存订单和关联的taco
	private ObjectMapper objectMapper;
	
	//通过autowired注释进行自动配置
	@Autowired
	public JdbcOrderRepository(JdbcTemplate jdbc) {
		this.orderInserter = new SimpleJdbcInsert(jdbc)
				.withTableName("Taco_Order")
				//假定数据库会自动生成主键id
				.usingGeneratedKeyColumns("id");
		
		this.orderTacoInserter = new SimpleJdbcInsert(jdbc).withTableName("Taco_Order_Tacos");
		
		this.objectMapper = new ObjectMapper();
	}
	
	@Override
	public Order save(Order order) {
		order.setPlacedAt(new Date());
		long orderId = saveOrderDetails(order);
		order.setId(orderId);
		List<Taco> tacos = order.getTacos();
		for (Taco taco : tacos) {
			saveTacoToOrder(taco, orderId);
		}
		return order;
	}
	
	private long saveOrderDetails(Order order) {
		@SuppressWarnings("unchecked")
		//由于SimpleJdbcInsert的两个函数需要使用Map作为变量，我们使用ObjectMapper将order转化为map进行存储
		Map<String, Object> values = objectMapper.convertValue(order, Map.class);
		
		values.remove("tacos");
		values.remove("id");

		Set<String> set = values.keySet();
		set.forEach(e -> System.out.println(e));
		values.put("placedAt", order.getPlacedAt());
		
		
		//这里可以返回数据库生成的Id
		long orderId = orderInserter.executeAndReturnKey(values).longValue();
		return orderId;
	}
	
	private void saveTacoToOrder(Taco taco, long orderId) {
		Map<String, Object> values = new HashMap<>();
		values.put("tacoOrder", orderId);
		values.put("taco", taco.getId());
		orderTacoInserter.execute(values);
	}
}
