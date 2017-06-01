drop table Card;
drop table Customer;
drop table Card_customer;
drop table Remittance;
drop table Card_remittance;
drop table Basket;
drop table Mobile_refill;
drop table Phone;
drop table Customer_basket;
drop table Keys;
drop table Message;
drop table Phone_message;

drop SEQUENCE card_seq;
drop SEQUENCE customer_seq;
drop SEQUENCE card_customer_seq;
drop SEQUENCE remittance_seq;
drop SEQUENCE card_remittance_seq;
drop SEQUENCE basket_seq;
drop SEQUENCE mobile_refill_seq;
drop SEQUENCE phone_seq;
drop SEQUENCE customer_basket_seq;
drop SEQUENCE keys_seq;

drop TRIGGER card_trigger;
drop TRIGGER customer_trigger;
drop TRIGGER card_customer_trigger;
drop TRIGGER phone_trigger;
drop TRIGGER customer_basket_trigger;
drop TRIGGER keys_trigger;


create table Keys (
  keys_id NUMBER(3),
  public_key NUMBER(10),
  private_key NUMBER(10),
  public_key_n NUMBER(10),
  public_key_fn NUMBER(10)
);

create table Card (
	card_id NUMBER(3),
	card_name VARCHAR(20),
	card_number VARCHAR(16),
	card_validity VARCHAR(10),
	cvv2_code VARCHAR(10),
	account_status VARCHAR(20) NOT NULL,
	currency_of_account VARCHAR(10) NOT NULL,
	CONSTRAINT card_pk PRIMARY KEY(card_id)
);

create table  Customer (
	customer_id NUMBER(3),
	customer_name VARCHAR(20) NOT NULL,
	customer_second_name VARCHAR(20) NOT NULL,
	customer_login VARCHAR(20) NOT NULL,
	customer_password  VARCHAR(100) NOT NULL,
  customer_status VARCHAR(10) NOT NULL,
	CONSTRAINT customer_pk PRIMARY KEY(customer_id)
);

create table Card_customer (
  card_customer_id NUMBER(3),
	card_id NUMBER(3),
	customer_id NUMBER(3),
	CONSTRAINT card_fk FOREIGN KEY (card_id)
	REFERENCES Card,
	CONSTRAINT customer_fk FOREIGN KEY (customer_id)
	REFERENCES Customer
);

create table Remittance (
	remittance_id NUMBER(3),
	sender_card_number NUMBER(16),
	recipient_card_number NUMBER(16),
	amount__of_remittance DECIMAL(31,6),
	currency_of_amount VARCHAR(10) NOT NULL,
	CONSTRAINT remittance_pk PRIMARY KEY(remittance_id)
);

create table Card_remittance(
	card_id NUMBER(3),
	remittance_id NUMBER(3),
	CONSTRAINT card_remittance_fk FOREIGN KEY (card_id)
	REFERENCES Card,
	CONSTRAINT remittance_fk FOREIGN KEY (remittance_id)
	REFERENCES Remittance
);

create table Basket (
	basket_line_id NUMBER(3),
	sender_card_number VARCHAR(16),
	recipient_number VARCHAR(16),
	amount__of_remittance DECIMAL(31,6),
	currency_of_amount VARCHAR(10) NOT NULL,
  data_of_operation VARCHAR(30) NOT NULL,
	status_of_line VARCHAR(15),
	CONSTRAINT basket_pk PRIMARY KEY(basket_line_id)
);

CREATE table Customer_Basket(
  customer_basket_line_id NUMBER(3),
  customer_id NUMBER(3),
  basket_line_id NUMBER(3),
  CONSTRAINT customer_basket_fk FOREIGN KEY (customer_id)
  REFERENCES Customer,
  CONSTRAINT basket_customer_fk FOREIGN KEY (basket_line_id)
  REFERENCES Basket
);

  create table Mobile_refill (
	mobile_refill_id NUMBER(3),
	basket_line_id NUMBER(3),
	card_id NUMBER(3),
	recipient_phone_number VARCHAR(11),
	amount__of_remittance DECIMAL(31,6),
	currency_of_amount VARCHAR(10) NOT NULL,
	CONSTRAINT mobile_refill_pk PRIMARY KEY(mobile_refill_id),
	CONSTRAINT card_mobile_refill_fk FOREIGN KEY (card_id)
	REFERENCES Card,
	CONSTRAINT basket_mobile_refill_fk FOREIGN KEY (basket_line_id)
	REFERENCES Basket
);

