import com.ideal.framework.constants.SQLConstants;
import com.ideal.framework.mybatis.criterion.MapperHelper;
import com.ideal.framework.mybatis.criterion.SqlParamsCriterionCollection;

/**  
 * @Title: javaTest.java
 * @Package 
 * @Description: TODO
 * @author himo.zhang ideal_tom@163.com
 * @date 2015-9-20 下午10:16:09
 */

/**
 * @ClassName: javaTest
 * @Description: TODO
 * @author himo.zhang ideal_tom@163.com
 * @date 2015-9-20 下午10:16:09
 */
public class javaTest {
	
	public static void main(String[] args) {
		MapperHelper mh = MapperHelper.getMapperHelperInstance();
		SqlParamsCriterionCollection sqlParamsCriterionCollection = SqlParamsCriterionCollection.getSqlParamsCriterionCollectionInit();
		sqlParamsCriterionCollection.addParam("username", SQLConstants.EQ, "himo");
		mh.addSqlParamsCriterionCollection(sqlParamsCriterionCollection);
		 
		System.out.println(mh.formatMapperHelperParams2String().get("username"));
		
		
	}

}
