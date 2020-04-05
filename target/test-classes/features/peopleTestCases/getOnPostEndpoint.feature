@all
Feature: police

  Scenario Template: Get details on police officers
    When we make a get request to police force=<policeForce> and check officers list response

    Examples:
      | policeForce    |
      | leicestershire |

