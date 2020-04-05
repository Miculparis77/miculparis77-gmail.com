@all
Feature: police

  Scenario Template: Get details on police officers
    Given we make a get request to police force=<policeForce> and check officers list response

    Examples:
      | policeForce    |
      | leicestershire |
      | cheshire       |
      | durham         |


