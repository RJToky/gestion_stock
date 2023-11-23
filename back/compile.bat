jar cvf ./production/back_stock.jar -C ./bin .
jar cvf ./production/back_stock.war -C ./web .
jar cvf C:\wildfly-29.0.1.Final\standalone\deployments\back_stock.ear -C ./production .
