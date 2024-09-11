package model.services;

public class BrazilTaxService implements TaxService {

    @Override
    public double tax(double amount) {
       return (amount <=100) ?  amount *0.20 : amount*0.15;
    }
}
