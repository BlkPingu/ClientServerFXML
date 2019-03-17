package backend.storage.administration;
import java.time.LocalDateTime;

class CargoLogistics {
    LocalDateTime date;
    int position;

    CargoLogistics(LocalDateTime date, int position) {
        this.date = date;
        this.position = position;
    }

    public LocalDateTime getDate() {
        return date;
    }

    int getPosition() {
        return position;
    }

}
