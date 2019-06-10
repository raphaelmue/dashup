-- phpMyAdmin SQL Dump
-- version 4.6.6deb5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jun 05, 2019 at 06:51 PM
-- Server version: 10.1.38-MariaDB-0ubuntu0.18.04.2
-- PHP Version: 7.2.17-0ubuntu0.18.04.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dashup_prod`
--
CREATE DATABASE IF NOT EXISTS `dashup_prod` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `dashup_prod`;

-- --------------------------------------------------------

--
-- Table structure for table `finances`
--

CREATE TABLE `finances`
(
    `id`       int(11) NOT NULL,
    `user_id`  int(11) NOT NULL,
    `category` varchar(32) DEFAULT NULL,
    `value`    int(11)     DEFAULT NULL,
    `content`  varchar(64) DEFAULT NULL,
    `selected` tinyint(1)  DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

-- --------------------------------------------------------

--
-- Table structure for table `panels`
--

CREATE TABLE `panels`
(
    `id`                  int(11)     NOT NULL,
    `user_id`             int(11)     NOT NULL,
    `name`                varchar(32) NOT NULL,
    `short_description`   varchar(256)         DEFAULT NULL,
    `descriptions`        text,
    `publication_date`    date                 DEFAULT NULL,
    `visibility`          tinyint(1)  NOT NULL,
    `category`            varchar(16)          DEFAULT NULL,
    `number_of_ratings`   int(8)      NOT NULL DEFAULT '0',
    `avg_of_ratings`      int(11)     NOT NULL DEFAULT '0',
    `number_of_downloads` int(64)     NOT NULL DEFAULT '0',
    `code_small`          text,
    `code_medium`         text,
    `code_large`          text,
    `icon_code`           varchar(64)          DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

--
-- Dumping data for table `panels`
--

INSERT INTO `panels` (`id`, `user_id`, `name`, `short_description`, `descriptions`, `publication_date`, `visibility`,
                      `category`, `number_of_ratings`, `avg_of_ratings`, `number_of_downloads`, `code_small`,
                      `code_medium`, `code_large`, `icon_code`)
VALUES (1, 3, 'Dashup Weather',
        'This is a short description. I dont know what to write but i need to write something so I ll just produce some shit here to test the design of the detail page.',
        'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.',
        '2019-03-19', 1, 'lifestyle', 3, 67, 1,
        '<dashup-grid-layout>\n    <dashup-text-input name=\"location\"\n                       placeholder=\'Enter the location here...\'\n                       value=\"Karlsruhe\"\n                       layout=\'{\"row\": 1, \"offset\": 0, \"size\": 8}\'>\n    </dashup-text-input>\n    <dashup-button text=\"Send\"\n                   mode=\"display\"\n                   dataAPI=\"http://api.openweathermap.org/data/2.5/weather?units=metric&appid=524da7907b1939626510c547ae65d67c&q=%location%\"\n                   params=\"location\"\n                   consumers=\'[{\"temperature\": \"apiData main temp\"},{\"humidity\": \"apiData main humidity\"}]\'\n                   layout=\'{\"row\": 1, \"offset\": 1, \"size\": 3}\'\n                   handleOnStart>\n    </dashup-button>\n    <dashup-display name=\"temperature\"\n                    label=\"Temperature\"\n                    quantity=\"°C\"\n                    layout=\'{\"row\": 2, \"offset\": 0, \"size\": 12}\'>\n    </dashup-display>\n    <dashup-display name=\"humidity\"\n                    label=\"Humidity\"\n                    quantity=\"%\"\n                    layout=\'{\"row\": 3, \"offset\": 0, \"size\": 12}\'>\n    </dashup-display>\n</dashup-grid-layout>',
        '<dashup-grid-layout>\r\n    <dashup-text-input name=\"location\"\r\n                       placeholder=\'Enter the location here...\'\r\n                       value=\"Karlsruhe\"\r\n                       layout=\'{\"row\": 1, \"offset\": 0, \"size\": 8}\'>\r\n    </dashup-text-input>\r\n    <dashup-button text=\"Send\"\r\n                   mode=\"display\"\r\n                   dataAPI=\"http://api.openweathermap.org/data/2.5/weather?units=metric&appid=524da7907b1939626510c547ae65d67c&q=%location%\"\r\n                   params=\"location\"\r\n                   consumers=\'[{\"temperature\": \"apiData main temp\"},{\"humidity\": \"apiData main humidity\"}]\'\r\n                   layout=\'{\"row\": 1, \"offset\": 1, \"size\": 3}\'\r\n                   handleOnStart>\r\n    </dashup-button>\r\n    <dashup-display name=\"temperature\"\r\n                    label=\"Temperature\"\r\n                    quantity=\"°C\"\r\n                    layout=\'{\"row\": 2, \"offset\": 0, \"size\": 12}\'>\r\n    </dashup-display>\r\n    <dashup-display name=\"humidity\"\r\n                    label=\"Humidity\"\r\n                    quantity=\"%\"\r\n                    layout=\'{\"row\": 3, \"offset\": 0, \"size\": 12}\'>\r\n    </dashup-display>\r\n</dashup-grid-layout>',
        '<dashup-grid-layout>\r\n    <dashup-text-input name=\"location\"\r\n                       placeholder=\'Enter the location here...\'\r\n                       value=\"Karlsruhe\"\r\n                       layout=\'{\"row\": 1, \"offset\": 0, \"size\": 8}\'>\r\n    </dashup-text-input>\r\n    <dashup-button text=\"Send\"\r\n                   mode=\"display\"\r\n                   dataAPI=\"http://api.openweathermap.org/data/2.5/weather?units=metric&appid=524da7907b1939626510c547ae65d67c&q=%location%\"\r\n                   params=\"location\"\r\n                   consumers=\'[{\"temperature\": \"apiData main temp\"},{\"humidity\": \"apiData main humidity\"}]\'\r\n                   layout=\'{\"row\": 1, \"offset\": 1, \"size\": 3}\'\r\n                   handleOnStart>\r\n    </dashup-button>\r\n    <dashup-display name=\"temperature\"\r\n                    label=\"Temperature\"\r\n                    quantity=\"°C\"\r\n                    layout=\'{\"row\": 2, \"offset\": 0, \"size\": 12}\'>\r\n    </dashup-display>\r\n    <dashup-display name=\"humidity\"\r\n                    label=\"Humidity\"\r\n                    quantity=\"%\"\r\n                    layout=\'{\"row\": 3, \"offset\": 0, \"size\": 12}\'>\r\n    </dashup-display>\r\n</dashup-grid-layout>',
        'cloud'),
       (2, 3, 'Dashup Share Banner', 'Dashup Share Banner', 'Dashup Share Banner', '2019-04-25', 1, 'finance', 1, 100,
        0,
        '<dashup-grid-layout>\r\n    <dashup-text-input name=\"code\"\r\n                       placeholder=\'Enter the share code here...\'\r\n                       value=\"SAP\"\r\n                       layout=\'{\"row\": 1, \"offset\": 0, \"size\": 8}\'>\r\n    </dashup-text-input>\r\n    <dashup-button text=\"Send\"\r\n                   mode=\"display\"\r\n                   dataAPI=\"https://api.iextrading.com/1.0/stock/%code%/chart\"\r\n                   params=\"code\"\r\n                   consumers=\'[{\"price\": \"apiData 19 high\"},{\"development\": \"apiData 19 changePercent\"}]\'\r\n                   layout=\'{\"row\": 1, \"offset\": 1, \"size\": 3}\'\r\n                   handleOnStart>\r\n    </dashup-button>\r\n    <dashup-display name=\"development\"\r\n                    label=\"Development\"\r\n                    quantity=\"%\"\r\n                    layout=\'{\"row\": 2, \"offset\": 0, \"size\": 12}\'>\r\n    </dashup-display>\r\n    <dashup-display name=\"price\"\r\n                    label=\"Share Value\"\r\n                    quantity=\"$\"\r\n                    layout=\'{\"row\": 3, \"offset\": 0, \"size\": 12}\'>\r\n    </dashup-display>\r\n</dashup-grid-layout>',
        '<dashup-grid-layout>\r\n    <dashup-text-input name=\"code\"\r\n                       placeholder=\'Enter the share code here...\'\r\n                       value=\"SAP\"\r\n                       layout=\'{\"row\": 1, \"offset\": 0, \"size\": 8}\'>\r\n    </dashup-text-input>\r\n    <dashup-button text=\"Send\"\r\n                   mode=\"display\"\r\n                   dataAPI=\"https://api.iextrading.com/1.0/stock/%code%/chart\"\r\n                   params=\"code\"\r\n                   consumers=\'[{\"price\": \"apiData 19 high\"},{\"development\": \"apiData 19 changePercent\"}]\'\r\n                   layout=\'{\"row\": 1, \"offset\": 1, \"size\": 3}\'\r\n                   handleOnStart>\r\n    </dashup-button>\r\n    <dashup-display name=\"development\"\r\n                    label=\"Development\"\r\n                    quantity=\"%\"\r\n                    layout=\'{\"row\": 2, \"offset\": 0, \"size\": 12}\'>\r\n    </dashup-display>\r\n    <dashup-display name=\"price\"\r\n                    label=\"Share Value\"\r\n                    quantity=\"$\"\r\n                    layout=\'{\"row\": 3, \"offset\": 0, \"size\": 12}\'>\r\n    </dashup-display>\r\n</dashup-grid-layout>',
        '<dashup-grid-layout>\r\n    <dashup-text-input name=\"code\"\r\n                       placeholder=\'Enter the share code here...\'\r\n                       value=\"SAP\"\r\n                       layout=\'{\"row\": 1, \"offset\": 0, \"size\": 8}\'>\r\n    </dashup-text-input>\r\n    <dashup-button text=\"Send\"\r\n                   mode=\"display\"\r\n                   dataAPI=\"https://api.iextrading.com/1.0/stock/%code%/chart\"\r\n                   params=\"code\"\r\n                   consumers=\'[{\"price\": \"apiData 19 high\"},{\"development\": \"apiData 19 changePercent\"}]\'\r\n                   layout=\'{\"row\": 1, \"offset\": 1, \"size\": 3}\'\r\n                   handleOnStart>\r\n    </dashup-button>\r\n    <dashup-display name=\"development\"\r\n                    label=\"Development\"\r\n                    quantity=\"%\"\r\n                    layout=\'{\"row\": 2, \"offset\": 0, \"size\": 12}\'>\r\n    </dashup-display>\r\n    <dashup-display name=\"price\"\r\n                    label=\"Share Value\"\r\n                    quantity=\"$\"\r\n                    layout=\'{\"row\": 3, \"offset\": 0, \"size\": 12}\'>\r\n    </dashup-display>\r\n</dashup-grid-layout>',
        'credit-card'),
       (3, 3, 'Dashup Todo', 'Dashup Todo', 'Dashup Todo', '2019-04-25', 1, 'planning', 2, 70, 1,
        '<dashup-grid-layout>\r\n    <dashup-text-input name=\"todo\"\r\n                       placeholder=\'Enter a task\'\r\n                       layout=\'{\"row\": 1, \"offset\": 0, \"size\": 8}\'\r\n                       clear>\r\n    </dashup-text-input>\r\n    <dashup-button text=\"Add\"\r\n                   mode=\"add\"\r\n                   dataAPI=\"http://localhost:9004/loadTodo\"\r\n                   storageAPI=\"http://localhost:9004/todo\"\r\n                   consumers=\'{\"list\": \"todo\", \"list\": \"apiData list\"}\'\r\n                   producers=\"todo\"\r\n                   layout=\'{\"row\": 1, \"offset\": 1, \"size\": 3}\'\r\n                   handleOnStart>\r\n    </dashup-button>\r\n    <dashup-list name=\"list\"\r\n                 selectable=\"true\"\r\n                 layout=\'{\"row\": 2, \"offset\": 0, \"size\": 12}\'>\r\n    </dashup-list>\r\n    <dashup-button text=\"Delete\"\r\n                   mode=\"delete\"\r\n                   storageAPI=\"http://localhost:9004/todo\"\r\n                   consumers=\'{\"list\": \"list\"}\'\r\n                   producers=\"list\"\r\n                   layout=\'{\"row\": 3, \"offset\": 0, \"size\": 12}\'>\r\n    </dashup-button>\r\n</dashup-grid-layout>',
        '<dashup-grid-layout>\r\n    <dashup-text-input name=\"todo\"\r\n                       placeholder=\'Enter a task here...\'\r\n                       layout=\'{\"row\": 1, \"offset\": 0, \"size\": 8}\'\r\n                       clear>\r\n    </dashup-text-input>\r\n    <dashup-button text=\"Add\"\r\n                   mode=\"add\"\r\n                   dataAPI=\"http://localhost:9004/loadTodo\"\r\n                   storageAPI=\"http://localhost:9004/todo\"\r\n                   consumers=\'[{\"list\": \"todo\"}, {\"list\": \"apiData list\"}]\'\r\n                   producers=\"todo\"\r\n                   layout=\'{\"row\": 1, \"offset\": 1, \"size\": 3}\'\r\n                   handleOnStart>\r\n    </dashup-button>\r\n    <dashup-list name=\"list\"\r\n                 selectable=\"true\"\r\n                 layout=\'{\"row\": 2, \"offset\": 0, \"size\": 12}\'>\r\n    </dashup-list>\r\n    <dashup-button text=\"Delete\"\r\n                   mode=\"delete\"\r\n                   storageAPI=\"http://localhost:9004/todo\"\r\n                   consumers=\'[{\"list\": \"list\"}]\'\r\n                   producers=\"list\"\r\n                   layout=\'{\"row\": 3, \"offset\": 0, \"size\": 12}\'>\r\n    </dashup-button>\r\n</dashup-grid-layout>',
        '<dashup-grid-layout>\r\n    <dashup-text-input name=\"todo\"\r\n                       placeholder=\'Enter a task here...\'\r\n                       layout=\'{\"row\": 1, \"offset\": 0, \"size\": 8}\'\r\n                       clear>\r\n    </dashup-text-input>\r\n    <dashup-button text=\"Add\"\r\n                   mode=\"add\"\r\n                   dataAPI=\"http://localhost:9004/loadTodo\"\r\n                   storageAPI=\"http://localhost:9004/todo\"\r\n                   consumers=\'[{\"list\": \"todo\"}, {\"list\": \"apiData list\"}]\'\r\n                   producers=\"todo\"\r\n                   layout=\'{\"row\": 1, \"offset\": 1, \"size\": 3}\'\r\n                   handleOnStart>\r\n    </dashup-button>\r\n    <dashup-list name=\"list\"\r\n                 selectable=\"true\"\r\n                 layout=\'{\"row\": 2, \"offset\": 0, \"size\": 12}\'>\r\n    </dashup-list>\r\n    <dashup-button text=\"Delete\"\r\n                   mode=\"delete\"\r\n                   storageAPI=\"http://localhost:9004/todo\"\r\n                   consumers=\'[{\"list\": \"list\"}]\'\r\n                   producers=\"list\"\r\n                   layout=\'{\"row\": 3, \"offset\": 0, \"size\": 12}\'>\r\n    </dashup-button>\r\n</dashup-grid-layout>',
        'tasks'),
       (4, 3, 'Dashup Finance', 'Dashup Finance', 'Dashup Finance', '2019-04-28', 1, 'finance', 1, 57, 0,
        '<dashup-grid-layout>\r\n    <dashup-list name=\"financeList\"\r\n                 layout=\'{\"row\": 1, \"offset\": 0, \"size\": 12}\'>\r\n    </dashup-list>\r\n    <dashup-text-input name=\"amount\"\r\n                       placeholder=\"Amount\"\r\n                       layout=\'{\"row\": 2, \"offset\": 0, \"size\": 12}\'\r\n                       clear>\r\n    </dashup-text-input>\r\n    <dashup-radio-button-group name=\"category\"\r\n                               layout=\'{\"row\": 3, \"offset\": 0, \"size\": 12}\'>\r\n        <dashup-radio-button group=\"category\"\r\n                             value=\"Family\"\r\n                             checked>\r\n        </dashup-radio-button>\r\n        <dashup-radio-button group=\"category\"\r\n                             value=\"Housing\">\r\n        </dashup-radio-button>\r\n        <dashup-radio-button group=\"category\"\r\n                             value=\"Meals\">\r\n        </dashup-radio-button>\r\n        <dashup-radio-button group=\"category\"\r\n                             value=\"Investments\">\r\n        </dashup-radio-button>\r\n    </dashup-radio-button-group>\r\n    <dashup-button text=\"Add\"\r\n                   mode=\"add\"\r\n                   dataAPI=\"http://localhost:9004/loadFinanceList\"\r\n                   storageAPI=\"http://localhost:9004/financeList\"\r\n                   consumers=\'[{\"financeList\": \"\"},{\"financeList\": \"apiData financeList\"}]\'\r\n                   producers=\"amount category\"\r\n                   layout=\'{\"row\": 4, \"offset\": 0, \"size\": 12}\'\r\n                   handleOnStart>\r\n    </dashup-button>\r\n    <dashup-button text=\"Del\"\r\n                   mode=\"delete\"\r\n                   storageAPI=\"http://localhost:9004/financeChart\"\r\n                   consumers=\'[{\"financeList\": \"\"},{\"financeList\": \"apiData financeList\"}]\'\r\n                   producers=\"financeList\"\r\n                   layout=\'{\"row\": 5, \"offset\": 0, \"size\": 12}\'>\r\n    </dashup-button>\r\n</dashup-grid-layout>',
        '<dashup-grid-layout>\r\n    <dashup-chart name=\"chart\"\r\n                  title=\"My Spendings\"\r\n                  category=\"Personal Spendings\"\r\n                  layout=\'{\"row\": 1, \"offset\": 0, \"size\": 12}\'>\r\n    </dashup-chart>\r\n    <dashup-text-input name=\"amount\"\r\n                       placeholder=\"Amount\"\r\n                       layout=\'{\"row\": 2, \"offset\": 0, \"size\": 5}\'\r\n                       clear>\r\n    </dashup-text-input>\r\n    <dashup-radio-button-group name=\"category\"\r\n                               layout=\'{\"row\": 2, \"offset\": 1, \"size\": 6}\'>\r\n        <dashup-radio-button group=\"category\"\r\n                             value=\"Family\"\r\n                             checked>\r\n        </dashup-radio-button>\r\n        <dashup-radio-button group=\"category\"\r\n                             value=\"Housing\">\r\n        </dashup-radio-button>\r\n        <dashup-radio-button group=\"category\"\r\n                             value=\"Meals\">\r\n        </dashup-radio-button>\r\n        <dashup-radio-button group=\"category\"\r\n                             value=\"Investments\">\r\n        </dashup-radio-button>\r\n    </dashup-radio-button-group>\r\n    <dashup-button text=\"Add\"\r\n                   mode=\"add\"\r\n                   dataAPI=\"http://localhost:9004/loadFinanceChart\"\r\n                   storageAPI=\"http://localhost:9004/financeChart\"\r\n                   consumers=\'[{\"chart\": \"\"},{\"chart\": \"apiData chart\"}]\'\r\n                   producers=\"amount category\"\r\n                   layout=\'{\"row\": 3, \"offset\": 0, \"size\": 5}\'\r\n                   handleOnStart>\r\n    </dashup-button>\r\n    <dashup-button text=\"Delete\"\r\n                   mode=\"delete\"\r\n                   storageAPI=\"http://localhost:9004/financeChart\"\r\n                   consumers=\'[{\"chart\": \"\"},{\"chart\": \"apiData chart\"}]\'\r\n                   producers=\"chart\"\r\n                   layout=\'{\"row\": 3, \"offset\": 2, \"size\": 5}\'>\r\n    </dashup-button>\r\n</dashup-grid-layout>',
        '<dashup-grid-layout>\r\n    <dashup-chart name=\"chart\"\r\n                  title=\"My Spendings\"\r\n                  category=\"Personal Spendings\"\r\n                  layout=\'{\"row\": 1, \"offset\": 0, \"size\": 12}\'>\r\n    </dashup-chart>\r\n    <dashup-text-input name=\"amount\"\r\n                       placeholder=\"Amount\"\r\n                       layout=\'{\"row\": 2, \"offset\": 0, \"size\": 5}\'\r\n                       clear>\r\n    </dashup-text-input>\r\n    <dashup-radio-button-group name=\"category\"\r\n                               layout=\'{\"row\": 2, \"offset\": 1, \"size\": 6}\'>\r\n        <dashup-radio-button group=\"category\"\r\n                             value=\"Family\"\r\n                             checked>\r\n        </dashup-radio-button>\r\n        <dashup-radio-button group=\"category\"\r\n                             value=\"Housing\">\r\n        </dashup-radio-button>\r\n        <dashup-radio-button group=\"category\"\r\n                             value=\"Meals\">\r\n        </dashup-radio-button>\r\n        <dashup-radio-button group=\"category\"\r\n                             value=\"Investments\">\r\n        </dashup-radio-button>\r\n    </dashup-radio-button-group>\r\n    <dashup-button text=\"Add\"\r\n                   mode=\"add\"\r\n                   dataAPI=\"http://localhost:9004/loadFinanceChart\"\r\n                   storageAPI=\"http://localhost:9004/financeChart\"\r\n                   consumers=\'[{\"chart\": \"\"},{\"chart\": \"apiData chart\"}]\'\r\n                   producers=\"amount category\"\r\n                   layout=\'{\"row\": 3, \"offset\": 0, \"size\": 5}\'\r\n                   handleOnStart>\r\n    </dashup-button>\r\n    <dashup-button text=\"Delete\"\r\n                   mode=\"delete\"\r\n                   storageAPI=\"http://localhost:9004/financeChart\"\r\n                   consumers=\'[{\"chart\": \"\"},{\"chart\": \"apiData chart\"}]\'\r\n                   producers=\"chart\"\r\n                   layout=\'{\"row\": 3, \"offset\": 2, \"size\": 5}\'>\r\n    </dashup-button>\r\n</dashup-grid-layout>',
        'dollar-sign'),
       (5, 2, 'Test Draft', 'Short description', 'Description', '2019-05-28', 0, 'productivity', 0, 0, 0,
        '<dashup-grid-layout>\n    <dashup-text-input name=\"location\"\n                       placeholder=\'Enter the location here...\'\n                       value=\"Karlsruhe\"\n                       layout=\'{\"row\": 1, \"offset\": 0, \"size\": 8}\'>\n    </dashup-text-input>\n    <dashup-button text=\"Send\"\n                   mode=\"display\"\n                   dataAPI=\"http://api.openweathermap.org/data/2.5/weather?units=metric&appid=524da7907b1939626510c547ae65d67c&q=%location%\"\n                   params=\"location\"\n                   consumers=\'[{\"temperature\": \"apiData main temp\"},{\"humidity\": \"apiData main humidity\"}]\'\n                   layout=\'{\"row\": 1, \"offset\": 1, \"size\": 3}\'\n                   handleOnStart>\n    </dashup-button>\n    <dashup-display name=\"temperature\"\n                    label=\"Temperature\"\n                    quantity=\"°C\"\n                    layout=\'{\"row\": 2, \"offset\": 0, \"size\": 12}\'>\n    </dashup-display>\n    <dashup-display name=\"humidity\"\n                    label=\"Humidity\"\n                    quantity=\"%\"\n                    layout=\'{\"row\": 3, \"offset\": 0, \"size\": 12}\'>\n    </dashup-display>\n</dashup-grid-layout>',
        '<dashup-grid-layout>\n    <dashup-text-input name=\"location\"\n                       placeholder=\'Enter the location here...\'\n                       value=\"Karlsruhe\"\n                       layout=\'{\"row\": 1, \"offset\": 0, \"size\": 8}\'>\n    </dashup-text-input>\n    <dashup-button text=\"Send\"\n                   mode=\"display\"\n                   dataAPI=\"http://api.openweathermap.org/data/2.5/weather?units=metric&appid=524da7907b1939626510c547ae65d67c&q=%location%\"\n                   params=\"location\"\n                   consumers=\'[{\"temperature\": \"apiData main temp\"},{\"humidity\": \"apiData main humidity\"}]\'\n                   layout=\'{\"row\": 1, \"offset\": 1, \"size\": 3}\'\n                   handleOnStart>\n    </dashup-button>\n    <dashup-display name=\"temperature\"\n                    label=\"Temperature\"\n                    quantity=\"°C\"\n                    layout=\'{\"row\": 2, \"offset\": 0, \"size\": 12}\'>\n    </dashup-display>\n    <dashup-display name=\"humidity\"\n                    label=\"Humidity\"\n                    quantity=\"%\"\n                    layout=\'{\"row\": 3, \"offset\": 0, \"size\": 12}\'>\n    </dashup-display>\n</dashup-grid-layout>',
        '<dashup-grid-layout>\n    <dashup-text-input name=\"location\"\n                       placeholder=\'Enter the location here...\'\n                       value=\"Karlsruhe\"\n                       layout=\'{\"row\": 1, \"offset\": 0, \"size\": 8}\'>\n    </dashup-text-input>\n    <dashup-button text=\"Send\"\n                   mode=\"display\"\n                   dataAPI=\"http://api.openweathermap.org/data/2.5/weather?units=metric&appid=524da7907b1939626510c547ae65d67c&q=%location%\"\n                   params=\"location\"\n                   consumers=\'[{\"temperature\": \"apiData main temp\"},{\"humidity\": \"apiData main humidity\"}]\'\n                   layout=\'{\"row\": 1, \"offset\": 1, \"size\": 3}\'\n                   handleOnStart>\n    </dashup-button>\n    <dashup-display name=\"temperature\"\n                    label=\"Temperature\"\n                    quantity=\"°C\"\n                    layout=\'{\"row\": 2, \"offset\": 0, \"size\": 12}\'>\n    </dashup-display>\n    <dashup-display name=\"humidity\"\n                    label=\"Humidity\"\n                    quantity=\"%\"\n                    layout=\'{\"row\": 3, \"offset\": 0, \"size\": 12}\'>\n    </dashup-display>\n</dashup-grid-layout>',
        ''),
       (6, 3, 'Dashup Clock', 'Dashup Clock', 'Dashup Clock', '2019-05-27', 1, 'time', 2, 4, 0,
        '<dashup-grid-layout>\r\n    <dashup-clock name=\"clock\" \r\n                  layout=\'{\"row\": 1, \"offset\": 3, \"size\": 6}\'>\r\n    </dashup-clock>\r\n</dashup-grid-layout>',
        '<dashup-grid-layout>\r\n    <dashup-clock name=\"clock\" \r\n                  layout=\'{\"row\": 1, \"offset\": 3, \"size\": 6}\'>\r\n    </dashup-clock>\r\n</dashup-grid-layout>',
        '<dashup-grid-layout>\r\n    <dashup-clock name=\"clock\" \r\n                  layout=\'{\"row\": 1, \"offset\": 3, \"size\": 6}\'>\r\n    </dashup-clock>\r\n</dashup-grid-layout>',
        'clock');

-- --------------------------------------------------------

--
-- Table structure for table `panels_tags`
--

CREATE TABLE `panels_tags`
(
    `id`       int(11) NOT NULL,
    `panel_id` int(11) NOT NULL,
    `tag_id`   int(11) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

--
-- Dumping data for table `panels_tags`
--

INSERT INTO `panels_tags` (`id`, `panel_id`, `tag_id`)
VALUES (1, 1, 1),
       (2, 1, 2),
       (3, 5, 1),
       (4, 5, 2);

-- --------------------------------------------------------

--
-- Table structure for table `properties`
--

CREATE TABLE `properties`
(
    `id`            int(11)     NOT NULL,
    `widget_id`     int(11)     NOT NULL,
    `property`      varchar(32) NOT NULL,
    `type`          varchar(16) NOT NULL,
    `name`          varchar(32) NOT NULL,
    `default_value` varchar(32) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

--
-- Dumping data for table `properties`
--

INSERT INTO `properties` (`id`, `widget_id`, `property`, `type`, `name`, `default_value`)
VALUES (1, 1, 'location.value', 'text', 'Location', 'Walldorf');

-- --------------------------------------------------------

--
-- Table structure for table `ratings`
--

CREATE TABLE `ratings`
(
    `id`         int(11)     NOT NULL,
    `user_id`    int(11)     NOT NULL,
    `panel_id`   int(11)     NOT NULL,
    `rating`     int(11)     NOT NULL,
    `title`      varchar(32) NOT NULL,
    `text`       text        NOT NULL,
    `changed_on` date        NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

--
-- Dumping data for table `ratings`
--

INSERT INTO `ratings` (`id`, `user_id`, `panel_id`, `rating`, `title`, `text`, `changed_on`)
VALUES (1, 4, 1, 87, 'First rating!',
        'I am Joshua of the House Targaryen, the First of His Name, The Unburnt, King of the Andals, the Rhoynar and the First Men, King of Meereen, Khal of the Great Grass Sea, Protector of the Realm, Breaker of Chains and Vather of Dragons!',
        '2018-10-16'),
       (2, 4, 1, 44, 'Second rating!', 'This is the second rating.', '2019-04-28'),
       (3, 3, 2, 100, 'Absolut geisteskrank', 'foll gail', '2019-05-17'),
       (4, 3, 4, 57, 'www', 'www', '2019-05-17'),
       (5, 3, 3, 66, 'dddd', 'ddddd', '2019-05-17'),
       (6, 3, 3, 74, 'ddd', 'ddd', '2019-05-17'),
       (7, 4, 1, 71, 'Test', 'This is a test.', '2019-05-27');

-- --------------------------------------------------------

--
-- Table structure for table `sections_panels`
--

CREATE TABLE `sections_panels`
(
    `id`           int(11)    NOT NULL,
    `panel_id`     int(11)    NOT NULL,
    `widget_index` int(11)    NOT NULL,
    `size`         varchar(8) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

--
-- Dumping data for table `sections_panels`
--

INSERT INTO `sections_panels` (`id`, `panel_id`, `widget_index`, `size`)
VALUES (14, 1, 0, 'small'),
       (14, 3, 1, 'large'),
       (14, 6, 2, 'small'),
       (15, 2, 0, 'large'),
       (15, 4, 1, 'large'),
       (29, 1, 0, 'small'),
       (30, 1, 0, 'large'),
       (31, 3, 0, 'large');

-- --------------------------------------------------------

--
-- Table structure for table `tags`
--

CREATE TABLE `tags`
(
    `id`   int(11)     NOT NULL,
    `text` varchar(32) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

--
-- Dumping data for table `tags`
--

INSERT INTO `tags` (`id`, `text`)
VALUES (1, 'Productivity'),
       (2, 'Weather');

-- --------------------------------------------------------

--
-- Table structure for table `todos`
--

CREATE TABLE `todos`
(
    `id`       int(11)     NOT NULL,
    `user_id`  int(11)     NOT NULL,
    `selected` tinyint(1)  NOT NULL,
    `content`  varchar(64) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

--
-- Dumping data for table `todos`
--

INSERT INTO `todos` (`id`, `user_id`, `selected`, `content`)
VALUES (879, 3, 0, 'Go shopping'),
       (880, 3, 0, 'Hack DigiWill'),
       (881, 3, 0, 'Pretend you are doing a great job');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users`
