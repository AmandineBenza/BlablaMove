cd ../
curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"event\":\"launch-database\"}" "localhost:8080/BBM/ADMIN"
docker-compose start $*