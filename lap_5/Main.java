package lap_5;

import lap_5.entities.Pet;
import lap_5.service.PetService;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Pet> pets = new ArrayList<>();
        PetService petService = new PetService(pets);
        petService.addInitialPets();
        petService.match(scanner, pets);
    }
}
