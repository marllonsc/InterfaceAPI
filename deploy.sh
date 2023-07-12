echo "Deployment Start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.!"
echo ""

mvn clean install
echo ""

eval $(minikube docker-env)
echo ""

minikube docker-env | Invoke-Expression
echo ""

docker build -t interfaceapi:0.0.1 .
echo ""

kubectl delete pod interface-api

kubectl run interface-api --image=interfaceapi:0.0.1 --image-pull-policy=Never

minikube service interface-api --url

echo ""
echo ""
echo ""

# Check the exit code of the deployment command
if [ $? -eq 0 ]; then
    echo "Deployment successful!"
else
    echo "Deployment failed!"
fi

echo "Deployment End >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.!"