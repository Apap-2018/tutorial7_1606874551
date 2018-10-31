package com.apap.tutorial7.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tutorial7.model.CarModel;
import com.apap.tutorial7.repository.CarDb;

/**
 * 
 * CarServiceImpl
 *
 */
@Service
@Transactional
public class CarServiceImpl implements CarService{
	@Autowired
	private CarDb carDb;
	
	@Override
	public CarModel addCar(CarModel car) {
		return carDb.save(car);
	}
	
	@Override
	public void carUpdate(CarModel updateCar, Long carId) {
		CarModel dataLama = carDb.findById(carId).get();
		dataLama.setBrand(updateCar.getBrand());
		dataLama.setType(updateCar.getType());
		dataLama.setAmount(updateCar.getAmount());
		dataLama.setPrice(updateCar.getPrice());
		carDb.save(dataLama);
	}
	
	@Override
	public CarModel findCarById(long id) {
		return carDb.findById(id).get();
		
	}
	
	@Override
	public List<CarModel> sortDrHarga(Long dealerId){
		return carDb.findByDealerIdOrderByPriceDesc(dealerId);
	}

	@Override
	public Optional<CarModel> getCarDetailById(Long id) {
		// TODO Auto-generated method stub
		return carDb.findById(id);
	}

	@Override
	public void deleteCar(CarModel car) {
		// TODO Auto-generated method stub
		carDb.delete(car);
	}

	@Override
	public List<CarModel> viewAll() {
		// TODO Auto-generated method stub
		return carDb.findAll();
	}
}
