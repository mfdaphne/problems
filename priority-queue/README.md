# Priority Queue

Joe works in a storehouse of a rubber duck lender. He is responsible for bringing rubber ducks to the front desk and for returning them to the store as well. But the manager is not satisfied with service the storehouse is providing to the front desk because either it takes too long before a delivery arrives or only a few items arrive. Joe’s manager wants to fix this and asks Joe to write a web service to accept the orders and provides a list of items to deliver to the front desk.
</br>

<h2>Problem Statement</h2>
Joe explains to you that he got a bunch of requirements for the priority queue and the web service. The constraints he was given are:
• The service should implement a RESTful service
• All orders will be placed in a single queue
• Each order is comprised by the ID of the client and the requested quantity
• A client can only place one order and existing orders cannot be modified
• Client ID’s are in the range of 1 to 20000
• Orders are sorted by the number of seconds they are in the queue

Joe is supposed to look at the queue every 5 minutes and bring as many orders to the front desk as possible. His cart is able to carry 25 rubber ducks and he should put as many orders into his cart without skipping, changing or splitting any orders.