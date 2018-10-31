package com.apap.tutorial7.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.apap.tutorial7.model.DealerModel;
import com.apap.tutorial7.rest.DealerDetail;
import com.apap.tutorial7.rest.Setting;
import com.apap.tutorial7.service.DealerService;

/**
 * 
 * DealerController
 *
 */
@RestController
@RequestMapping("/dealer")
public class DealerController {
	@Autowired
	private DealerService dealerService;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Bean
	public RestTemplate rest() {
	return new RestTemplate();
	}
	
	@GetMapping(value = "/{dealerId}")
	private DealerModel viewDealer(@PathVariable ("dealerId") long dealerId, Model model ) {
		return dealerService.getDealerDetailById(dealerId).get();
	}
	
	@PostMapping(value = "/add")
	private DealerModel addDealerSubmit(@RequestBody DealerModel dealer) {
		return dealerService.addDealer(dealer);
	}
	
	@DeleteMapping(value = "/delete")
	private String deleteDealer(@RequestParam("dealerId") long dealerId, Model model) {
		DealerModel dealer = dealerService.getDealerDetailById(dealerId).get();
		dealerService.deleteDealer(dealer);
		return "delete";
	}
	
	@PutMapping(value = "/{id}")
	private String updateDealerSubmit(
			@PathVariable (value = "id") long id,
			@RequestParam("alamat") String alamat,
			@RequestParam("telp") String telp) {
		DealerModel dealer = (DealerModel) dealerService.getDealerDetailById(id).get();
		if(dealer.equals(null) ) {
			return "Couldn't find your dealer";
		}
		dealer.setAlamat(alamat);
		dealer.setNoTelp(telp);
		dealerService.dealerUpdate(dealer, id);
		return "update success";
	}
	
	@GetMapping()
	private List<DealerModel> viewAllDealer(Model model) {
		return dealerService.viewAllDealer();
	}
	
	@GetMapping(value = "/status/{dealerId}")
	private String getStatus(@PathVariable ("dealerId") long dealerId) throws Exception {
		String path = Setting.dealerUrl + "dealer?id=" + dealerId;
		return restTemplate.getForEntity(path, String.class).getBody();
	}
	
	@GetMapping(value = "/full/{dealerId}")
	private DealerDetail poststatus (@PathVariable ("dealerId") long dealerId) throws Exception {
	String path = Setting.dealerUrl + "/dealer";
	DealerModel dealer = dealerService.getDealerDetailById(dealerId).get();
	DealerDetail detail = restTemplate.postForObject(path, dealer, DealerDetail.class);
	return detail;
	
	
	
	
//	@RequestMapping(value = "/dealer/update/{dealerId}", method = RequestMethod.GET)
//	private String update(@PathVariable(value = "dealerId") Long dealerId, Model model) {
//		DealerModel archive = dealerService.getDealerDetailById(dealerId).get();
//		
//		model.addAttribute("dealer", archive);
//		return "update-dealer";
//	}
//		
//		
//	@RequestMapping(value = "/dealer/update/{dealerId}", method = RequestMethod.POST)
//	private String updateDealerSubmit(@PathVariable(value = "dealerId") Long dealerId, @ModelAttribute DealerModel dealer) {
//		dealerService.dealerUpdate(dealer, dealerId);
//		return "update";
//		
//	}
//	@RequestMapping(value = "/dealer/view/all", method = RequestMethod.GET)
//	private String viewAllDealer(Model model) { 
//		DealerDb dealerRepo = dealerService.viewAllDealer();
//		List<DealerModel> listDealer = dealerRepo.findAll();
//		model.addAttribute("listDealer",listDealer);
//		
//		return "view-all";
//	}
//	@RequestMapping(value = "/dealer/view", method = RequestMethod.GET)
//	public String viewDealer(String dealerId, Model model) {
//		
//		Long id = Long.parseLong(dealerId);
//		DealerModel archive = dealerService.getDealerDetailById(id).get();
//		List<CarModel>carList = carService.sortDrHarga(id);
//		
//		model.addAttribute("list", carList);
//		model.addAttribute("dealer", archive);
//		return "view-dealer";
//	}
	
//	@Autowired
//	private CarService carService;
//	
//	@RequestMapping("/")
//	private String home() {
//		return "home";
//	}
	
//	@RequestMapping(value = "/dealer/add", method = RequestMethod.GET)
//	private String add(Model model) {
//		model.addAttribute("dealer", new DealerModel());
//		return "addDealer";
//	}
	}
}
