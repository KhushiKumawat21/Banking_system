package service;

public interface CardService {
	    void createDebitCard( String accountNumber,String customerId, String cardHolderName);
	    void createCreditCard( String accountNumber,String customerId, String cardHolderName);
	    void blockDebitCard( String accountNumber,String customerId);
	    void blockCreditCard( String accountNumber,String customerId);
	  ;
	   
}
