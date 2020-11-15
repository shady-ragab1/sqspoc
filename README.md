### Find CloudFormation stack
sqsTemplate.txt

### stack creation
aws cloudformation create-stack --stack-name mysqsstack --template-body file://sqsTemplate.txt

### stack deletion
aws cloudformation delete-stack --stack-name mysqsstack

### add your AWS CLI credentials in application-local.yml
cloud:
  aws:
    credentials:
      access-key: ACCESS_KEY
      secret-key: SECRET_KEY
      
### sample request
curl --location --request POST 'http://localhost:10091/sqs/send' \
--header 'Content-Type: application/json' \
--data-raw '{
    "id": 102,
    "message": "Welcome to spring sqs tutorial"
}'