(
    `id`         int(11)     NOT NULL,
    `email`      varchar(128) DEFAULT NULL,
    `user_name`  varchar(32) NOT NULL,
    `name`       varchar(32)  DEFAULT NULL,
    `surname`    varchar(32)  DEFAULT NULL,
    `password`   varchar(64)  DEFAULT NULL,
    `salt`       varchar(32)  DEFAULT NULL,
    `birth_date` date         DEFAULT NULL,
    `company`    varchar(32)  DEFAULT NULL,
    `bio`        longtext
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `email`, `user_name`, `name`, `surname`, `password`, `salt`, `birth_date`, `company`, `bio`)
VALUES (2, 'raphael@muesseler.de', 'raphaelmue', 'Müßeler', 'Raphael',
        '922031458f20a4354ad3a86534b48effd526f23b338d0a684065db8a51cb72cd', '2gexlSnhthHz4wMGxg473ajByHrdIUm1',
        '1999-03-18', 'SAP SE',
        'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.'),
       (3, 'testuser@gmail.com', 'testuser', 'user', 'test',
        '9333f30896b32a1b5796f94781fb9cd8728adfed464402b0522592a7d0073eae', 'h8cEkA4QNYmObXFd9x305dT9sVIGmcNP', NULL,
        NULL, NULL),
       (4, 'jschulz99@web.de', 'Joshua Schulz', 'Der', 'Coolste',
        '8e779d3958c86b5c058c26faeaa807df8dcfd494b7c774d23ac92306be7a2d7b', '5TwtH8RQgSS5aG67qlbUsqFCju133KR0',
        '1999-05-07', 'SAP SE',
        'Die Reihe Color TV-Game (jap. ?????????, Kar? Terebi G?mu) umfasst die ersten fünf stationären Spielkonsolen des später zum Weltmarktführer der Videospielebranche aufgestiegenen Unternehmens Nintendo. Die zwischen dem 1. Juni 1977 und Ende 1980 in Japan veröffentlichten Geräte sind der ersten Generation von Spielkonsolen zuzuordnen, d. h. die enthaltenen Spiele sind durch festverdrahtete Logik untrennbar mit dem Gerät verbunden und nicht durch zusätzliche Spielmodule austauschbar. Die Bezeichnung Color bezieht sich auf die Fähigkeit – im Gegensatz zu vielen anderen damaligen Spielkonsolen – farbige Grafiken auf dem angeschlossenen Fernseher ausgeben zu können. Im Vergleich zu heutigen Videospielsystemen sind die Grafiken und Toneffekte jedoch nur noch als rudimentär zu bezeichnen.\n\nDie beiden ersten Konsolen der Reihe, Color TV-Game 6 und Color TV-Game 15, sind Adaptionen des damals weit verbreiteten Pong-Spiels. Sie entstanden in Zusammenarbeit mit Mitsubishi Electric und kamen am 1. Juni 1977 auf den Markt. Die beiden Geräte grenzten sich unter anderem durch ihren niedrigen Preis von 48.000 Yen[8] von der Konkurrenz ab und ermöglichten durch ihre zahlreichen Verkäufe Nintendo den Aufstieg zum Marktführer des japanischen Heimkonsolenmarktes. Im Jahre 1978 folgte die Rennspiel-Konsole Color TV-Game Racing 112 und 1979 die Breakout-Adaption Color TV-Game Block Kuzushi. Als letztes Gerät der Reihe erschien 1980 die Othello-Simulation Computer TV-Game.\n\nZu den Verkaufszahlen der Reihe existieren verschiedene Aussagen: David Sheff gibt ca. drei Millionen verkaufte Geräte an, während den späteren Recherchen von Florent Gorges zufolge nur etwa 1,5 Millionen Einheiten abgesetzt wurden.\n\n\nInhaltsverzeichnis\n1	Hintergrund\n2	Color TV-Game\n2.1	Entstehungsgeschichte\n2.2	Color TV-Game 6\n2.3	Color TV-Game 15\n2.4	Verkaufszahlen und Markterfolg\n3	Racing 112 und Block Kuzushi\n3.1	Entwicklung und Gehäusegestaltung\n3.2	Color TV-Game Racing 112\n3.3	Color TV-Game Block Kuzushi\n3.4	Verkaufszahlen\n4	Computer TV-Game\n5	Rezeption\n5.1	Kritik\n5.2	Bedeutung für Nintendo\n5.3	Rezeption durch spätere Videospiele\n6	Literatur\n7	Weblinks\n8	Einzelnachweise\nHintergrund\nIn Folge der Ölkrise 1973 war Nintendos Geschäft mit Laser-Schießsimulationen (Laser Clay Shooting System) zusammengebrochen. Da es sich dabei um das erfolgreichste Geschäft des seit 1889 bestehenden Herstellers von Hanafuda-Spielkarten und Spielzeugen handelte, stand der Konzern vor einer großen Krise. Ab 1975 entwickelte der Konzernpräsident Hiroshi Yamauchi (1927–2013) Interesse am Markt der Videospiele. Als Folge dessen brachte Nintendo in den folgenden Jahren seine ersten Arcade-Automaten auf den Markt. Außerdem beobachtete Yamauchi das hauptsächlich von Atari und Magnavox im Westen aufgebaute Geschäft mit Videospielkonsolen für den Heimgebrauch.[9] In Japan war am 13. September 1975 die erste Heimkonsole auf den Markt gekommen, das TV Tennis Electrotennis von Epoch-sha.[10]\n\nColor TV-Game\nEntstehungsgeschichte\n\nYamauchi lizenzierte das Konzept der Magnavox Odyssey für Nintendos erste Konsolen.\nUm eine eigene Heimkonsole veröffentlichen und so Teil des neuen Marktes werden zu können, lizenzierte Yamauchi 1977 das Konzept der Magnavox Odyssey, die 1972 als erste kommerzielle Heimkonsole erschienen war.[11] Da Nintendo die benötigte Technik nicht selbst herstellen konnte, war die Kooperation mit einem Elektronikunternehmen erforderlich.[7] Daher ging Nintendo für das Projekt eine Kooperation mit Mitsubishi Electric ein.[12]\n\nMitsubishi hatte sich wegen eines eigenen Heimkonsolen-Projekts an Nintendo gewandt. Aufgrund von harter Konkurrenz in der Taschenrechner-Branche hatte sich Mitsubishi in der zweiten Hälfte der 1970er Jahre anderen Sektoren des Elektronik-Marktes zugewandt und unter anderem ein Konsolen-Projekt gestartet. Dafür hatte der Konzern mit einem Unternehmen namens Systek zusammengearbeitet. Wegen dessen baldiger Insolvenz hatte das Projekt nicht fertiggestellt werden können.[12]\n\nYamauchi übertrug die Leitung für das gemeinsame Konsolenprojekt zwischen Nintendo und Mitsubishi an den früher bei Sharp angestellten Ingenieur Masayuki Uemura (* 1943).[12] Als Supervisor von Mitsubishi war Hiromitsu Yagi beteiligt.[13] Nintendo übernahm die Gestaltung und den Vertrieb der Konsole, während Mitsubishi für die Herstellung des integrierten Schaltkreises zuständig war,[12] der die Technik hinter der zu entwickelnden Konsole darstellte.[13]\n\nUm sich von der Konkurrenz im damaligen japanischen Heimkonsolen-Markt abzugrenzen, die ihre Konsolen meist für über 20.000 Yen verkaufte, peilte Yamauchi für Nintendos Produkt einen Verkaufspreis von unter 10.000 Yen an.[12] Damit die Produktionskosten wieder eingefahren werden konnten, musste die Konsole jedoch mindestens 12.000 Yen kosten. Daher entschied Nintendo, die Konsole in zwei Varianten anzubieten, die sich in Umfang und Komfort und dadurch auch im Preis unterscheiden sollten. Die günstigere Version sollte unter der angepeilten Preisgrenze und damit unterhalb der Produktionskosten, die teurere über dem Herstellungspreis und dafür weit über der Kostengrenze vertrieben werden und so für Nintendo profitabel sein.[1]\n\nDie Technik der beiden Konsolenvarianten ist trotz der unterschiedlichen Spieleanzahl identisch. Dadurch wurden die Herstellungskosten reduziert.[14] Der integrierte Schaltkreis, den Mitsubishi für die die beiden Konsolen herstellt, trägt die Bezeichnung M58815P. Spätere Revisionen der beiden Konsolen sind mit dem Schaltkreis M58816P ausgestattet.[15]\n\nColor TV-Game 6\n\nScreenshot eines Spiels von Color TV-Game 6 mit zwei Schlägern pro Spieler und dem Punktestand 4:9.\n\nDas Color TV-Game 6 mit Anleitung und Karton.\nDas Color TV-Game 6 (??? ??????6[16] Kar? Terebi-G?mu Roku) misst 29 cm × 14 cm × 8 cm und enthält sechs Spiele, denen das Konzept des Spieleklassikers Pong (Atari, 1972) zugrunde liegt. Es handelt sich effektiv um drei verschiedene Spiele,[1] die ausschließlich einen Zweispieler-Modus bieten. Jedes Spiel gibt es als Variante mit einem oder zwei Schlägern pro Spieler, wodurch die titelgebende Spieleanzahl zustande kommt.[17] Die Spieler bewegen ihre Schläger mithilfe je eines am Gehäuse befindlichen Drehreglers.[18] Die Bildschirmausgabe der Konsole erfolgt in Farbe.[1]\n\nMit sechs LR20-Batterien bietet Color TV-Game 6 eine Laufzeit von etwa neun Stunden. Einen Stromanschluss hat die Konsole nicht.[1]\n\nDas Color TV-Game 6 erschien im Juni 1977 zum Preis von 9.800 Yen.[1] Die ursprüngliche Variante (Modellbezeichnung CTG-6S) ist weiß; einige Wochen später folgte eine Revision mit orangefarbenem Gehäuse (CTG-6V) für 5.000 Yen. Jene Revision enthält einen Stromadapter, der mit allen Color-TV-Game-Konsolen kompatibel ist, und ist die am weitesten verbreitete Variante des Color TV-Game 6.[19]\n\nEs folgten Sonderveröffentlichungen durch andere Unternehmen. Sharp etwa verkaufte eine eigene, von Nintendo lizenzierte Version des Color TV-Game 6 zusammen mit eigenen Fernsehern. Außerdem boten Unternehmen wie der Instant-Nudel-Produzent House Shanmen aus Werbegründen eigene Varianten der Konsole in sehr geringer Stückzahl an.[20]\n\nColor TV-Game 15\n\nDas Color TV-Game 15 bot gegenüber der parallel veröffentlichten 6er-Version mehr Spiele und zwei verkabelte Controller.\nNeben dem Color TV-Game 6 veröffentlichte Nintendo die erweiterte Variante der Konsole, das Color TV-Game 15 (?????????15[14] Kar? Terebi-G?mu J?-Go) mit 15 Spielen. Alle sechs Spiele des Color TV-Game 6 sind enthalten; die neuen Spiele sind ebenfalls Pong-Abwandlungen. Die Ausnahme stellt das Shooting Game dar, in dem der Spieler mit einem Schläger Ziele auf der anderen Seite des Bildschirmes treffen muss.[2] Die übrigen sieben Spiele gibt es nur als Zweispieler-Variante mit je wieder einem oder zwei Schlägern pro Spieler.[14]\n\nDas Color TV-Game 15 hat die Maße 21 cm × 15 cm × 10 cm und kann sowohl mit LR20-Batterien als auch mit einem separat vertriebenen Stromadapter betrieben werden. Die beiden Controller der Konsole sind im Gegensatz zum Color TV-Game 6 verkabelt, sodass die Spiele sie für eine komfortablere Steuerung in die Hand nehmen können.[2]\n\nDas Color TV-Game 15 wurde parallel zum Color TV-Game 6 im Juni 1977 veröffentlicht. Es kostete 15.000 Yen und war damit weniger als doppelt so teuer wie das Color TV-Game 6, während es über zwei Mal so viele Spiele enthält. Später brachte Nintendo unter der Modellbezeichnung CTG-15V eine Revision mit orangem und rotem Gehäuse heraus.[21] Sharp veröffentlichte eine Variante mit weißer Gehäusefarbe.[14] Gegen Weihnachten 1978 reduzierte Nintendo den Preis des Color TV-Game 15 auf 7.500 Yen. Wenig später stellte das Unternehmen den Verkauf der Konsole ein.[21]\n\nVerkaufszahlen und Markterfolg\nDie Verkaufszahlen der beiden Konsolen sind umstritten. Laut Angaben des Nintendo-Historikers Florent Gorges von 2010 verkaufte sich das Color TV-Game 6 insgesamt 360.000 Mal, womit es im Kontext der damaligen Branche als großer Erfolg galt.[1] Die teurere Variante hingegen soll 700.000 Mal in Japan über die Ladentheken gegangen sein.[2] Der Journalist David Sheff nannte 1993 für beide Konsolen Verkaufszahlen von je etwa einer Million Einheiten.[7] Eine weitere Angabe stammt vom Spielejournalisten Chris Kohler, der 2004 schrieb, dass beide Color-TV-Game-Konsolen zusammen über eine Million Mal verkauft wurden.[18]\n\nColor TV-Game 6 und 15 erwiesen sich als die erfolgreichsten Videospielkonsolen auf dem damaligen japanischen Markt. Mit ihnen konnte sich Nintendo erfolgreich gegen die Konkurrenz behaupten.[2] Nintendo beherrschte damit etwa 70 % des damaligen japanischen Heimvideospielmarkts, während die übrigen Marktanteile auf 20 andere Unternehmen entfielen.[22]\n\nRacing 112 und Block Kuzushi\nEntwicklung und Gehäusegestaltung\nDa Nintendo seine beiden Konsolen aufgrund der fehlenden Modulunterstützung nicht durch die Veröffentlichung neuer Spiele aktuell halten konnte, war ihr Marktzyklus von kurzer Dauer. Der Erfolg der zwei Varianten des Color TV-Game war somit nur kurzfristig. Um ihn aufrechtzuerhalten, musste Nintendo neue Konsolen auf den Markt bringen.[13] So begann die Entwicklung zweier neuer Color-TV-Game-Konsolen mit den Titeln Racing 112 und Block Kuzushi. Für das Hardwaredesign der beiden Konsolen war Takehiro Izushi (* 1952) zuständig.[23] Die Gehäusegestaltung verantwortete Shigeru Miyamoto (* 1952), der spätere Schöpfer von Spieleserien wie Super Mario und The Legend of Zelda. Es war der erste Auftrag für den 1977 bei Nintendo eingestellten Industriedesigner Miyamoto. Er war mit der Gestaltung der beiden vorherigen Konsolen unzufrieden und strebte für die nächsten Konsolen ein Design an, das spaßiger aussehen solle. So bestand er darauf, einen Schaltknüppel für das Rennspiel Racing 112 zu integrieren. Die Schalter auf dem Gehäuse von Racing 112, mit denen der Spieler Optionen einstellen kann, beschriftete Miyamoto nicht, sondern versah sie mit einfach zu begreifenden Symbolen. Das Gehäuse von Block Kuzushi entwarf er derart, dass Links- und Rechtshänder das Spiel gleichermaßen genießen können.[24]\n\nBlock Kuzushi wurde als erster Color-TV-Game-Teil selbständig von Nintendo und ohne Mitwirkung von Mitsubishi entwickelt. Daher ist das Nintendo-Logo erstmals auf der Oberseite der Konsole sichtbar.[25]\n\nColor TV-Game Racing 112\nDie dritte Nintendo-Konsole, Color TV-Game Racing 112 (??????????????112[26] Kar? Terebi-G?mu R?shingu Hyaku-J?-Ni), bietet ein Rennspiel. Die Straße wird aus der Vogelansicht und ohne räumliche Perspektive dargestellt. Die Konsole umfasst zehn Spielvariationen mit Zielen wie möglichst viele Autos in einer bestimmten Zeitspanne zu überholen oder möglichst weit ohne einen Zusammenstoß zu fahren. Maximal kann die Konsole vier Fahrzeuge gleichzeitig darstellen.[27] Einstellbar sind Optionen wie die Autoanzahl, das -tempo, die Straßenart, Leitplanken oder Glätte, sodass insgesamt 112 verschiedene Spielmodi enthalten sind.[28]\n\nIm Einzelspieler-Modus dient ein in das Gehäuse integriertes Lenkrad inklusive Schaltung als Steuerung. Außerdem bietet die Konsole einen Zweispieler-Modus, bei dem zur Steuerung ausschließlich die beiden enthaltenen Kabel-Controller mit je einem Drehregler verwendet werden können.[27]\n\nMit einer Größe von 47 cm × 17 cm × 26 cm ist das Color TV-Game Racing 112 die größte jemals von Nintendo veröffentlichte Konsole. Das integrierte Lenkrad weist einen Durchmesser von 18 cm auf.[27]\n\nUrsprünglich für 18.000 Yen angekündigt, erschien Color TV-Game Racing 112 im Juni 1978 schließlich für 12.500 Yen.[3] 1979 senkte Nintendo den Preis der Konsole jedoch auf 5.000 Yen ab.[27]\n\nColor TV-Game Block Kuzushi\n\nColor TV-Game Block Kuzushi zeigt als erste Nintendo-Konsole prominent den Unternehmens-Schriftzug.\nColor TV-Game Block Kuzushi (???????????????[29] Kar? Terebi-G?mu Burokku Kuzushi) hat die Maße 32 cm × 17 cm × 8 cm. Die Konsole bietet sechs Varianten des Spielkonzeptes hinter Breakout (Atari, 1976).[30] Entsprechend steuert der Spieler einen Schläger an der unteren Seite des Bildschirmes, mit dem er einen Ball kontrollieren kann. Berührt der Ball einen der im oberen Bildschirmabschnitt dargestellten Blöcke, so erhält der Spieler Punkte und der getroffene Block verschwindet.[29] Im Spielmodus Block Lighter sind vier Blöcke blinkend hervorgehoben, die der Spieler treffen muss, während die übrigen Blöcke Bonuspunkte einbringen. In einem weiteren Spielmodus, Block Trough, verschwinden alle berührten Blöcke und das Ziel besteht darin, möglichst schnell sämtliche Blöcke verschwinden zu lassen.[25] Die Konsole ermöglicht dem Spieler über Schalter die Spielvariante und die Anzahl der Bälle zu bestimmen. Im Spiel kann der Spieler den Schläger über einen Drehregler kontrollieren und so Einfluss auf die Geschwindigkeit und den Abprallwinkel des Balles nehmen.[30]\n\nBlock Kuzushi kam für 13.500 Yen auf den Markt. Als Erscheinungszeitraum wird in der Sekundärliteratur April 1979 angegeben.[29] Einem japanischen Prospekt zufolge war der offizielle Erscheinungstermin der 8. März 1979.[4] 1980 wurde der Preis der Konsole auf 8.300 Yen reduziert und 1981 wurde sie vom Markt genommen.[25]\n\nVerkaufszahlen\nVon Racing 112 setzte Nintendo laut Gorges etwa 160.000 Einheiten ab,[3] während Block Kuzushi in Japan trotz starker Konkurrenz von Atari und Epoch-sha nach Angaben des japanischen Toy Journal von 1979 über 400.000 Mal verkauft wurde.[30] Angaben von David Sheff zufolge verkauften sich Racing 112 und Block Kuzushi hingegen je eine halbe Million Mal.[7]\n\nComputer TV-Game\n\nDas Computer TV-Game von 1980 ist die seltenste Nintendo-Konsole.\nSiehe auch: Computer Othello\nComputer TV-Game (???????TV???[31] Konpy?t? Terebi-G?mu) ist die letzte der frühen Nintendo-Konsolen und erschien Anfang 1980. Es ist eine Heimumsetzung von Computer Othello (Nintendo, 1978), einem Arcade-Spiel von Nintendo, das seinerseits eine Adaption des strategischen Zweipersonen-Brettspiels Othello darstellt. Die Konsole bietet keine Farbbildausgabe.[5]\n\nComputer TV-Game enthält die gleiche Technik wie der Arcade-Automat, auf dem die Konsole basiert. Deshalb ist sie sehr groß, war zum Zeitpunkt ihrer Veröffentlichung technisch veraltet und ist auf einen Stromanschluss mit einem Gewicht von etwa zwei Kilogramm angewiesen.[31]\n\nEntweder kann gegen einen zweiten Spieler oder gegen den Computer gespielt werden.[31] Ein Vorteil der Konsole gegenüber der Arcade-Version besteht darin, dass der Spieler unbegrenzt viel Zeit für einen Zug hat.[5]\n\nDas Computer TV-Game erschien 1980 zum Preis von 48.000 Yen. Dies widersprach Yamauchis Strategie, Konsolen möglichst günstig herauszubringen, und ist dadurch zu erklären, dass sich die Konsole hauptsächlich an Unternehmen richtete. Diese sollten Computer TV-Game etwa ihren Kunden zur Unterhaltung oder zur Überbrückung von Wartezeiten bereitstellen. Entsprechend veröffentlichte Nintendo die Konsole in geringer Stückzahl. Sie gilt als das seltenste elektronische Nintendo-Produkt.[5]\n\nRezeption\nKritik\nDer Videospieljournalist Chris Kohler schrieb 2004, dass die Konsolen der Reihe Color TV-Game inhaltlich zwar nur wenige Unterschiede zur Konkurrenz aufwiesen, sich jedoch durch ihre Gehäuse- und Controllergestaltung abheben konnten. Durch die Farbgebung ihrer Gehäuse sahen die Konsolen Kohler zufolge deutlich aus wie Spielzeuge.[24]\n\nColor TV-Game 6 und Color TV-Game 15 überzeugten den damaligen japanischen Videospielmarkt durch ihre gute Qualität, ihren relativ niedrigen Preis und ihre Farbdarstellung. Die meisten Konkurrenzkonsolen boten im Vergleich zu Nintendos Produkten schwarz-weiß-Grafik, ähnelten einander stark und waren verhältnismäßig teurer.[22]\n\nDie 112 Spielvarianten des Color TV-Game Racing 112 ähnelten einander sehr stark. Da die Konsole jedoch ein für die damalige Zeit beeindruckendes Spieltempo bot und die Steuerung gut funktionierte, gilt sie trotzdem als spielerisch von guter Qualität.[27]\n\nColor TV-Game Block Kuzushi grenzt sich durch seine vielfältigen Spieloptionen von anderen damaligen Breakout-Klonen ab. Das Tempo des Balls und die Steuerung gelten als gelungen, einige der enthaltenen Spiele als originell und abwechslungsreich.[30]\n\nComputer TV-Game wurde für seinen ungewöhnlich hohen Preis und die im Vergleich dazu niedrige Qualität kritisiert. Die künstliche Intelligenz ist simpel und bietet dem Spieler keine hohe Herausforderung.[5]\n\nBedeutung für Nintendo\nDank seiner ersten Heimkonsolen stieg Nintendo zum Führer des japanischen Heimvideospielmarktes auf und konnte die zuvor drohende Insolvenz abwenden. Ohne Erweiterungsmöglichkeiten in Form von Spielmodulen stellten die Color-TV-Game-Konsolen allerdings nur kurzfristige Erfolge dar. Daher suchte Nintendo anschließend nach Produkten, die nachhaltigere Erfolge ermöglichten.[13]\n\nNach der Veröffentlichung der Color-TV-Game-Konsolen wandte sich Nintendo innerhalb des Spielesektors dem Arcade- und dem Handheld-Markt (Game & Watch) zu. Die seither angesammelte Erfahrung resultierte 1983 in der Veröffentlichung des Famicom, einer Heimkonsole mit austauschbaren Spielmodulen, die Nintendo mit Unterstützung des Unternehmens Ricoh herstellte. Unter dem Namen Nintendo Entertainment System gelangte das Famicom zu globalem Erfolg.[32] Der leitende Mitsubishi-Techniker des Color-TV-Game-Projektes, Hiromitsu Yagi, war später bei Ricoh für die Technik des Famicom zuständig.[13]\n\nRezeption durch spätere Videospiele\nDas Nintendo-Spiel Alleyway (GB, 1989) baut auf das Spielprinzip von Color TV-Game Block Kuzushi auf.[13] In WarioWare, Inc.: Mega Microgame$! (GBA, 2003) ist eine Umsetzung von Racing 112 in Form eines Minispiels enthalten und in WarioWare: Smooth Moves (Wii, 2006) gibt es ein auf Color TV-Game 6 basierendes Minispiel.[33] Außerdem taucht Color TV-Game 15 als sogenannte Helfertrophäe in Super Smash Bros. for Nintendo 3DS / for Wii U (3DS/Wii U, 2014) auf.[13]\n\nLiteratur\nFlorent Gorges: The History of Nintendo, Volume 1. 1889–1980: From playing-cards to Game & Watch. Pix\'N Love, 2010, ISBN 978-2-918272-15-1, 8: The First Video Game Consoles, S. 212–225.\nChris Kohler: Power Up. How Japanese Video Games Gave the World an Extra Life. BradyGames, Indianapolis, Indiana 2004, ISBN 0-7440-0424-1, S. 30–33.\nDavid Sheff: Game Over. How Nintendo Zapped an American Industry, Captured Your Dollars, and Enslaved Your Children. Random House, New York 1993, ISBN 0-679-40469-4, S. 26 f.\nErik Voskuil: Before Mario. The fantastic toys from the video game giant\'s early days. Omaké Books, 2014, ISBN 978-2-919603-10-7, 5: Home Consoles, S. 188–209.\nWeblinks\n Commons: Color TV-Game – Sammlung von Bildern, Videos und Audiodateien\nÜbersichtsseite zur Color-TV-Game-Reihe, bei: Before Mario, vom 15. März 2011, englisch\nInside Nintendo 56: Color TV-Game – die Geschichte der ersten Nintendo-Konsolen, bei: Nintendo-Online, vom 28. Dezember 2014\nNintendo\'s First Console Is One You\'ve Never Played, bei: Kotaku, vom 25. März 2011, englisch\nVideo zum Color TV-Game 6, bei YouTube, vom 11. Juli 2010, englisch\nEinzelnachweise\n Gorges, The History of Nintendo, 2010, S. 216.\n Gorges, The History of Nintendo, 2014, S. 218.\n Gorges, The History of Nintendo, 2010, S. 220.\n Prospekt zum Color TV Game Block Kuzushi; Zugriff via Erik Voskuil: Nintendo Color TV Game Block Kuzushi - Leaflet. In: Before Mario. 7. Januar 2012, abgerufen am 27. Februar 2015 (englisch).\n Gorges, The History of Nintendo, 2010, S. 224.\n Gorges, The History of Nintendo, 2010, S. 216, 218, 220, 222.\n Sheff, Game Over, 1993, S. 27.\n 10 Oldest Video Game Consoles in The World. In: Oldest.org. 4. Dezember 2017, abgerufen am 21. Januar 2019 (amerikanisches Englisch).\n Sheff, Game Over, 1993, S. 26 f.\n Gorges, The History of Nintendo, 2010, S. 212.\n Kohler, Power Up, 2004, S. 30.\n Gorges, The History of Nintendo, 2010, S. 214.\n Tobias Schmitz: Inside Nintendo 56: Color TV-Game – die Geschichte der ersten Nintendo-Konsolen. In: Nintendo-Online. 28. Dezember 2014, abgerufen am 7. Januar 2015.\n Voskuil, Before Mario, 2014, S. 194.\n Wartungshandbuch Color TV-Game 15; Zugriff via Erik Voskuil: Nintendo Color TV Game 15 - Service Manual (??? ?????? 15 ???? ?????). In: Before Mario. 28. Dezember 2011, abgerufen am 27. Februar 2015 (englisch).\n Voskuil, Before Mario, 2014, S. 190.\n Voskuil, Before Mario, 2014, S. 190, 194.\n Kohler, Power Up, 2004, S. 31.\n Gorges, The History of Nintendo, 2010, S. 216 f.\n Gorges, The History of Nintendo, 2014, S. 217.\n Gorges, The History of Nintendo, 2010, S. 218 f.\n Gorges, The History of Nintendo, 2010, S. 219.\n Iwata fragt: Game & Watch, 1. Als die Entwickler alles machten. In: Nintendo. April 2010, abgerufen am 14. Februar 2015.\n Kohler, Power Up, 2004, S. 32 f.\n Gorges, The History of Nintendo, 2010, S. 223.\n Voskuil, Before Mario, 2014, S. 198.\n Gorges, The History of Nintendo, 2010, S. 220 f.\n Kohler, Power Up, 2004, S. 33.\n Voskuil, Before Mario, 2014, S. 202.\n Gorges, The History of Nintendo, 2014, S. 222.\n Voskuil, Before Mario, 2014, S. 206.\n Voskuil, Before Mario, 2014, S. 188.\n Gorges, The History of Nintendo, 2010, S. 234.\nEinklappen\nKonsolen von Nintendo\nStationäre Spielkonsolen	\nColor TV-Game (6 • 15 • Racing 112 • Block Breaker • Computer TV-Game) • Nintendo Entertainment System (Famicom) • Super Nintendo Entertainment System • Virtual Boy • Nintendo 64 (iQue Player) • Nintendo GameCube (Panasonic Q) • Wii • Wii U • NES Classic Mini • Nintendo Switch • SNES Classic Mini\n\n{{{Bild-Beschreibung}}}\nTragbare Spielkonsolen	\nGame & Watch • Game Boy • Game Boy Advance • Pokémon Mini • Nintendo DS (DSi) • Nintendo 3DS (2DS)\n\n	Dieser Artikel wurde am 28. Februar 2015 in dieser Version in die Liste der lesenswerten Artikel aufgenommen.\nKategorien: Wikipedia:LesenswertNintendo-Spielkonsole\nNavigationsmenü\nNicht angemeldetDiskussionsseiteBeiträgeBenutzerkonto erstellenAnmeldenArtikelDiskussionLesenBearbeitenQuelltext bearbeitenVersionsgeschichteSuche\nWikipedia durchsuchen\nHauptseite\nThemenportale\nZufälliger Artikel\nMitmachen\nArtikel verbessern\nNeuen Artikel anlegen\nAutorenportal\nHilfe\nLetzte Änderungen\nKontakt\nSpenden\nWerkzeuge\nLinks auf diese Seite\nÄnderungen an verlinkten Seiten\nSpezialseiten\nPermanenter Link\nSeiten­informationen\nWikidata-Datenobjekt\nArtikel zitieren\nDrucken/­exportieren\nBuch erstellen\nAls PDF herunterladen\nDruckversion\nIn anderen Projekten\nCommons\n\nIn anderen Sprachen\nEnglish\nEspañol\nFrançais\nHrvatski\nItaliano\nNederlands\nPolski\n???????\nTürkçe\n13 weitere\nLinks bearbeiten\nDiese Seite wurde zuletzt am 26. April 2019 um 08:43 Uhr bearbeitet.\nAbrufstatistik\n\nDer Text ist unter der Lizenz „Creative Commons Attribution/Share Alike“ verfügbar; Informationen zu den Urhebern und zum Lizenzstatus eingebundener Mediendateien (etwa Bilder oder Videos) können im Regelfall durch Anklicken dieser abgerufen werden. Möglicherweise unterliegen die Inhalte jeweils zusätzlichen Bedingungen. Durch die Nutzung dieser Website erklären Sie sich mit den Nutzungsbedingungen und der Datenschutzrichtlinie einverstanden.\nWikipedia® ist eine eingetragene Marke der Wikimedia Foundation Inc.');

