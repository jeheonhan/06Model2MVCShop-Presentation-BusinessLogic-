package com.model2.mvc.web.product;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

@Controller
public class ProductController {
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	public ProductController() {
	}
	
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	@RequestMapping("/addProduct.do")
	public String addProduct(@RequestParam(value="file", required=false) MultipartFile imgFile,
								@ModelAttribute("pvo") Product product) throws Exception {
		
		System.out.println("/addProduct");
		
		String temDir = 
				"C:\\workspace\\06.Model2MVCShop(Presentation+BusinessLogic)\\WebContent\\images\\uploadFiles\\";
		
		String manuDate = product.getManuDate();
		manuDate = manuDate.replaceAll("-", "");
		product.setManuDate(manuDate);
		
		if(!imgFile.isEmpty()) {
			if(imgFile.getSize() < 1000000) {
				
			String fileName = imgFile.getOriginalFilename();
			product.setFileName(fileName);
			try {
				File uploadedFile = new File(temDir, fileName);
				imgFile.transferTo(uploadedFile);
			}catch(Exception e) {
				System.out.println(e);
			}
			
			}else {
				System.out.println("파일용량 초과!!!");
			}
		}
		
		productService.addProduct(product);
		
		return "forward:/product/addProduct.jsp";
	}
	
	@RequestMapping("/getProduct.do")
	public String getProduct(@RequestParam("prodNo") int prodNo,
							@RequestParam("menu") String menu, Model model,
							@CookieValue(value="history", required=false) String history,
							HttpServletResponse response) throws Exception {
		
		System.out.println("==================== /getProduct 시작  ================");
		
		if(history != null) {
			if(!history.contains(""+prodNo+"")){		//쿠키 중복값 넣지 않게
			history += ","+prodNo;
			}
		}else {
			history = ","+prodNo;
		}		
		Cookie cookie = new Cookie("history", history);		
		response.addCookie(cookie);		
		
		Product product = productService.getProduct(prodNo);		
		model.addAttribute("pvo", product);
		
		if(menu.equals("manage")) {			
			return "forward:/product/updateProduct.jsp";			
		}else {
			return "forward:/product/getProduct.jsp";			
		}	
		
	}
	
	@RequestMapping("/updateProduct.do")
	public String updateProduct(@ModelAttribute("product") Product product, 
								@RequestParam("prodNo") int prodNo,
								@RequestParam("manuDate") String manuDate) throws Exception{
		
		System.out.println("/updateProduct");	
		
		manuDate = manuDate.replaceAll("-", "");
		product.setManuDate(manuDate);
		
		productService.updateProduct(product);
		
		product = productService.getProduct(prodNo);
		
		System.out.println("updatePurchase 수정된 내용 확인 : "+product);
		
		
		return "redirect:/getProduct.do?prodNo="+prodNo+"&menu=confirm";
	}
	
	@RequestMapping("/listProduct.do")
	public String listProduct(@ModelAttribute("search") Search search , Model model , 
								HttpServletRequest request) throws Exception {
		
		System.out.println("/listProduct 시작=========");
		
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		Map<String , Object> map = productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);		
		
		
		return "forward:/product/listProduct.jsp";
	}
	

}
