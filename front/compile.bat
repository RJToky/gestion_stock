jar cvf ./production/front_stock.jar -C ./bin .
jar cvf ./web/WEB-INF/lib/front_stock.jar -C ./bin .
jar cvf ./production/front_stock.war -C ./web .
jar cvf C:\wildfly-29.0.1.Final\standalone\deployments\front_stock.ear -C ./production .
