
#PROJECT=vcpf-api
#PROJECT=scraping-process
PROJECT=morphemeanalysis-process
#PROJECT=terminator

cd ./${PROJECT}/
./mvnw clean package -DskipTests=true
cd ../
