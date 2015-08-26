CREATE TABLE IF NOT EXISTS token (
  access_token VARCHAR(36) NOT NULL,
  username     VARCHAR(20) NOT NULL,

  PRIMARY KEY (access_token),
  INDEX user_ind (username),

  CONSTRAINT fk_token_user FOREIGN KEY (username)
  REFERENCES user (username)
    ON UPDATE CASCADE
    ON DELETE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  AUTO_INCREMENT = 1;