#!/bin/sh

# Lucas id
lucas=1
echo "Lucas creates an announcement for his bike to be transported from Sophia to Paris"
curl -s -d '{"idTransmitter":1, "nameTransmitter":"Lucas", "startPoint":"Sophia", "endPoint":"Paris","startDate":"2018-10-12", "endDate":"2018-12-24", "type" :"GOOD"}' -H "Content-Type: application/json" -X POST http://localhost:8080/announcements > annonce_lucas.json

lucas_announcement=$(cat annonce_lucas.json | jq -r '.id')

# Hope id
hope=2
echo "Hope creates an announcement stating she travels from Nice to Paris on the 2018-10-12."
curl -s -d '{"idTransmitter":1, "nameTransmitter":"Lucas", "startPoint":"Sophia", "endPoint":"Paris","startDate":"2018-10-12", "endDate":"2018-12-24", "type" :"COURSE"}' -H "Content-Type: application/json" -X POST http://localhost:8080/announcements > annonce_hope.json

echo "The matching service founds a match with Lucas and Hope."
echo "The good is assigned to Hope for delivery through a course"

# Irrelevant ? Obviously he know she received it, but maybe it is need on the app
echo "Hope notifies that she received the good"


#curl -s -d '{"idGoodAnnouncement":2,"idDriverAnnouncement":2,"state":"STARTED"}' -H "Content-Type: application/json" -X POST http://localhost:8085/tracking > tracking_lucas.json

curl -s -d RECEIVED -H "Content-Type: application/json" -X PATCH http://localhost:8085/tracking/2

echo "Lucas check the status of his good" # Lucas poll the results
curl -s http://localhost:8085/tracking/2 > annonce_lucas_etat.json
cat annonce_lucas_etat.json

echo "Hope notifies that she starts the course"
curl -s -d STARTED -H "Content-Type: application/json" -X PATCH http://localhost:8085/tracking/2

echo "Lucas check the status of his good" # Lucas poll the results
curl -s http://localhost:8085/tracking/2 > annonce_lucas_etat.json
cat annonce_lucas_etat.json

echo "Hope notifies that she has arrived in Paris and can deliver the good"
curl -s -d DELIVERING -H "Content-Type: application/json" -X PATCH http://localhost:8085/tracking/2

echo "Charles check the status of the good to receive" # Lucas poll the results
curl -s http://localhost:8085/tracking/2 > annonce_lucas_etat.json
cat annonce_lucas_etat.json

echo "Hope notifies that she delivered the good"
curl -s -d DELIVERED -H "Content-Type: application/json" -X PATCH http://localhost:8085/tracking/2

echo "Charles confirms that he received the good"
curl -s -d CONFIRMED -H "Content-Type: application/json" -X PATCH http://localhost:8085/tracking/2

echo "Hope check that he has been credited"
curl http://localhost:8082/tracking/2

