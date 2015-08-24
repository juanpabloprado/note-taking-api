CREATE TABLE IF NOT EXISTS note (
  id       INT(11)     NOT NULL AUTO_INCREMENT,
  title    VARCHAR(50) NOT NULL,
  content  TEXT,
  username VARCHAR(20) NOT NULL,


  PRIMARY KEY (id),
  INDEX user_ind (username),

  CONSTRAINT fk_note_user FOREIGN KEY (username)
  REFERENCES user (username)
    ON DELETE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  AUTO_INCREMENT = 1;