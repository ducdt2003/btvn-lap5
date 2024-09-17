package lap_5.service;

import lap_5.entities.Pet;
import lap_5.utils.TYPE;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class PetService {

    private ArrayList<Pet> pets;

    public PetService(ArrayList<Pet> pets) {
        this.pets = pets;
    }


    public void addInitialPets() {
        pets.add(new Pet(1, "Cún", "Bulldog", 3, "Female", "Hung dữ", TYPE.DOG, "richter.jpg"));
        pets.add(new Pet(2, "Jack", "Bulldog", 4, "Male", "Hung dữ", TYPE.DOG, "florentino.jpg"));
        pets.add(new Pet(3, "Haki", "Simpa", 2, "Female", "hiền lành", TYPE.DOG, "omen.jpg"));
        pets.add(new Pet(4, "Yena", "Siamese", 3, "Male", "Hoàng Thượng", TYPE.CAT, "yena.jpg"));
    }

    public void match(Scanner scanner, ArrayList<Pet> pets) {
        System.out.println("Nhập thông tin pet của bạn:");
        System.out.print("ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Species: ");
        String species = scanner.nextLine();
        System.out.print("Age: ");
        int age = Integer.parseInt(scanner.nextLine());
        System.out.print("Sex: ");
        String sex = scanner.nextLine();
        System.out.print("Description: ");
        String description = scanner.nextLine();
        System.out.print("Type (DOG/CAT): ");
        // nhận 1 chuổi và trả về kiê giá trị tương ứng
        TYPE type = TYPE.valueOf(scanner.nextLine().toUpperCase());
        System.out.print("Images: ");
        String images = scanner.nextLine();
        Pet userPet = new Pet(id, name, species, age, sex, description, type, images);
        random(scanner, userPet);
    }

    public void random(Scanner scanner, Pet userPet){
        Random random = new Random();
        while (true) {
            Pet matchedPet = pets.get(random.nextInt(pets.size()));
            if (matchedPet.getType() == userPet.getType() && !matchedPet.getSex().equals(userPet.getSex())) {
                System.out.println("Pet phù hợp: " + matchedPet);
                System.out.print("Bạn có muốn tìm pet khác không? (Y/N): ");
                String choice = scanner.nextLine();
                if (choice.equalsIgnoreCase("Y")) {
                    System.out.println("Tiếp tục");
                } else {
                    System.out.println("Thoát Chương Trình");
                    break;
                }
            }
        }
    }
}

