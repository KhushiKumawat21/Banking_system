package service;

public class ChequeBookRequestServiceImpl implements ChequeBookRequestService {

	@Override
	public boolean issue(String AccountNumber) {
		System.out.println("Your ChequeBook has been issued");
		return true;
	}
	
	
}
