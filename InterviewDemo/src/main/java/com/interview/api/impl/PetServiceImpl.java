package com.interview.api.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import com.interview.api.PetException;
import com.interview.api.inter.PetService;
import com.interview.api.model.NewPet;
import com.interview.api.model.Pet;


@Component
public class PetServiceImpl implements PetService{
	
	static List<Pet> petValues = new ArrayList<Pet>();
	
	static Long petId = 1L;
	
	@Override
	public List<Pet> findPet(String tag, int limit){
		List<String> splitTag = Arrays.asList(tag.split(","));
		List<Pet> tagPet = petValues.stream().filter( p -> splitTag.contains(p.getTag())).collect(Collectors.toList());
		if(tagPet.isEmpty()) {
			throw new PetException("Pet not found");
		} else {
			if(limit!=0) {
				tagPet = tagPet.stream().limit(limit).collect(Collectors.toList());
			}
			return tagPet;
		}
	}
	
	@Override
	public Pet addPet(NewPet newPet) {
		boolean idExists = petValues.stream().anyMatch(t -> t.getName().equalsIgnoreCase(newPet.getName()));
		System.out.println("Pet with Name::"+newPet.getName()+"::"+idExists);
		
		if(idExists) {
			throw new PetException("Error in Pet Creation");
		} else {
			Pet pet = new Pet(newPet);
			pet.setId(petId);
			petId +=1;
			petValues.add(pet);
			return pet;
		}
    }
    
	@Override
	public Pet findPetById(Long id) {
    	Pet pet = petValues.stream().filter(pets -> pets.getId().equals(id))
    		  .findAny()
    		  .orElseThrow(() ->new PetException("Pet not found"));
    	return pet;
    }
    
	
	@Override
	public void deletePet(Long id) {
		boolean removed = petValues.removeIf(t -> t.getId() == id);
		if(!removed) {
			throw new PetException("Pet deletion failed");
		}
		
	}
 }

