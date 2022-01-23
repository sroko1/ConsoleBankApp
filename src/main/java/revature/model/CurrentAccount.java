package revature.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;


    public class CurrentAccount extends Account {
        public CurrentAccount() {
            super();
            type = AccountType.CURRENT;
        }

        public CurrentAccount(String number, BigDecimal balance, String pid) {
            super();
            type = AccountType.CURRENT;
        }

        //JOINT ACCOUNT
        public CurrentAccount(String number, BigDecimal balance, String pid, String pid2) {
            super();
            type = AccountType.CURRENT;
        }

        @Override
        public String getNumber() {
            return number;
        }

        @Override
        public BigDecimal getBalance() {
            return balance;
        }




        public void newCurrentAccount(List<CurrentAccount> applicationList, List<CurrentAccount> currentAccounts) {
            BigDecimal tempBalance;
            String tempPID;
            String tempPID2;
            String tempNumber = String.valueOf(currentAccounts.size());
            char choice;

            Scanner sc = new Scanner(System.in);

            System.out.println("Creating a (Current) Account");

            System.out.println("Are you creating a Joint(J) or a Regular (R) account?\n");
            choice = sc.next().charAt(0);

            switch (choice) {
                case 'R' -> {
                    System.out.println("Please enter your PID Number\n");
                    tempBalance = BigDecimal.ZERO;
                    tempPID = sc.next();
                    tempNumber = tempNumber + 1;
                    applicationList.add(new CurrentAccount(tempNumber, tempBalance, tempPID));
                    System.out.println("Application Successful, Kindly wait for acceptance\n");
                }
                case 'J' -> {
                    System.out.println("Please enter your PID Number\n");
                    tempBalance = BigDecimal.ZERO;
                    tempPID = sc.next();
                    System.out.println("Please enter your SECOND PID Number\n");
                    tempPID2 = sc.next();
                    tempNumber = tempNumber + 1;
                    applicationList.add(new CurrentAccount(tempNumber, tempBalance, tempPID, tempPID2));
                }
                default -> throw new IllegalStateException("Unexpected value: " + choice);
            }
        }

    }