create table Phone (
  phone_id NUMBER(3),
  customer_id NUMBER(3),
  phone_operator VARCHAR(10),
  phone_number VARCHAR(16),
  phone_account DECIMAL(31,6),
  currency_of_acount VARCHAR(10) NOT NULL,
  CONSTRAINT phone_pk PRIMARY KEY(phone_id),
  CONSTRAINT customer_id_fk FOREIGN KEY (customer_id)
  REFERENCES Customer
);

create table Message (
  message_id NUMBER(3),
  message_content VARCHAR(4),
  CONSTRAINT message_pk PRIMARY KEY(message_id)
);

create table Phone_message(
  phone_message_id NUMBER(3),
  phone_id NUMBER(3),
  message_id NUMBER(3),
  CONSTRAINT phone_message_pk PRIMARY KEY(phone_message_id),
  CONSTRAINT phone_id_fk FOREIGN KEY (phone_id)
  REFERENCES Phone,
  CONSTRAINT message_id_fk FOREIGN KEY (message_id)
  REFERENCES Message
);


CREATE SEQUENCE card_seq start with 1 INCREMENT BY 1 NOMAXVALUE ;
CREATE SEQUENCE customer_seq start with 1 INCREMENT BY 1 NOMAXVALUE ;
CREATE SEQUENCE card_customer_seq start with 1 INCREMENT BY 1 NOMAXVALUE ;
CREATE SEQUENCE remittance_seq start with 1 INCREMENT BY 1 NOMAXVALUE ;
CREATE SEQUENCE card_remittance_seq start with 1 INCREMENT BY 1 NOMAXVALUE ;
CREATE SEQUENCE basket_seq start with 1 INCREMENT BY 1 NOMAXVALUE ;
CREATE SEQUENCE mobile_refill_seq start with 1 INCREMENT BY 1 NOMAXVALUE ;
CREATE SEQUENCE phone_seq start with 1 INCREMENT BY 1 NOMAXVALUE;
CREATE SEQUENCE customer_basket_seq start with 1 INCREMENT BY 1 NOMAXVALUE;
CREATE SEQUENCE keys_seq start with 1 INCREMENT BY 1 NOMAXVALUE;

CREATE or REPLACE TRIGGER card_trigger
BEFORE INSERT ON Card
for EACH ROW
	BEGIN
		select card_seq.nextval
		INTO :new.card_id
		from dual;
	END;

CREATE or REPLACE TRIGGER customer_trigger
BEFORE INSERT ON Customer
for EACH ROW
	BEGIN
		select customer_seq.nextval
		INTO :new.customer_id
		from dual;
	END;

CREATE or REPLACE TRIGGER card_customer_trigger
BEFORE INSERT ON Card_customer
for EACH ROW
  BEGIN
    select card_customer_seq.nextval
    INTO :new.card_customer_id
    from dual;
  END;

CREATE or REPLACE TRIGGER basket_trigger
BEFORE INSERT ON Basket
for EACH ROW
  BEGIN
    select basket_seq.nextval
    INTO :new.basket_line_id
    from dual;
  END;

CREATE or REPLACE TRIGGER phone_trigger
BEFORE INSERT ON Phone
for EACH ROW
  BEGIN
    select phone_seq.nextval
    INTO :new.phone_id
    from dual;
  END;

CREATE or REPLACE TRIGGER customer_basket_trigger
BEFORE INSERT ON Customer_basket
for EACH ROW
  BEGIN
    select customer_basket_seq.nextval
    INTO :new.customer_basket_line_id
    from dual;
  END;

CREATE or REPLACE TRIGGER keys_trigger
BEFORE INSERT ON Keys
for EACH ROW
  BEGIN
    select keys_seq.nextval
    INTO :new.keys_id
    from dual;
  END;

CREATE OR REPLACE PROCEDURE ExistUser(log IN VARCHAR2, pass OUT VARCHAR2,
                                  exis OUT NUMBER )
AS
  k NUMBER;
  BEGIN
    SET TRANSACTION ISOLATION LEVEL READ COMMITTED;

    SELECT count(customer_login) into k
    FROM Customer
    WHERE customer_login = log;

    IF (k=0)  THEN
      exis := 0;
    ELSE IF(k=1) THEN
      SELECT customer_password into pass
      FROM Customer
      WHERE customer_login = log;
      exis := 1;
      END IF;
    END IF;

  END ExistUser;

