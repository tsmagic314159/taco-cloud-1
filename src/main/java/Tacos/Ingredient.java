package Tacos;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
//force 可以将无参构造器中的final属性变量初始化为null
@NoArgsConstructor(access=AccessLevel.PRIVATE, force = true)
//@Entity可将类声明为JAP实体
@Entity
public class Ingredient {
	
	@Id
	private final String id;
	private final String name;
	private final String type;
//	
//	public static enum Type{
//		WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
//	}
}
