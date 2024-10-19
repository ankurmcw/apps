Objective:
----------

Implement a recommendation engine.

Requirements:
-------------

* Many users will make purchases at more than one merchant.
* We'd like to analyze that cross purchasing behavior to make recommendations to new user about where else they might like to shop.
* Imagine we have a list where each entry is an individual user's history of purchases, i.e., the list of merchants that the user has made a purchase at.
* We want to take that list and find, for any merchant with at least one purchase, what other merchant(s) are most frequently seen in users' shopping behavior.
* e.g. [['Casper', 'Purple', 'Wayfair'],['Purple', 'Wayfair', 'Tradesy'],['Wayfair', 'Tradesy', 'Peloton']]

Expected output:
----------------

    'Casper': ['Purple', 'Wayfair'],

    'Purple': ['Wayfair'],

    'Wayfair': ['Purple', 'Tradesy'],

    'Tradesy': ['Wayfair'],

    'Peloton': ['Wayfair', 'Tradesy']