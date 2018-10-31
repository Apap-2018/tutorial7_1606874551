package com.apap.tutorial7.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.apap.tutorial7.model.CarModel;
import com.apap.tutorial7.service.CarService;
import com.apap.tutorial7.service.DealerService;

@RestController
@RequestMapping("/car")
public class CarController {
	@Autowired
	private CarService carService;

	@Autowired
	private DealerService dealerService;


	@GetMapping()
	private List<CarModel> viewAllCar(Model model){
		return carService.viewAll();
	}

	@GetMapping(value = "/{carId}")
	private CarModel viewCar(@PathVariable("carId") long carId, Model model) {
		return carService.getCarDetailById(carId).get();
	}

	@DeleteMapping(value = "/delete/{carId}")
	private String deleteCar(@PathVariable ("carId") long carId, Model model) {
		CarModel car = carService.getCarDetailById(carId).get();
		carService.deleteCar(car);
		return "car has been deleted";
	}

	@PostMapping(value = "/add")
	private CarModel addCar(@RequestBody CarModel car) {
		return carService.addCar(car);
	}

	@PutMapping(value = "/{carId}")
	private String updateCar(@PathVariable ("carId") long carId,
			@RequestParam(value = "brand", required = false) String brand,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "price", required = false) String price,
			@RequestParam(value = "amount", required = false) String  amount,
			@RequestParam(value = "dealerId", required = false) String dealerId) {

		CarModel car = carService.getCarDetailById(carId).get();
		if(car.equals(null)){
			return "Couldn't find your dealer";
		}

		if (brand != null){
			car.setBrand(brand);
		}

		if (type != null){
			car.setType(type);
		}

		if (price != null){
			car.setPrice(Long.parseLong(price));
		}

		if (amount != null){
			car.setAmount(Integer.parseInt(amount));
		}

		if (dealerId !=null){
			car.setDealer(dealerService.getDealerDetailById(Long.parseLong(dealerId)).get());
		}

		carService.addCar(car);
		return "car update success";
	}
}