package backend.storage.administration;
import backend.enums.Hazard;
import backend.exceptions.NullValueException;
import backend.storage.cargo.Cargo;
import userinterface.dialogs.Dialogs;

import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Warehouse{

    public ConcurrentHashMap<Integer, Cargo>  allCargo = new ConcurrentHashMap<>();
    ConcurrentHashMap<Cargo, CargoLogistics> cargoUnitData = new ConcurrentHashMap<>();

    private volatile int dimensionsAllowed;
    volatile int capacity;
    volatile int volumeStored = 0;

    public Warehouse(int dimensionsAllowed, int capacity) {
        this.dimensionsAllowed = dimensionsAllowed;
        this.capacity = capacity;
    }

    public synchronized void storeCargoFromAdministration(Administration administration, int cargoNumber) {
        for(Cargo cargo : administration.cargoList){
            System.out.println("[" + administration.cargoList.indexOf(cargo)+ "] " + cargo.toString() );
        }
        System.out.println();
        System.out.print("Index of Arraylist: ");
        try {
            this.newCargo(administration.cargoList.get(cargoNumber));
            System.out.println("Cargo stored in warehouse");
        }catch (NullPointerException invalid){
            System.out.println("Cargo Number does not exist.");
        }
    }

    synchronized Cargo getOldestCargoInWarehouse(){
        LocalDateTime currentOldest = LocalDateTime.now();
        Cargo currentOldestCargo = null;

        for (Map.Entry<Cargo, CargoLogistics> entry : this.cargoUnitData.entrySet()) {
            System.out.println("Current Item : " + entry.getKey() + " Storage Date : " + entry.getValue().getDate());

            if(entry.getValue().getDate().isBefore(currentOldest)){
                currentOldest = entry.getValue().getDate();
                currentOldestCargo = entry.getKey();
            }
        }


        return currentOldestCargo;
    }



    private synchronized void storeCargo(Cargo cargo){
        int i = 0;
        while (allCargo.containsKey(i)) {
            i++;
        }
        allCargo.putIfAbsent(i, cargo);
    }

    private synchronized void checkforHazards(Cargo cargo){
        if(cargo.getHazards().isEmpty()){
            Dialogs.noHazardsInCargo();
        }else{
            Dialogs.cargoContainsFollowingHazards(cargo.getHazards().toString());
        }
    }

    private synchronized boolean checkDimensions(Cargo cargo){
        if(cargo.getSize() > this.dimensionsAllowed){
            Dialogs.unabletoStoreCargo(this.dimensionsAllowed, cargo.getSize());
            return false;
        }else{
            System.out.println("Cargo dimensions allowed");
            return true;
        }
    }

    private synchronized void checkForExistingCustomer(Cargo cargo){
        for(Map.Entry<Integer, Cargo> entry : allCargo.entrySet()) {
            Cargo value = entry.getValue();
            if(value.getOwner() == cargo.getOwner()) {
                System.out.println("Adding to existing Cargo of Customer");
                return;
            }
        }
        System.out.println("First Cargo for this customer has been stored.");
    }

    private synchronized void setDateAndPosition(Cargo cargo){
        LocalDateTime todaysDate = LocalDateTime.now();
        ArrayList<Integer> positions = new ArrayList<>();
        cargoUnitData.forEach((k,v) ->  positions.add(v.getPosition()));
        int i = 0;
        while (positions.contains(i)) {
            i++;
        }
        cargoUnitData.putIfAbsent(cargo, new CargoLogistics(todaysDate, i));
        System.out.println("Storage Date: " + cargoUnitData.get(cargo).date);
        System.out.println("Storage Date: " + cargoUnitData.get(cargo).position);

    }

    private synchronized Set<List<? extends Cargo>> sortCargoByType(){
        List<backend.storage.cargo.DryCargo> dryCargoList = new ArrayList<>();
        List<backend.storage.cargo.BoxedCargo> boxedCargoList = new ArrayList<>();
        List<backend.storage.cargo.LiquidCargo> liquidCargoList = new ArrayList<>();
        List<backend.storage.cargo.MixedDryBoxed> mixedDryBoxedList = new ArrayList<>();
        List<backend.storage.cargo.MixedDryLiquid> mixedDryLiquidList = new ArrayList<>();
        List<backend.storage.cargo.MixedLiquidBoxed> mixedLiquidBoxedList = new ArrayList<>();
        List<backend.storage.cargo.MixedDryLiquidBoxed> mixedDryLiquidBoxedList = new ArrayList<>();

        allCargo.forEach((k,v) -> {
                if(v instanceof backend.storage.cargo.BoxedCargo){
                    backend.storage.cargo.BoxedCargo cargoInstance = (backend.storage.cargo.BoxedCargo) v;
                    boxedCargoList.add(cargoInstance);
                }
                if(v instanceof backend.storage.cargo.DryCargo){
                    backend.storage.cargo.DryCargo cargoInstance = (backend.storage.cargo.DryCargo) v;
                    dryCargoList.add(cargoInstance);
                }
                if(v instanceof backend.storage.cargo.LiquidCargo){
                    backend.storage.cargo.LiquidCargo cargoInstance = (backend.storage.cargo.LiquidCargo) v;
                    liquidCargoList.add(cargoInstance);
                }
                if(v instanceof backend.storage.cargo.MixedDryBoxed){
                    backend.storage.cargo.MixedDryBoxed cargoInstance = (backend.storage.cargo.MixedDryBoxed) v;
                    mixedDryBoxedList.add(cargoInstance);
                }
                if(v instanceof backend.storage.cargo.MixedDryLiquid) {
                    backend.storage.cargo.MixedDryLiquid cargoInstance = (backend.storage.cargo.MixedDryLiquid) v;
                    mixedDryLiquidList.add(cargoInstance);
                }
                if (v instanceof backend.storage.cargo.MixedLiquidBoxed) {
                    backend.storage.cargo.MixedLiquidBoxed cargoInstance = (backend.storage.cargo.MixedLiquidBoxed) v;
                    mixedLiquidBoxedList.add(cargoInstance);
                }
                if (v instanceof backend.storage.cargo.MixedDryLiquidBoxed) {
                    backend.storage.cargo.MixedDryLiquidBoxed cargoInstance = (backend.storage.cargo.MixedDryLiquidBoxed) v;
                    mixedDryLiquidBoxedList.add(cargoInstance);
                }
            });
        Set<List <? extends Cargo>> sortedCargo = new HashSet<>();
        sortedCargo.add(dryCargoList);
        sortedCargo.add(boxedCargoList);
        sortedCargo.add(liquidCargoList);
        sortedCargo.add(mixedDryLiquidList);
        sortedCargo.add(mixedDryBoxedList);
        sortedCargo.add(mixedLiquidBoxedList);
        sortedCargo.add(mixedDryLiquidBoxedList);
    return  sortedCargo;
    }


    public synchronized void getAllCargoWithPosition(){
        cargoUnitData.forEach((k,v) -> {
            if(k instanceof backend.storage.cargo.BoxedCargo){
                backend.storage.cargo.BoxedCargo cargoInstance = (backend.storage.cargo.BoxedCargo) k;
                System.out.println("Cargo: "+ cargoInstance.toString() + " at position " + v.position);

            }
            if(k instanceof backend.storage.cargo.DryCargo){
                backend.storage.cargo.DryCargo cargoInstance = (backend.storage.cargo.DryCargo) k;
                System.out.println("Cargo: "+ cargoInstance.toString() + " at position " + v.position);

            }
            if(k instanceof backend.storage.cargo.LiquidCargo){
                backend.storage.cargo.LiquidCargo cargoInstance = (backend.storage.cargo.LiquidCargo) k;
                System.out.println("Cargo: "+ cargoInstance.toString() + " at position " + v.position);

            }
            if(k instanceof backend.storage.cargo.MixedDryBoxed){
                backend.storage.cargo.MixedDryBoxed cargoInstance = (backend.storage.cargo.MixedDryBoxed) k;
                System.out.println("Cargo: "+ cargoInstance.toString() + " at position " + v.position);

            }
            if(k instanceof backend.storage.cargo.MixedDryLiquid) {
                backend.storage.cargo.MixedDryLiquid cargoInstance = (backend.storage.cargo.MixedDryLiquid) k;
                System.out.println("Cargo: "+ cargoInstance.toString() + " at position " + v.position);

            }
            if (k instanceof backend.storage.cargo.MixedLiquidBoxed) {
                backend.storage.cargo.MixedLiquidBoxed cargoInstance = (backend.storage.cargo.MixedLiquidBoxed) k;
                System.out.println("Cargo: "+ cargoInstance.toString() + " at position " + v.position);

            }
            if (k instanceof backend.storage.cargo.MixedDryLiquidBoxed) {
                backend.storage.cargo.MixedDryLiquidBoxed cargoInstance = (backend.storage.cargo.MixedDryLiquidBoxed) k;
                System.out.println("Cargo: "+ cargoInstance.toString() + " at position " + v.position);

            }

        });
    }

    private synchronized Set<Hazard> getHazards(){
        Set<Hazard> hazardsInStorage = new HashSet<>();
        allCargo.forEach( (k,v) -> hazardsInStorage.addAll(v.getHazards()));
        return hazardsInStorage;
    }


    public synchronized void deleteCargo(Integer key){
        if(key != null){
            if(!allCargo.containsKey(key)) {
                try {
                    throw new NullPointerException();
                } catch (NullPointerException e) {
                    System.out.println("Key does not exist: " + key);
                    e.printStackTrace();
                }
            }
            this.volumeStored -= allCargo.get(key).getSize();
            cargoUnitData.remove(allCargo.get(key));
            allCargo.remove(key);
        }else{
            try {
                throw new NullValueException("Is null: " + key);
            } catch (NullValueException e) {
                System.out.println("No Cargo deleted. Key was null.");
                e.printStackTrace();
            }
        }
    }



    synchronized void getCustomersCargoAmount(String customerName){
        if(customerName != null){
            List<Cargo> cargoOfOwner = new ArrayList<>();

            for(Map.Entry<Integer, Cargo> entry : allCargo.entrySet()) {
                Cargo value = entry.getValue();
                if(customerName.equals(value.getOwner().getName()))
                    cargoOfOwner.add(value);
            }
            System.out.format("The amount of backend.storage.cargo pieces for customer %10s is: %4d",customerName, cargoOfOwner.size());
            System.out.println();
        }else{
            try {
                throw new NullValueException("Is null: " + customerName);
            } catch (NullValueException e) {
                e.printStackTrace();
            }
        }

    }

    boolean hasNoSpace(Cargo cargo){
        return (this.volumeStored + cargo.getSize()) > capacity;
    }

    public static Integer getKeyByValueFromCargo(Map<Integer, Cargo> map, Cargo value) {
        for (Map.Entry<Integer, Cargo> entry : map.entrySet()) {
            if (entry.getValue().getSize() == value.getSize() &&
                    entry.getValue().getOwner().getName().equals(value.getOwner().getName()) &&
                    entry.getValue().getHazards().equals(value.getHazards())
            ) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static Integer getKeyByValueForServer(Map<Integer, Cargo> map, Cargo value){
        for (Map.Entry<Integer, Cargo> entry : map.entrySet()) {
            if (entry.getValue().getOwner().getName().equals(value.getOwner().getName())
            && entry.getValue().getHazards().equals(value.getHazards())
            && entry.getValue().getSize() == value.getSize()) {
                return entry.getKey();
            }
        }
        return null;
    }


    public synchronized boolean newCargo(Cargo cargo){
        if(cargo != null){
            if(checkDimensions(cargo)){
                if(hasNoSpace(cargo)){
                    Dialogs.warehouseFull();
                }else{
                    this.volumeStored += cargo.getSize();
                    Dialogs.warehouseCapacityLevel(this.volumeStored, this.capacity);
                    setDateAndPosition(cargo);
                    checkForExistingCustomer(cargo);
                    checkforHazards(cargo);
                    storeCargo(cargo);
                    return true;
                }
            }
        }else{
            try {
                throw new NullValueException("Is null: " + cargo);
            } catch (NullValueException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    void printCargoByType() {
        Set<List<? extends Cargo>> sortedCargo = sortCargoByType();
        if(sortedCargo != null){
            sortedCargo.forEach(s -> {
                String typeName;
                if(s.isEmpty()){
                    System.out.println("Cargo Type not in Store"); //would have liked here to access an emphty lists class type name, but didn't get there.
                }else{
                    typeName = s.get(0).getClass().getSimpleName(); //this was the workaround for not empthy lists, to access the first element and make a className request
                    System.out.format("Type %45s  |   Amount: %10d ", typeName , s.size());
                    System.out.println();
                }
            });
        }else{
            try {
                throw new NullValueException("Is null: " + sortedCargo);
            } catch (NullValueException e) {
                e.printStackTrace();
            }
        }

    }

    void getHazardsInStorage(){
        Set<Hazard> hazards = getHazards();
        if(hazards != null){
            if(hazards.isEmpty()){
                System.out.println("No hazards in backend.storage.");
            }else System.out.println("Hazards in Storage: " + hazards.toString());
        }else{
            try {
                throw new NullValueException("Is null: " + hazards);
            } catch (NullValueException e) {
                e.printStackTrace();
            }
        }
    }

    void getHazardsNotInStorage(){
        Set<Hazard> hazards = getHazards();
        if(hazards != null){
            Set<Hazard> hazardsNotInStorge = new HashSet<>();
            if(!hazards.contains(Hazard.explosive)) hazardsNotInStorge.add(Hazard.explosive);
            if(!hazards.contains(Hazard.flammable)) hazardsNotInStorge.add(Hazard.flammable);
            if(!hazards.contains(Hazard.radioactive)) hazardsNotInStorge.add(Hazard.radioactive);
            if(!hazards.contains(Hazard.toxic)) hazardsNotInStorge.add(Hazard.toxic);
            System.out.println("Hazards not in Storage: " + hazardsNotInStorge.toString());
        }else{
            try {
                throw new NullValueException("Is null: " + hazards);
            } catch (NullValueException e) {
                e.printStackTrace();
            }
        }
    }
}
