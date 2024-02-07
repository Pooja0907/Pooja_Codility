package com.petstore.api.test;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;
import com.petstore.api.endpoints.PetEndPoints;
import com.petstore.payload.Pet;

import io.restassured.response.Response;

public class PetRegressionSuite {

	Faker faker;
	Pet petPayload;
	Pet updatedPetPayload;

	public Logger logger;

	@BeforeClass
	public void setup() {
		faker = new Faker();
		petPayload = new Pet();
		petPayload.setId(faker.idNumber().hashCode());
		petPayload.setName(faker.name().firstName());
		
		updatedPetPayload =new Pet();
		updatedPetPayload.setId(faker.number().randomDigit());
		updatedPetPayload.setName(faker.name().firstName());

		// logs
		logger = LogManager.getLogger(this.getClass());
		logger.debug("debugging.....");
		
		

	}

	@Test(priority = 1)
	public void testAddPet() {
		logger.info("********** Creating pet  ***************");
		Response response = PetEndPoints.addPet(petPayload);
		response.then().log().all();
		int idn = response.path("id");
		String dogName=response.path("name");

		Assert.assertEquals(response.getStatusCode(), 200);

		logger.info("**********pet is created  ***************");
		Assert.assertEquals(idn,petPayload.getId());
		Assert.assertEquals(dogName,petPayload.getName());

	}

	@Test(priority = 2)
	public void testGetPetById() {
		logger.info("********** finding pet by id ***************");

		Response response = PetEndPoints.getPet(this.petPayload.getId());
		response.then().log().all();
		int dogId = response.path("id");
		String dogName=response.path("name");
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(dogId,petPayload.getId());
		Assert.assertEquals(dogName,petPayload.getName());

		logger.info("**********pet   is found ***************");

	}

	@Test(priority = 3)
	public void testUpdateExistingPet() {
		logger.info("********** data before updating the pet ***************");

		Response response = PetEndPoints.updatePet(petPayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);

		logger.info("********** updating pet  ***************");

		Response responseAfterupdate = PetEndPoints.updatePet(updatedPetPayload);

		logger.info("********** response after updating pet  ***************");

		responseAfterupdate.then().log().all();
		int updatedDogId=responseAfterupdate.path("id");
		String updatedDogName=responseAfterupdate.path("name");
		Assert.assertEquals(responseAfterupdate.getStatusCode(), 200);
		Assert.assertEquals(updatedDogId,updatedPetPayload.getId());
		Assert.assertEquals(updatedDogName,updatedPetPayload.getName());

	}

	@Test(priority = 4)
	public void testDeletePetById() {
		logger.info("**********   Deleting pet  ***************");

		Response response = PetEndPoints.deletePet(this.petPayload.getId());
		Assert.assertEquals(response.getStatusCode(), 200);

		logger.info("********** pet is  deleted ***************");
	}

}
