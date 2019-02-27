#!/bin/sh

./manifest-tool-linux-amd64 --insecure push from-spec ./services/accounting/manifest-blablamove-multi-platform.yaml 
./manifest-tool-linux-amd64 --insecure push from-spec ./services/announcement/manifest-blablamove-multi-platform.yaml 
./manifest-tool-linux-amd64 --insecure push from-spec ./services/billing/manifest-blablamove-multi-platform.yaml 
./manifest-tool-linux-amd64 --insecure push from-spec ./services/matching/manifest-blablamove-multi-platform.yaml 
./manifest-tool-linux-amd64 --insecure push from-spec ./services/tracking/manifest-blablamove-multi-platform.yaml