if [ ! -f $BUILD_DIR/Procfile ]; then
cat <<EOF
default_process_types:
    web: `java $JAVA_OPTS -jar ./build/libs/dropwizard-rest-api-1.0-SNAPSHOT-fat.jar --port 8080 server heroku-config.yml`
EOF
fi