# AccountBook
"AccountBook" is a RESTful web service serves as an online account book. Technology Stack: Java, Spring Framework, Maven, Hibernate, MySQL

More Documents coming, for now please refer to the following as a simple instruction:

AccountBook has three elements: User, Category and Transaction

User (Just a plain old user account, with a username but no authentication):
-addUser: Add a user to the database
-deleteUser: Delete the user from the database and CLEAR ALL CONTENTS FOR THIS USER

Category (A category that the transactions belongs to, such as "grocery"):
-addCategory: Add a category to the database, a category must associate with a user.
-deleteCategory: Delete a category from the database and CLEAR ALL CONTENTS FOR THIS CATEGORY.

Transaction (A transaction you made, can be either "debit" or "credit", it helps you keep track of the money you spent or earned)
-addTransaction: Add a transaction to the database, a transaction must associate with a user and a category.
-updateTransaction: Update each field of an existing transaction.
-deleteTransaction: Delete a transaction from the database.
-searchTransactionByDate: Return all transactions that apply, you need to provide the "username", "categoryname" and the "date" for the transactions.
-searchTransactionByDateRange: Return all transactions that apply, you need to provide the "username", "categoryname", "startdate" and "enddate" for the transactions.
-totalTransaction: Return the total amount of money you SPENT and EARNED for a given period, you need to provide the "username", "categoryname", "startdate" and "enddate" for the transactions.
-totalDebitTransaction: Return the total amount of money you SPENT for a given period, you need to provide the "username", "categoryname", "startdate" and "enddate" for the transactions.
-totalCreditTransaction: Return the total amount of money you EARNED for a given period, you need to provide the "username", "categoryname", "startdate" and "enddate" for the transactions.


A collection of the sample requests for the functions above has been provided ("AccountBook.postman_collection"). To use this file, you need to download Chrome extension "Postman" (https://www.getpostman.com) and then import it. It should be clear from there.