# Bank-App
# Bank Application

This project is a desktop banking application written in Java. The application allows customers to perform banking transactions and admins to perform administrative functions. The user interface is built with Java Swing.

## Features

### Customer Features

- **Deposit**: Customers can deposit money into their accounts.
- **Withdraw**: Customers can withdraw money from their accounts.
- **Open Account**: New accounts can be created.
- **View Account**: Existing accounts and balances can be viewed.
- **Get Credit Card**: Credit card applications can be made.
- **Pay Credit Card Debt**: Credit card debts can be paid.
- **Money Transfer**: Money transfers can be made between accounts.
- **Loan Application**: Loan applications can be made.

### Admin Features

- **View Customer**: All customers can be viewed.
- **Approve Loan**: Loan applications can be approved.
- **Customer Transaction History**: Customer transaction history can be viewed.

- **Staff View**: All staff can be viewed.

- **Credit Card Pending Transactions**: Pending credit card transactions can be viewed.

- **Staff Transaction History**: Staff transaction history can be viewed.

- **Staff Add**: New staff can be added.

### Staff Features

- **Credit Card Approval**: Credit card applications can be approved.

- **Credit Approval**: Credit applications can be approved.

## Technologies Used

- **Programming Language**: Java
- **User Interface**: Java Swing
- **ORM**: Hibernate

## Installation

### Requirements

- Java Development Kit (JDK) 11+
- Maven

### Steps

1. **Clone the Project**:
```sh
git clone https://github.com/Bunox/Bank-App.git
cd bank-app
```

2. **Compile and Run the Project**:
```sh
# Install Maven dependencies
mvn clean install

# Run the application
mvn exec:java -Dexec.mainClass="com.bankapp.Main"
```

## Usage

1. **Customer Login**:
- When the application is opened, you can perform the above customer operations by logging in as a customer.

2. **Admin Login**:
- You can access the administration panel and perform admin operations by logging in as admin.

3. **Staff Login**:
- You can approve credit card and loan applications by logging in as staff.

## License

This project is licensed under the MIT License. For more information, see the `LICENSE` file.

![image](https://user-images.githubusercontent.com/44865326/154795466-f554480c-a213-45cc-bdb4-2505671926b2.png)

![image](https://user-images.githubusercontent.com/44865326/154795538-dd837fd3-34e2-4c15-a0b3-5c2fd674ae4c.png)
