#!/bin/sh

# Lucas id
lucas=1
echo "Lucas creates an announcement for his bike to be transported from Sophia to Paris"
curl -s -d '{"idTransmitter":1, "nameTransmitter":"Lucas", "startPoint":"Sophia", "endPoint":"Paris","startDate":"2018-10-12", "endDate":"2018-12-24"}' -H "Content-Type: application/json" -X POST http://localhost:8080/announcements > annonce_lucas.json

lucas_announcement=$(cat annonce_lucas.json | jq -r '.id')

# Hope id
hope=2
echo "Hope creates an announcement stating she travels from Nice to Paris on the 2018-10-12."
curl -s -d '{"idTransmitter":1, "nameTransmitter":"Lucas", "startPoint":"Sophia", "endPoint":"Paris","startDate":"2018-10-12", "endDate":"2018-12-24"}' -H "Content-Type: application/json" -X POST http://localhost:8080/announcements > annonce_hope.json

echo "The matching service founds a match with Lucas and Hope."
echo "The good is assigned to Hope for delivery through a course"

# Irrelevant ? Obviously he know she received it, but maybe it is need on the app
echo "Hope notifies that she received the good"
curl -s -d '{"status": "RECEIVED"}' -H "Content-Type: application/json" -X PATCH http://localhost:8085/tracking/${lucas_announcement}

echo "Lucas check the status of his good" # Lucas poll the results
curl -s http://localhost:8085/tracking/${lucas_announcement} > annonce_lucas_etat.json
cat annonce_lucas_etat.json

echo "Hope notifies that she starts the course"
curl -s -d '{"status": "STARTED"}' -H "Content-Type: application/json" -X PATCH http://localhost:8085/tracking/${lucas_announcement}

echo "Lucas check the status of his good" # Lucas poll the results
curl -s http://localhost:8085/tracking/${lucas_announcement} > annonce_lucas_etat.json
cat annonce_lucas_etat.json

echo "Hope notifies that she has arrived in Paris and can deliver the good"
curl -s -d '{"status": "DELIVERING"}' -H "Content-Type: application/json" -X PATCH http://localhost:8085/tracking/${lucas_announcement}

echo "Charles check the status of the good to receive" # Lucas poll the results
curl -s http://localhost:8085/tracking/${lucas_announcement} > annonce_lucas_etat.json
cat annonce_lucas_etat.json

echo "Hope notifies that she delivered the good"
curl -s -d '{"status": "DELIVERED"}' -H "Content-Type: application/json" -X PATCH http://localhost:8085/tracking/${lucas_announcement}

echo "Charles confirms that he received the good"
curl -s -d '{"status": "CONFIRMED"}' -H "Content-Type: application/json" -X PATCH http://localhost:8085/tracking/${lucas_announcement}

echo "Hope check that he has been credited"
curl http://localhost:8082/tracking/${hope}