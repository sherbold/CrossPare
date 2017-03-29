
# Create table for results
CREATE TABLE `results` (
  `idresults` int(11) NOT NULL AUTO_INCREMENT,
  `configurationName` varchar(45) NOT NULL,
  `productName` varchar(45) NOT NULL,
  `classifier` varchar(45) NOT NULL,
  `testsize` int(11) DEFAULT NULL,
  `trainsize` int(11) DEFAULT NULL,
  `error` double DEFAULT NULL,
  `recall` double DEFAULT NULL,
  `precision` double DEFAULT NULL,
  `fscore` double DEFAULT NULL,
  `gscore` double DEFAULT NULL,
  `mcc` double DEFAULT NULL,
  `auc` double DEFAULT NULL,
  `aucec` double DEFAULT NULL,
  `tpr` double DEFAULT NULL,
  `tnr` double DEFAULT NULL,
  `fpr` double DEFAULT NULL,
  `fnr` double DEFAULT NULL,
  `tp` double DEFAULT NULL,
  `fn` double DEFAULT NULL,
  `tn` double DEFAULT NULL,
  `fp` double DEFAULT NULL,
  PRIMARY KEY (`idresults`)
) ENGINE=InnoDB AUTO_INCREMENT=77777 DEFAULT CHARSET=utf8;

# Create results view
CREATE VIEW `resultsView` AS select `results`.`configurationName` AS `configurationName`,`results`.`productName` AS `productName`,`results`.`classifier` AS `classifier`,concat(substr(`results`.`configurationName`,(locate('-',`results`.`configurationName`) + 1)),'-',`results`.`classifier`) AS `config`,count(0) AS `repetitions`,avg(`results`.`testsize`) AS `testsize`,avg(`results`.`trainsize`) AS `trainsize`,avg(`results`.`error`) AS `error`,avg(`results`.`recall`) AS `recall`,avg(`results`.`precision`) AS `precision`,avg(`results`.`fscore`) AS `fscore`,avg(`results`.`gscore`) AS `gscore`,avg(`results`.`mcc`) AS `mcc`,avg(`results`.`auc`) AS `auc`,avg(`results`.`aucec`) AS `aucec`,avg(`results`.`tpr`) AS `tpr`,avg(`results`.`tnr`) AS `tnr`,avg(`results`.`fpr`) AS `fpr`,avg(`results`.`fnr`) AS `fnr`,avg(`results`.`tp`) AS `tp`,avg(`results`.`fn`) AS `fn`,avg(`results`.`tn`) AS `tn`,avg(`results`.`fp`) AS `fp` from `results` group by `results`.`configurationName`,`results`.`productName`,`results`.`classifier`;
