package com.interview.api.inter;

import java.util.List;

import com.interview.api.model.NewPet;
import com.interview.api.model.Pet;

public interface PetService {
	
	
	public List<Pet> findPet(String tag, int limit);
	public Pet addPet(NewPet newPet);
	public Pet findPetById(Long id);
	public void deletePet(Long id);

}
