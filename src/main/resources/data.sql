INSERT INTO store (id, branchName, address) 
VALUES
  (1, 'Headquarter', '24 Kellogg Rd New Hartford, New York(NY)'),
  (2, 'Fallsburg', 'Po Box 422 South Fallsburg, New York(NY)'),
  (3, 'Levittown', '44 Griddle Ln Levittown, New York(NY)'),
  (4, 'LongBeach', '242 W Hudson St Long Beach, New York(NY)'),
  (5, 'Chazy', '7829 State 22 Rte West Chazy, New York(NY)');

INSERT INTO goods (id, name, price, type) 
VALUES
  (1, 'Borden Milk', 4, 'food'),
  (2, 'Organic Valley Milk', 3.5, 'food'),
  (3, 'Margherita Pizza', 2.5, 'food'),
  (4, 'Levis Jeans', 10, 'clothes'),
  (5, 'Calvin Klein Underwear', 12, 'clothes');

INSERT INTO customer (id, name, sex, age) 
VALUES
  (1, 'Michael Ouyang', 'Male', 22),
  (2, 'Danny Hsu', 'Male', 23),
  (3, 'Christina Anstead', 'Female', 36),
  (4, 'Greg James', 'Male', 34),
  (5, 'Mollie Elizabeth King', 'Female', 32);
