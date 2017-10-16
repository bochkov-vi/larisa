CREATE TABLE IF NOT EXISTS FILE
(
  ID_FILE      INTEGER PRIMARY KEY     NOT NULL,
  IMAGE_HEIGHT INTEGER,
  IMAGE_WITH   INTEGER,
  NAME         VARCHAR_IGNORECASE(255),
  TYPE         VARCHAR_IGNORECASE(255),
  ENCODING     VARCHAR_IGNORECASE(255),
  DATA         BLOB                    NOT NULL,
  EDITOR       VARCHAR_IGNORECASE(255) NOT NULL,
  CREATOR      VARCHAR_IGNORECASE(255) NOT NULL,
  CREATED_DATE TIMESTAMP,
  EDITED_DATE  TIMESTAMP,
  CONSTRAINT FK_FILE_EDITOR FOREIGN KEY (EDITOR) REFERENCES ACCOUNT (LOGIN),
  CONSTRAINT FK_FILE_CREATOR FOREIGN KEY (CREATOR) REFERENCES ACCOUNT (LOGIN)
);

CREATE TABLE IF NOT EXISTS CUSTOMER
(
  ID_CUSTOMER  INTEGER PRIMARY KEY NOT NULL,
  NAME         VARCHAR_IGNORECASE(255),
  PATRONYMIC   VARCHAR_IGNORECASE(255),
  FAMILY       VARCHAR_IGNORECASE(255),
  ADDRESS      VARCHAR_IGNORECASE(255),
  PHONE        VARCHAR_IGNORECASE(255),
  EMAIL        VARCHAR_IGNORECASE(255),
  CREATED_DATE TIMESTAMP
);

CREATE TABLE IF NOT EXISTS ACCOUNT (
  LOGIN           VARCHAR_IGNORECASE(255) PRIMARY KEY NOT NULL,
  EXPIRATION_DATE DATE,
  PASSWORD        VARCHAR_IGNORECASE(255),
  ID_CUSTOMER     INTEGER,
  CREATED_DATE    TIMESTAMP,
  CONSTRAINT FK_ACCOUNT_ID_CUSTOMER FOREIGN KEY (ID_CUSTOMER) REFERENCES CUSTOMER (ID_CUSTOMER)
);


CREATE TABLE IF NOT EXISTS ACCOUNT_ROLE (
  ACCOUNT_LOGIN VARCHAR_IGNORECASE(255),
  ROLE          VARCHAR_IGNORECASE(255),
  CONSTRAINT FK_ACCOUNT_ROLE_ACCOUNT_LOGIN FOREIGN KEY (ACCOUNT_LOGIN) REFERENCES ACCOUNT (LOGIN)
);


CREATE TABLE IF NOT EXISTS CUSTOMER_EMAIL
(
  CUSTOMER_ID_CUSTOMER INTEGER,
  EMAIL                VARCHAR_IGNORECASE(255),
  CONSTRAINT FK_CUSTOMER_EMAIL_CUSTOMER_ID_CUSTOMER FOREIGN KEY (CUSTOMER_ID_CUSTOMER) REFERENCES CUSTOMER (ID_CUSTOMER)
);

CREATE TABLE IF NOT EXISTS CUSTOMER_FILE
(
  ID_CUSTOMER INTEGER NOT NULL,
  ID_FILE     INTEGER NOT NULL,
  CONSTRAINT CONSTRAINT_3B PRIMARY KEY (ID_CUSTOMER, ID_FILE),
  CONSTRAINT FK_CUSTOMER_FILE_ID_CUSTOMER FOREIGN KEY (ID_CUSTOMER) REFERENCES CUSTOMER (ID_CUSTOMER),
  CONSTRAINT FK_CUSTOMER_FILE_ID_FILE FOREIGN KEY (ID_FILE) REFERENCES FILE (ID_FILE)
);

