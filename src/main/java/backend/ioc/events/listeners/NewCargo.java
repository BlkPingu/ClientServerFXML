package backend.ioc.events.listeners;

import backend.enums.Hazard;
import backend.storage.administration.Administration;
import backend.storage.administration.Customer;
import backend.storage.administration.Warehouse;
import backend.storage.cargo.*;
import userinterface.dialogs.Dialogs;
import backend.ioc.events.listeners.interfaces.InputEventListener;
import backend.ioc.events.InputEvent;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

public class NewCargo implements InputEventListener {

    Scanner scanner = new Scanner(System.in);



    public Set<Hazard> enterHazards() {

        Set<Hazard> hazards = new HashSet<>();
        boolean done = false;

        while (!done) {
            Dialogs.hazards();

            System.out.print("Enter (0-4): ");
            int choice;
            try{
                choice = scanner.nextInt();

            switch (choice) {
                case 0:
                    done = true;
                    break;
                case 1:
                    hazards.add(Hazard.flammable);
                    System.out.println("flammable added");
                    System.out.println();
                    break;
                case 2:
                    hazards.add(Hazard.toxic);
                    System.out.println("toxic added");
                    System.out.println();
                    break;
                case 3:
                    hazards.add(Hazard.radioactive);
                    System.out.println("radioactive added");
                    System.out.println();
                    break;
                case 4:
                    hazards.add(Hazard.explosive);
                    System.out.println("explosive added");
                    System.out.println();
                    break;
                default:
                    System.out.println("Incorrect Input");
                    System.out.println();
                    break;

                }
            }catch(InputMismatchException ime){

            }
        }
        return hazards;
    }
    public int enterSize() throws InputMismatchException{
        int size = 0;
        boolean repeat = true;
        while(repeat)
            try{
                System.out.print("Enter Size: ");
                size = scanner.nextInt();
                repeat = false;
            }catch(InputMismatchException ime){
                System.out.println("Wrong Input! Only Integers allowed");
                scanner.next();
            }

        return size;
    }


    public void newCargo(Warehouse warehouse, Administration administration) {
        specificCargo(enterSize(), addCustomerToCargo(), enterHazards(), administration);
    }

    public void specificCargo(int size, Customer owner, Set<Hazard> hazards, Administration administration){
        Dialogs.cargoType();

        System.out.print("Enter (0-6): ");
        int choice = scanner.nextInt();
        switch (choice) {
            case 0:
                  administration.cargoList.add(new BoxedCargo(size, owner, hazards, setFragile()));
                break;
            case 1:
                 administration.cargoList.add(new DryCargo(size, owner, hazards, setSolid()));
                break;
            case 2:
                administration.cargoList.add(new LiquidCargo(size, owner, hazards, setPressurized()));

                break;
            case 3:
                administration.cargoList.add(new MixedDryLiquid(size, owner, hazards, setSolid(), setPressurized()));
                break;
            case 4:
                administration.cargoList.add(new MixedDryBoxed(size, owner, hazards,setFragile(), setSolid()));
                break;
            case 5:
                administration.cargoList.add(new MixedLiquidBoxed(size, owner, hazards, setFragile(), setPressurized()));
                break;
            case 6:
                administration.cargoList.add(new MixedDryLiquidBoxed(size, owner, hazards, setFragile(), setSolid(), setPressurized()));
                break;
            default:
                System.out.println("Incorrect Input");
                break;
        }
        System.out.println("Cargo added to Cargo List");
    }


    /**
     * @return
     * Set attribute is a helper method for setSolid, setFragile and setressurized.
     * It takes user input y or Y to return boolean true and false on otherwise.
     */
    public Boolean setAttribute(){
        String choice = scanner.next();
        if(choice.equals("y") || choice.equals("Y")){
            System.out.println("Set true.");
            return true;
        }else {
            System.out.println("Set false.");
            return false;
        }
    }

    /**
     * @return
     * returns setAttribute paired with specific console output to
     */
    public Boolean setSolid(){
        System.out.print("Cargo solid [y/Y = true // AnyKey == False]: ");
        return setAttribute();
    }
    /**
     * @return
     * returns setAttribute paired with specific console output to
     */
    public Boolean setFragile(){
        System.out.println("Cargo fragile [y/Y = true // AnyKey == False]: ");
        return setAttribute();
    }
    /**
     * @return
     * returns setAttribute paired with specific console output to
     */
    public Boolean setPressurized(){
        System.out.println("Cargo pressurized [y/Y = true // AnyKey == False]: ");
        return setAttribute();
    }


    /**
     * @return
     * creates new Customer with name for a backend.storage.cargo instance
     */
    public Customer addCustomerToCargo(){
        System.out.print("New Owner Name: ");
        String name = scanner.next();
        System.out.println();
        return new Customer(name);
    }


    /**
     * @param warehouse
     * @param event
     * Executes newCargo() on Event "new" as userinput
     */
    @Override
    public void onInputEvent(Administration administration, InputEvent event) {
        if (null != event.getText() && event.getText().equals("new")){
            System.out.println("Input => '" + event.getText() + "'");
            newCargo(administration.warehouses.get(0), administration);
        }else{new NullPointerException();}
    }
}
