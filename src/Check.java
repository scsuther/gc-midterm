
public class Check extends Payment {
private String checkNumber;


public Check() {
	super();
}

public Check(double amount, String checkNumber) {
	super(amount);
	this.checkNumber = checkNumber;
}

public String getCheckNumber() {
	return checkNumber;
}

public void setCheckNumber(String checkNumber) {
	this.checkNumber = checkNumber;
}



@Override
public String toString() {
	return "Check [checkNumber=" + checkNumber + ", amount=" + amount + "]";
}

@Override
double pay(double amount) {
	// TODO Auto-generated method stub
	
	return amount;
}


}