CREATE TABLE IF NOT EXISTS CUSTOMER_ORDER
(
  ID_CUSTOMER_ORDER INTEGER PRIMARY KEY NOT NULL,
  ID_CUSTOMER       INTEGER             NOT NULL,
  CREATED_DATE      TIMESTAMP,
  CONSTRAINT FK_CUSTOMER_ORDER_ID_CUSTOMER FOREIGN KEY (ID_CUSTOMER) REFERENCES CUSTOMER (ID_CUSTOMER)
);

CREATE TABLE IF NOT EXISTS CUSTOMER_PHONE
(
  CUSTOMER_ID_CUSTOMER INTEGER,
  PHONES               VARCHAR_IGNORECASE(255),
  CONSTRAINT FK_CUSTOMER_PHONE_CUSTOMER_ID_CUSTOMER FOREIGN KEY (CUSTOMER_ID_CUSTOMER) REFERENCES CUSTOMER (ID_CUSTOMER)
);


CREATE TABLE IF NOT EXISTS MAKER
(
  ID_MAKER     INTEGER PRIMARY KEY     NOT NULL,
  NAME         VARCHAR_IGNORECASE(255) NOT NULL,
  NOTE         VARCHAR_IGNORECASE(255),
  CREATED_DATE TIMESTAMP
);

CREATE UNIQUE INDEX IF NOT EXISTS CONSTRAINT_INDEX_4
  ON MAKER (NAME);

CREATE TABLE IF NOT EXISTS PRODUCT_TYPE
(
  ID_PRODUCT_TYPE INTEGER PRIMARY KEY     NOT NULL,
  ID_MAKER        INTEGER,
  NAME            VARCHAR_IGNORECASE(255) NOT NULL,
  VOLUME_NOTE     VARCHAR_IGNORECASE(45),
  SERTIFICATED    DATE DEFAULT NULL,
  EDITOR          VARCHAR_IGNORECASE(255) NOT NULL,
  CREATOR         VARCHAR_IGNORECASE(255) NOT NULL,
  CREATED_DATE    TIMESTAMP,
  EDITED_DATE     TIMESTAMP,
  CONSTRAINT FK_PRODUCT_TYPE_ID_MAKER FOREIGN KEY (ID_MAKER) REFERENCES MAKER (ID_MAKER),
  CONSTRAINT FK_PRODUCT_TYPE_EDITOR FOREIGN KEY (EDITOR) REFERENCES ACCOUNT (LOGIN),
  CONSTRAINT FK_PRODUCT_TYPE_CREATOR FOREIGN KEY (CREATOR) REFERENCES ACCOUNT (LOGIN)
);


CREATE TABLE IF NOT EXISTS INVENTORY
(
  ID_INVENTORY    INTEGER PRIMARY KEY     NOT NULL,
  DATE            DATE,
  ID_PRODUCT_TYPE INTEGER,
  VOLUME          DOUBLE,
  PRICE           DOUBLE,
  EXPIRATION_DATE DATE,
  EDITOR          VARCHAR_IGNORECASE(255) NOT NULL,
  CREATOR         VARCHAR_IGNORECASE(255) NOT NULL,
  CREATED_DATE    TIMESTAMP,
  EDITED_DATE     TIMESTAMP,
  CONSTRAINT FK_INVENTORY_ID_PRODUCT_TYPE FOREIGN KEY (ID_PRODUCT_TYPE) REFERENCES PRODUCT_TYPE (ID_PRODUCT_TYPE),
  CONSTRAINT FK_INVENTORY_EDITOR FOREIGN KEY (EDITOR) REFERENCES ACCOUNT (LOGIN),
  CONSTRAINT FK_INVENTORY_CREATOR FOREIGN KEY (CREATOR) REFERENCES ACCOUNT (LOGIN)
);

