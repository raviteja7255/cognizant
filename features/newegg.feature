Feature: Test newegg application
  
  Scenario: validate mandatory fields in the checkout page
    Given Open newegg Login page in GoogleChrome browser
    When I enter valid login details
    Then verify user home page
    Then Search for the items given in the excel file and add them to cart
    Then Validate whether the right products are added into the cart
    Then edit and remove an item from cart
    Then click on checkout and logout
    Then login to forgot password
    Then open gmail and verify the rest password
	   Then application should be closed