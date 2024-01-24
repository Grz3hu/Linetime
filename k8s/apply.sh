#!/usr/bin/env bash
kubectl apply -f linetime-namespace.yaml

kubectl apply -f backend/backend-deployment.yaml
kubectl apply -f backend/backend-service.yaml
kubectl apply -f backend/backend-ingress.yaml
kubectl apply -f backend/backend-autoscaler.yaml

kubectl apply -f db/postgres-config.yaml
kubectl apply -f db/postgres-secret.yaml
kubectl apply -f db/postgres-init-script.yaml
kubectl apply -f db/postgres-kubegres.yaml

kubectl apply -f frontend/frontend-deployment.yaml
kubectl apply -f frontend/frontend-service.yaml
kubectl apply -f frontend/frontend-ingress.yaml
kubectl apply -f frontend/frontend-autoscaler.yaml