CREATE TABLE IF NOT EXISTS INVENTORY_FILE
(
  ID_INVENTORY INTEGER NOT NULL,
  ID_FILE      INTEGER NOT NULL,
  CONSTRAINT CONSTRAINT_7 PRIMARY KEY (ID_INVENTORY, ID_FILE),
  CONSTRAINT FK_INVENTORY_FILE_ID_INVENTORY FOREIGN KEY (ID_INVENTORY) REFERENCES INVENTORY (ID_INVENTORY),
  CONSTRAINT FK_INVENTORY_FILE_ID_FILE FOREIGN KEY (ID_FILE) REFERENCES FILE (ID_FILE)
);


CREATE TABLE IF NOT EXISTS MAKER_FILE
(
  ID_MAKER INTEGER NOT NULL,
  ID_FILE  INTEGER NOT NULL,
  CONSTRAINT CONSTRAINT_6 PRIMARY KEY (ID_MAKER, ID_FILE),
  CONSTRAINT FK_MAKER_FILE_ID_MAKER FOREIGN KEY (ID_MAKER) REFERENCES MAKER (ID_MAKER),
  CONSTRAINT FK_MAKER_FILE_ID_FILE FOREIGN KEY (ID_FILE) REFERENCES FILE (ID_FILE)
);

CREATE TABLE IF NOT EXISTS OUTGO
(
  ID_OUTGO        INTEGER PRIMARY KEY     NOT NULL,
  DATE            DATE                    NOT NULL,
  ID_PRODUCT_TYPE INTEGER,
  VOLUME          DECIMAL(6, 2),
  PRICE           DECIMAL(6, 2),
  EDITOR          VARCHAR_IGNORECASE(255) NOT NULL,
  CREATOR         VARCHAR_IGNORECASE(255) NOT NULL,
  CREATED_DATE    TIMESTAMP,
  EDITED_DATE     TIMESTAMP,
  CONSTRAINT FK_OUTGO_ID_PRODUCT_TYPE FOREIGN KEY (ID_PRODUCT_TYPE) REFERENCES PRODUCT_TYPE (ID_PRODUCT_TYPE),
  CONSTRAINT FK_OUTGO_EDITOR FOREIGN KEY (EDITOR) REFERENCES ACCOUNT (LOGIN),
  CONSTRAINT FK_OUTGO_CREATOR FOREIGN KEY (CREATOR) REFERENCES ACCOUNT (LOGIN)
);

CREATE TABLE IF NOT EXISTS PRICE
(
  ID_PRICE        INTEGER PRIMARY KEY     NOT NULL,
  ID_PRODUCT_TYPE INTEGER,
  DATE_FROM       DATE                    NOT NULL,
  DATE_TO         DATE,
  PRICE           DOUBLE,
  EDITOR          VARCHAR_IGNORECASE(255) NOT NULL,
  CREATOR         VARCHAR_IGNORECASE(255) NOT NULL,
  CREATED_DATE    TIMESTAMP,
  EDITED_DATE     TIMESTAMP,
  CONSTRAINT FK_PRICE_ID_PRODUCT_TYPE FOREIGN KEY (ID_PRODUCT_TYPE) REFERENCES PRODUCT_TYPE (ID_PRODUCT_TYPE),
  CONSTRAINT FK_PRICE_EDITOR FOREIGN KEY (EDITOR) REFERENCES ACCOUNT (LOGIN),
  CONSTRAINT FK_PRICE_CREATOR FOREIGN KEY (CREATOR) REFERENCES ACCOUNT (LOGIN)
);

CREATE UNIQUE INDEX IF NOT EXISTS PRICE_UNIQUE_INDEX_4
  ON PRICE (ID_PRODUCT_TYPE, DATE_FROM);

