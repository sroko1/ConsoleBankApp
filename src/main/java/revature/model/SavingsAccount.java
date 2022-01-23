package revature.model;

import revature.exception.NegativeAmountException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;



    public class SavingsAccount extends Account {
        private double savingFactor = 1.23;

        public SavingsAccount() {
            super();
            type = AccountType.SAVINGS;
        }

        public SavingsAccount(String number, BigDecimal balance, String pid) {
            super();
            type = AccountType.SAVINGS;
        }

        //  For JOINT ACCOUNT
        public SavingsAccount(String number, BigDecimal balance, String pid, String pid2) {
            super();

        }



        public double getSavingFactor() {
            return savingFactor;
        }

        @Override
        public void deposit(BigDecimal amount) throws NegativeAmountException {
            BigDecimal resultAmount = amount.multiply(BigDecimal.valueOf(savingFactor));
            super.deposit(resultAmount);
        }



        public void newSavingAccount(List<SavingsAccount> applicationList, List<SavingsAccount> savingAccounts) {
            BigDecimal tempBalance;
            String tempPID;
            String tempPID2;
            String tempNumber = String.valueOf(savingAccounts.size());
            char choice;

            Scanner sc = new Scanner(System.in);

            System.out.println("Creating a (Savings) Account");

            choice = sc.next().charAt(0);
            switch (choice) {
                case 'R' -> {
                    System.out.println("Please enter your PID Number\n");
                    tempBalance = BigDecimal.ZERO;
                    tempPID = sc.next();
                    tempNumber = tempNumber + 1;
                    applicationList.add(new SavingsAccount(tempNumber, tempBalance, tempPID));
                    System.out.println("Application Successful, Kindly wait for acceptance\n");
                }
                case 'J' -> {
                    System.out.println("Please enter your PID Number\n");
                    tempBalance = BigDecimal.ZERO;
                    tempPID = sc.next();
                    System.out.println("Please enter your SECOND PID Number\n");
                    tempPID2 = sc.next();
                    tempNumber = tempNumber + 1;
                    applicationList.add(new SavingsAccount(tempNumber, tempBalance, tempPID, tempPID2));
                    System.out.println("Application Successful, Kindly wait for acceptance\n");
                }
                default -> throw new IllegalStateException("Unexpected value: " + choice);
            }

        }

    }
