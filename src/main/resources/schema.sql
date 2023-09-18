CREATE TABLE account (
  card_number BIGINT PRIMARY KEY,
  amount DOUBLE NOT NULL );

CREATE TABLE transaction (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  account_id BIGINT NOT NULL,
  date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  amount DOUBLE NOT NULL,
  CONSTRAINT account_fk FOREIGN KEY (account_id) REFERENCES account (card_number) );