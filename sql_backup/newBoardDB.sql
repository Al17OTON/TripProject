-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema ssafytrip
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema ssafytrip
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ssafytrip` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `ssafytrip` ;


-- -----------------------------------------------------
-- Table `ssafytrip`.`board`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafytrip`.`board` ;

CREATE TABLE IF NOT EXISTS `ssafytrip`.`board` (
  `article_no` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(16) NULL DEFAULT NULL,
  `subject` VARCHAR(100) NULL DEFAULT NULL,
  `content` VARCHAR(2000) NULL DEFAULT NULL,
  `hit` INT NULL DEFAULT 0,
  `register_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`article_no`))
 /* INDEX `board_to_users_user_id_fk` (`user_id` ASC) VISIBLE,
  CONSTRAINT `board_to_users_user_id_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `ssafytrip`.`users` (`id`))*/
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafytrip`.`memo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafytrip`.`memo` ;

CREATE TABLE IF NOT EXISTS `ssafytrip`.`memo` (
  `memo_no` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(16) NULL DEFAULT NULL,
  `comment` VARCHAR(500) NULL DEFAULT NULL,
  `memo_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `article_no` INT NULL DEFAULT NULL,
  PRIMARY KEY (`memo_no`))
  /*INDEX `memo_to_board_article_no_fk` (`article_no` ASC) VISIBLE,
  INDEX `memo_to_users_fk_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `memo_to_board_article_no_fk`
    FOREIGN KEY (`article_no`)
    REFERENCES `ssafytrip`.`board` (`article_no`),
  CONSTRAINT `memo_to_users_user_id_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `ssafytrip`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)*/
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

select * from board;

select * from board;

select * from users;

delete from board where article_no = 29;


update board
set subject = 22
where article_no = 29;

