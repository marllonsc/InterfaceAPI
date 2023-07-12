# Run on first time only -------------------------------------------------------------------------
echo "Service Starting"
echo ""
kubectl delete service interface-api
echo ""

kubectl expose pod interface-api --type=NodePort --port=8080 --target-port=8080 --name=interface-api
echo ""

minikube service interface-api --url
echo ""
echo ""

# Check the exit code of the deployment command
if [ $? -eq 0 ]; then
    echo "Service Create successful!"
else
    echo "Service Create failed!"
fi