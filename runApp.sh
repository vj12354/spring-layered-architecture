#!/bin/sh
#
# Just run this file
# To build, test, run the App
#

cd shopping

./gradlew clean build

./gradlew test

echo "Test completed, restuls can be found at: "
echo "`pwd`/build/reports/tests/test/index.html"
echo ""

echo 'Starting Shopping App...'
./gradlew bootRun &> runApp.log &

echo "PID: $!, check ./shopping/runApp.log for status..."
echo ""
echo "*** Make sure WIREMOCK is started, at http://localhost:8081/ ***"
echo ""

echo "To access the App visit: http://localhost:8082/orders"
echo 'And click "Place an Order" link on the page.'
echo ""
