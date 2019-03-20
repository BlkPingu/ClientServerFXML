package userinterface.dialogs;

import backend.storage.administration.Warehouse;
import backend.storage.cargo.Cargo;

public class Dialogs {

    public static void options() {
        System.out.println("Options for Cargo:");
        System.out.println();
        System.out.println("> delete");
        System.out.println("> new");
        System.out.println("> printall");
        System.out.println("> store");
        System.out.println("> exit ");
    }

    public static void cargoType() {
        System.out.println("[1]  BoxedCargo");
        System.out.println("[2]  DryCargo");
        System.out.println("[3]  LiquidCargo");
        System.out.println("[4]  MixedDryBoxedCargo");
        System.out.println("[5]  MixedDryLiquidCargo");
        System.out.println("[6]  MixedLiquidBoxed");
        System.out.println("[7]  MixedDryLiquidBoxed");
    }

    public static void hazards() {
        System.out.println("[0]  NOT HAZARDOUS / ALL HAZARDS ADDED");
        System.out.println("[1]  flammable");
        System.out.println("[2]  toxic");
        System.out.println("[3]  radioactive");
        System.out.println("[4]  explosive");
    }

    public static void enterCargoNumber(){
        System.out.println("Enter Cargo Number: ");
    }
    public static void enterWarehouseNumber(){
        System.out.println("Enter FXMLWarehouse Number: ");
    }
    public static void warehouseFull(){
        System.out.println("FXMLWarehouse is at full capacity. Storage denied.");
    }
    public static void warehouseCapacityLevel(int volumeStored, int capacity){
        System.out.format("FXMLWarehouse now at %7d / %7d", volumeStored, capacity);
        System.out.println();
    }
    public static void unabletoStoreCargo(int dimentionsAllowed, int size){
        System.out.format("Unable to store backend.storage.cargo. Allowed dimensions: %3d. Cargo dimensions: %3d", dimentionsAllowed, size);
        System.out.println();
    }
    public static void cargoContainsFollowingHazards(String hazards){
        System.out.println("This backend.storage.cargo contains the following hazards: " + hazards);
    }
    public static void noHazardsInCargo(){
        System.out.println("This backend.storage.cargo does not contain hazards.");
    }
    public static void tryingToStoreCargoInWarehouse(String name, Cargo cargo, Warehouse warehouse){
        System.out.println(name + " trying to store " + cargo + " in " + warehouse);
    }
    public static void successfullyStoredCargoInWarehouse(String name, Cargo cargo, Warehouse warehouse){
        System.out.println(name + " successfully stored " + cargo + " in " + warehouse);
    }
}
