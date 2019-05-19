package com.model2.mvc.service.product.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;


/*
 *	FileName :  UserServiceTest.java
 * ㅇ JUnit4 (Test Framework) 과 Spring Framework 통합 Test( Unit Test)
 * ㅇ Spring 은 JUnit 4를 위한 지원 클래스를 통해 스프링 기반 통합 테스트 코드를 작성 할 수 있다.
 * ㅇ @RunWith : Meta-data 를 통한 wiring(생성,DI) 할 객체 구현체 지정
 * ㅇ @ContextConfiguration : Meta-data location 지정
 * ㅇ @Test : 테스트 실행 소스 지정
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/context-common.xml",
									"classpath:config/context-aspect.xml",
									"classpath:config/context-mybatis.xml",
									"classpath:config/context-transaction.xml"})
public class ProductServiceTest {

	//==>@RunWith,@ContextConfiguration 이용 Wiring, Test 할 instance DI
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	//@Test
	public void testAddProduct() throws Exception {
		
		Product product = new Product();
		product.setProdName("의자");
		product.setManuDate("20190312");
		product.setPrice(20000);
		product.setProdDetail("상태 양호");
		product.setFileName("");		
		
		productService.addProduct(product);
		
		product = productService.getProduct(product.getProdNo());

		//==> console 확인
		//System.out.println(user);
		
		//==> API 확인
		Assert.assertEquals("의자", product.getProdName());
		Assert.assertEquals("20190312", product.getManuDate());
		Assert.assertEquals(20000, product.getPrice());
		Assert.assertEquals("상태 양호", product.getProdDetail());
	}
	
	@Test
	public void testGetProduct() throws Exception {
		
		Product product = new Product();
		
//		product.setProdName("의자");
//		product.setManuDate("20190312");
//		product.setPrice(20000);
//		product.setProdDetail("상태 양호");
//		product.setFileName("");
		
		product = productService.getProduct(10081);

		//==> console 확인
		//System.out.println(user);
		
		//==> API 확인
		Assert.assertEquals("핸드폰케이스", product.getProdName());
		Assert.assertEquals("20190415", product.getManuDate());
		Assert.assertEquals(11000, product.getPrice());
		Assert.assertEquals("새 제품", product.getProdDetail());
		Assert.assertEquals("aaaaa", product.getFileName());
		
	}


	//@Test
	 public void testUpdateProduct() throws Exception{
		 
		Product product = productService.getProduct(10081);
		Assert.assertNotNull(product);
		
		Assert.assertEquals("의자", product.getProdName());
		Assert.assertEquals("20190312", product.getManuDate());
		Assert.assertEquals(20000, product.getPrice());
		Assert.assertEquals("상태 양호", product.getProdDetail());
		Assert.assertEquals(null, product.getFileName());
		
		product.setProdNo(10081);
		product.setProdName("핸드폰케이스");
		product.setManuDate("20190415");
		product.setPrice(11000);
		product.setProdDetail("새 제품");
		product.setFileName("aaaaa");
		
		productService.updateProduct(product);
		
		product = productService.getProduct(10081);
		Assert.assertNotNull(product);
		
		//==> console 확인
		//System.out.println(user);
			
		//==> API 확인
		Assert.assertEquals("핸드폰케이스", product.getProdName());
		Assert.assertEquals("20190415", product.getManuDate());
		Assert.assertEquals(11000, product.getPrice());
		Assert.assertEquals("새 제품", product.getProdDetail());
		Assert.assertEquals("aaaaa", product.getFileName());
	 }
	 
	 //==>  주석을 풀고 실행하면....
	 //@Test
	 public void testGetProductListAll() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(5);
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(5, list.size());
	 	
		//==> console 확인
	 	//System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setCurrentPage(1);
	 	search.setPageSize(5);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("");
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(5, list.size());
	 	
	 	//==> console 확인
	 	//System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
	 
	 //@Test
	 public void testGetProductListByProductNo() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("10000");
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(1, list.size());
	 	
		//==> console 확인
	 	//System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("111");
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
		//==> console 확인
	 	//System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
	 
	//@Test
	 public void testGetProductListByProductName() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword("자");
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	//Assert.assertEquals(3, list.size());
	 	
		//==> console 확인
	 	System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	Assert.assertEquals(6, map.get("totalCount"));
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
//	 	search.setSearchCondition("1");
//	 	search.setSearchKeyword("뷰");
//	 	map = productService.getProductList(search);
//	 	
//	 	list = (List<Object>)map.get("list");
//	 	Assert.assertEquals(0, list.size());
	 	
		//==> console 확인
	 	System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
	 
 	//@Test
	 public void testGetProductListByProductPrice() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("2");
	 	search.setSearchKeyword("20");
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
		//==> console 확인
	 	System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	Assert.assertEquals(5, map.get("totalCount"));
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
//		 	search.setSearchCondition("1");
//		 	search.setSearchKeyword("뷰");
//		 	map = productService.getProductList(search);
//		 	
//		 	list = (List<Object>)map.get("list");
//		 	Assert.assertEquals(0, list.size());
	 	
		//==> console 확인
	 	System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
 
}