CREATE TABLE IF NOT EXISTS PRODUCT
(
  ID_PRODUCT      INTEGER PRIMARY KEY     NOT NULL,
  DATE            DATE,
  ID_PRODUCT_TYPE INTEGER,
  VOLUME          DOUBLE,
  PRICE           DOUBLE,
  EXPIRATION_DATE DATE,
  EDITOR          VARCHAR_IGNORECASE(255) NOT NULL,
  CREATOR         VARCHAR_IGNORECASE(255) NOT NULL,
  CREATED_DATE    TIMESTAMP,
  EDITED_DATE     TIMESTAMP,
  CONSTRAINT FK_PRODUCT_ID_PRODUCT_TYPE FOREIGN KEY (ID_PRODUCT_TYPE) REFERENCES PRODUCT_TYPE (ID_PRODUCT_TYPE),
  CONSTRAINT FK_PRODUCT_EDITOR FOREIGN KEY (EDITOR) REFERENCES ACCOUNT (LOGIN),
  CONSTRAINT FK_PRODUCT_CREATOR FOREIGN KEY (CREATOR) REFERENCES ACCOUNT (LOGIN)
);

CREATE TABLE IF NOT EXISTS PRODUCT_FILE
(
  ID_PRODUCT INTEGER NOT NULL,
  ID_FILE    INTEGER NOT NULL,
  CONSTRAINT CONSTRAINT_C85 PRIMARY KEY (ID_PRODUCT, ID_FILE),
  CONSTRAINT FK_PRODUCT_FILE_ID_PRODUCT FOREIGN KEY (ID_PRODUCT) REFERENCES PRODUCT (ID_PRODUCT),
  CONSTRAINT FK_PRODUCT_FILE_ID_FILE FOREIGN KEY (ID_FILE) REFERENCES FILE (ID_FILE)
);


CREATE UNIQUE INDEX IF NOT EXISTS CONSTRAINT_INDEX_C
  ON PRODUCT_TYPE (NAME);
CREATE TABLE IF NOT EXISTS PRODUCT_TYPE_FILE
(
  ID_PRODUCT_TYPE INTEGER NOT NULL,
  ID_FILE         INTEGER NOT NULL,
  CONSTRAINT CONSTRAINT_A PRIMARY KEY (ID_PRODUCT_TYPE, ID_FILE),
  CONSTRAINT FK_PRODUCT_TYPE_FILE_ID_PRODUCT_TYPE FOREIGN KEY (ID_PRODUCT_TYPE) REFERENCES PRODUCT_TYPE (ID_PRODUCT_TYPE),
  CONSTRAINT FK_PRODUCT_TYPE_FILE_ID_FILE FOREIGN KEY (ID_FILE) REFERENCES FILE (ID_FILE)
);

CREATE TABLE IF NOT EXISTS PRODUCT_TYPE_P
(
  ID_PRODUCT_TYPE        INTEGER NOT NULL,
  ID_PRODUCT_TYPE_PARENT INTEGER NOT NULL,
  CONSTRAINT CONSTRAINT_1D PRIMARY KEY (ID_PRODUCT_TYPE, ID_PRODUCT_TYPE_PARENT),
  CONSTRAINT FK_PRODUCT_TYPE_P_ID_PRODUCT_TYPE FOREIGN KEY (ID_PRODUCT_TYPE) REFERENCES PRODUCT_TYPE (ID_PRODUCT_TYPE),
  CONSTRAINT FK_PRODUCT_TYPE_P_ID_PRODUCT_TYPE_PARENT FOREIGN KEY (ID_PRODUCT_TYPE_PARENT) REFERENCES PRODUCT_TYPE (ID_PRODUCT_TYPE)
);

CREATE TABLE IF NOT EXISTS SEQUENCE
(
  SEQ_NAME  VARCHAR_IGNORECASE(50) PRIMARY KEY NOT NULL,
  SEQ_COUNT DECIMAL(38)
);

CREATE TABLE IF NOT EXISTS TOKEN
(
  SERIES       VARCHAR_IGNORECASE(255) PRIMARY KEY NOT NULL,
  DATE         TIMESTAMP                           NOT NULL,
  TOKEN_VALUE  VARCHAR_IGNORECASE(255)             NOT NULL,
  LOGIN        VARCHAR_IGNORECASE(255)             NOT NULL,
  CREATED_DATE TIMESTAMP,
  CONSTRAINT FK_TOKEN_LOGIN FOREIGN KEY (LOGIN) REFERENCES ACCOUNT (LOGIN)
);