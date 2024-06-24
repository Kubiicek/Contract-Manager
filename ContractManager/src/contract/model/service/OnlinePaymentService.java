package contract.model.service;

public interface OnlinePaymentService {

	double paymentFree(double amount);
	double interest(double amount, int months);
}
