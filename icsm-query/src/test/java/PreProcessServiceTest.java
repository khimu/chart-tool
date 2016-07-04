import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.icsm.service.impl.ItunesRssPreProcessService;


@ContextConfiguration(
        locations = {
                "classpath:/applicationContext-dao.xml",
                "classpath:/applicationContext-service.xml",
                "classpath*:/applicationContext.xml",
                "classpath:**/applicationContext*.xml"})
public class PreProcessServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

	private String free = "topfreeapplications";
	private int port = 27017;
	
	private ItunesRssPreProcessService preprocessService;
	
	@Test
	public void testProcessFree(){
		System.out.println(preprocessService.preProcessFree());
	}
}
