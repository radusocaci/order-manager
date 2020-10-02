
# Order Manager Application

Java-based application developed as part of an assignment in my second year of studies. The application provides a simple way for a customer to place an order, while also managing the interaction between the customer and the product owner. Moreover, when the transaction is complete, the application allows the product owner to generate a receipt.

The user interaction is done through a Swing GUI. "Server-side" the application uses basic Java with an MVC architecture. All the information is then persisted in a MySQL database using a custom-build method based on reflection and JDBC (no ORM integration). For more information please check the [documentation](https://github.com/radusocaci/order-manager/blob/master/Order%20Manager%20Documentation.pdf).
