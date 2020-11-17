### Find CloudFormation stack
sqsTemplate.txt

### stack creation
aws cloudformation create-stack --stack-name messagingstack --template-body file://messagingTemplate.txt

### stack deletion
aws cloudformation delete-stack --stack-name messagingstack

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
    "message": "Welcome to spring messaging tutorial"
}'