CREATE OR REPLACE PROCEDURE ExistSameLogin(log IN VARCHAR2,exis OUT NUMBER )AS
  k NUMBER;
  BEGIN
    SET TRANSACTION ISOLATION LEVEL READ COMMITTED;

    SELECT count(customer_login) into k
    FROM Customer
    WHERE customer_login = log;

    IF (k=0)  THEN
      exis := 0;
    ELSE
      exis := 1;
    END IF;

  END ExistSameLogin;


CREATE OR REPLACE PROCEDURE AmountOfCards
  (cusid IN NUMBER,  cards OUT NUMBER)AS
  BEGIN
    SET TRANSACTION ISOLATION LEVEL READ COMMITTED;

    SELECT count(customer_id) INTO cards
    FROM Card_customer
    WHERE customer_id = cusid;

  END AmountOfCards;


CREATE OR REPLACE PROCEDURE UserName(log IN VARCHAR2,  pass IN VARCHAR2, castnam OUT VARCHAR2, castsecondnam OUT VARCHAR2, castid OUT NUMBER)AS
  BEGIN
    SET TRANSACTION ISOLATION LEVEL READ COMMITTED;

    SELECT customer_name INTO castnam
    FROM Customer
    WHERE customer_login = log
    AND customer_password = pass;

    SELECT customer_second_name INTO castsecondnam
    FROM Customer
    WHERE customer_login = log
    AND customer_password = pass;

    SELECT customer_id INTO castid
    FROM Customer
    WHERE customer_login = log
    AND customer_password = pass;

  END UserName;


CREATE OR REPLACE PROCEDURE ShowCards(ccid IN NUMBER, cusid IN VARCHAR2,  cardsid OUT NUMBER)AS
	k NUMBER;
  BEGIN
		SET TRANSACTION ISOLATION LEVEL READ COMMITTED;

    SELECT count(card_id) into k
    FROM Card_customer
    WHERE customer_id = cusid
          AND card_customer_id = ccid;

    if(k = 0) THEN
      cardsid := 0;
      else if(k!=0)THEN

        SELECT card_id into cardsid
        FROM Card_customer
        WHERE customer_id = cusid
        AND card_customer_id = ccid;
        end if;

    end if;

	END ShowCards;


CREATE OR REPLACE PROCEDURE CardInfo
  (cardid IN VARCHAR2,cardnumber OUT VARCHAR2,cardaccount OUT DECIMAL, cardcurrency OUT VARCHAR2)
AS
  k NUMBER;
  BEGIN
    SET TRANSACTION ISOLATION LEVEL READ COMMITTED;

    SELECT count(card_number) into k
    FROM Card
    WHERE card_id = cardid;

    IF (k = 0) THEN
      cardnumber:='none';
      cardaccount:=0.0;
      cardcurrency:='none';
    ELSE IF (k !=0) then

    SELECT card_number into cardnumber
    FROM Card
    WHERE card_id = cardid;

    SELECT account_status into cardaccount
    FROM Card
    WHERE card_id = cardid;

    SELECT currency_of_account into cardcurrency
    FROM Card
    WHERE card_id = cardid;
      end if;
      end if;

  END CardInfo;


CREATE OR REPLACE PROCEDURE CardInfoNumber
  (cardnumber IN VARCHAR2,cardaccount OUT DECIMAL, cardcurrency OUT VARCHAR2)
AS
  BEGIN
    SET TRANSACTION ISOLATION LEVEL READ COMMITTED;

    SELECT account_status into cardaccount
    FROM Card
    WHERE card_number = cardnumber;

    SELECT currency_of_account into cardcurrency
    FROM Card
    WHERE card_number = cardnumber;

  END CardInfoNumber;

CREATE OR REPLACE PROCEDURE LastCard
  (maxcardid OUT NUMBER)AS
  BEGIN
    SET TRANSACTION ISOLATION LEVEL READ COMMITTED;

    SELECT max(card_id) INTO maxcardid
    FROM Card;

  END LastCard;

CREATE OR REPLACE PROCEDURE MaxMinIdCards
  (cusid IN NUMBER,  maxcardid OUT NUMBER, mincardid OUT NUMBER)AS
  BEGIN
    SET TRANSACTION ISOLATION LEVEL READ COMMITTED;

    SELECT max(card_id) INTO maxcardid
    FROM Card_customer
    WHERE customer_id = cusid;

    SELECT min(card_id) INTO mincardid
    FROM Card_customer
    WHERE customer_id = cusid;

  END MaxMinIdCards;