-- --------------------------------------------------------

--
-- Table structure for table `users_properties`
--

CREATE TABLE `users_properties`
(
    `id`          int(11)     NOT NULL,
    `user_id`     int(11)     NOT NULL,
    `property_id` int(11)     NOT NULL,
    `value`       varchar(32) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

--
-- Dumping data for table `users_properties`
--

INSERT INTO `users_properties` (`id`, `user_id`, `property_id`, `value`)
VALUES (1, 2, 1, 'Karlsruhe');

-- --------------------------------------------------------

--
-- Table structure for table `users_sections`
--

CREATE TABLE `users_sections`
(
    `user_id`       int(11)     NOT NULL,
    `id`            int(11)     NOT NULL,
    `section_name`  varchar(35) NOT NULL,
    `section_index` int(11)     NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

--
-- Dumping data for table `users_sections`
--

INSERT INTO `users_sections` (`user_id`, `id`, `section_name`, `section_index`)
VALUES (3, 14, 'Personal section', 0),
       (3, 15, 'How to make money', 1),
       (4, 25, 'New Section', -1),
       (4, 26, 'New Section2', 25),
       (4, 29, 'Dashup Weather', 2),
       (2, 30, 'Weather', 0),
       (2, 31, 'Productivity', 1);

-- --------------------------------------------------------

--
-- Table structure for table `users_settings`
--

CREATE TABLE `users_settings`
(
    `id`               int(11)     NOT NULL,
    `user_id`          int(11)     NOT NULL,
    `background_image` varchar(512)         DEFAULT NULL,
    `theme`            varchar(16) NOT NULL,
    `language`         varchar(2)  NOT NULL DEFAULT 'en'
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

--
-- Dumping data for table `users_settings`
--

INSERT INTO `users_settings` (`id`, `user_id`, `background_image`, `theme`, `language`)
VALUES (1, 2,
        'https://esossl-a.akamaihd.net/assets/img/cms/media/cf495066fddb391eecaf4abad1404341_the-elder-scrolls-online-elsweyr_wallpaper-1920x1080.jpg',
        'blue-sky', 'de'),
       (2, 3,
        'https://images.pexels.com/photos/457882/pexels-photo-457882.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940',
        'black-night', 'de'),
       (3, 4, '', 'red-love', 'de');

-- --------------------------------------------------------

--
-- Table structure for table `users_tokens`
--

CREATE TABLE `users_tokens`
(
    `id`          int(11)     NOT NULL,
    `user_id`     int(11)     NOT NULL,
    `token`       varchar(64) NOT NULL,
    `expire_date` date        NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

--
-- Dumping data for table `users_tokens`
--

INSERT INTO `users_tokens` (`id`, `user_id`, `token`, `expire_date`)
VALUES (7, 2, 'W9sOT0mnbl8PKambIE6ZfoqDx4PLjm8mKl5tFb4GcoK7P0n09LxfVOR4xbY7bB8b', '2019-05-08'),
       (8, 2, 'iwIO43T5LGbc83WRR2ZJbF84O4pnYtp3kK1jpw7rcYtyCmVqzW90IJBqSTMTZCoM', '2019-05-08'),
       (9, 2, '6zrF4XBEXW0etXB3PReDsjziL5K9S1jakIeUuEtPA2Sw93rVMQti6sXCvuGcbBMb', '2019-05-08'),
       (10, 2, 'yUTF8rbZhaQSnjfIILjFLwbAe4pr0hlQTOD41NgQQk83uhzpNW97jrTpHe7c8uq1', '2019-05-11'),
       (11, 2, '6s9GLJF4DCnie4zLREOAanmo6UHlMB6c6lNYJImAwBdnmdJZAzEb2KfCgS3eOeKS', '2019-05-23'),
       (17, 3, 'tiX4JAcqEcUioh8YvbNl3Pplcfdhsh7yyg3upDRagtYGAHd3Xt1YmoD2CV5MCvF7', '2019-05-26'),
       (18, 4, 'CTLXqnKrmeTtSmVCFEt1Ez9HnGPh3MSlnJr1sVUs450CLhNc2ThCEv8qdbc2jZ1Y', '2019-05-26'),
       (21, 2, 'nUxXcu2LvEi8PQpvbmI6gMRx8ayUjQXIipNjZk0YxG6DEfZBiVukYw4VOhS1R7ay', '2019-05-28'),
       (22, 3, 'yLxds5mD9fhRRYV55W4b4HnuL32I0RXZWUSRfH27efmnMm7M5Y1XTLeLbR4OfcHs', '2019-05-28'),
       (24, 4, 'kY1ya8zysJXTMSA8VxGsQDBHBHMl1iGxrPLHvKQgLBgSQ2O95KKlN6c5lnOD9uaW', '2019-05-29'),
       (25, 3, 'gueO46FZGlgtUnjMSIZl1ixopg3sovpHB0fKnTj1i4cBBySVimdh33DNjhCR3nQc', '2019-06-05'),
       (26, 3, 'wGyLSu40ApwFOVUnUazznsiUzY8jSLY7125y9AwNPpI6aUTafJoJfjZJGOAsmZRJ', '2019-06-09'),
       (27, 3, 'qKvlDIwMUQNlOiEGAWk1RhjbsnyRwY5Nho9W94U3kw16NSkNjpsq6suY5b93ChxX', '2019-06-17'),
       (28, 2, 'e6GDcU3CWvqyuRXVuFDVzawLxxNy1D3LLTbFfqJFjEZt7d7EVWYzD4Ifyu9fM6Yq', '2019-06-21'),
       (29, 3, 'FcY5wSA6Syzy9SDfS8lcMfHNEGOCbwQlGISLSitDrP1mz1ZpYs5R3FMf1SHFc7kx', '2019-06-21'),
       (30, 2, '7qGoMseayHMzMmSqDPhQkMZUNpQ33DLNWHI7zPsMcGqpi3h3iIuGGbG0jjPbmTBQ', '2019-06-24'),
       (31, 2, '8qIw6LlurozCnCmy4ZbXSpKMITbLU4VO2zR2dwauTLlFOBP9EvpG078g6BTLgbsh', '2019-06-24'),
       (32, 3, 'v2hRSqjaQhWvRplF7UWqoQDsAM3U7j0TNB0Vkg343PPP26BLftDGVNDPJr71wK15', '2019-06-25'),
       (33, 4, 'ezu0txog1NCiyt6ZcvEVC2xVJzIcdgsgq6VDsOCeHtRWlawglv4RTUQw6rFpL7kJ', '2019-06-27'),
       (34, 3, 'mdg2O8mfNlU3oUuLfdVBTlVMhn1QY4g2r9ZFAl5dsK45BIMq0YmCR9etW3HCLvwd', '2019-06-27'),
       (37, 3, 'DLhxfQsyS2jtvbqxeo8Hks4XTm5rlLEr8osP8nOdPEj9e2mBligHQdHO68jCZklc', '2019-06-28');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `finances`
--
ALTER TABLE `finances`
    ADD PRIMARY KEY (`id`),
    ADD UNIQUE KEY `finances_id_uindex` (`id`),
    ADD KEY `finance_user_id` (`user_id`);

--
-- Indexes for table `panels`
--
ALTER TABLE `panels`
    ADD PRIMARY KEY (`id`),
    ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `panels_tags`
--
ALTER TABLE `panels_tags`
    ADD PRIMARY KEY (`id`),
    ADD KEY `panels_tags_tag_id` (`tag_id`),
    ADD KEY `panel_tags_panel_id` (`panel_id`);

--
-- Indexes for table `properties`
--
ALTER TABLE `properties`
    ADD PRIMARY KEY (`id`),
    ADD UNIQUE KEY `widget_id` (`widget_id`, `property`);

--
-- Indexes for table `ratings`
--
ALTER TABLE `ratings`
    ADD PRIMARY KEY (`id`),
    ADD KEY `ratings_user_id` (`user_id`),
    ADD KEY `ratings_panel_id` (`panel_id`);

--
-- Indexes for table `sections_panels`
--
ALTER TABLE `sections_panels`
    ADD PRIMARY KEY (`id`, `panel_id`),
    ADD KEY `section_id` (`id`),
    ADD KEY `panel_id` (`panel_id`);

--
-- Indexes for table `tags`
--
ALTER TABLE `tags`
    ADD PRIMARY KEY (`id`);

--
-- Indexes for table `todos`
--
ALTER TABLE `todos`
    ADD PRIMARY KEY (`id`),
    ADD UNIQUE KEY `todos_id_uindex` (`id`),
    ADD KEY `todo_user_id` (`user_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
    ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users_properties`
--
ALTER TABLE `users_properties`
    ADD PRIMARY KEY (`id`),
    ADD KEY `user_rel` (`user_id`),
    ADD KEY `property_rel` (`property_id`);

--
-- Indexes for table `users_sections`
--
ALTER TABLE `users_sections`
    ADD PRIMARY KEY (`id`),
    ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `users_settings`
--
ALTER TABLE `users_settings`
    ADD PRIMARY KEY (`id`),
    ADD UNIQUE KEY `user_layout_id_uindex` (`id`),
    ADD UNIQUE KEY `user_layout_user_id_uindex` (`user_id`);

--
-- Indexes for table `users_tokens`
--
ALTER TABLE `users_tokens`
    ADD PRIMARY KEY (`id`),
    ADD KEY `users_tokens_user_id` (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `finances`
--
ALTER TABLE `finances`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `panels`
--
ALTER TABLE `panels`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,
    AUTO_INCREMENT = 7;
--
-- AUTO_INCREMENT for table `panels_tags`
--
ALTER TABLE `panels_tags`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,
    AUTO_INCREMENT = 5;
--
-- AUTO_INCREMENT for table `properties`
--
ALTER TABLE `properties`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,
    AUTO_INCREMENT = 2;
--
-- AUTO_INCREMENT for table `ratings`
--
ALTER TABLE `ratings`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,
    AUTO_INCREMENT = 8;
--
-- AUTO_INCREMENT for table `tags`
--
ALTER TABLE `tags`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,
    AUTO_INCREMENT = 3;
--
-- AUTO_INCREMENT for table `todos`
--
ALTER TABLE `todos`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,
    AUTO_INCREMENT = 882;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,
    AUTO_INCREMENT = 5;
--
-- AUTO_INCREMENT for table `users_properties`
--
ALTER TABLE `users_properties`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,
    AUTO_INCREMENT = 2;
--
-- AUTO_INCREMENT for table `users_sections`
--
ALTER TABLE `users_sections`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,
    AUTO_INCREMENT = 32;
--
-- AUTO_INCREMENT for table `users_settings`
--
ALTER TABLE `users_settings`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,
    AUTO_INCREMENT = 4;
--
-- AUTO_INCREMENT for table `users_tokens`
--
ALTER TABLE `users_tokens`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,
    AUTO_INCREMENT = 38;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `panels`
--
ALTER TABLE `panels`
    ADD CONSTRAINT `users_rel` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `panels_tags`
--
ALTER TABLE `panels_tags`
    ADD CONSTRAINT `panel_tags_panel_id` FOREIGN KEY (`panel_id`) REFERENCES `panels` (`id`),
    ADD CONSTRAINT `panels_tags_tag_id` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`);

--
-- Constraints for table `properties`
--
ALTER TABLE `properties`
    ADD CONSTRAINT `widget_rel` FOREIGN KEY (`widget_id`) REFERENCES `panels` (`id`);

--
-- Constraints for table `ratings`
--
ALTER TABLE `ratings`
    ADD CONSTRAINT `ratings_panel_id` FOREIGN KEY (`panel_id`) REFERENCES `panels` (`id`),
    ADD CONSTRAINT `ratings_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `sections_panels`
--
ALTER TABLE `sections_panels`
    ADD CONSTRAINT `section_panels_panel_id` FOREIGN KEY (`panel_id`) REFERENCES `panels` (`id`),
    ADD CONSTRAINT `section_panels_section_id` FOREIGN KEY (`id`) REFERENCES `users_sections` (`id`);

--
-- Constraints for table `todos`
--
ALTER TABLE `todos`
    ADD CONSTRAINT `todo_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `users_properties`
--
ALTER TABLE `users_properties`
    ADD CONSTRAINT `property_rel` FOREIGN KEY (`property_id`) REFERENCES `properties` (`id`),
    ADD CONSTRAINT `user_rel` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `users_sections`
--
ALTER TABLE `users_sections`
    ADD CONSTRAINT `users_sections_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `users_settings`
--
ALTER TABLE `users_settings`
    ADD CONSTRAINT `users_settings_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `users_tokens`
--
ALTER TABLE `users_tokens`
    ADD CONSTRAINT `users_tokens_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
