package com.interview;

import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.interview.api.model.NewPet;
import com.interview.api.model.Pet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PetServiceControllerTest extends AbstractTest {

	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void testAddPets() throws Exception {
		String uri = "/api/pets";
		NewPet pet = new NewPet();
		pet.setName("TestingPet");
		pet.setTag("testtags");
		String inputJson = super.mapToJson(pet);
		
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(mvcResult.getResponse().getContentType(),MediaType.APPLICATION_JSON_UTF8_VALUE);
		JSONAssert.assertEquals("{\"name\":\"TestingPet\",\"tag\":\"testtags\"}", content, JSONCompareMode.LENIENT);
	}

	
	@Test
	public void testAddPetsEmptyName() throws Exception {
		String uri = "/api/pets";
		NewPet pet = new NewPet();
		String inputJson = super.mapToJson(pet);
		
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(404, status);
		
	}

	
	@Test
	public void testAddPetsDuplication() throws Exception {
		String uri = "/api/pets";
		NewPet pet = new NewPet();
		pet.setName("TestingPet");
		pet.setTag("testtags");
		String inputJson = super.mapToJson(pet);
		
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON).content(inputJson))
				.andReturn();
		
		 mvcResult = mvc.perform(
					MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON).content(inputJson))
					.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(404, status);
		String content = mvcResult.getResponse().getContentAsString();
		JSONAssert.assertEquals("{\"message\": \"Error in Pet Creation\"}", content, JSONCompareMode.LENIENT);
	}


	@Test
	public void testFindPetsById() throws Exception {
		String uri = "/api/pets/2";
		
		/**Add More pets**/
		String uriPost = "/api/pets";
		NewPet pet = new NewPet();
		pet.setName("TestingPet2");
		pet.setTag("testtags2");
		String inputJson = super.mapToJson(pet);
		mvc.perform(MockMvcRequestBuilders.post(uriPost).contentType(MediaType.APPLICATION_JSON).content(inputJson))
		.andReturn();
		/**Add More pets**/
		
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		JSONAssert.assertEquals("{\"id\":2}", content, JSONCompareMode.LENIENT);
		
	}
	
	@Test
	public void testFindPetsByIdFail() throws Exception {
		String uri = "/api/pets/20";
		
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(404, status);
		String content = mvcResult.getResponse().getContentAsString();
		JSONAssert.assertEquals("{\"message\":\"Pet not found\"}", content, JSONCompareMode.LENIENT);
	}

	
	
	@Test
	public void testfindPets() throws Exception {
		String uri = "/api/pets?tags=testtags";
		
		/**Add More pets**/
		String uriPost = "/api/pets";
		NewPet pet = new NewPet();
		pet.setName("TestingPet1");
		pet.setTag("testtags");
		String inputJson = super.mapToJson(pet);
		mvc.perform(MockMvcRequestBuilders.post(uriPost).contentType(MediaType.APPLICATION_JSON).content(inputJson))
				.andReturn();
		/**Add More pets**/
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		
		String content = mvcResult.getResponse().getContentAsString();
		Pet[] petList = super.mapFromJson(content, Pet[].class);
		assertTrue(petList.length > 0);

	}
	
	@Test
	public void testfindPetsWithLimit() throws Exception {
		String uri = "/api/pets?tags=testtags&limit=1";
		
		/**Add More pets**/
		String uriPost = "/api/pets";
		NewPet pet = new NewPet();
		pet.setName("TestingPet1");
		pet.setTag("testtags");
		String inputJson = super.mapToJson(pet);
		mvc.perform(MockMvcRequestBuilders.post(uriPost).contentType(MediaType.APPLICATION_JSON).content(inputJson))
				.andReturn();
		/**Add More pets**/
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		
		String content = mvcResult.getResponse().getContentAsString();
		Pet[] petList = super.mapFromJson(content, Pet[].class);
		assertTrue(petList.length ==1);

	}



	@Test
	public void testfindPetsFail() throws Exception {
		String uri = "/api/pets?tags=testtagsFail";
		
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		System.out.println("status" + status);
		
		assertEquals(404, status);
		String content = mvcResult.getResponse().getContentAsString();
		JSONAssert.assertEquals("{\"message\":\"Pet not found\"}", content, JSONCompareMode.LENIENT);
	}
	
	@Test
	public void testfindPetsMultipleTags() throws Exception {
		String uri = "/api/pets?tags=testtags,tag2";
		
		/**Add More pets**/
		String uriPost = "/api/pets";
		NewPet pet = new NewPet();
		pet.setName("TestingPet1");
		pet.setTag("testtags");
		String inputJson = super.mapToJson(pet);
		mvc.perform(MockMvcRequestBuilders.post(uriPost).contentType(MediaType.APPLICATION_JSON).content(inputJson))
				.andReturn();
		
		pet = new NewPet();
		pet.setName("TestingPet2");
		pet.setTag("tag2");
		inputJson = super.mapToJson(pet);
		mvc.perform(MockMvcRequestBuilders.post(uriPost).contentType(MediaType.APPLICATION_JSON).content(inputJson))
				.andReturn();
		/**Add More pets**/
		
		
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		System.out.println("status" + status);
		
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Pet[] petList = super.mapFromJson(content, Pet[].class);
		assertTrue(petList.length > 0);
	}



	@Test
	public void testDeletePets() throws Exception {
		String uri = "/api/pets/1";

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri).accept(MediaType.APPLICATION_JSON))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		System.out.println("---->>>>" + status);
		assertEquals(204, status);
		
		String content = mvcResult.getResponse().getErrorMessage();
		System.out.println("Delete Pets by ID---->>>>" + content);
		assertEquals(content, "pet deleted");
	}
	
	
	
	@Test
	public void testDeletePetsFail() throws Exception {
		String uri = "/api/pets/30";

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri).accept(MediaType.APPLICATION_JSON))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(404, status);
		String content = mvcResult.getResponse().getContentAsString();
		System.out.println("Delete Pets by ID---->>>>" + content);
		JSONAssert.assertEquals("{\"message\":\"Pet deletion failed\"}", content, JSONCompareMode.LENIENT);
	}


}