CREATE OR REPLACE PROCEDURE ExistCard(cardnum IN VARCHAR2, existcard OUT NUMBER)AS
  BEGIN
    SET TRANSACTION ISOLATION LEVEL READ COMMITTED;

    SELECT count(card_id) into existcard
    FROM Card
    WHERE card_number = cardnum;

    IF (existcard IS NULL) THEN
      existcard:=0;
    END IF;

  END ExistCard;

CREATE OR REPLACE PROCEDURE UpdateCard(cardnum IN VARCHAR2, amount IN DECIMAL)AS
  BEGIN
    SET TRANSACTION ISOLATION LEVEL READ COMMITTED;

    UPDATE Card
    SET account_status = amount
    WHERE card_number = cardnum;

  END UpdateCard;

CREATE OR REPLACE PROCEDURE UpdateBasket(newstatus IN VARCHAR2, opdata IN VARCHAR2)AS
  maxid NUMBER;
  BEGIN
    SET TRANSACTION ISOLATION LEVEL READ COMMITTED;

    SELECT max(basket_line_id) INTO maxid
    FROM Basket;

    UPDATE Basket
    SET status_of_line = newstatus
    WHERE basket_line_id = maxid;

    UPDATE Basket
    SET data_of_operation = opdata
    WHERE basket_line_id = maxid;

  END UpdateBasket;

CREATE OR REPLACE PROCEDURE PhoneInfoNumber(phonenumber IN VARCHAR2,phoneaccount OUT DECIMAL, phonecurrency OUT VARCHAR2)AS
  BEGIN
    SET TRANSACTION ISOLATION LEVEL READ COMMITTED;

    SELECT phone_account into phoneaccount
    FROM Phone
    WHERE phone_number = phonenumber;

    SELECT currency_of_acount into phonecurrency
    FROM Phone
    WHERE phone_number = phonenumber;

  END PhoneInfoNumber;

CREATE OR REPLACE PROCEDURE UpdatePhone(phonenum IN VARCHAR2, amount IN DECIMAL)AS
  BEGIN
    SET TRANSACTION ISOLATION LEVEL READ COMMITTED;

    UPDATE Phone
    SET phone_account = amount
    WHERE phone_number = phonenum;

  END UpdatePhone;

CREATE OR REPLACE PROCEDURE LastBasketId(maxbasketid OUT NUMBER)AS
  BEGIN
    SET TRANSACTION ISOLATION LEVEL READ COMMITTED;

    SELECT max(basket_line_id) into maxbasketid
    FROM Basket;

  END LastBasketId;

CREATE OR REPLACE PROCEDURE BasketLines
  (lineid IN NUMBER, cardsender OUT VARCHAR2, cardrecipient OUT VARCHAR2,
   amountremittance OUT DECIMAL, currencyofamount OUT VARCHAR2, dataofoperation OUT VARCHAR2)
AS
  k NUMBER;
  BEGIN
    SET TRANSACTION ISOLATION LEVEL READ COMMITTED;

    SELECT count(sender_card_number) INTO k
    FROM Basket
    WHERE status_of_line = 'committed'
          AND basket_line_id = lineid;

    IF(k !=0) THEN

    SELECT sender_card_number INTO cardsender
    FROM Basket
    WHERE status_of_line = 'committed'
          AND basket_line_id = lineid;

    SELECT recipient_number INTO cardrecipient
    FROM Basket
    WHERE status_of_line = 'committed'
          AND basket_line_id = lineid;

    SELECT amount__of_remittance INTO amountremittance
    FROM Basket
    WHERE status_of_line = 'committed'
          AND basket_line_id = lineid;

    SELECT currency_of_amount INTO currencyofamount
    FROM Basket
    WHERE status_of_line = 'committed'
          AND basket_line_id = lineid;

    SELECT data_of_operation INTO dataofoperation
    FROM Basket
    WHERE status_of_line = 'committed'
          AND basket_line_id = lineid;

    ELSE IF (k = 0) THEN
      cardsender :=0;
      cardrecipient:=0;
      amountremittance :=0;
      currencyofamount :=0;
    END IF;
    END IF;

  END BasketLines;

CREATE OR REPLACE PROCEDURE GetBasketId(castid IN NUMBER, castbasketid IN NUMBER, basketid OUT NUMBER)AS
  k NUMBER;
  BEGIN
    SET TRANSACTION ISOLATION LEVEL READ COMMITTED;

    SELECT count(basket_line_id) INTO k
    FROM Customer_basket
    WHERE customer_id = castid
    AND customer_basket_line_id = castbasketid;

    IF (k = 0) THEN
      basketid:=0;
    ELSE IF (k !=0) THEN

      SELECT basket_line_id INTO basketid
      FROM Customer_basket
      WHERE customer_id = castid
            AND customer_basket_line_id = castbasketid;
    END IF;
      END IF;

  END GetBasketId;

