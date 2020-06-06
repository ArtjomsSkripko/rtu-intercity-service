Feature: Offer creation

  Scenario: Create intercity offers for adult passenger
    Given offer request for trip from RIGA to DAUGAVPILS
      And set departure time to after 1 hour
      And add passenger type ADULT
      And add transport type BUS
      And add place type ECO
      And add company name NORDEKA
      And set number of tickets to 1
    When call create offers
    Then response status is OK
      And 1 offer has route from RIGA to DAUGAVPILS
      And 1 offer id is not null
      And 1 offer has discount value 1.00

  Scenario: Create intercity offers for scholar passenger
    Given offer request for trip from RIGA to DAUGAVPILS
      And set departure time to after 1 hour
      And add passenger type SCHOLAR
      And add transport type BUS
      And add place type ECO
      And add company name NORDEKA
      And set number of tickets to 1
    When call create offers
    Then response status is OK
      And 1 offer has route from RIGA to DAUGAVPILS
      And 1 offer id is not null
      And 1 offer has discount value 0.60

  Scenario: Create intercity offers when no data found
    Given offer request for trip from RIGA to KRASLAVA
      And set departure time to after 1 hour
      And add passenger type SCHOLAR
      And add transport type BUS
      And add place type ECO
      And add company name NORDEKA
      And set number of tickets to 1
    When call create offers
    Then response status is NOT_FOUND
