#!/usr/bin/env bash

for i in $(seq 1 2 20)
do
	for j in $(seq $i)
	do
		python test.py &
	done

	current_time=$(date "+%T")
	backend_replicas=$(kubectl get hpa -n linetime linetime-backend-hpa -o=jsonpath='{.status.currentReplicas}')
	frontend_replicas=$(kubectl get hpa -n linetime linetime-frontend-hpa -o=jsonpath='{.status.currentReplicas}')
	echo "$current_time,$i,$backend_replicas,$frontend_replicas" >> data.csv

	wait < <(jobs -p)
done
