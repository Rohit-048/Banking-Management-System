import javax.swing.*;

class Bank {
    String name;
    int accno, aadharcardno, password, key;
    int[] activity = new int[5];
    float balance;
    Bank next;
}

public class ChaseBank {
    static int[] h = new int[10];
    static Bank acc = null;
    static Bank temp;

    public static void balance() {
        JOptionPane.showMessageDialog(null, "Current balance: " + temp.balance, "Balance", JOptionPane.INFORMATION_MESSAGE);
    }

    public static int hash(int val) {
        int hk, hk1, c = 0, c1 = 3, c2 = 5;
        do {
            hk1 = val % 10;
            hk = (hk1 + c * c1 + c2 * c * c) % 10;
            if (h[hk] == -1) {
                h[hk] = val;
                return hk;
            } else if (hk == 10) {
                JOptionPane.showMessageDialog(null, "Value cannot be stored.", "Error", JOptionPane.ERROR_MESSAGE);
                break;
            } else
                c++;
        } while (h[hk] != -1);
        return -1;
    }

    public static Bank lastactivity(Bank acc) {
        int i, j = 0;
        while (temp.activity[j] != 0) {
            j++;
        }
        StringBuilder activities = new StringBuilder("The latest activities are: \n");
        for (i = j - 1; i >= 0; i--) {
            activities.append(temp.activity[i]).append("\n");
        }
        JOptionPane.showMessageDialog(null, activities.toString(), "Latest Transactions", JOptionPane.INFORMATION_MESSAGE);
        return acc;
    }

