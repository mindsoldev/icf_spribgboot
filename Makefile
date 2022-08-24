APP = icf-test
#GCP_PROJECT_ID = 	--> környezeti változóból jön egyébként potent-plasma-359706 
# $env:GCP_PROJECT_ID = 'potent-plasma-359706'
#ENVIRONMENT =		--> környezeti változóból jön egyébként pl.: staging
# $env:ENVIRONMENT = 'staging'

set-dev-environment:
	set GCP_PROJECT_ID = 'potent-plasma-359706'
	set ENVIRONMENT = 'dev'
	
set-staging-environment:
	set GCP_PROJECT_ID = 'potent-plasma-359706'
	set ENVIRONMENT = 'staging'
	
set-prod-environment:
	set GCP_PROJECT_ID = 'potent-plasma-359706'
	set ENVIRONMENT = 'prod'
	
test-environment:
	@echo GCP_PROJECT_ID = %GCP_PROJECT_ID%
	@echo ENVIRONMENT = %ENVIRONMENT%

# GCloud-specific targets
gcloud-docker-init:
	gcloud auth configure-docker

gcloud-docker-build:
	docker build -t gcr.io/$(GCP_PROJECT_ID)/$(APP):$(ENVIRONMENT) .
	
gcloud-docker-push:
	docker push gcr.io/$(GCP_PROJECT_ID)/$(APP):$(ENVIRONMENT)
	
gcloud-run-deploy:
	gcloud run deploy $(APP)-$(ENVIRONMENT) \
	--region europe-central2 \
	--image gcr.io/$(GCP_PROJECT_ID)/$(APP):$(ENVIRONMENT) \
	--port 1118 \
	--project $(GCP_PROJECT_ID) \
	--max-instances 1 \
	--platform managed \
	--labels environment=$(ENVIRONMENT) \
	--allow-unauthenticated
	