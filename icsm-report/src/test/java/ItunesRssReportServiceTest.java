import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.icsm.service.impl.ItunesRssReportService;



@ContextConfiguration(
        locations = {
                "classpath:/applicationContext-dao.xml",
                "classpath:/applicationContext-service.xml",
                "classpath*:/applicationContext.xml",
                "classpath:**/applicationContext*.xml"})
public class ItunesRssReportServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private ItunesRssReportService itunesRssReportService;
	
	@Test 
	public void testCampaignReport(){
		String report = itunesRssReportService.reportForCampaigns();
		System.out.println(report);
	}
}