CREATE OR REPLACE PROCEDURE MaxMinCustomerBasketId(castid IN NUMBER, maxcastbaskid OUT NUMBER, mincastbaskid OUT NUMBER)AS
  k NUMBER;
  BEGIN
    SET TRANSACTION ISOLATION LEVEL READ COMMITTED;

    SELECT count(customer_basket_line_id) INTO k
    FROM Customer_basket
    WHERE customer_id = castid;

    IF (k = 0) THEN
      maxcastbaskid:=0;
      mincastbaskid:=0;
    ELSE
      SELECT max(customer_basket_line_id) INTO maxcastbaskid
      FROM Customer_basket
      WHERE customer_id = castid;

      SELECT min(customer_basket_line_id) INTO mincastbaskid
      FROM Customer_basket
      WHERE customer_id = castid;
    END IF;

  END MaxMinCustomerBasketId;


CREATE OR REPLACE PROCEDURE GetKeys(publickey OUT NUMBER,privatekey OUT NUMBER,  publickeyn OUT NUMBER, publickeyfn OUT NUMBER)AS
  k NUMBER;
  BEGIN
    SET TRANSACTION ISOLATION LEVEL READ COMMITTED;

    SELECT max(keys_id) into k
    FROM Keys;

    SELECT public_key into publickey
    FROM Keys
    WHERE keys_id = k;

    SELECT private_key into privatekey
    FROM Keys
    WHERE keys_id = k;

    SELECT public_key_n into publickeyn
    FROM Keys
    WHERE keys_id = k;

    SELECT public_key_fn into publickeyfn
    FROM Keys
    WHERE keys_id = k;

  END GetKeys;


CREATE OR REPLACE PROCEDURE GetAllCustomers(custid IN NUMBER, custname OUT VARCHAR2, custsecondname OUT VARCHAR2,
  custlogin OUT VARCHAR2, custpass OUT VARCHAR2, custstatus OUT VARCHAR2)AS
  k NUMBER;
  BEGIN
    SET TRANSACTION ISOLATION LEVEL READ COMMITTED;

    SELECT count(customer_name) into k
    FROM Customer
    WHERE customer_id = custid;

    IF(k=0) THEN
      custname:='none';
      custsecondname:='none';
      custlogin:='none';
      custpass:='none';

      ELSE IF (k!=0) THEN

        SELECT customer_name into custname
        FROM Customer
        WHERE customer_id = custid;

        SELECT customer_second_name into custsecondname
        FROM Customer
        WHERE customer_id = custid;

        SELECT customer_login into custlogin
        FROM Customer
        WHERE customer_id = custid;

        SELECT customer_password into custpass
        FROM Customer
        WHERE customer_id = custid;

        SELECT customer_status into custstatus
        FROM Customer
        WHERE customer_id = custid;

      END IF;
    END IF;

  END GetAllCustomers;


CREATE OR REPLACE PROCEDURE AmountOfCustomers(maxcustid OUT NUMBER, mincustid OUT NUMBER)AS
  BEGIN
    SET TRANSACTION ISOLATION LEVEL READ COMMITTED;

    SELECT max(customer_id) into maxcustid
    FROM Customer;

    SELECT min(customer_id) into mincustid
    FROM Customer;

    IF (maxcustid IS NULL) THEN
      maxcustid:=0;
    END IF;

    IF (mincustid IS NULL) THEN
      mincustid:=0;
    END IF;

  END AmountOfCustomers;


CREATE OR REPLACE PROCEDURE CustomerBlock(custlogin IN VARCHAR2)AS
  BEGIN
    SET TRANSACTION ISOLATION LEVEL READ COMMITTED;

    UPDATE Customer
    SET customer_status = 'block'
    WHERE customer_login = custlogin;

  END CustomerBlock;


CREATE OR REPLACE PROCEDURE CustomerUnBlock(custlogin IN VARCHAR2)AS
  BEGIN
    SET TRANSACTION ISOLATION LEVEL READ COMMITTED;

    UPDATE Customer
    SET customer_status = 'unblock'
    WHERE customer_login = custlogin;

  END CustomerUnBlock;


