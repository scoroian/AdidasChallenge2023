@echo off

cd adiclub-service
call mvn clean install
cd ..

cd email-service
call mvn clean install
cd ..

cd priority-sale-service
call mvn clean install
cd ..

cd public-service
call mvn clean install
cd ..

docker-compose up
