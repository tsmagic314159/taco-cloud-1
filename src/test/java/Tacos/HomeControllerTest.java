package Tacos;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

//该注解可将HomeController注册到SpringMvc中，以便接下来对它发送请求
@WebMvcTest()
class HomeControllerTest {
	
	//自动装配MockMvc用于测试
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void test() {
		try {
			mockMvc.perform(get("/"))
			//测试相应状态，是否为Http 200
			.andExpect(status().isOk())
			//视图的逻辑名称应该为home
			.andExpect(view().name("home"))
			//这里要注意使用content时import的使用，应该为ResultMatchers 而不是RequestBuilders
			.andExpect(content().string(containsString("Welcome to...")));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
