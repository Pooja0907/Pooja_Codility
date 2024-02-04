package com.petstore.api.endpoints;

import static io.restassured.RestAssured.given;

import java.util.ResourceBundle;

import com.petstore.payload.Pet;

import io.restassured.http.ContentType;
import io.restassured.response.Response;


public class PetEndPoints {

		
		static ResourceBundle getURL()
		{
			ResourceBundle routes= ResourceBundle.getBundle("routes"); 
			return routes;
		}
	
	
	//method to create a new pet
		public static Response addPet(Pet payload)
		{
			String post_url=getURL().getString("post_url");
			
			
			Response response=given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(payload)
			.when()
				.post(post_url);
				
			return response;
		}
		
		//find the pet by Id
		public static Response getPet(int petId)
		{
			String get_url=getURL().getString("get_url");
			
			
			Response response=given()
							.pathParam("petId",petId)
			.when()
				.get(get_url);
				
			return response;
		}
		
		//update an existing pet
		public static Response updatePet(Pet payload)
		{
			String update_url=getURL().getString("update_url");
			
			Response response=given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)			
				.body(payload)
			.when()
				.put(update_url);
				
			return response;
		}
		
		//delete the pet by its id
		public static Response deletePet(int petId)
		{
			String delete_url=getURL().getString("delete_url");
			
			Response response=given()
							.pathParam("petId",petId)
			.when()
				.delete(delete_url);
				
			return response;
		}	
				
		
}
