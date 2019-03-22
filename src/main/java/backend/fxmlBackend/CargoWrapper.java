package backend.fxmlBackend;

import backend.enums.Hazard;
import backend.serialization.SaveObject;
import backend.storage.administration.Customer;
import backend.storage.cargo.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class CargoWrapper {


    private static boolean getProperties(SaveObject saveObject, char character){
        List<Character> characterList = saveObject.getProperties().chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());


        for (char c : characterList){
            switch(c){
                case 'F': if(character == 'F') return true;
                    break;
                case 'S': if(character == 'S') return true;
                    break;
                case 'P': if(character == 'P') return true;
                    break;
                case '-':
                    System.out.println("hit a '-' property");
                    break;
                default:
                    System.out.println("translate Properties has undefined state");
                    break;
            }
        }
        System.out.println("returning false");
        return false;
    }

    private static Collection<Hazard> collectHazards(SaveObject saveObject){
        Collection<Hazard> hazardCollection = new HashSet<>();

        if(saveObject.getExplosive()) hazardCollection.add(Hazard.explosive);
        if(saveObject.getToxic()) hazardCollection.add(Hazard.toxic);
        if(saveObject.getFlammable()) hazardCollection.add(Hazard.flammable);
        if(saveObject.getRadioactive()) hazardCollection.add(Hazard.radioactive);

        return hazardCollection;
    }

    public static Cargo SaveObject2Cargo(SaveObject saveObject){

        switch(saveObject.getType()){
            case "Boxed":
                return new BoxedCargo(saveObject.getSize(),new Customer(saveObject.getCustomer()), collectHazards(saveObject), getProperties(saveObject, 'F') );
            case "Dry":
                return new DryCargo(saveObject.getSize(),new Customer(saveObject.getCustomer()), collectHazards(saveObject), getProperties(saveObject, 'S') );
            case "Liquid":
                return new LiquidCargo(saveObject.getSize(),new Customer(saveObject.getCustomer()), collectHazards(saveObject), getProperties(saveObject, 'P') );
            case "Dry | Boxed":
                return new MixedDryBoxed(saveObject.getSize(),new Customer(saveObject.getCustomer()), collectHazards(saveObject), getProperties(saveObject, 'F'), getProperties(saveObject, 'S') );
            case "Dry | Liquid":
                return new MixedDryLiquid(saveObject.getSize(),new Customer(saveObject.getCustomer()), collectHazards(saveObject), getProperties(saveObject, 'S'),getProperties(saveObject, 'P'));
            case "Liquid | Boxed":
                return new MixedLiquidBoxed(saveObject.getSize(),new Customer(saveObject.getCustomer()), collectHazards(saveObject), getProperties(saveObject, 'F'), getProperties(saveObject, 'P') );
            case "Liquid | Boxed | Dry":
                return new MixedDryLiquidBoxed(saveObject.getSize(),new Customer(saveObject.getCustomer()), collectHazards(saveObject), getProperties(saveObject, 'F') ,getProperties(saveObject, 'S'), getProperties(saveObject, 'P'));
            default:
                System.out.println("Unrecognized Cargo Type");
                break;
        }
        return null;
    }

    public static SaveObject Cargo2SaveObject(Cargo cargo){
        String properties;
        SaveObject saveObject;

        //"Boxed":
        if(cargo instanceof BoxedCargo){
            if (((BoxedCargo) cargo).isFragile())
                 properties = "--F";
            else properties = "---";
            return new SaveObject("Boxed",cargo.getOwner().getName(), 0, cargo.getSize(), cargo.getHazards().contains(Hazard.radioactive), cargo.getHazards().contains(Hazard.flammable), cargo.getHazards().contains(Hazard.toxic), cargo.getHazards().contains(Hazard.explosive), properties);
        }
        //"Dry":
        if(cargo instanceof DryCargo){
            if (((DryCargo) cargo).isSolid())
                 properties = "-S-";
            else properties = "---";
            return new SaveObject("Dry",cargo.getOwner().getName(), 0, cargo.getSize(), cargo.getHazards().contains(Hazard.radioactive), cargo.getHazards().contains(Hazard.flammable), cargo.getHazards().contains(Hazard.toxic), cargo.getHazards().contains(Hazard.explosive), properties);
        }
        //"Liquid":
        if(cargo instanceof LiquidCargo){
            if (((LiquidCargo) cargo).isPressurized())
                 properties = "P--";
            else properties = "---";
            return new SaveObject("Liquid",cargo.getOwner().getName(), 0, cargo.getSize(), cargo.getHazards().contains(Hazard.radioactive), cargo.getHazards().contains(Hazard.flammable), cargo.getHazards().contains(Hazard.toxic), cargo.getHazards().contains(Hazard.explosive), properties);
        }
        //"Dry | Boxed":
        if(cargo instanceof MixedDryBoxed){
            if (((MixedDryBoxed) cargo).isSolid() && ((MixedDryBoxed) cargo).isFragile()) properties = "-SF";
            else if(((MixedDryBoxed) cargo).isFragile() && !((MixedDryBoxed) cargo).isSolid()) properties = "--F";
            else if(!((MixedDryBoxed) cargo).isFragile() && ((MixedDryBoxed) cargo).isSolid()) properties = "-S-";
            else properties = "---";
           return new SaveObject("Dry | Boxed",cargo.getOwner().getName(), 0, cargo.getSize(), cargo.getHazards().contains(Hazard.radioactive), cargo.getHazards().contains(Hazard.flammable), cargo.getHazards().contains(Hazard.toxic), cargo.getHazards().contains(Hazard.explosive), properties);
        }
        //"Dry | Liquid":
        if(cargo instanceof MixedDryLiquid){
            if(((MixedDryLiquid) cargo).isSolid() && ((MixedDryLiquid) cargo).isPressurized()) properties = "PS-";
            else if(((MixedDryLiquid) cargo).isPressurized() && !((MixedDryLiquid) cargo).isSolid()) properties = "P--";
            else if(((MixedDryLiquid) cargo).isSolid() && !((MixedDryLiquid) cargo).isPressurized()) properties = "-S-";
            else properties = "---";
            return new SaveObject("Dry | Liquid",cargo.getOwner().getName(), 0, cargo.getSize(), cargo.getHazards().contains(Hazard.radioactive), cargo.getHazards().contains(Hazard.flammable), cargo.getHazards().contains(Hazard.toxic), cargo.getHazards().contains(Hazard.explosive), properties);
        }
        //"Liquid | Boxed":
        if(cargo instanceof MixedLiquidBoxed){
            if(((MixedLiquidBoxed) cargo).isPressurized() && ((MixedLiquidBoxed) cargo).isFragile()) properties = "P-F";
            else if(!((MixedLiquidBoxed) cargo).isPressurized() && ((MixedLiquidBoxed) cargo).isFragile()) properties = "--F";
            else if(((MixedLiquidBoxed) cargo).isPressurized() && !((MixedLiquidBoxed) cargo).isFragile()) properties = "P--";
            else properties = "---";
            return new SaveObject("Dry | Liquid",cargo.getOwner().getName(), 0, cargo.getSize(), cargo.getHazards().contains(Hazard.radioactive), cargo.getHazards().contains(Hazard.flammable), cargo.getHazards().contains(Hazard.toxic), cargo.getHazards().contains(Hazard.explosive), properties);
        }
        //"Liquid | Boxed | Dry":
        if(cargo instanceof MixedDryLiquidBoxed){
            if(((MixedDryLiquidBoxed) cargo).isPressurized() && ((MixedDryLiquidBoxed) cargo).isSolid() && ((MixedDryLiquidBoxed) cargo).isFragile()) properties = "PSF";
            else if(!((MixedDryLiquidBoxed) cargo).isPressurized() && ((MixedDryLiquidBoxed) cargo).isSolid() && ((MixedDryLiquidBoxed) cargo).isFragile()) properties = "-SF";
            else if(((MixedDryLiquidBoxed) cargo).isPressurized() && !((MixedDryLiquidBoxed) cargo).isSolid() && ((MixedDryLiquidBoxed) cargo).isFragile()) properties = "P-F";
            else if(((MixedDryLiquidBoxed) cargo).isPressurized() && ((MixedDryLiquidBoxed) cargo).isSolid() && !((MixedDryLiquidBoxed) cargo).isFragile()) properties = "PS-";
            else if(((MixedDryLiquidBoxed) cargo).isPressurized() && !((MixedDryLiquidBoxed) cargo).isSolid() && !((MixedDryLiquidBoxed) cargo).isFragile()) properties = "P--";
            else if(!((MixedDryLiquidBoxed) cargo).isPressurized() && ((MixedDryLiquidBoxed) cargo).isSolid() && !((MixedDryLiquidBoxed) cargo).isFragile()) properties = "-S-";
            else if(!((MixedDryLiquidBoxed) cargo).isPressurized() && !((MixedDryLiquidBoxed) cargo).isSolid() && ((MixedDryLiquidBoxed) cargo).isFragile()) properties = "--F";
            else properties = "---";
            return new SaveObject("Liquid | Boxed | Dry",cargo.getOwner().getName(),0 , cargo.getSize(), cargo.getHazards().contains(Hazard.radioactive), cargo.getHazards().contains(Hazard.flammable), cargo.getHazards().contains(Hazard.toxic), cargo.getHazards().contains(Hazard.explosive), properties);
        }
        System.out.println("Cargo2SaveObject returns null");
        return null;
    }
}
