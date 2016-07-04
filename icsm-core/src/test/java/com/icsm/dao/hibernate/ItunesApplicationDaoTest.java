package com.icsm.dao.hibernate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.icsm.dao.BaseDaoTestCase;
import com.icsm.dao.ItunesApplicationDao;
import com.icsm.model.ItunesApplication;

public class ItunesApplicationDaoTest extends BaseDaoTestCase {

	@Autowired 
	private ItunesApplicationDao itunesApplicationDao;
	
	@Test
	public void testFindAllApplicationsForNames(){
		List<String> names = new ArrayList<String>();
		
		names.add("scrabblefree");
		names.add("skyburger");
		
		List<ItunesApplication> found = itunesApplicationDao.findAllApplicationsForNames(names, 28);
		
		Assert.assertNotNull(found);
		
		Assert.assertFalse(found.isEmpty());

		for(ItunesApplication ia : found){
			System.out.println(ia.getName());
			System.out.println(ia.getBundleId());
		}
	}  
	   
	@Test
	public void testPullLastestRecordsByAppIds(){
		String[] itunesAppIds = new String[] {"454172001" ,"431464056" ,"515287854" ,"399409305" ,"376049023" ,"517411527" ,"512939461" ,"508635577" ,"419090308" ,"454172001" ,"512106046" ,"467042425" ,"513265471" ,"473907606" ,"386303262" ,"422020153" ,"516761043" ,"499125840" ,"481028647" ,"523468692" ,"443819138" ,"465065038" ,"496216983" ,"484989542" ,"445365724" ,"487067872" ,"468569981" ,"456848110" ,"510774440" ,"498832254" ,"426860241" ,"431566331" ,"524492063" ,"524492063" ,"525944697" ,"518251712" ,"439809640" ,"438084823" ,"526451816" ,"507502198" ,"499981226" ,"504489252" ,"407465021" ,"448725575" ,"503914473" ,"526466663" ,"405229260" ,"450163154" ,"352506978" ,"512800631" ,"425652036" ,"501714652" ,"372268904" ,"474307066" ,"443251389" ,"512256156" ,"451727346" ,"501023143" ,"449945376" ,"407757286" ,"324684580" ,"530066681" ,"507456012" ,"531313279" ,"467577200" ,"445293137" ,"486781045" ,"525246631" ,"508574596" ,"516483186" ,"491994942" ,"490973963" ,"526595488" ,"474138682" ,"474138682" ,"379046047" ,"518887839" ,"441902554" ,"489900074" ,"504241451" ,"503965096" ,"417272785" ,"422966028" ,"422966028" ,"453298978" ,"435797419" ,"487572815" ,"522003196" ,"415637674" ,"408468471" ,"464131808" ,"415637674" ,"468711590" ,"498919087" ,"493430974" ,"512256156" ,"518714563" ,"458530296" ,"535742883" ,"415637674" ,"422627169" ,"510904503" ,"499914369" ,"519565643" ,"499189611" ,"431822578" ,"439873467" ,"502462192" ,"439839440" ,"477388213" ,"493430974" ,"447330640" ,"476439094" ,"498472838" ,"507051652" ,"478053054" ,"445283501" ,"485467849" ,"480645514" ,"504733846" ,"445751461" ,"522115429" ,"432378630" ,"427063436" ,"489421978" ,"520348873" ,"504125808" ,"509460017" ,"510891330" ,"507731026" ,"462677768" ,"450451813" ,"473130352" ,"475101593" ,"491594085" ,"516596639" ,"416508786" ,"536883414" ,"519626246" ,"520920728" ,"529436115" ,"524086769" ,"414053537" ,"485429891" ,"513503002" ,"528241232" ,"500116670" ,"469982206" ,"531319314" ,"493268178" ,"469253573" ,"500116670" ,"491843426" ,"509028911" ,"472079015" ,"434756152" ,"432749907" ,"523171614" ,"365497220" ,"467810884" ,"510350079" ,"518242841" ,"477401349" ,"516535756" ,"398157641" ,"467719928" ,"494728164" ,"452176000" ,"498685137" ,"532464789" ,"518650157" ,"459585905" ,"498685137" ,"521889293" ,"516848018" ,"535003306" ,"480018989" ,"455388588" ,"469010071" ,"469010072" ,"320636131" ,"523486500" ,"503504304" ,"500099330" ,"479812410" ,"527244231" ,"471412333" ,"537392789" ,"464551137" ,"529308283" ,"514829543" ,"497640478" ,"516908531" ,"529702149" ,"458792705" ,"518201510" ,"494921441" ,"393879882" ,"332023892" ,"499502156" ,"426398707" ,"528518997" ,"514485964" ,"407960691" ,"436665663" ,"407960696" ,"524299475" ,"494819371" ,"535500004" ,"536686495" ,"398692281" ,"408468471" ,"504320207" ,"398692284" ,"398692284" ,"515143187" ,"399387522" ,"521096121" ,"380908399" ,"519074759" ,"485429891" ,"502855954" ,"528665762"};
		
		String startDate = "2012-07-21T09:26:43-0700";
		  
		List<ItunesApplication> result = itunesApplicationDao.pullLastestRecordsByAppIds(Arrays.asList(itunesAppIds), 0);
		
		Assert.assertFalse(result.isEmpty());
		
		Assert.assertTrue(result.size() > 0);
		
		for(ItunesApplication ia : result){
			System.out.println(ia.getItunesAppId());
		}
	}
	
	
	@Test
	public void testDroppedApp(){

		List<ItunesApplication> result = itunesApplicationDao.findDroppedApps(null, 200, 48);
		
		System.out.println("size:" + result.size());
		
		Assert.assertFalse(result.isEmpty());
		
		Assert.assertTrue(result.size() > 0);
	}
	
