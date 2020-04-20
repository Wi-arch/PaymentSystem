# PaymentSystem

## The application provides the following features
#### 1) Regardless of the role:
- Registration
- Authorization
- Password recovery
- Change language
- Feedback form
- Logout
#### 2) For the role "User"
- view exchange rates for the current date
- change personal data (name, surname, password)
- lock account
- create a request to unlock the card
- create a request for opening a card
- create a request to open an bank account
- create a request to open a card to an existing bank account
- view all requests and their status
- view all bank accounts
- generation of bank account statement
- view all payment cards
- generation of a payment card statement
- card payment (write-off / deposit) operation
- making a transfer from card to card
- lock payment card
#### 3) For the role "Administrator"
- view exchange rates for the current date
- updating of exchange rates (using API
 National Bank of the Republic of Belarus. See <http://www.nbrb.by/APIHelp/ExRates>)
- view all users
- lock / unlock user account
- view all user requests
- request processing / rejection
- view all user bank accounts
- lock / unlock bank account
- generation of a bank account statement
- view all user payment cards
- lock / unlock payment card
- generation of a payment card statement
#### Additionally, the application implements automatic execution of daily tasks:
- updating exchange rates
- closing cards with expired

## Required configuration
#### Java 8
#### Server Tomcat 9.0
#### Data base MySQL
