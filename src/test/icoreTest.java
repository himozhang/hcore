
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ideal.core.user.entity.User;
import com.ideal.core.user.service.IUserService;
import com.ideal.framework.constants.SQLConstants;
import com.ideal.framework.mybatis.criterion.MapperHelper;
import com.ideal.framework.mybatis.criterion.SqlParamsCriterionCollection;

/** 
 * @Description:
 * @CreateTime 2015-9-12 下午06:59:07
 * @author:himo
 * @mail:zhangyao0905@gmail.com
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-applicationContext.xml", 
									"classpath:mybatis/spring-mybatis.xml" })
public class icoreTest {

	 @Resource
	 private IUserService userService;
	
	@Test
	public void TestAutho(){
//		List<User> lst=userService.getRemoteUser( 1,10);
//        System.out.println("lst.size() : "+lst.size());
	}
	
	
	 
}
