package com.apap.tutorial7.service;

import java.util.List;
import java.util.Optional;

import com.apap.tutorial7.model.CarModel;

/**
 * 
 * CarService
 *
 */
public interface CarService {
	Optional<CarModel> getCarDetailById(Long id);
	CarModel addCar(CarModel car);
	void deleteCar(CarModel car);
	CarModel findCarById(long id);
	void carUpdate(CarModel car, Long id);
	List<CarModel> sortDrHarga(Long dealerId);
	List<CarModel> viewAll();
}