	@Test
	public void testNotHotApps(){

		List<ItunesApplication> result = itunesApplicationDao.findNotHotApps(null, 20, 1);
		
		System.out.println("size:" + result.size());
		
		Assert.assertFalse(result.isEmpty());
		
		Assert.assertTrue(result.size() > 0);
	}
	
	@Test
	public void testFindHotApp(){

		List<ItunesApplication> result = itunesApplicationDao.findHotApps(null, 20, 48);
		
		System.out.println("size:" + result.size());
		
		Assert.assertFalse(result.isEmpty());
		
		Assert.assertTrue(result.size() > 0);
	}
	
	@Test
	public void testFindAllNewApps(){

		List<ItunesApplication> result = itunesApplicationDao.findAllNewApps(null, 20, 1);
		
		System.out.println("size:" + result.size());
		
		Assert.assertFalse(result.isEmpty());
		
		Assert.assertTrue(result.size() > 0);
	}
	
	@Test
	public void testFindAppByCompanyName(){

		List<ItunesApplication> result = itunesApplicationDao.getAppsByCompanyName("espn", 1);
		
		System.out.println("size:" + result.size());
		
		Assert.assertFalse(result.isEmpty());
		
		Assert.assertTrue(result.size() > 0);
	}
	
	/*
	 * How to make a call to get values to graph a chart
	 */
	@Test
	public void testfindAppByAppId(){

		List<ItunesApplication> result = itunesApplicationDao.findAppByAppId("512939461", 48);
		
		System.out.println("size:" + result.size());
		
		for(ItunesApplication ia : result){
			System.out.println(ia.getMoved());
		}
		
		Assert.assertFalse(result.isEmpty());
		
		Assert.assertTrue(result.size() > 0);
	}
	
	

	@Test
	public void testfindTop300OfTheHour(){

		List<ItunesApplication> result = itunesApplicationDao.findTop300OfTheHour(48);
		
		System.out.println("size:" + result.size());
		
		Assert.assertFalse(result.isEmpty());
		
		Assert.assertTrue(result.size() > 0);
	}
	
	@Test
	public void testgetLastBatch(){
		List<ItunesApplication> result = itunesApplicationDao.getPaidLastBatch();
		
		System.out.println("size:" + result.size());
		
		Assert.assertFalse(result.isEmpty());
		
		Assert.assertTrue(result.size() > 0);
	}
	
}
