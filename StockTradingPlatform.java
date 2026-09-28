import java.util.ArrayList;
import java.util.Scanner;

class Stock {
String symbol;
String name;
double price;

Stock(String symbol, String name, double price) {
    this.symbol = symbol;
    this.name = name;
    this.price = price;
}

void displayStock() {
    System.out.println(symbol + " - " + name + " - Price: " + price);
}

}

class Holding {
Stock stock;
int quantity;

Holding(Stock stock, int quantity) {
    this.stock = stock;
    this.quantity = quantity;
}

}

class Transaction {
String type;
String symbol;
int quantity;
double price;

Transaction(String type, String symbol, int quantity, double price) {
    this.type = type;
    this.symbol = symbol;
    this.quantity = quantity;
    this.price = price;
}

void displayTransaction() {
    double total = quantity * price;

    System.out.println(
        type + " | " +
        symbol + " | Quantity: " +
        quantity + " | Price: " +
        price + " | Total: " +
        total
    );
}

}

class Portfolio {
ArrayList<Holding> holdings = new ArrayList<>();

void addStock(Stock stock, int quantity) {
    for (Holding holding : holdings) {
        if (holding.stock.symbol.equals(stock.symbol)) {
            holding.quantity += quantity;
            return;
        }
    }

    holdings.add(new Holding(stock, quantity));
}

boolean sellStock(Stock stock, int quantity) {
    for (Holding holding : holdings) {
        if (holding.stock.symbol.equals(stock.symbol)) {

            if (holding.quantity >= quantity) {
                holding.quantity -= quantity;

                if (holding.quantity == 0) {
                    holdings.remove(holding);
                }

                return true;
            }

            return false;
        }
    }

    return false;
}

void displayPortfolio() {
    System.out.println("\n--- Portfolio ---");

    if (holdings.isEmpty()) {
        System.out.println("Portfolio is empty.");
        return;
    }

    double totalValue = 0;

    for (Holding holding : holdings) {
        double value = holding.stock.price * holding.quantity;

        System.out.println(
            holding.stock.symbol +
            " | Quantity: " +
            holding.quantity +
            " | Value: " +
            value
        );

        totalValue += value;
    }

    System.out.println("Total Portfolio Value: " + totalValue);
}

void displayProfitLoss() {
    double totalValue = 0;

    for (Holding holding : holdings) {
        totalValue += holding.stock.price * holding.quantity;
    }

    System.out.println("Current Portfolio Value: " + totalValue);
}

}

class User {
String name;
double balance;
Portfolio portfolio;
ArrayList<Transaction> transactions = new ArrayList<>();

User(String name, double balance) {
    this.name = name;
    this.balance = balance;
    this.portfolio = new Portfolio();
}

void buyStock(Stock stock, int quantity) {

    if (quantity <= 0) {
        System.out.println("Invalid quantity.");
        return;
    }

    double totalCost = stock.price * quantity;

    if (totalCost > balance) {
        System.out.println("Insufficient balance.");
        return;
    }

    balance -= totalCost;

    portfolio.addStock(stock, quantity);

    transactions.add(
        new Transaction(
            "BUY",
            stock.symbol,
            quantity,
            stock.price
        )
    );

    System.out.println("Stock purchased successfully.");
}

void sellStock(Stock stock, int quantity) {

    if (quantity <= 0) {
        System.out.println("Invalid quantity.");
        return;
    }

    boolean sold = portfolio.sellStock(stock, quantity);

    if (!sold) {
        System.out.println("Not enough shares to sell.");
        return;
    }

    double totalAmount = stock.price * quantity;

    balance += totalAmount;

    transactions.add(
        new Transaction(
            "SELL",
            stock.symbol,
            quantity,
            stock.price
        )
    );

    System.out.println("Stock sold successfully.");
}

void displayTransactions() {
    System.out.println("\n--- Transaction History ---");

    if (transactions.isEmpty()) {
        System.out.println("No transactions yet.");
        return;
    }

    for (Transaction transaction : transactions) {
        transaction.displayTransaction();
    }
}

void displayUser() {
    System.out.println("\n--- User Details ---");
    System.out.println("Name: " + name);
    System.out.println("Balance: " + balance);
}

}

public class StockTradingPlatform {

public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);

    Stock tcs = new Stock(
        "TCS",
        "Tata Consultancy Services",
        3500
    );

    Stock infy = new Stock(
        "INFY",
        "Infosys",
        1500
    );

    Stock reliance = new Stock(
        "RELIANCE",
        "Reliance Industries",
        2800
    );

    User user = new User("", 50000);

    System.out.print("Enter your name: ");
    user.name = scanner.nextLine();

    int choice;

    do {

        System.out.println("\n===== STOCK TRADING PLATFORM =====");
        System.out.println("1. View Market");
        System.out.println("2. Buy Stock");
        System.out.println("3. Sell Stock");
        System.out.println("4. View Portfolio");
        System.out.println("5. View User Details");
        System.out.println("6. View Transaction History");
        System.out.println("7. View Portfolio Value");
        System.out.println("8. Exit");

        System.out.print("Enter your choice: ");
        choice = scanner.nextInt();

        if (choice == 1) {

            System.out.println("\n--- Stock Market ---");

            tcs.displayStock();
            infy.displayStock();
            reliance.displayStock();

        } else if (choice == 2) {

            System.out.println("\n--- Buy Stock ---");
            System.out.println("1. TCS");
            System.out.println("2. INFY");
            System.out.println("3. RELIANCE");

            System.out.print("Choose stock: ");
            int buyChoice = scanner.nextInt();

            System.out.print("Enter quantity: ");
            int quantity = scanner.nextInt();

            if (buyChoice == 1) {
                user.buyStock(tcs, quantity);
            } else if (buyChoice == 2) {
                user.buyStock(infy, quantity);
            } else if (buyChoice == 3) {
                user.buyStock(reliance, quantity);
            } else {
                System.out.println("Invalid stock choice.");
            }

        } else if (choice == 3) {

            System.out.println("\n--- Sell Stock ---");
            System.out.println("1. TCS");
            System.out.println("2. INFY");
            System.out.println("3. RELIANCE");

            System.out.print("Choose stock: ");
            int sellChoice = scanner.nextInt();

            System.out.print("Enter quantity: ");
            int quantity = scanner.nextInt();

            if (sellChoice == 1) {
                user.sellStock(tcs, quantity);
            } else if (sellChoice == 2) {
                user.sellStock(infy, quantity);
            } else if (sellChoice == 3) {
                user.sellStock(reliance, quantity);
            } else {
                System.out.println("Invalid stock choice.");
            }

        } else if (choice == 4) {

            user.portfolio.displayPortfolio();

        } else if (choice == 5) {

            user.displayUser();

        } else if (choice == 6) {

            user.displayTransactions();

        } else if (choice == 7) {

            user.portfolio.displayProfitLoss();

        } else if (choice == 8) {

            System.out.println(
                "Thank you for using Stock Trading Platform."
            );

        } else {

            System.out.println(
                "Invalid choice. Please try again."
            );
        }

    } while (choice != 8);

    scanner.close();
}

}