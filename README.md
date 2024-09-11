## Example of Interfaces in Java: Calculating Taxes for a Car Rental Company

This project demonstrates a solution with and without interfaces for the following challenge:

"A Brazilian car rental company charges an hourly rate for rentals up to 12 hours. However, if the rental duration exceeds 12 hours, the rental will be charged based on a daily rate. In addition to the rental amount, a tax amount is added to the price according to the country's rules, which in the case of Brazil, is 20% for values up to 100.00, or 15% for values above 100.00. Create a program that reads the rental data (car model, start and end time of the rental), as well as the hourly and daily rental rates. The program should then generate the payment invoice (containing the rental amount, tax amount, and total payment amount) and display the data on the screen."

In Java, interfaces define contracts that classes can implement. This promotes flexibility and decoupling in the code, making it easier to maintain and extend. 

## Problem

The initial solution presents a high coupling between the `**RentalService**` class, responsible for calculating the rental price, and the `**BrazilTaxService**` class, responsible for calculating Brazilian taxes.  This strong dependency can make it difficult to modify or extend the tax calculation logic in the future.

## Solution without Interface:

**Class: BrazilTaxService**

```java
package model.services;

public class BrazilTaxService {
    public double tax(double amount){
        if (amount<=100.00) return amount * 0.20;
        else return amount * 0.15;
    }
}
```

**Class: RentalTaxService**

```java
public class RentalService {
    private double pricePerHour;
    private double pricePerDay;
    private BrazilTaxService brazilTaxService;

    public RentalService(double pricePerHour, double pricePerDay, BrazilTaxService brazilTaxService) {
        this.pricePerHour = pricePerHour;
        this.pricePerDay = pricePerDay;
        this.brazilTaxService = brazilTaxService;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public BrazilTaxService getBrazilTaxService() {
        return brazilTaxService;
    }

    public void setBrazilTaxService(BrazilTaxService brazilTaxService) {
        this.brazilTaxService = brazilTaxService;
    }

    public void processInvoice(CarRental carRental) {
        double minutes = Duration.between(carRental.getStartDate(), carRental.getEndDate()).toMinutes();
        double hours = minutes / 60;
        double basicPayment = (hours <= 12.0) ? pricePerHour * Math.ceil(hours) : pricePerDay * Math.ceil(hours/24);
        double tax = getBrazilTaxService().tax(basicPayment);
        carRental.setInvoice(new Invoice(basicPayment, tax));
    }
}
```

## Solution Using Interface:

To address the high coupling issue, we introduce a `TaxService` interface. This allows us to decouple the `RentalService` from the specific implementation of the Brazilian tax calculation.

**Interface: TaxService**

```java
package model.services;

public interface TaxService {
    double tax(double amount);
}
```

**Class: RentalService:**

```java
public class RentalService {
    private double pricePerHour;
    private double pricePerDay;
    private TaxService taxService;

    public RentalService(double pricePerHour, double pricePerDay, TaxService taxService) {
        this.pricePerHour = pricePerHour;
        this.pricePerDay = pricePerDay;
        this.taxService = taxService;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public TaxService getTaxService() {
        return taxService;
    }

    public void TaxService(TaxService TaxService) {
        this.taxService = TaxService;
    }

    public void processInvoice(CarRental carRental) {
        double minutes = Duration.between(carRental.getStartDate(), carRental.getEndDate()).toMinutes();
        double hours = minutes / 60;
        double basicPayment = (hours <= 12.0) ? pricePerHour * Math.ceil(hours) : pricePerDay * Math.ceil(hours / 24);
        double tax = taxService.tax(basicPayment);
        carRental.setInvoice(new Invoice(basicPayment, tax));
    }
}
```

**Class: BrazilTaxService:**

```java
package model.services;

public class BrazilTaxService implements TaxService {

    @Override
    public double tax(double amount) {
       return (amount <=100) ?  amount *0.20 : amount*0.15;
    }
}
```

## Conclusion

By implementing the `TaxService` interface, the `BrazilTaxService` class now adheres to a contract. This means that any class implementing the `TaxService` interface can be used by the `**RentalService`, making the code more flexible and maintainable. We can easily introduce new tax calculation logics without modifying the `**RentalService` class, simply by creating new classes that implement the `TaxService` interface. 

