CREATE TABLE user (id INTEGER auto_increment,
                     name VARCHAR(255) NOT NULL,
                     passwd VARCHAR(255) NOT NULL,
                     PRIMARY KEY (id));

CREATE TABLE sms (id INTEGER auto_increment,
                    user_id INT  NOT NULL,
                    idSMSC VARCHAR(45) NULL,
                    subid VARCHAR(20) NULL,
                    msisdn VARCHAR(11) NOT NULL,
                    sender VARCHAR(11) NOT NULL,
                    text VARCHAR(160) NOT NULL,
                    status SMALLINT NOT NULL,
                    datetime_inbound DATETIME NOT NULL,
                    datetime_lastmodified DATETIME NULL,
                    ackurl VARCHAR(255) NULL,
                    datetime_scheduled DATETIME NULL,
                    PRIMARY KEY (id));