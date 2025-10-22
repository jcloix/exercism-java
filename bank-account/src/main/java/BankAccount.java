class BankAccount {
    Integer amount =0;
    boolean open = false;
    void open() throws BankAccountActionInvalidException {
        if(open) {
            throw new BankAccountActionInvalidException("Account already open");
        }
        amount=0;
        open=true;
    }

    void close() throws BankAccountActionInvalidException {
        if(!open) {
            throw new BankAccountActionInvalidException("Account not open");
        }
        open=false;
    }

    synchronized int getBalance() throws BankAccountActionInvalidException {
        if(!open) {
            throw new BankAccountActionInvalidException("Account closed");
        }
        return amount;
    }

    synchronized void deposit(int amount) throws BankAccountActionInvalidException {
        if(amount < 0) {
            throw new BankAccountActionInvalidException("Cannot deposit or withdraw negative amount");
        }
        if(!open) {
            throw new BankAccountActionInvalidException("Account closed");
        }
        this.amount+=amount;
    }

    synchronized void withdraw(int amount) throws BankAccountActionInvalidException {
        if(!open) {
            throw new BankAccountActionInvalidException("Account closed");
        }
        if(amount < 0) {
            throw new BankAccountActionInvalidException("Cannot deposit or withdraw negative amount");
        }
        if(amount > this.amount) {
            throw new BankAccountActionInvalidException("Cannot withdraw more money than is currently in the account");
        }
        this.amount-=amount;
    }

}