CREATE OR REPLACE PROCEDURE GetCustomerStatus(custlogin IN VARCHAR2, custstatus OUT VARCHAR2)AS
  BEGIN
    SET TRANSACTION ISOLATION LEVEL READ COMMITTED;

   SELECT customer_status INTO custstatus
     FROM Customer
       WHERE customer_login = custlogin;

  END GetCustomerStatus;

CREATE OR REPLACE PROCEDURE UpdateCustPassWithNewKey(custid IN VARCHAR2, custpass IN VARCHAR2)AS
  BEGIN
    SET TRANSACTION ISOLATION LEVEL READ COMMITTED;

    UPDATE Customer
    SET customer_password = custpass
    WHERE customer_id = custid;

  END UpdateCustPassWithNewKey;


CREATE OR REPLACE PROCEDURE UpdateKeys AS
  k NUMBER;
  BEGIN
    SET TRANSACTION ISOLATION LEVEL READ COMMITTED;

    SELECT max(keys_id) into k
    FROM Keys;

    DELETE FROM Keys
    WHERE keys_id = k;

  END UpdateKeys;


CREATE OR REPLACE PROCEDURE AmountOfKeys(amountofkeys OUT NUMBER) AS
  BEGIN
    SET TRANSACTION ISOLATION LEVEL READ COMMITTED;

    SELECT count(keys_id) INTO amountofkeys
      FROM Keys;

  END AmountOfKeys;


CREATE OR REPLACE PROCEDURE ClearHistory(custid IN NUMBER) AS
  k NUMBER;
  BEGIN
    SET TRANSACTION ISOLATION LEVEL READ COMMITTED;

    SELECT count(customer_id) INTO k
    FROM Customer_Basket
    WHERE customer_id = custid;

    IF( k!=0) THEN
      DELETE FROM Customer_Basket
      WHERE customer_id = custid;
    END IF;

  END ClearHistory;


CREATE OR REPLACE PROCEDURE CustomerName(custid IN NUMBER, customername OUT VARCHAR2, customersecondname OUT VARCHAR2) AS
  k NUMBER;
  BEGIN
    SET TRANSACTION ISOLATION LEVEL READ COMMITTED;

    SELECT count(customer_id) INTO k
    FROM Customer
    WHERE customer_id = custid;

    IF( k!=0) THEN

      SELECT customer_name INTO customername
      FROM Customer
      WHERE customer_id = custid;

      SELECT customer_second_name INTO customersecondname
      FROM Customer
      WHERE customer_id = custid;

    END IF;

  END CustomerName;


CREATE OR REPLACE PROCEDURE LastCustomerId(maxcastid OUT NUMBER) AS
  BEGIN
    SET TRANSACTION ISOLATION LEVEL READ COMMITTED;

    SELECT max(customer_id) INTO maxcastid
    FROM Customer;

  END LastCustomerId;


CREATE OR REPLACE PROCEDURE CustomerPhone(castid IN NUMBER, phoneid OUT NUMBER) AS
  k NUMBER;
  BEGIN
    SET TRANSACTION ISOLATION LEVEL READ COMMITTED;

    SELECT count(phone_id) INTO k
    FROM Phone
    WHERE customer_id = castid;

    IF(k=0) THEN
      phoneid:=0;
      ELSE IF(k!=0) THEN
        SELECT phone_id INTO phoneid
        FROM Phone
        WHERE customer_id = castid;
        END IF;
      END IF;

  END CustomerPhone;


drop procedure ExistUser;
drop procedure AmountOfCards;
drop procedure UserName;
drop procedure ShowCards;
drop procedure CardInfo;
drop procedure CardInfoNumber;
drop procedure LastCard;
drop procedure MaxMinIdCards;
drop procedure ExistCard;
drop procedure UpdateCard;
drop procedure UpdateBasket;
drop procedure PhoneInfoNumber;
drop procedure UpdatePhone;
drop procedure LastBasketId;
drop procedure BasketLines;
drop PROCEDURE GetBasketId;
drop procedure MaxMinCustomerBasketId;
drop procedure ExistSameLogin;
drop procedure GetKeys;
drop procedure GetAllCustomers;
drop procedure AmountOfCustomers;
drop procedure CustomerBlock;
drop procedure GetCustomerStatus;
drop procedure CustomerUnBlock;
drop procedure UpdateCustPassWithNewKey;
drop procedure UpdateKeys;
drop procedure AmountOfKeys;
drop procedure ClearHistory;
drop procedure CustomerName;
drop procedure LastCustomerId;
drop procedure CustomerPhone;