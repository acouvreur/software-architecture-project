#!/bin/sh

echo "Connecting to announcement :"
curl http://localhost:8080

echo ""

echo "Connecting to accounting :"
curl http://localhost:8181

echo ""

echo "Connecting to billing :"
curl http://localhost:8182

echo ""

echo "Connecting to course :"
curl http://localhost:8183

echo ""

echo "Connecting to matching :"
curl http://localhost:8184

echo ""

echo "Connecting to tracking :"
curl http://localhost:8185

echo ""