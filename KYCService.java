package service;
public interface KYCService {
	  void collectCustomerInfo(String customerName, String customerId, String address, String contactNumber, String ssn);
	 //   boolean verifyCustomer(String customerId, String ssn);
}
