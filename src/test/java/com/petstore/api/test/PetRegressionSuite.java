package com.petstore.api.test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.github.javafaker.Faker;
import com.petstore.api.endpoints.PetEndPoints;
import com.petstore.payload.Pet;

import io.restassured.response.Response;

public class PetRegressionSuite {

	Faker faker;
	Pet petPayload;

	public Logger logger;

	@BeforeClass
	public void setup() {
		faker = new Faker();
		petPayload = new Pet();

		petPayload.setId(faker.idNumber().hashCode());

		petPayload.setName(faker.name().firstName());

		// logs
		logger = LogManager.getLogger(this.getClass());
		logger.debug("debugging.....");
		

	}

	@Test(priority = 1)
	public void testAddPet() {
		logger.info("********** Creating pet  ***************");
		Response response = PetEndPoints.addPet(petPayload);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200);

		logger.info("**********pet is created  ***************");
		

	}

	@Test(priority = 2)
	public void testGetPetById() {
		logger.info("********** finding pet by id ***************");

		Response response = PetEndPoints.getPet(this.petPayload.getId());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);

		logger.info("**********pet   is found ***************");

	}

	@Test(priority = 3)
	public void testUpdateExistingPet() {
		logger.info("********** Updating pet ***************");

		Response response = PetEndPoints.updatePet(petPayload);
		response.then().log().body();

		Assert.assertEquals(response.getStatusCode(), 200);

		logger.info("********** pet updated ***************");

		Response responseAfterupdate = PetEndPoints.getPet(this.petPayload.getId());
		Assert.assertEquals(responseAfterupdate.getStatusCode(), 200);

	}

	@Test(priority = 4)
	public void testDeletePetById() {
		logger.info("**********   Deleting pet  ***************");

		Response response = PetEndPoints.deletePet(this.petPayload.getId());
		Assert.assertEquals(response.getStatusCode(), 200);

		logger.info("********** pet is  deleted ***************");
	}

}
