package com.interview.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;


import com.interview.api.PetException;
import com.interview.api.inter.PetService;
import com.interview.api.model.NewPet;
import com.interview.api.model.Pet;
import com.interview.api.model.PetError;


@RestController
//@RequestMapping(path="/api", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(path="/api")
public class PetServiceController {

	@Autowired
	PetService petService;
	
	@RequestMapping(path="/pets")
	@Description(value="")
	public @ResponseBody List<Pet> findPets(@RequestParam(name="tags", required=false) String tags,
    		@RequestParam(name="limit", required=false, defaultValue="0") int limit) {
		System.out.println("Find Pets By Tags-->>>>");
		return petService.findPet(tags, limit);
    }
	
	@RequestMapping(path="/pets",method=RequestMethod.POST)
    @Description(value="Creates a new pet in the store.  Duplicates are allowed")
	public @ResponseBody Pet addPet(@RequestBody @Valid NewPet newPet) {
		System.out.println("Add Pets -->>>>");
		return petService.addPet(newPet);
    }
    
	@RequestMapping(path ="/pets/{id}")
    @Description(value="Returns a user based on a single ID, if the user does not have access to the pet")
	public @ResponseBody Pet findPetById(@PathVariable("id") Long id) {
    	System.out.println("Find Pets By Id-->>>>"+id);
    	return petService.findPetById(id);
    }
    
	
	@DeleteMapping("/pets/{id}")
	@Description(value="deletes a single pet based on the ID supplied")
	@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "pet deleted")
	public void deletePet(@PathVariable(name="id", required=true) Long id){
		System.out.println("Delete Pets -->>>>");
		petService.deletePet(id);	
	}
	
    
	@ExceptionHandler(PetException.class)
	  public final ResponseEntity<PetError> handlePetNotFoundException(PetException ex, WebRequest request) {
	    PetError errorDetails = new PetError(ex.getMessage(),request.getDescription(false));
	    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<PetError> handlerValidationError(Exception ex, WebRequest request) {
	    PetError errorDetails = new PetError(ex.getLocalizedMessage(),request.getDescription(false));
	    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}}
