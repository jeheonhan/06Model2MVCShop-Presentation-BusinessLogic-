package com.model2.mvc.service.purchase.test;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/context-common.xml",
									"classpath:config/context-aspect.xml",
									"classpath:config/context-mybatis.xml",
									"classpath:config/context-transaction.xml"})

public class PurchaseServiceTest {
	
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	
//	@Test
	public void addPurchaseTest() throws Exception {
		
		Purchase purchase = new Purchase();
		Product product = new Product();
		User user = new User();
		
//		product.setProdNo(10023);
//		user.setUserId("user01");
//		
//		purchase.setPurchaseProd(product);
//		purchase.setBuyer(user);
//		purchase.setPaymentOption("100");
//		purchase.setReceiverName("가나다");
//		purchase.setReceiverPhone("010-1234-1234");
//		purchase.setDivyAddr("충남천안");
//		purchase.setDivyRequest("빠른배송");
//		purchase.setTranCode("111");
//		purchase.setDivyDate("2019-05-10");
//		
//		purchaseService.addPurchase(purchase);
		
		purchase = purchaseService.getPurchase2(10023);
		
		Assert.assertEquals("100", purchase.getPaymentOption());
		Assert.assertEquals("user01", purchase.getBuyer().getUserId());
		Assert.assertEquals(10023, purchase.getPurchaseProd().getProdNo());
		Assert.assertEquals("가나다", purchase.getReceiverName());
		
		
	}
	
//	@Test
	public void getPurchaseTest() throws Exception {
		
		Purchase purchase = new Purchase();
		
		purchase = purchaseService.getPurchase(10000);
		
		Assert.assertEquals("100", purchase.getPaymentOption());
		Assert.assertEquals("user01", purchase.getBuyer().getUserId());
		Assert.assertEquals(10082, purchase.getPurchaseProd().getProdNo());
		Assert.assertEquals("SCOTT", purchase.getReceiverName());
		Assert.assertEquals("010-1111-1111", purchase.getReceiverPhone());
		Assert.assertEquals("서울 강남", purchase.getDivyAddr());		
		
	}
	
//	@Test
	public void getPurchase2Test() throws Exception{
		
		Purchase purchase = new Purchase();
		
		purchase = purchaseService.getPurchase2(10082);
		
		Assert.assertEquals("100", purchase.getPaymentOption());
		Assert.assertEquals("user01", purchase.getBuyer().getUserId());
		Assert.assertEquals(10082, purchase.getPurchaseProd().getProdNo());
		Assert.assertEquals("SCOTT", purchase.getReceiverName());
		Assert.assertEquals("010-1111-1111", purchase.getReceiverPhone());
		Assert.assertEquals("서울 강남", purchase.getDivyAddr());
		Assert.assertEquals("333", purchase.getTranCode());
	}
	
//	@Test
	public void updatePurchaseTest() throws Exception{
		
		Purchase purchase = new Purchase();
		
		purchase.setTranNo(10006);
		purchase.setPaymentOption("101");
		purchase.setReceiverName("홍길동");
		purchase.setReceiverPhone("010-2222-2222");
		purchase.setDivyAddr("경기수원");
		purchase.setDivyRequest("빠른배송");
		purchase.setDivyDate("2019-05-08");
		
		purchaseService.updatePurcahse(purchase);
		
		purchase = purchaseService.getPurchase(10006);
		
		Assert.assertEquals("101", purchase.getPaymentOption());
		Assert.assertEquals("user01", purchase.getBuyer().getUserId());
		Assert.assertEquals(10084, purchase.getPurchaseProd().getProdNo());
		Assert.assertEquals("홍길동", purchase.getReceiverName());
		Assert.assertEquals("010-2222-2222", purchase.getReceiverPhone());
		Assert.assertEquals("경기수원", purchase.getDivyAddr());
		Assert.assertEquals("333", purchase.getTranCode());
		
	}
	
//	@Test
	public void updateTranCode() throws Exception {
		
		Purchase purchase = new Purchase();
		
		purchase.setTranNo(10007);
		purchase.setTranCode("111");
		
		purchaseService.updateTranCode(purchase);
		
		purchase = purchaseService.getPurchase(10007);
		
		Assert.assertEquals("111", purchase.getTranCode());
	}
	
//	@Test
	public void getPurchaseList() throws Exception{
		
		Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(5);
	 	
	 	Map<String,Object> map = purchaseService.getPurchaseList(search, "user000");
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(0, list.size());
	}

}
