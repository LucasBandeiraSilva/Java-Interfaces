package application;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

import model.entities.CarRental;
import model.entities.Vehicle;
import model.services.BrazilTaxService;
import model.services.RentalService;

public class Program {
    public static void main(String[] args) {
        DateTimeFormatter dateTimeformatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter with the Rental data: ");
        System.out.print("Car model: ");
        String carModel = scanner.nextLine();
        System.out.print("Pickup (dd/MM/yyyy HH:mm): ");
        LocalDateTime pickupDate = LocalDateTime.parse(scanner.nextLine(), dateTimeformatter);
        System.out.print("Return (dd/MM/yyyy HH:mm): ");
        LocalDateTime returnDate = LocalDateTime.parse(scanner.nextLine(), dateTimeformatter);

        CarRental carRental = new CarRental(pickupDate, returnDate, new Vehicle(carModel));
        System.out.println("Enter with the price per hour: ");
        double pricePerHour = scanner.nextDouble();
        System.out.println("Enter with the price per day: ");
        double pricePerDay = scanner.nextDouble();
        RentalService rentalService = new RentalService(pricePerHour, pricePerDay, new BrazilTaxService());
        rentalService.processInvoice(carRental);

        System.out.println("INVOICE");
        System.out.printf("Basic payment: $%.2f%n", carRental.getInvoice().getBasicPayment());
        System.out.printf("Tax:$%.2f%n", carRental.getInvoice().getTax());
        System.out.printf("Total payment:$%.2f%n", +carRental.getInvoice().getTotalPayment());

        scanner.close();
    }
}
