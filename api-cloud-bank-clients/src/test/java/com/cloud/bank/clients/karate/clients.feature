Feature: Customers

  @first
  Scenario: Create a new customer with invalid data
    Given url 'http://localhost:18000/clientes'
    And request {"name":"John Doe","gender":"MALE","age":30,"identification":"","address":"123 Main St, Springfield, IL 62701","phone":"217-555-5555","password":"my-S3cr3t-P@ssw0rd"}
    When method PUT
    Then status 400
