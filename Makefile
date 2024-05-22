
# Makefile
.PHONY: build

clean:
	echo "Cleaning up..."
	./gradlew clean
	docker compose down --volumes --remove-orphans

test:
	echo "Running tests..."
	./gradlew check

build:
	echo "Building the project..."
	./gradlew build -x test

docker:
	echo "Building the docker image..."
	docker compose up -d --build --force-recreate --remove-orphans

integrationTest:
	echo "Running integration tests..."
	postman collection run integration-test/api-cloud-bank.postman_collection.json -e integration-test/api-cloud-bank-docker.postman_environment.json

deploy: clean test build docker