    public static Bank withdraw(Bank acc) {
        int i = 0;
        float amount;
        String amountStr = JOptionPane.showInputDialog(null, "Enter the amount:", "Withdraw", JOptionPane.QUESTION_MESSAGE);
        if (amountStr == null || amountStr.isEmpty()) {
            return acc;
        }
        amount = Float.parseFloat(amountStr);
        if (amount > temp.balance) {
            JOptionPane.showMessageDialog(null, "Insufficient balance.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            temp.balance = (temp.balance) - amount;
        }
        while (temp.activity[i] != 0) {
            i++;
        }
        temp.activity[i] = -Math.round(amount);
        return acc;
    }

    public static Bank deposit(Bank acc) {
        int i = 0;
        float amount;
        String amountStr = JOptionPane.showInputDialog(null, "Enter the amount:", "Deposit", JOptionPane.QUESTION_MESSAGE);
        if (amountStr == null || amountStr.isEmpty()) {
            return acc;
        }
        amount = Float.parseFloat(amountStr);
        temp.balance = (temp.balance) + amount;
        while (temp.activity[i] != 0) {
            i++;
        }
        temp.activity[i] = Math.round(amount);
        return acc;
    }

    public static Bank createAccount(Bank acc) {
    int adc, i, pass, choice = JOptionPane.YES_OPTION;
    String name1;
    Bank newBank, ptr;
    do {
        name1 = JOptionPane.showInputDialog(null, "Enter Name:", "Create Account", JOptionPane.QUESTION_MESSAGE);
        if (name1 == null || name1.isEmpty()) {
            continue;
        }
        String adcStr = JOptionPane.showInputDialog(null, "Enter Aadhar card number:", "Create Account", JOptionPane.QUESTION_MESSAGE);
        if (adcStr == null || adcStr.isEmpty()) {
            continue;
        }
        adc = Integer.parseInt(adcStr);

        String passStr = JOptionPane.showInputDialog(null, "Enter your Password:", "Create Account", JOptionPane.QUESTION_MESSAGE);
        if (passStr == null || passStr.isEmpty()) {
            continue;
        }
        pass = Integer.parseInt(passStr);

        newBank = new Bank();
        newBank.name = name1;
        newBank.aadharcardno = adc;
        newBank.key = hash(pass);
        newBank.balance = 0;
        newBank.accno = newBank.aadharcardno + 5789;
        JOptionPane.showMessageDialog(null, "Your Account No. is: " + newBank.accno, "Account Created", JOptionPane.INFORMATION_MESSAGE);
        for (i = 0; i <= 4; i++) {
            newBank.activity[i] = 0;
        }
        if (acc == null) {
            newBank.next = null;
            acc = newBank;
        } else {
            ptr = acc;
            while (ptr.next != null)
                ptr = ptr.next;
            ptr.next = newBank;
            newBank.next = null;
        }
        choice = JOptionPane.showConfirmDialog(null, "DO YOU WANT TO CREATE A NEW ACCOUNT?", "Create Account", JOptionPane.YES_NO_OPTION);
    } while (choice == JOptionPane.YES_OPTION); // Corrected condition
    return acc;
}



    public static Bank login(Bank acc) {
        int pass, acn;
        Bank ptr;
        boolean reenter = false;
        do {
            pass = -1;  // Default or sentinel value
    if (reenter) {
        JOptionPane.showMessageDialog(null, "ACCOUNT DOESN'T EXIST or INVALID ACCOUNT NUMBER or PASSWORD\nPLEASE RE-ENTER YOUR DETAILS.", "Login Error", JOptionPane.ERROR_MESSAGE);
    }
    reenter = true;
    ptr = acc;
    String acnStr = JOptionPane.showInputDialog(null, "Enter account number:", "Login", JOptionPane.QUESTION_MESSAGE);
    if (acnStr == null || acnStr.isEmpty()) {
        continue;
    }
    acn = Integer.parseInt(acnStr);

    String passStr = JOptionPane.showInputDialog(null, "Enter your password:", "Login", JOptionPane.QUESTION_MESSAGE);
    if (passStr == null || passStr.isEmpty()) {
        continue;
    }
    pass = Integer.parseInt(passStr);

    while ((ptr != null) && (ptr.accno != acn)) {
        ptr = ptr.next;
    }

    // Check if the password has been successfully obtained
    if (pass != -1 && (ptr == null || h[ptr.key] != pass)) {
        JOptionPane.showMessageDialog(null, "INVALID ACCOUNT NUMBER or PASSWORD\nPLEASE RE-ENTER YOUR DETAILS.", "Login Error", JOptionPane.ERROR_MESSAGE);
    }
} while (pass == -1 || (ptr == null || h[ptr.key] != pass));

JOptionPane.showMessageDialog(null, "LOGIN SUCCESSFUL.\nWELCOME " + ptr.name, "Login Successful", JOptionPane.INFORMATION_MESSAGE);
temp = ptr;
return acc;
    }

    public static void main(String[] args) {
        int choice, option, opt;
        System.out.println("***********Chase BANK************");
        System.out.println("     *****MAIN MENU*****    ");
        for (int i = 0; i <= 9; i++)
            h[i] = -1;
        choice = JOptionPane.showOptionDialog(
                null,
                "1. CREATE ACCOUNT\n2. LOGIN",
                "Main Menu",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                new String[]{"CREATE ACCOUNT", "LOGIN"},
                null
        );

        if (choice == 0)
            acc = createAccount(acc);

        opt = JOptionPane.showOptionDialog(
                null,
                "DO YOU WANT TO LOGIN?",
                "Login",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                new String[]{"YES", "NO"},
                null
        );

        if (opt == 0) {
            acc = login(acc);
            do {
                option = JOptionPane.showOptionDialog(
                        null,
                        "1. Withdraw\n2. Deposit\n3. Latest Transactions\n4. Balance\n5. Logout",
                        "Menu",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        new String[]{"Withdraw", "Deposit", "Latest Transactions", "Balance", "Logout"},
                        null
                );

                switch (option) {
                    case 0:
                        acc = withdraw(acc);
                        JOptionPane.showMessageDialog(null, "Amount has been withdrawn.", "Withdraw", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case 1:
                        acc = deposit(acc);
                        JOptionPane.showMessageDialog(null, "Amount has been deposited.", "Deposit", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case 2:
                        acc = lastactivity(acc);
                        break;
                    case 3:
                        balance();
                        break;
                    case 4:
                        JOptionPane.showMessageDialog(null, "YOU HAVE BEEN LOGGED OUT SUCCESSFULLY.", "Logout", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } while (option != 4);
        }
    }
}
