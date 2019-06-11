cd ../de.dashup.application
mvn spring-boot:run -Dspring-boot.run.arguments=--database=prod &

echo 'Started dashup application successfully!'